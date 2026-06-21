package client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Message;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AnthropicClient {

    private static final String MODEL = "gemini-2.0-flash-lite";
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final String apiKey;

    public AnthropicClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public String chat(String systemPrompt, String userMessage) throws Exception {
        return chat(systemPrompt, List.of(new Message("user", userMessage)));
    }

    public String chat(String systemPrompt, List<Message> messages) throws Exception {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                + MODEL + ":generateContent?key=" + apiKey;

        // Build request body
        ObjectNode body = mapper.createObjectNode();

        // System instruction
        ObjectNode systemNode = body.putObject("system_instruction");
        ArrayNode systemParts = systemNode.putArray("parts");
        systemParts.addObject().put("text", systemPrompt);

        // Messages
        ArrayNode contents = body.putArray("contents");
        for (Message m : messages) {
            ObjectNode content = contents.addObject();
            content.put("role", m.getRole().equals("assistant") ? "model" : "user");
            ArrayNode parts = content.putArray("parts");
            parts.addObject().put("text", m.getContent());
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Gemini API error " + response.statusCode()
                    + ": " + response.body());
        }

        JsonNode json = mapper.readTree(response.body());
        return json.get("candidates").get(0)
                .get("content").get("parts").get(0)
                .get("text").asText();
    }
}
package agents;

import client.AnthropicClient;
import model.AgentResponse;

public class SentimentAgent {

    private static final String SYSTEM_PROMPT = """
            You are a customer sentiment analysis specialist.
            Analyze the customer's message and return ONLY a JSON object like:
            {
              "urgency": "HIGH",
              "emotion": "frustrated",
              "reason": "Customer is angry about billing error",
              "requiresEscalation": true
            }
            urgency must be exactly LOW, MEDIUM, or HIGH.
            requiresEscalation should be true for HIGH urgency only.
            """;

    private final AnthropicClient client;

    public SentimentAgent(AnthropicClient client) {
        this.client = client;
    }

    public AgentResponse analyze(String customerMessage) {
        AgentResponse result = new AgentResponse("SentimentAgent", "");
        try {
            String raw  = client.chat(SYSTEM_PROMPT, customerMessage);
            String json = raw.replaceAll("```json|```", "").trim();

            String urgency = extractField(json, "urgency");
            String reason  = extractField(json, "reason");
            boolean escalate = json.contains("\"requiresEscalation\": true")
                    || json.contains("\"requiresEscalation\":true");

            result.setSentiment(urgency != null ? urgency.toUpperCase() : "MEDIUM");
            result.setRequiresEscalation(escalate);
            result.setResponse(reason != null ? reason : raw);

        } catch (Exception e) {
            result.setResponse("Sentiment analysis failed: " + e.getMessage());
        }
        return result;
    }

    private String extractField(String json, String field) {
        String key = "\"" + field + "\"";
        int idx = json.indexOf(key);
        if (idx == -1) return null;
        int colon = json.indexOf(":", idx);
        int start = json.indexOf("\"", colon);
        if (start == -1) return null;
        int end = json.indexOf("\"", start + 1);
        if (end == -1) return null;
        return json.substring(start + 1, end);
    }
}
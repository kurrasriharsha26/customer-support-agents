package model;

public class AgentResponse {
    private String agentName;
    private String response;
    private String sentiment;
    private boolean requiresEscalation;

    public AgentResponse(String agentName, String response) {
        this.agentName = agentName;
        this.response = response;
        this.requiresEscalation = false;
        this.sentiment = "MEDIUM";
    }

    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }
    public boolean isRequiresEscalation() { return requiresEscalation; }
    public void setRequiresEscalation(boolean r) { this.requiresEscalation = r; }
}
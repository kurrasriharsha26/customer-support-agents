package agents;

import client.AnthropicClient;
import model.AgentResponse;

public class EscalationAgent {

    private static final String SYSTEM_PROMPT = """
            You are a senior customer support specialist handling escalated cases.
            1. Acknowledge the customer's concern empathetically.
            2. Apologize sincerely where appropriate.
            3. Explain that a human specialist will follow up within 24 hours.
            4. Provide a case reference (format: CASE-<random 5 digit number>).
            5. Offer any immediate self-service steps if possible.
            Keep the tone warm, professional, and solution-focused.
            """;

    private final AnthropicClient client;

    public EscalationAgent(AnthropicClient client) {
        this.client = client;
    }

    public AgentResponse escalate(String customerMessage, String reason) {
        AgentResponse result = new AgentResponse("EscalationAgent", "");
        try {
            String prompt = "Customer message: " + customerMessage +
                    "\nReason for escalation: " + reason +
                    "\nPlease draft an empathetic escalation response.";
            result.setResponse(client.chat(SYSTEM_PROMPT, prompt));
            result.setRequiresEscalation(true);
        } catch (Exception e) {
            result.setResponse("Escalation failed: " + e.getMessage());
        }
        return result;
    }
}
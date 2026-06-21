package agents;

import client.AnthropicClient;
import model.AgentResponse;

public class FaqAgent {

    private static final String KNOWLEDGE_BASE = """
            RETURN POLICY:
            - Items can be returned within 30 days of purchase with receipt.
            - Electronics must be unopened for a full refund; opened electronics get store credit only.
            - Sale items are final sale and cannot be returned.

            SHIPPING:
            - Standard shipping takes 5-7 business days and is free on orders over $50.
            - Express shipping (2 business days) costs $12.99.

            ORDER TRACKING:
            - Tracking numbers are emailed within 24 hours of dispatch.
            - Visit our website /track-order page and enter your order number.

            PAYMENT:
            - We accept Visa, MasterCard, Amex, PayPal, and Apple Pay.

            WARRANTY:
            - All products carry a 1-year manufacturer warranty.
            """;

    private static final String SYSTEM_PROMPT =
            "You are a helpful customer support FAQ specialist.\n" +
                    "Use ONLY the knowledge base below to answer questions.\n" +
                    "If the answer is NOT in the knowledge base, respond with exactly:\n" +
                    "ESCALATE: <brief reason>\n\n" +
                    "KNOWLEDGE BASE:\n" + KNOWLEDGE_BASE;

    private final AnthropicClient client;

    public FaqAgent(AnthropicClient client) {
        this.client = client;
    }

    public AgentResponse answer(String customerMessage) {
        AgentResponse result = new AgentResponse("FaqAgent", "");
        try {
            String response = client.chat(SYSTEM_PROMPT, customerMessage);
            if (response.startsWith("ESCALATE:")) {
                result.setRequiresEscalation(true);
                result.setResponse(response.substring("ESCALATE:".length()).trim());
            } else {
                result.setResponse(response);
            }
        } catch (Exception e) {
            result.setResponse("FAQ lookup failed: " + e.getMessage());
            result.setRequiresEscalation(true);
        }
        return result;
    }
}
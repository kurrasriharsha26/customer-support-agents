package agents;

import client.AnthropicClient;
import model.AgentResponse;

public class OrchestratorAgent {

    private final SentimentAgent  sentimentAgent;
    private final FaqAgent        faqAgent;
    private final EscalationAgent escalationAgent;

    public OrchestratorAgent(AnthropicClient client) {
        this.sentimentAgent  = new SentimentAgent(client);
        this.faqAgent        = new FaqAgent(client);
        this.escalationAgent = new EscalationAgent(client);
    }

    public AgentResponse handle(String customerMessage) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Processing: " + customerMessage);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Step 1: Sentiment
        System.out.println("[1] SentimentAgent analyzing...");
        AgentResponse sentiment = sentimentAgent.analyze(customerMessage);
        System.out.println("    Urgency: " + sentiment.getSentiment());

        // Step 2a: High urgency → escalate directly
        if ("HIGH".equals(sentiment.getSentiment()) || sentiment.isRequiresEscalation()) {
            System.out.println("[2] HIGH urgency → EscalationAgent");
            AgentResponse result = escalationAgent.escalate(customerMessage, sentiment.getResponse());
            result.setSentiment("HIGH");
            print(result);
            return result;
        }

        // Step 2b: Try FAQ
        System.out.println("[2] Routing to FaqAgent...");
        AgentResponse faq = faqAgent.answer(customerMessage);

        if (!faq.isRequiresEscalation()) {
            faq.setSentiment(sentiment.getSentiment());
            print(faq);
            return faq;
        }

        // Step 3: FAQ failed → escalate
        System.out.println("[3] FAQ could not answer → EscalationAgent");
        AgentResponse result = escalationAgent.escalate(customerMessage, faq.getResponse());
        result.setSentiment(sentiment.getSentiment());
        print(result);
        return result;
    }

    private void print(AgentResponse r) {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Agent    : " + r.getAgentName());
        System.out.println("Sentiment: " + r.getSentiment());
        System.out.println("Escalated: " + r.isRequiresEscalation());
        System.out.println("Response :\n" + r.getResponse());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }
}
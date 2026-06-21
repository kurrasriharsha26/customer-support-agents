import agents.OrchestratorAgent;
import client.AnthropicClient;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("ERROR: Set GEMINI_API_KEY environment variable.");
            System.exit(1);
        }

        AnthropicClient   client       = new AnthropicClient(apiKey);
        OrchestratorAgent orchestrator = new OrchestratorAgent(client);
        Scanner           scanner      = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("   Customer Support AI - Multi Agent System  ");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("Type your question and press Enter.");
        System.out.println("Type 'exit' to quit.\n");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye! Have a great day.");
                break;
            }

            if (input.isEmpty()) {
                System.out.println("Please type a message.\n");
                continue;
            }

            orchestrator.handle(input);
        }

        scanner.close();
    }
}
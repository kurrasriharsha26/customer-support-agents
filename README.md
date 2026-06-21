# 🤖 Customer Support Multi-Agent AI System

A Multi-Agent AI System for customer support built with **Java** and **Google Gemini API**.
Multiple specialized AI agents collaborate to handle customer queries intelligently — just like a real support team.

---

## 📌 Project Overview

This project demonstrates **Agentic AI** — where multiple AI agents work together autonomously to:
- Understand customer emotions and urgency
- Answer common questions from a knowledge base
- Escalate complex issues with empathetic responses
- Route queries to the right agent automatically

---

## 🏗️ Architecture
Customer Message

│

▼

┌─────────────────────┐

│  OrchestratorAgent  │  ← The Brain (routes all traffic)

└────────┬────────────┘

│

├──[Step 1]──► SentimentAgent   (Urgency: LOW / MEDIUM / HIGH)

│

├──[Step 2a]─► EscalationAgent  (if HIGH urgency)

│

├──[Step 2b]─► FaqAgent         (if LOW / MEDIUM)

│                   │

└──[Step 3]─────────► EscalationAgent (if FAQ can't answer)

---

## 🤖 Agents

| Agent | Role |
|---|---|
| **OrchestratorAgent** | Coordinates all agents and routes customer queries |
| **SentimentAgent** | Detects emotion and urgency (LOW / MEDIUM / HIGH) |
| **FaqAgent** | Answers common questions from a knowledge base |
| **EscalationAgent** | Handles complex issues with empathetic responses |

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 24 | Core programming language |
| Google Gemini API | AI engine powering all agents |
| Jackson | JSON parsing |
| HttpClient | REST API calls |
| Maven | Dependency management |
| IntelliJ IDEA | IDE |

---

## 📁 Project Structure
customer-support-agents/

├── src/main/java/

│   ├── Main.java                     ← Entry point (interactive chat)

│   ├── agents/

│   │   ├── OrchestratorAgent.java    ← Routes queries between agents

│   │   ├── SentimentAgent.java       ← Detects urgency & emotion

│   │   ├── FaqAgent.java             ← Answers from knowledge base

│   │   └── EscalationAgent.java      ← Handles complex issues

│   ├── client/

│   │   └── AnthropicClient.java      ← Gemini API HTTP client

│   └── model/

│       ├── Message.java              ← Chat message model

│       └── AgentResponse.java        ← Agent result model

├── pom.xml                           ← Maven dependencies

└── README.md

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 24+
- IntelliJ IDEA (Community Edition)
- Google Gemini API key (free at https://aistudio.google.com)

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/YourUsername/customer-support-agents.git
cd customer-support-agents
```

**2. Get a free Gemini API key**
- Go to https://aistudio.google.com
- Click Get API Key → Create API Key
- Copy the key

**3. Set the API key in IntelliJ**
- Click top dropdown → Edit Configurations
- Environment Variables → Add:
  - Name: `GEMINI_API_KEY`
  - Value: `your-api-key-here`

**4. Load Maven dependencies**
- Open `pom.xml`
- Click "Load Maven Changes" when prompted

**5. Run the project**
- Open `Main.java`
- Click the ▶ Run button

---

## 💬 Example Interaction
╔══════════════════════════════════════════╗

Customer Support AI - Multi Agent System

╚══════════════════════════════════════════╝

Type your question and press Enter.

Type 'exit' to quit.
You: What is your return policy for electronics?
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

[1] SentimentAgent analyzing...

Urgency: LOW

[2] Routing to FaqAgent...

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Agent    : FaqAgent

Sentiment: LOW

Escalated: false

Response :

Electronics must be unopened for a full refund.

Opened electronics are eligible for store credit only.

Returns must be within 30 days with receipt.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
You: You charged my card TWICE! Fix this NOW!
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

[1] SentimentAgent analyzing...

Urgency: HIGH

[2] HIGH urgency → EscalationAgent

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Agent    : EscalationAgent

Sentiment: HIGH

Escalated: true

Response :

Dear Customer, I sincerely apologize for the

double charge. This is our top priority.

Case reference: CASE-91847

A senior specialist will contact you within 24 hours.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

---

## 🔄 Agent Routing Logic

| Customer Message | Urgency | Agent Used |
|---|---|---|
| "What is return policy?" | LOW | FaqAgent |
| "How long is shipping?" | LOW | FaqAgent |
| "My order hasn't arrived" | MEDIUM | EscalationAgent |
| "You charged me twice!!" | HIGH | EscalationAgent (direct) |

---

## 🚀 Future Enhancements

- [ ] Web UI using Spring Boot
- [ ] Database to store customer cases
- [ ] Email Agent to auto-send responses
- [ ] OrderTrackingAgent for live order status
- [ ] Memory to remember past conversations
- [ ] Multi-language support

---

## 👨‍💻 Author

**Kurra Sriharsha**
- GitHub: [@kurrasriharsha26](https://github.com/kurrasriharsha26/customer-support-agents)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

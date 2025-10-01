# MCP Server Demo

This Spring Boot application is a simple MCP (Model Context Protocol) server that exposes custom tools related to an on-call support schedule. It can be run in two different transport modes: STDIO and HTTP.

## Overview

This server exposes two tools for an AI model to use, which are automatically discovered by Spring AI:

*   `getCurrentDate`: A method annotated with `@McpTool` that returns the current date.
*   `getOnCallSupportByDate`: Another `@McpTool` method that returns the on-call engineers for a given date.

---

## Part 1: Running in STDIO Mode

In this mode, the server communicates over standard input/output. This is useful for local development and for being launched directly by another process, like the Model Inspector.

### 1. Configure for STDIO

Open the `src/main/resources/application.properties` file and ensure the `STDIO` configuration is active:

```properties
# For STDIO transport
spring.main.web-application-type=none
spring.ai.mcp.server.stdio=true

# Disable banner and logging for cleaner STDIO communication
spring.main.banner-mode=off
logging.level.root=OFF
```

### 2. Build the Application

Package the application to create an executable JAR file.

```bash
# From the mcp-server-demo directory
./mvnw clean package
```

### 3. Connect with Model Inspector

You can now use a tool like the [Model Inspector](https://github.com/modelcontextprotocol/model-inspector) to connect to your server. In the Model Inspector, configure a new client with the following command, adjusting the path to your JAR file:

```
java -jar path/to/mcp-server-demo-0.0.1-SNAPSHOT.jar
```

You will be able to see the available tools (`getCurrentDate`, `getOnCallSupportByDate`) and interact with them.

---

## Part 2: Running in HTTP Mode

In this mode, the server runs as a standalone web service, exposing its MCP endpoint over HTTP. This allows clients, like our `mcp-client-demo`, to connect to it over the network.

### 1. Configure for HTTP

Open `src/main/resources/application.properties` and switch to the HTTP configuration by commenting out the STDIO lines and uncommenting the HTTP lines. This involves enabling the web server and setting the protocol to `STREAMABLE`, which is the new default for HTTP transports.

```properties
# Set the port for the HTTP server
server.port=8081
# Set the protocol to the modern Streamable HTTP transport
spring.ai.mcp.server.protocol=STREAMABLE

# Comment out the STDIO transport lines
# For STDIO transport
# spring.main.web-application-type=none
# spring.ai.mcp.server.stdio=true

# Disable banner and logging for cleaner STDIO communication
# spring.main.banner-mode=off
# logging.level.root=OFF

```

### 2. Run the Application

Run the application as a standard Spring Boot application.

```bash
# From the mcp-server-demo directory
./mvnw spring-boot:run
```

The server will start on port `8081`. Now you can proceed to the second part of the `mcp-client-demo` to connect to this server over HTTP.

---

## Part 3: MCP Prompts and Resources

Beyond tools, this server also exposes richer context to AI models using **Prompts** and **Resources**.

*   **Resources**: These are documents or data that an AI can use for context. This server provides an official on-call policy document.
*   **Prompts**: These are pre-defined prompt templates that can be used to guide an AI toward a specific task. This server includes a prompt for generating a daily GitHub summary.

### 1. Build the Executable JAR

First, ensure you have built the server in STDIO mode so you have an executable JAR file.

```bash
# From the mcp-server-demo directory
./mvnw clean package
```

### 2. Configure Claude Desktop

To add the server to Claude Desktop, you need to edit its configuration file.

1.  Open Claude Desktop's settings.
2.  Navigate to the **Developer** section.
3.  Click on **Edit Config**. This will open the `claude_desktop_config.json` file in your default editor.

Now, add the following `mcpServers` entry to the JSON file. Be sure to replace `/path/to/your/` with the actual, absolute path to the JAR file you just built.

```json
{
  "mcpServers": {
    "on-call": {
      "command": "java",
      "args": ["-jar", "/path/to/your/mcp-server-demo-0.0.1-SNAPSHOT.jar"]
    }
  }
}
```

Save the file and restart Claude Desktop for the changes to take effect.

### 3. Interact with your Server in Claude

After restarting, your `on-call` server will be active. Here’s how to use it in the Claude UI:

*   **View Connected Servers and Tools**: Click the **server icon** (which looks like a plug) in the chat input area. You will see your `on-call` server listed, and you can expand it to see the tools it provides (`getCurrentDate`, `getOnCallSupportByDate`).

*   **Use Prompts and Resources**: Click the **plus icon (`+`)** in the chat input area. You will see a list of available context items, including:
    *   `On-Call Escalation Policy` (a Resource)
    *   `github-daily-summary-prompt` (a Prompt)

*   **Example Usage**:
    1.  Click the `+` icon and select the **On-Call Escalation Policy** to attach it to your conversation.
    2.  Now you can ask questions about it directly:
        > "What is the escalation path for a SEV-2 issue?"
    3.  You can also ask questions that require the AI to use a tool:
        > "Who is on call for support today?"
    4.  To use the prompt, click the `+` icon and select the **github-daily-summary-prompt**. The prompt text will appear in your chat input, ready for you to fill in the repository details:
        > Generate a concise daily summary for the GitHub repository 'spring-projects/spring-ai' owned by 'John'.
        > ...

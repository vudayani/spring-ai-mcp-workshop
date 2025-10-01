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

Next, add the server to your Claude Desktop configuration file. On macOS, this file is typically located at `~/Library/Application Support/Claude/claude_desktop_config.json`.

Add the following entry, making sure to replace `/path/to/your/` with the actual path to the JAR file you just built.

```json
{
  "mcpServers": {
    "on-call-support-server": {
      "command": "java",
      "args": ["-jar", "/path/to/your/mcp-server-demo-0.0.1-SNAPSHOT.jar"]
    }
  }
}
```

### 3. Interact with your Server in Claude

After restarting Claude Desktop, your server will be available. You can now interact with it:

*   **Connect to the Server**: Use the `/connect` command to connect to your server.
    ```
    /connect on-call-support-server
    ```

*   **List Available Context**: Use the `/list` command to see all the tools, prompts, and resources your server exposes.
    ```
    /list
    ```
    Claude will display `getCurrentDate`, `getOnCallSupportByDate`, the `github-daily-summary` prompt, and the `on-call://escalation-policy` resource.

*   **Attach a Resource**: Attach the policy document to your conversation to ask questions about it.
    ```
    /attach on-call://escalation-policy
    ```
    Now you can ask:
    > "What is the escalation path for a SEV-2 issue?"

*   **Use a Tool**: You can also invoke tools directly or ask a question that would cause the AI to use a tool.
    > "Who is on call for support today?"

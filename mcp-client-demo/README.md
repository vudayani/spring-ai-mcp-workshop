# MCP Client Demo

This Spring Boot application demonstrates how a `ChatClient` can be enhanced to connect with external tools and services through the Model Context Protocol (MCP). It acts as a client that can discover and invoke functions on one or more MCP servers.

## Overview

In the first part of the workshop, this client will be configured to connect to a single MCP server:

1.  **GitHub Server**: The official [GitHub MCP Server](https://github.com/github/github-mcp-server), which the client runs in a Docker container.

This setup allows an AI model to use tools from the GitHub server to answer questions like "What are the recent activities in the `spring-projects/spring-ai` repository?".

Later in the workshop, we will update the configuration to connect to our own custom-built MCP server as well.

## Running the Application (Part 1: GitHub Server)

Before running the client, ensure you have completed all the setup steps in the main `README.md` at the root of this project, including configuring your LLM and GitHub API keys.

1.  **Configure the LLM Provider**: Open the `src/main/resources/application.properties` file in this module. Uncomment the two lines corresponding to the LLM provider you wish to use (OpenAI, Anthropic, or Ollama).

2.  **Run the Application**: Navigate to this module's directory and use the Maven Spring Boot plugin to run the application.

    ```bash
    # From the mcp-client-demo directory
    ../mvnw spring-boot:run
    ```

    Alternatively, you can run the `McpClientDemoApplication` class from your IDE. Once the application starts, it will launch the GitHub MCP server in Docker and be ready to receive requests.

3. Switch to a terminal and run the curl command
    ```bash
    curl "http://localhost:8080/github-summary?repoOwner=repoOwner&repoName=spring-ai"
    ```

---

## Running the Application (Part 2: Adding the Custom Server)

After you have built the `mcp-server-demo`, you can connect it to this client. There are two ways to do this, demonstrating two different MCP transports.

### Option A: Connecting via STDIO

In this method, the client will launch the server's JAR file directly and communicate with it over standard input/output.

1.  **Build the MCP Server**: Ensure the JAR file for the `mcp-server-demo` exists. If not, build it.
    ```bash
    # From the mcp-server-demo root directory
    ./mvnw clean package
    ```

2.  **Update MCP Configuration**: Open `src/main/resources/mcp-servers-config.json` and add the `on-call` server configuration. The client will now manage both the GitHub server and the on-call server.

    ```json
    {
      "servers": [
        {
          "name": "github",
          "command": "docker",
          "args": [
            "run", "-i", "--rm",
            "-e", "GITHUB_PERSONAL_ACCESS_TOKEN",
            "ghcr.io/github/github-mcp-server"
          ],
          "transport": "stdio"
        },
        {
          "name": "on-call",
          "command": "java",
          "args": [
            "-jar",
            "../../mcp-server-demo/target/mcp-server-demo-0.0.1-SNAPSHOT.jar"
          ],
          "transport": "stdio"
        }
      ]
    }
    ```

3.  **Restart the Application**: Run the `mcp-client-demo` application again. It will now launch and connect to 
*both* the GitHub server and the custom on-call server, making all their tools available to the AI model.

### Option B: Connecting via HTTP (Streamable Transport)

In this method, the `mcp-server-demo` runs independently as a web server, and our client connects to its HTTP endpoint using the modern **Streamable HTTP** transport.

1.  **Start the MCP Server**: Follow the "Part 2: Running in HTTP Mode" instructions in the `mcp-server-demo/README.md` to configure and start the server. Ensure it is running and accessible on `http://localhost:8081`.

2.  **Update Client Configuration**: The Streamable HTTP transport is configured directly in `application.properties`. Open `src/main/resources/application.properties` in *this* module (`mcp-client-demo`) and add the following lines:

    ```properties
    # On-Call Server Connection (Streamable HTTP)
    spring.ai.mcp.client.streamable-http.connections.on-call.url=http://localhost:8081/mcp
    ```
    *Note: The default endpoint for the Streamable WebMVC server is `/mcp`. If you only point to the root (`/`), you will get a 404 error.*

3.  **Restart the Application**: Run the `mcp-client-demo` again. It will now connect to the running `on-call` server over HTTP. The client will still manage the GitHub server via `stdio` (from the `mcp-servers-config.json` file) and will now also be connected to your custom server via Streamable HTTP.

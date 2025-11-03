# From Prompts to Agents: Supercharging Spring Boot with AI and MCP

Welcome to the "From Prompts to Agents" workshop! In this 90-minute session, we'll explore how to build intelligent, context-aware AI applications with Spring AI and the Model Context Protocol (MCP).

We'll go beyond simple prompts to create applications that can reason over your data, integrate with external systems, and orchestrate real-world workflows.

## What We're Building

In this workshop, we'll build an AI assistant that helps a developer start their day. We will build an MCP client that can connect to [Github MCP server](https://github.com/github/github-mcp-server) and a custom MCP server that exposes internal tools. By the end, you'll be able to ask questions like:

*   "What are the recent activities in the `spring-projects/spring-ai` repository?"
*   "Who is on call for support today?"

You can find the complete code for this workshop in this GitHub repository.

## Prerequisites

Before you begin, ensure you have the following installed:

*   **Java 21 or higher**
*   **Maven**: For building the project
*   **Git**: For cloning the repository
*   **Docker**: We'll use Docker to run the GitHub MCP Server. You can find installation instructions at the [official Docker website](https://docs.docker.com/get-docker/).
*   **Node.js and npx**: We'll use `npx` to run the MCP Model Inspector. You can install it from the [official Node.js website](https://nodejs.org/).
*   **An IDE**: Your favorite IDE (Spring Tool Suite, IntelliJ IDEA, VS Code with Spring extensions)
*   **An AI Model Provider Account**: You'll need an API key from a provider like OpenAI or Anthropic, or a locally running model with Ollama

## Setup Instructions

### 1. Clone the Repository

Open your terminal and clone this repository:

```bash
git clone git@github.com:vudayani/spring-ai-mcp-workshop.git
cd spring-ai-mcp-workshop
```

### 2. Configure Your AI Model Provider

Spring AI supports a wide range of LLM providers. For this workshop, you can use any provider, but we have included instructions for three popular options below. For a complete list of supported providers and their specific configurations, please refer to the [official Spring AI documentation](https://docs.spring.io/spring-ai/reference/api/chat/comparison.html).

Choose one of the following options to configure your connection to a Large Language Model (LLM).

#### Option A: OpenAI

1.  **Get API Key**: If you don't have one, create an API key at [platform.openai.com/api-keys](https://platform.openai.com/api-keys).
2.  **Set Environment Variable**: Export the key as an environment variable.

    ```bash
    export SPRING_AI_OPENAI_API_KEY='<your-openai-api-key>'
    ```

3.  **Verify Your Key**: Run the following `curl` command to ensure your key is working correctly.

    ```bash
    curl https://api.openai.com/v1/chat/completions \
      -H "Content-Type: application/json" \
      -H "Authorization: Bearer $SPRING_AI_OPENAI_API_KEY" \
      -d '{
        "model": "gpt-4o",
        "messages": [{"role": "user", "content": "Tell me a joke"}]
      }'
    ```

    You should see a successful JSON response from the API.

#### Option B: Anthropic

1.  **Get API Key**: Sign up and get your API key from the [Anthropic Console](https://console.anthropic.com/).
2.  **Set Environment Variable**: Export the key as an environment variable.

    ```bash
    export SPRING_AI_ANTHROPIC_API_KEY='<your-anthropic-api-key>'
    ```

3.  **Verify Your Key**: Run the following `curl` command to test your key.

    ```bash
    curl https://api.anthropic.com/v1/messages \
      --header "x-api-key: $SPRING_AI_ANTHROPIC_API_KEY" \
      --header "anthropic-version: 2023-06-01" \
      --header "content-type: application/json" \
      --data '{
          "model": "claude-sonnet-4-5-20250929",
          "max_tokens": 1024,
          "messages": [
              {"role": "user", "content": "Tell me a joke"}
          ]
      }'
    ```

    A successful response will confirm your key is valid.

### 3. Configure GitHub Access Token

During the workshop, we will connect our Spring AI application to the [GitHub MCP Server](https://github.com/github/github-mcp-server) to interact with GitHub repositories. This requires a GitHub Personal Access Token (PAT).

1.  **Create a Personal Access Token**: Follow the [official GitHub documentation](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic) to create a "classic" token. When prompted for scopes, select the following:
    *   `repo`
    *   `read:org`
    *   `read:user`

2.  **Store Your Token**: It is recommended to store your token in an environment variable or a `.env` file.

    **Option A: Environment Variable**
    ```bash
    export GITHUB_PERSONAL_ACCESS_TOKEN='<your-github-token>'
    ```

    **Option B: .env file**
    Create a file named `.env` in the root of the project and add the following line:
    ```
    GITHUB_PERSONAL_ACCESS_TOKEN='<your-github-token>'
    ```
    To prevent accidentally committing your token, add `.env` to your `.gitignore` file:
    ```bash
    echo ".env" >> .gitignore
    ```

3.  **Verify Your Token**: To ensure your token is working correctly, make a request to the GitHub API. Make sure to replace `$GITHUB_PERSONAL_ACCESS_TOKEN` with your actual token if you are not using an environment variable.

    ```bash
    curl -H "Authorization: Bearer $GITHUB_PERSONAL_ACCESS_TOKEN" https://api.github.com/user
    ```

    A successful response will return a JSON object with your GitHub user profile information. This confirms your token is valid and has the correct permissions.

## Exploring MCP with the Model Inspector

Before we write any Java code, let's explore the Model Context Protocol (MCP) using the [Model Inspector](https://github.com/modelcontextprotocol/model-inspector). This command-line tool allows us to connect to any MCP server, view its available tools, and interact with them directly.

The `npx` command lets us run these tools without a global installation. Open your terminal and try out the following pre-built servers.

#### 1. Filesystem Server
This server exposes tools that let an AI interact with your local filesystem (e.g., `list_files`, `read_file`).

```bash
npx -y @modelcontextprotocol/inspector npx @modelcontextprotocol/server-filesystem /path/to/a/directory
```
*Make sure to replace `/path/to/a/directory` with a real path on your machine, for example, your Desktop.*

#### 2. Everything Server
This server includes a wide variety of sample tools for demonstration purposes.

```bash
npx -y @modelcontextprotocol/inspector npx @modelcontextprotocol/server-everything
```
Playing with these servers will give you a feel for the request/response flow of MCP and the types of primitives you can expose.

## Workshop Modules

This repository contains the following modules, which we will build upon during the workshop. Please refer to the `README.md` file inside each module for detailed instructions on how to run them.

*   `mcp-client-demo`: A Spring Boot application demonstrating how to connect to and use existing MCP servers.
*   `mcp-server-demo`: A simple MCP server built with Spring Boot, exposing custom tools for an AI agent to use.

Happy coding!


## Resources

### Official Resources
- [Spring AI Project](https://spring.io/projects/spring-ai)
- [Spring AI Reference Documentation](https://docs.spring.io/spring-ai/reference/)
- [Spring AI API Documentation](https://docs.spring.io/spring-ai/docs/1.0.0-SNAPSHOT/api/)

### Code Examples

- [Spring AI Official Examples](https://github.com/spring-projects/spring-ai-examples) - Comprehensive official repository containing examples for all Spring AI features including MCP dynamic tools, prompt engineering patterns, agentic workflows, vector stores, and various model integrations (2025)

### Core Resources
- [MCP Client Documentation](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-client-boot-starter-docs.html) - Official documentation for implementing the Model Context Protocol client in Spring AI applications.
- [MCP Client Examples](https://github.com/spring-projects/spring-ai-examples/tree/main/model-context-protocol) - Comprehensive examples showcasing the Model Context Protocol implementation in Spring AI, including client-server communication, tool discovery, filesystem operations, weather services, web search integration, and dynamic tool updates.
- [MCP Annotations](https://github.com/spring-ai-community/mcp-annotations) - Annotation-based programming model for implementing MCP servers and clients. Provides a clean, declarative approach to handling MCP operations with reduced boilerplate code. Includes core annotations that depend only on the MCP Java SDK and a Spring AI integration module.

### Talks & Videos
- [#1 Spring AI Tutorial | Introduction](https://www.youtube.com/watch?v=4nBG848oArI) - Introductory tutorial covering Spring AI basics and setup
- [From Single-Shot LLMs to Intelligent Agents](https://www.youtube.com/watch?v=l38CPjOYsHk) - Building intelligent agents with Spring AI framework
- [AI for Java Developers: Full Course / Workshop on Getting Started with Spring AI](https://www.youtube.com/watch?v=FzLABAppJfM) - Comprehensive workshop covering Spring AI fundamentals for Java developers
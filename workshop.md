# Workshop Quick Reference Guide
## From Prompts to Agents: Supercharging Spring Boot Apps with AI and MCP

This guide provides quick reference commands and snippets for the workshop. Keep this handy during the session!

---

## 🔧 Pre-Workshop Setup Verification

### 1. Verify AI Provider API Key
**OpenAI:**
```bash
curl https://api.openai.com/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $SPRING_AI_OPENAI_API_KEY" \
  -d '{"model": "gpt-4o", "messages": [{"role": "user", "content": "Tell me a joke"}]}'
```

**Anthropic:**
```bash
curl https://api.anthropic.com/v1/messages \
  --header "x-api-key: $SPRING_AI_ANTHROPIC_API_KEY" \
  --header "anthropic-version: 2023-06-01" \
  --header "content-type: application/json" \
  --data '{"model": "claude-sonnet-4-5-20250929", "max_tokens": 1024, "messages": [{"role": "user", "content": "Tell me a joke"}]}'
```

### 2. Verify GitHub Access Token
```bash
curl -H "Authorization: Bearer $GITHUB_PERSONAL_ACCESS_TOKEN" https://api.github.com/user
```
*Should return your GitHub user profile information*

If you don't have GitHub Personal Access Token (PAT), you can create one by following the below steps:

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

---

## 🚀 Workshop Demo Commands

### 1. Explore MCP with Model Inspector

**Filesystem Server:**
```bash
npx -y @modelcontextprotocol/inspector npx @modelcontextprotocol/server-filesystem /path/to/a/directory
```
*Replace `/path/to/a/directory` with a real path (e.g., your Desktop)*

### 2. Build and Run MCP Client Demo

**Navigate to client directory:**
```bash
cd mcp-client-demo
```

**Run the application:**
```bash
../mvnw spring-boot:run
```

**Test GitHub endpoint:**
```bash
curl "http://localhost:8080/github-create-issue?repoOwner=vudayani&repoName=spring-ai-mcp-workshop"
```

### 3. Configure On-Call Server in Client

**Add to `mcp-servers-config.json`:**
```json
    {
      "name": "on-call",
      "command": "java",
      "args": [
        "-jar",
        "../../mcp-server-demo/target/mcp-server-demo-0.0.1-SNAPSHOT.jar"
      ],
    }
```

### 4. Build and Test On-Call Server

**Build the server:**
```bash
cd ../mcp-server-demo
./mvnw clean package
```

**Test on-call endpoint:**
```bash
curl "http://localhost:8080/on-call"
```

---

*Keep this guide open during the workshop for quick reference!*

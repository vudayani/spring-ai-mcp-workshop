package com.example.mcpworkshop.server.prompts;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springframework.stereotype.Component;

import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;

@Component
public class GitHubPrompts {
	
	private static final Logger log = LoggerFactory.getLogger(GitHubPrompts.class);


    @McpPrompt(name = "github-daily-summary", description = "A prompt template to generate a daily summary of a GitHub repository's activity.")
    public GetPromptResult getGitHubDailySummaryPrompt() {
        String message = """
                Provide a daily summary of the GitHub repository {repoName} owned by {repoOwner}. The summary should include the following sections:

                ### **1. Recent Commits:**
                   - Summarize latest commits made to the 'main' branch in the last 24 hours
                   - For each commit, include:
                     - Commit messages (with a Short description if available of the commit purpose)
                     - Authors and date
                     - Direct link to the commit

                ### **2. Open Pull Requests:**
                   - List all open pull requests, highlighting:
                      - Title with a direct link to the PR
                      - Author and date
                      - Short description** of the PR's purpose
                      - Requested reviewers (if any)
                   - Highlight PRs that need attention (e.g., open for more than 2 days).

                ### **3. Open Issues:**
                   - List high-priority open issues, prioritizing those labeled "high-priority".
                   - For each issue, include:
                      - Title with a direct link to issue
                      - Labels or milestones

                ### **4. Formatting for Slack:**
                   - Present the information in a structured and well-organized format.
                   - Use **bold headings** and bullet points for clarity.
                   - If a section has no updates, clearly indicate that.
                   - End with a gentle reminder for the team to review PRs or resolve critical issues.
                   - Post the summary to the '#dev-updates' Slack channel.

                The summary should be concise, clear, and actionable to help the development team quickly understand the repository's status and priorities.
                """;
        return new GetPromptResult(
                "GitHub daily summary prompt",
                List.of(new PromptMessage(Role.USER, new TextContent(message)))
        );
    }
}

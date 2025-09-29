package com.example.mcpworkshop.client;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.modelcontextprotocol.client.McpSyncClient;

@RestController
public class ChatController {

	private final ChatClient chatClient;

	public ChatController(ChatClient.Builder builder, List<McpSyncClient> mcpSyncClients) {
		this.chatClient = builder
				.defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
				.build();
	}
	
	@GetMapping("/github-summary")
	public String getGithubSummary(@RequestParam String repoOwner, @RequestParam String repoName) {

		var prompt = """
				Provide a daily summary for the GitHub repository {repoName} owned by {repoOwner}.
				The summary should cover activity in the last 24 hours and include:
				1. Recent commits to the main branch.
				2. Open pull requests that need attention.
				3. High-priority open issues.
				""";


       return chatClient.prompt()
           .user(userSpec -> userSpec
               .text(prompt)
               .param("repoOwner", repoOwner)
               .param("repoName", repoName))
           .call()
           .content();
	   }
	
	@GetMapping("/on-call")
	public String getOnCallSupport() {
		var prompt = "Who is on call for support today?";
		return chatClient.prompt()
               .user(prompt)
               .call()
               .content();
	   }

}

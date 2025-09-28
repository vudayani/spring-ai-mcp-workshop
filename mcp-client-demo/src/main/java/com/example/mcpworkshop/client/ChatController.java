package com.example.mcpworkshop.client;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.modelcontextprotocol.client.McpSyncClient;

@RestController
public class ChatController {

	private final ChatClient chatClient;

	public ChatController(ChatClient.Builder builder, List<McpSyncClient> mcpSyncClients) {
		this.chatClient = builder
				.defaultTools(mcpSyncClients)
				.build();
	}

	@GetMapping("/github-activity")
	public String getGithubActivity(@RequestParam String repoOwner, @RequestParam String repoName) {

		var prompt = """
				Get the github activity for the past one day in the repo venkat-vmv owned by blogging-platform
				""";

		return chatClient.prompt()
			.user(userSpec -> userSpec
				.text(prompt)
				.param("repoOwner", repoOwner)
				.param("repoName", repoName))
			.call()
			.content();
	}
	
}

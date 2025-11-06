package com.example.mcpworkshop.client;

// import org.springframework.ai.chat.client.ChatClient;
// import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

	// private final ChatClient chatClient;

	// public ChatController(ChatClient.Builder builder, SyncMcpToolCallbackProvider mcpTools) {
	// 	this.chatClient = builder
	// 			.defaultToolCallbacks(mcpTools.getToolCallbacks())
	// 			.build();
	// }
	
	// @GetMapping("/github-create-issue")
	// public String getGithubIssue(@RequestParam String repoOwner, @RequestParam String repoName) {

	// 	var prompt = """
	// 			A build has failed for the repository {repoName} owned by {repoOwner}.

	// 			Failure Summary:
	// 			- Error: NullPointerException in UserController.java at line 58
	// 			- Cause: The 'userService' bean was not initialized before use
	// 			- Impact: User registration and login endpoints are failing
				
	// 			Please create a GitHub issue in this repository describing the failure.
	// 			Include:
	// 			1. A clear, concise title for the issue
	// 			2. A short description summarizing the error
	// 			3. Suggested next steps for fixing it
	// 			""";


    //    return chatClient.prompt()
    //        .user(userSpec -> userSpec
    //            .text(prompt)
    //            .param("repoOwner", repoOwner)
    //            .param("repoName", repoName))
    //        .call()
    //        .content();
	//    }
	
	// @GetMapping("/github-summary")
	// public String getGithubSummary(@RequestParam String repoOwner, @RequestParam String repoName) {

	// 	var prompt = """
	// 			Provide a summary for latest activity in the GitHub repository {repoName} owned by {repoOwner}.
	// 			The summary should include:
	// 			1. Recent commits to the main branch
	// 			2. Open pull requests that need attention
	// 			3. High-priority open issues
	// 			""";


    //    return chatClient.prompt()
    //        .user(userSpec -> userSpec
    //            .text(prompt)
    //            .param("repoOwner", repoOwner)
    //            .param("repoName", repoName))
    //        .call()
    //        .content();
	//    }
	
	// @GetMapping("/on-call")
	// public String getOnCallSupport() {
	// 	var prompt = "Who is on call for support today?";
	// 	return chatClient.prompt()
    //            .user(prompt)
    //            .call()
    //            .content();
	//    }

}

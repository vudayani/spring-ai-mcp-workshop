
package com.example.spring_ai_demo.controller;

import com.example.spring_ai_demo.service.CICDTools;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/ask")
public class AIController {

  @Value("classpath:prompt/build-analysis.st")
  private Resource promptTemplate;
  private final ChatClient chatClient;
  private final ChatMemory chatMemory;
  private final VectorStore vectorStore;
  private final CICDTools cicdTools;

  public AIController(ChatClient chatClient, ChatMemory chatMemory, VectorStore vectorStore,
      CICDTools cicdTools) {
    this.chatClient = chatClient;
    this.chatMemory = chatMemory;
    this.vectorStore = vectorStore;
    this.cicdTools = cicdTools;
  }

  @PostMapping("/{sessionId}")
  public String ask(@PathVariable String sessionId, @RequestBody Map<String, String> request) {
    String message = request.get("message");

    // Retrieve chat history
    var history = chatMemory.get(sessionId);

    // Perform similarity search in vector store
    List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder()
        .query(message).topK(5).build());
    String context = docs.stream().map(Document::getText).collect(Collectors.joining("\n---\n"));

    // Load the prompt template from the resource
    PromptTemplate template = new PromptTemplate(promptTemplate);
    var prompt = template.create(Map.of("history", history, "context", context, "message", message));

    //Given tools definition here to Model along with Memory and Context
    String response = chatClient.prompt(prompt)
        .tools(cicdTools)
        .advisors(advisorSpec -> advisorSpec.param("conversationId", sessionId)).call().content();

    // Update chat memory
    chatMemory.add(sessionId, new UserMessage(message));
    assert response != null;
    chatMemory.add(sessionId, new SystemMessage(response));
    return response;
  }
}
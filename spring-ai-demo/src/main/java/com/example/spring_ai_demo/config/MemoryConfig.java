package com.example.spring_ai_demo.config;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MemoryConfig {
  @Bean
  public ChatMemoryRepository chatMemoryRepository() {
    return new InMemoryChatMemoryRepository();
  }

  @Bean
  public ChatMemory chatMemory(ChatMemoryRepository repo) {
    return MessageWindowChatMemory.builder()
        .chatMemoryRepository(repo)
        .maxMessages(100)
        .build();
  }
}
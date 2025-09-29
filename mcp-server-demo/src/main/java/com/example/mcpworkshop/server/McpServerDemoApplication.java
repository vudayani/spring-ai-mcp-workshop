package com.example.mcpworkshop.server;

import java.util.List;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.mcpworkshop.server.tools.OnCallTools;

@SpringBootApplication
public class McpServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpServerDemoApplication.class, args);
	}
	
	@Bean
	public List<ToolCallback> toolCallbacks(OnCallTools onCallTools) {
		return List.of(ToolCallbacks.from(onCallTools));
	}

}

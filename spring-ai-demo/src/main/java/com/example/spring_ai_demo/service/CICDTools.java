package com.example.spring_ai_demo.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class CICDTools {

  @Tool(name = "commitFix", description = "Commit fix to Git repository")
  public String commitFix(String fileName, String fixDescription) {
    return "✅ Mock: Committed fix to " + fileName + " with message: " + fixDescription;
  }

  @Tool(name = "rerunPipeline", description ="Rerun Jenkins pipeline")
  public String rerunPipeline() {
    return "🚀 Mock: Jenkins pipeline rerun triggered successfully.";
  }
}

package com.example.spring_ai_demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class LogLoader implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(LogLoader.class);
  private final VectorStore vectorStore;

  private static final String DOCS_FOLDER = "dev-docs";

  public LogLoader(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  @Override
  public void run(String... args) {
    List<Document> docs = loadDocuments();
    if (!docs.isEmpty()) {
      vectorStore.add(docs);
      System.out.println("✅ Loaded " + docs.size() + " documents into Vector Store.");
    } else {
      System.err.println("⚠️ No documents found in " + DOCS_FOLDER);
    }
    log.info("✅ Vector store populated successfully.");
  }


  private List<Document> loadDocuments() {
    List<Document> documents = new ArrayList<>();
    ClassPathResource folderResource = new ClassPathResource(DOCS_FOLDER);
    Path folderPath = null;
    try {
      folderPath = folderResource.getFile().toPath();
    } catch (IOException e) {
      log.error("Failed to load folder {}", DOCS_FOLDER, e);
    }

    try {
      assert folderPath != null;
      Files.walk(folderPath)
          .filter(Files::isRegularFile)
          .forEach(path -> {
            try {
              String content = Files.readString(path);
              documents.add(new Document(content,
                  Map.of("filename", path.getFileName().toString()
                  )));
            } catch (IOException e) {
              log.error("Failed to load file {}", path.getFileName().toString(), e);
            }
          });
    } catch (IOException e) {
      log.error("Failed to load folder {}", DOCS_FOLDER, e);
    }
    return documents;
  }
}

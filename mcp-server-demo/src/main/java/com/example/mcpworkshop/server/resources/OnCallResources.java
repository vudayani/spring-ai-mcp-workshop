//package com.example.mcpworkshop.server.resources;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.nio.charset.StandardCharsets;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springaicommunity.mcp.annotation.McpResource;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.FileCopyUtils;
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class OnCallResources {
//
//    private static final Logger log = LoggerFactory.getLogger(OnCallResources.class);
//    private String escalationPolicy;
//
//    @McpResource(
//            uri = "on-call://escalation-policy",
//            name = "On-Call Escalation Policy",
//            description = "The official policy for escalating on-call support issues.",
//            mimeType = "text/markdown"
//    )
//    public String getEscalationPolicy() {
//        return this.escalationPolicy;
//    }
//
//    @PostConstruct
//    public void init() {
//        log.info("Loading On-Call Escalation Policy from file 'on-call-policy.txt'");
//        ClassPathResource resource = new ClassPathResource("/data/on-call-escalation-policy.txt");
//        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
//            this.escalationPolicy = FileCopyUtils.copyToString(reader);
//            log.info("On-Call Escalation Policy loaded successfully.");
//        } catch (IOException e) {
//            log.error("Failed to read on-call policy", e);
//            throw new RuntimeException("Failed to read on-call policy", e);
//        }
//    }
//
//}
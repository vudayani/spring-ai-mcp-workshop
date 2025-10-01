package com.example.mcpworkshop.server.tools;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

import com.example.mcpworkshop.server.model.OnCallSupport;
import com.example.mcpworkshop.server.service.OnCallSupportService;

import java.time.format.DateTimeParseException;

@Component
public class OnCallTools {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(OnCallTools.class);
	
    private final OnCallSupportService onCallSupportService;

    public OnCallTools(OnCallSupportService onCallSupportService) {
        this.onCallSupportService = onCallSupportService;
    }

    @McpTool(name = "getCurrentDate", description = "Get the current date. Date format is YYYY-MM-DD.")
    public String getCurrentDate() {
        log.info("Getting current date");
        return LocalDate.now().toString();
    }
    
    @McpTool(name = "getOnCallSupportByDate", description = "Get the list of engineers on call for a specific date. Date format is YYYY-MM-DD.")
    public OnCallSupport getOnCallSupportByDate(String dateStr) {
        log.info("Getting on call support information for the date: {}", dateStr);
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return onCallSupportService.findOnCallSupportByDate(date)
                    .orElse(new OnCallSupport("No one is on-call for " + dateStr, "Contact support lead"));
        } catch (DateTimeParseException e) {
            log.error("Invalid date format for: {}", dateStr, e);
            return new OnCallSupport("Invalid date format", "Please use YYYY-MM-DD.");
        }
    }
}

package com.example.mcpworkshop.server.tools;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.example.mcpworkshop.server.model.OnCallSupport;
import com.example.mcpworkshop.server.service.OnCallSupportService;

@Component
public class OnCallTools {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(OnCallTools.class);
	
    private final OnCallSupportService onCallSupportService;

    public OnCallTools(OnCallSupportService onCallSupportService) {
        this.onCallSupportService = onCallSupportService;
    }

    @Tool(name = "current_date", description = "Get the current date in YYYY-MM-DD format")
    public String currentDate() {
    	log.info("Getting current date");
        return LocalDate.now().toString();
    }

    @Tool(name = "get_on_call_support_by_date", description = "Get the on-call support team for a specific date")
    public OnCallSupport getOnCallSupportByDate(String dateStr) {
    	log.info("Getting on call support information for the date");
        LocalDate date = LocalDate.parse(dateStr);
        return onCallSupportService.findOnCallSupportByDate(date)
                .orElse(new OnCallSupport("No one on-call", "Contact support lead"));
    }
}

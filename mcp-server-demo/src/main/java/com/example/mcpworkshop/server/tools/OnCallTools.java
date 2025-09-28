package com.example.mcpworkshop.server.tools;

import com.example.mcpworkshop.server.model.OnCallSupport;
import com.example.mcpworkshop.server.service.OnCallSupportService;
import org.springframework.ai.model.tool.Tool;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OnCallTools {
    private final OnCallSupportService onCallSupportService;

    public OnCallTools(OnCallSupportService onCallSupportService) {
        this.onCallSupportService = onCallSupportService;
    }

    @Tool(name = "current_date", description = "Get the current date in YYYY-MM-DD format")
    public String currentDate() {
        return LocalDate.now().toString();
    }

    @Tool(name = "get_on_call_support_by_date", description = "Get the on-call support team for a specific date")
    public OnCallSupport getOnCallSupportByDate(
        @Description("The date to check in YYYY-MM-DD format") String dateStr
    ) {
        LocalDate date = LocalDate.parse(dateStr);
        return onCallSupportService.findOnCallSupportByDate(date)
                .orElse(new OnCallSupport("No one on-call", "Contact support lead"));
    }
}

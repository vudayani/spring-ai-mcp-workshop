package com.example.mcpworkshop.server.service;

import com.example.mcpworkshop.server.model.OnCallSupport;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class OnCallSupportService {

    private static final Map<LocalDate, OnCallSupport> onCallRoster = Map.of(
            LocalDate.of(2025, 10, 18), new OnCallSupport("Alice", "Bob"),
            LocalDate.of(2025, 10, 19), new OnCallSupport("Charlie", "David"),
            LocalDate.of(2025, 10, 20), new OnCallSupport("Eve", "Frank")
    );

    public Optional<OnCallSupport> findOnCallSupportByDate(LocalDate date) {
        return Optional.ofNullable(onCallRoster.get(date));
    }
}

package com.example.mcpworkshop.server.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mcpworkshop.server.model.OnCallSupport;

@Service
public class OnCallSupportService {

    private static final Map<LocalDate, OnCallSupport> onCallRoster = Map.of(
            LocalDate.of(2025, 11, 3), new OnCallSupport("Alice", "Bob"),
            LocalDate.of(2025, 11, 4), new OnCallSupport("Charlie", "David"),
            LocalDate.of(2025, 11, 5), new OnCallSupport("Eve", "Frank")
    );

    public Optional<OnCallSupport> findOnCallSupportByDate(LocalDate date) {
        return Optional.ofNullable(onCallRoster.get(date));
    }
}

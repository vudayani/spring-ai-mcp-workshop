package com.example.mcpworkshop.server.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mcpworkshop.server.model.OnCallSupport;

@Service
public class OnCallSupportService {

    private static final Map<LocalDate, OnCallSupport> onCallRoster = Map.of(
            LocalDate.of(2025, 11, 7), new OnCallSupport("Alice", "Bob"),
            LocalDate.of(2025, 11, 8), new OnCallSupport("Charlie", "David"),
            LocalDate.of(2025, 11, 9), new OnCallSupport("Eve", "Frank"),
            LocalDate.of(2025, 11, 10), new OnCallSupport("Grace", "Heidi"),
            LocalDate.of(2025, 11, 11), new OnCallSupport("Ivan", "Judy"),
            LocalDate.of(2025, 11, 12), new OnCallSupport("Ken", "Laura"),
            LocalDate.of(2025, 11, 13), new OnCallSupport("Manoj", "Nieraj"),
            LocalDate.of(2025, 11, 14), new OnCallSupport("Olivia", "Peggy"),
            LocalDate.of(2025, 11, 15), new OnCallSupport("Quentin", "Rita"),
            LocalDate.of(2025, 11, 16), new OnCallSupport("Uma", "Victor")
    );

    public Optional<OnCallSupport> findOnCallSupportByDate(LocalDate date) {
        return Optional.ofNullable(onCallRoster.get(date));
    }
}

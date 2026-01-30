package com.example.mcpworkshop.server.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mcpworkshop.server.model.OnCallSupport;

@Service
public class OnCallSupportService {

    private static final Map<LocalDate, OnCallSupport> onCallRoster = Map.of(
            LocalDate.of(2026, 01, 30), new OnCallSupport("Alice", "Bob"),
            LocalDate.of(2026, 01, 31), new OnCallSupport("Charlie", "David"),
            LocalDate.of(2026, 02, 1), new OnCallSupport("Eve", "Frank"),
            LocalDate.of(2026, 02, 2), new OnCallSupport("Grace", "Heidi"),
            LocalDate.of(2026, 02, 3), new OnCallSupport("Ivan", "Judy"),
            LocalDate.of(2026, 02, 4), new OnCallSupport("Ken", "Laura"),
            LocalDate.of(2026, 02, 5), new OnCallSupport("Manoj", "Nieraj"),
            LocalDate.of(2026, 02, 6), new OnCallSupport("Olivia", "Peggy"),
            LocalDate.of(2026, 02, 7), new OnCallSupport("Quentin", "Rita"),
            LocalDate.of(2026, 02, 8), new OnCallSupport("Uma", "Victor")
    );

    public Optional<OnCallSupport> findOnCallSupportByDate(LocalDate date) {
        return Optional.ofNullable(onCallRoster.get(date));
    }
}

package com.becaconnect.pe.becaconnect.service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class AuditLogService {

    private static final String LOG_FILE = "src/main/resources/audit.log";

    public void logEvent(String username, String role, String event) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String logEntry = String.format("[%s] - User: %s (%s) - Event: %s%n",
                    LocalDateTime.now(), username, role, event);
            writer.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

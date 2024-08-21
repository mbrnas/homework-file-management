package org.onecode.filemanagementapi.controller;

import org.onecode.filemanagementapi.model.AccessLog;
import org.onecode.filemanagementapi.service.AccessLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files/logs")
public class AccessLogController {

    private final AccessLogService accessLogService;

    public AccessLogController(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    @PostMapping("/log")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> logAccess(@RequestParam String userId, @RequestParam String action, @RequestParam String fileName) {
        accessLogService.logAccess(userId, action, fileName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<List<AccessLog>> getUserAccessLogs(@PathVariable String userId) {
        List<AccessLog> logs = accessLogService.getAccessLogsByUser(userId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/file/{fileName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<List<AccessLog>> getFileAccessLogs(@PathVariable String fileName) {
        List<AccessLog> logs = accessLogService.getAccessLogsForFile(fileName);
        return ResponseEntity.ok(logs);
    }
}

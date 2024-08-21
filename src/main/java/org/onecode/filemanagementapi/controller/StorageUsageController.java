package org.onecode.filemanagementapi.controller;

import org.onecode.filemanagementapi.model.StorageUsage;
import org.onecode.filemanagementapi.service.StorageUsageService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/storage")
public class StorageUsageController {

    private final StorageUsageService storageUsageService;

    public StorageUsageController(StorageUsageService storageUsageService) {
        this.storageUsageService = storageUsageService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<StorageUsage> getStorageUsage(@PathVariable String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        StorageUsage usage = storageUsageService.getUsage(userId);
        return ResponseEntity.ok(usage);
    }

    @PostMapping("/update/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateStorageUsage(@PathVariable String userId, @RequestParam long fileSize, @RequestParam boolean increase) {
        if (userId == null || userId.isEmpty() || fileSize <= 0) {
            return ResponseEntity.badRequest().build();
        }
        storageUsageService.updateUsage(userId, fileSize, increase);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/set-quota/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> setQuota(@PathVariable String userId, @RequestParam long quota) {
        if (userId == null || userId.isEmpty() || quota <= 0) {
            return ResponseEntity.badRequest().build();
        }
        storageUsageService.setQuota(userId, quota);
        return ResponseEntity.ok().build();
    }
}


package org.onecode.filemanagementapi.service;

import org.onecode.filemanagementapi.config.Constants;
import org.onecode.filemanagementapi.model.StorageUsage;
import org.onecode.filemanagementapi.repository.StorageUsageRepository;
import org.springframework.stereotype.Service;

@Service
public class StorageUsageService {

    private final StorageUsageRepository storageUsageRepository;

    public StorageUsageService(StorageUsageRepository storageUsageRepository) {
        this.storageUsageRepository = storageUsageRepository;
    }

    public void updateUsage(String userId, long fileSize, boolean increase) {
        StorageUsage usage = storageUsageRepository.findByUserId(userId).orElse(new StorageUsage(userId, 0, Constants.DEFAULT_USER_QUOTA));
        long newUsedSpace = increase ? usage.getUsedSpace() + fileSize : usage.getUsedSpace() - fileSize;
        usage.setUsedSpace(newUsedSpace);
        storageUsageRepository.save(usage);
    }

    public StorageUsage getUsage(String userId) {
        return storageUsageRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Usage not found"));
    }

    public void setQuota(String userId, long quota) {
        StorageUsage usage = storageUsageRepository.findByUserId(userId).orElse(new StorageUsage(userId, 0, quota));
        usage.setQuota(quota);
        storageUsageRepository.save(usage);
    }
}

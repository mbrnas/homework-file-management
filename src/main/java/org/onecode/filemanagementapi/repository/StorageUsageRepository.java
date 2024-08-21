package org.onecode.filemanagementapi.repository;

import org.onecode.filemanagementapi.model.StorageUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageUsageRepository extends MongoRepository<StorageUsage, String> {

    Optional<StorageUsage> findByUserId(String userId);
}

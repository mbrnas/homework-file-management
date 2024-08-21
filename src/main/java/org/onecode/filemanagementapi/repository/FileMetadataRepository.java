package org.onecode.filemanagementapi.repository;

import org.onecode.filemanagementapi.model.FileMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileMetadataRepository extends MongoRepository<FileMetadata, String>{
    List<FileMetadata> findAllByUserId(String userId);
}

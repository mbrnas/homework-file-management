package org.onecode.filemanagementapi.repository;

import org.onecode.filemanagementapi.model.AccessLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessLogRepository extends MongoRepository<AccessLog, String>{
    List<AccessLog> findAllByUserId(String userId);

    List<AccessLog> findAllByFileName(String fileName);
}

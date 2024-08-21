package org.onecode.filemanagementapi.service;

import org.onecode.filemanagementapi.model.AccessLog;
import org.onecode.filemanagementapi.repository.AccessLogRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccessLogService {


    private final AccessLogRepository accessLogRepository;

    public AccessLogService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public void logAccess(String userId, String action, String fileName) {
        AccessLog log = new AccessLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setFileName(fileName);
        log.setTimestamp(new Date());
        accessLogRepository.save(log);
    }

    public List<AccessLog> getAccessLogsByUser(String userId) {
        return accessLogRepository.findAllByUserId(userId);
    }

    public List<AccessLog> getAccessLogsForFile(String fileName) {
        return accessLogRepository.findAllByFileName(fileName);
    }
}

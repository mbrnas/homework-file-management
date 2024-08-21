package org.onecode.filemanagementapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "storage_usage")
public class StorageUsage {
    @Id
    private String id;
    private String userId;
    private long usedSpace;
    private long quota;

    public StorageUsage(String userId, long usedSpace, long quota) {
        this.userId = userId;
        this.usedSpace = usedSpace;
        this.quota = quota;
    }
}


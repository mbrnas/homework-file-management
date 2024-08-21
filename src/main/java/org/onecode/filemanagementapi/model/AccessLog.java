package org.onecode.filemanagementapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "access_logs")
public class AccessLog {
    @Id
    private String id;
    private String userId;
    private String action;
    private String fileName;
    private Date timestamp;

}


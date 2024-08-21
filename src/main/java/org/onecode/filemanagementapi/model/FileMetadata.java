package org.onecode.filemanagementapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "file_metadata")
public class FileMetadata {
    @Id
    private String id;
    private String userId;
    private String fileName;
    private long fileSize;
    private String fileType;
    private Date uploadDate;
}

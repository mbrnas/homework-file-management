package org.onecode.filemanagementapi.model.submission;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Document(collection = "submissions")
@Data
public class Submission {
    @Id
    private String id;

    private String assignmentId;

    private String studentId;

    private String filePath;

    private LocalDateTime submittedAt;
}

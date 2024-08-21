package org.onecode.filemanagementapi.model.assignment;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "assignments")
@Data
public class Assignment {
    @Id
    private String id;

    private String title;

    private String description;

    private LocalDateTime deadline;

    private String courseId;
}

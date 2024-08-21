package org.onecode.filemanagementapi.model.school;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "school")
public class School {
    private String id;
    private String name;
    private String address;
}

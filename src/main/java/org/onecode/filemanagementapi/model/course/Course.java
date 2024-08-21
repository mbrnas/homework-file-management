package org.onecode.filemanagementapi.model.course;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "courses")
@Data
public class Course {
    @Id
    private String id;

    private String name;

    private List<String> enrolledStudentsIds = new ArrayList<>();

    private String teacherId;

    private String schoolId;
}

package org.onecode.filemanagementapi.repository;

import org.onecode.filemanagementapi.model.assignment.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AssignmentRepository extends MongoRepository<Assignment, String>{
    List<Assignment> findByCourseId(String courseId);
}

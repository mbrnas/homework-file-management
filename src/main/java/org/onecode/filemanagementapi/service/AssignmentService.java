package org.onecode.filemanagementapi.service;

import org.onecode.filemanagementapi.model.assignment.Assignment;
import org.onecode.filemanagementapi.repository.AssignmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public List<Assignment> findAssignmentsByCourseId(String courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }

    public Optional<Assignment> getAssignmentById(String id) {
        return assignmentRepository.findById(id);
    }

    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(String id, Assignment assignment) {
        assignment.setId(id);
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(String id) {
        assignmentRepository.deleteById(id);
    }
}

package org.onecode.filemanagementapi.controller;

import org.onecode.filemanagementapi.model.assignment.Assignment;
import org.onecode.filemanagementapi.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/assignments")
@CrossOrigin("*")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable String id) {
        Optional<Assignment> assignment = assignmentService.getAssignmentById(id);
        return assignment.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    public List<Assignment> getAssignmentsByCourseId(@PathVariable String courseId) {
        return assignmentService.findAssignmentsByCourseId(courseId);
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable String id, @RequestBody Assignment assignment) {
        Assignment updatedAssignment = assignmentService.updateAssignment(id, assignment);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable String id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}

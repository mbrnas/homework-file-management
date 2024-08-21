package org.onecode.filemanagementapi.controller;

import com.amazonaws.services.s3.model.S3Object;
import org.onecode.filemanagementapi.model.assignment.Assignment;
import org.onecode.filemanagementapi.model.course.Course;
import org.onecode.filemanagementapi.model.submission.Submission;
import org.onecode.filemanagementapi.service.AssignmentService;
import org.onecode.filemanagementapi.service.CourseService;
import org.onecode.filemanagementapi.service.S3Service;
import org.onecode.filemanagementapi.service.SubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;
    private final AssignmentService assignmentService;
    private final S3Service s3Service;
    private final CourseService courseService;

    private static final Logger log = LoggerFactory.getLogger(SubmissionController.class);

    public SubmissionController(SubmissionService submissionService, AssignmentService assignmentService, S3Service s3Service, CourseService courseService) {
        this.submissionService = submissionService;
        this.assignmentService = assignmentService;
        this.s3Service = s3Service;
        this.courseService = courseService;
    }

    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionService.getAllSubmissions();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadSubmission(@RequestParam("file") MultipartFile file,
                                              @RequestParam("assignmentId") String assignmentId,
                                              @RequestParam("studentId") String studentId) {
        Optional<Assignment> assignmentOpt = assignmentService.getAssignmentById(assignmentId);
        if (assignmentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Assignment not found");
        }

        Assignment assignment = assignmentOpt.get();
        Optional<Course> courseOpt = courseService.getCourseById(assignment.getCourseId());
        if (courseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Course not found for this assignment");
        }

        try {
            submissionService.uploadSubmission(file, assignmentId, studentId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Submission uploaded successfully!");
        } catch (IOException e) {
            log.error("Failed to upload file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }


    @GetMapping("/download/{assignmentId}")
    public ResponseEntity<List<byte[]>> downloadAllSubmissionsForAssignment(@PathVariable String assignmentId) {
        Optional<Assignment> assignmentOpt = assignmentService.getAssignmentById(assignmentId);
        if (!assignmentOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        List<Submission> submissions = submissionService.findByAssignmentId(assignmentId);
        if (submissions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<byte[]> files = submissions.stream()
                .map(submission -> {
                    try {
                        S3Object s3Object = s3Service.getFile(submission.getFilePath());
                        return s3Object.getObjectContent().readAllBytes();
                    } catch (IOException e) {
                        log.error("Error reading file {}", submission.getFilePath(), e);
                        return null;
                    }
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(files);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Submission> updateSubmission(@PathVariable String id, @RequestBody Submission submission) {
        Submission updatedSubmission = submissionService.updateSubmission(id, submission);
        return ResponseEntity.ok(updatedSubmission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable String id) {
        submissionService.deleteSubmission(id);
        return ResponseEntity.noContent().build();
    }
}

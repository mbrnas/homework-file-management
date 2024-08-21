package org.onecode.filemanagementapi.service;

import org.onecode.filemanagementapi.model.assignment.Assignment;
import org.onecode.filemanagementapi.model.course.Course;
import org.onecode.filemanagementapi.model.school.School;
import org.onecode.filemanagementapi.model.submission.Submission;
import org.onecode.filemanagementapi.repository.AssignmentRepository;
import org.onecode.filemanagementapi.repository.SchoolRepository;
import org.onecode.filemanagementapi.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final SchoolRepository schoolRepository;
    private final S3Service s3Service;
    private final CourseService courseService;
    private final AssignmentService assignmentService;

    public SubmissionService(SubmissionRepository submissionRepository, AssignmentRepository assignmentRepository, SchoolRepository schoolRepository, S3Service s3Service, CourseService courseService, AssignmentService assignmentService) {
        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.schoolRepository = schoolRepository;
        this.s3Service = s3Service;
        this.courseService = courseService;
        this.assignmentService = assignmentService;
    }

    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    public List<Submission> findByAssignmentId(String assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }

    public Optional<Submission> getSubmissionById(String id) {
        return submissionRepository.findById(id);
    }

    public Submission uploadSubmission(MultipartFile file, String assignmentId, String studentId) throws IOException {
        Optional<Assignment> assignmentOpt = assignmentService.getAssignmentById(assignmentId);
        if (!assignmentOpt.isPresent()) {
            throw new IllegalStateException("Assignment not found");
        }
        Assignment assignment = assignmentOpt.get();
        Optional<Course> courseOpt = courseService.getCourseById(assignment.getCourseId());
        if (!courseOpt.isPresent()) {
            throw new IllegalStateException("Course not found for this assignment");
        }
        Course course = courseOpt.get();

        Optional<School> schoolOpt = schoolRepository.findById(course.getSchoolId());
        if (!schoolOpt.isPresent()) {
            throw new IllegalStateException("School not found for this course");
        }
        School school = schoolOpt.get();

        String sanitizedSchoolName = school.getName().replaceAll("\\W+", "_").toLowerCase();
        String sanitizedCourseName = course.getName().replaceAll("\\W+", "_").toLowerCase();
        String keyName = sanitizedSchoolName + "/" + sanitizedCourseName + "/" + assignmentId + "/" + studentId + "/"  + file.getOriginalFilename();

        s3Service.uploadFile(keyName, file);

        Submission submission = new Submission();
        submission.setAssignmentId(assignmentId);
        submission.setStudentId(studentId);
        submission.setFilePath(keyName);
        submission.setSubmittedAt(LocalDateTime.now());
        return submissionRepository.save(submission);
    }


    public Submission updateSubmission(String id, Submission submission) {
        submission.setId(id);
        return submissionRepository.save(submission);
    }

    public void deleteSubmission(String id) {
        submissionRepository.deleteById(id);
    }
}

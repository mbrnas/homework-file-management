package org.onecode.filemanagementapi.controller;

import org.onecode.filemanagementapi.dto.response.CourseDTO;
import org.onecode.filemanagementapi.model.course.Course;
import org.onecode.filemanagementapi.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/courses")
@CrossOrigin("*")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PostMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<Course> enrollStudentToCourse(@PathVariable String courseId, @PathVariable String studentId) {
        Course enrolledCourse = courseService.enrollStudentToCourse(courseId, studentId);
        if (enrolledCourse != null) {
            return ResponseEntity.ok(enrolledCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{courseId}/assignTeacher/{teacherId}")
    public ResponseEntity<Course> assignTeacherToCourse(@PathVariable String courseId, @PathVariable String teacherId) {
        Course assignedCourse = courseService.assignTeacherToCourse(courseId, teacherId);
        if (assignedCourse != null) {
            return ResponseEntity.ok(assignedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable String id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        return ResponseEntity.ok(updatedCourse);
    }

    @GetMapping("/enrolled/{studentId}")
    public ResponseEntity<List<Course>> getEnrolledCoursesForStudent(@PathVariable String studentId) {
        List<Course> enrolledCourses = courseService.getEnrolledCoursesForStudent(studentId);
        return ResponseEntity.ok(enrolledCourses);
    }

    @GetMapping("/taught/{teacherId}")
    public ResponseEntity<List<Course>> getCoursesTaughtByTeacher(@PathVariable String teacherId) {
        List<Course> courses = courseService.getCoursesTaughtByTeacher(teacherId);
        if (courses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}

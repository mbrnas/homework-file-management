package org.onecode.filemanagementapi.service;

import org.onecode.filemanagementapi.dto.response.CourseDTO;
import org.onecode.filemanagementapi.model.course.Course;
import org.onecode.filemanagementapi.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course enrollStudentToCourse(String courseId, String studentId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            List<String> enrolledStudentsIds = course.getEnrolledStudentsIds();
            if (!enrolledStudentsIds.contains(studentId)) {
                enrolledStudentsIds.add(studentId);
                return courseRepository.save(course);
            }
        }
        return null;
    }

    public Course assignTeacherToCourse(String courseId, String teacherId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setTeacherId(teacherId);
            return courseRepository.save(course);
        }
        return null;
    }

    public Optional<Course> getCourseById(String id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(String id, Course course) {
        course.setId(id);
        return courseRepository.save(course);
    }

    public List<Course> getEnrolledCoursesForStudent(String studentId) {
        List<Course> enrolledCourses = courseRepository.findByEnrolledStudentsId(studentId);
        return enrolledCourses;
    }

    public List<Course> getCoursesTaughtByTeacher(String teacherId) {
        List<Course> coursesTaught = courseRepository.findByTeacherId(teacherId);
        return coursesTaught;
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    private List<CourseDTO> convertToCourseDTOList(List<Course> courses) {
        return courses.stream()
                .map(this::convertToCourseDTO)
                .collect(Collectors.toList());
    }

    private CourseDTO convertToCourseDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        return dto;
    }
}

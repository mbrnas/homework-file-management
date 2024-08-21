package org.onecode.filemanagementapi.repository;

import org.onecode.filemanagementapi.dto.response.CourseDTO;
import org.onecode.filemanagementapi.model.course.Course;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    @Query("{ 'enrolledStudentsIds' : ?0 }")
    List<Course> findByEnrolledStudentsId(String studentId);

    List<Course> findByTeacherId(String teacherId);

    List<Course> findBySchoolId(String schoolId);

    @Aggregation({
            "{ $match: { 'teacherId': ?0 } }",  // Assuming you still want to filter by teacher ID
            "{ $project: { id: 1, name: 1 } }"  // Only include course ID and name in the results
    })
    List<CourseDTO> findCoursesWithTeacherDetails(String teacherId);

}
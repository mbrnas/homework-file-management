package org.onecode.filemanagementapi.repository;

import org.onecode.filemanagementapi.model.school.School;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SchoolRepository extends MongoRepository<School, String> {
    @Query("{ 'name' : ?0 }")
    List<School> findByName(String name);
}

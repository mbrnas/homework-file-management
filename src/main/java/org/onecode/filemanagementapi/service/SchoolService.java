package org.onecode.filemanagementapi.service;

import org.onecode.filemanagementapi.model.school.School;
import org.onecode.filemanagementapi.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public List<School> getAllSchools() {
        return schoolRepository.findAll();
    }

    public List<School> getSchoolByName(String name) {
        return schoolRepository.findByName(name);
    }

    public School getSchoolById(String id) {
        return schoolRepository.findById(id).orElse(null);
    }

    public School createSchool(School school) {
        return schoolRepository.save(school);
    }

    public School updateSchool(String id, School school) {
        school.setId(id);
        return schoolRepository.save(school);
    }

    public void deleteSchool(String id) {
        schoolRepository.deleteById(id);
    }

    public void deleteAllSchools() {
        schoolRepository.deleteAll();
    }

    public boolean isSchoolExist(String id) {
        return schoolRepository.existsById(id);
    }

}

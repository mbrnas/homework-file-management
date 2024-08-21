package org.onecode.filemanagementapi.controller;

import org.onecode.filemanagementapi.model.school.School;
import org.onecode.filemanagementapi.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("")
    public ResponseEntity<List<School>> getAllSchools() {
        if (schoolService.getAllSchools().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(schoolService.getAllSchools());
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<School>> getSchoolByName(@PathVariable String name) {
        if (schoolService.getSchoolByName(name).isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(schoolService.getSchoolByName(name));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable String id) {
        if (schoolService.getSchoolById(id) == null || !schoolService.isSchoolExist(id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(schoolService.getSchoolById(id));
        }
    }

    @PostMapping("")
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        return ResponseEntity.ok(schoolService.createSchool(school));
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> updateSchool(@PathVariable String id, @RequestBody School school) {
        if (schoolService.getSchoolById(id) == null || !schoolService.isSchoolExist(id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(schoolService.updateSchool(id, school));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable String id) {
        if (schoolService.getSchoolById(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            schoolService.deleteSchool(id);
            return ResponseEntity.noContent().build();
        }

    }
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllSchools() {
        schoolService.deleteAllSchools();
        return ResponseEntity.noContent().build();
    }

}

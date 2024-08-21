package org.onecode.filemanagementapi.controller;

import org.onecode.filemanagementapi.model.FileMetadata;
import org.onecode.filemanagementapi.service.FileMetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files/metadata")
public class FileMetadataController {

    private final FileMetadataService fileMetadataService;

    public FileMetadataController(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        fileMetadataService.saveFileMetadata(file, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully");
    }

    @GetMapping("/{fileId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<FileMetadata> getFileMetadata(@PathVariable String fileId) {
        if(fileId == null || fileId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        FileMetadata metadata = fileMetadataService.getFileMetadata(fileId);
        return ResponseEntity.ok(metadata);
    }

    @DeleteMapping("/{fileId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Void> deleteFileMetadata(@PathVariable String fileId) {
        if(fileId == null || fileId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        fileMetadataService.deleteFileMetadata(fileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<List<FileMetadata>> listUserFiles(@PathVariable String userId) {
        if(userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<FileMetadata> files = fileMetadataService.listFilesByUserId(userId);
        return ResponseEntity.ok(files);
    }
}


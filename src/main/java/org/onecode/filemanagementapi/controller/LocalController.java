package org.onecode.filemanagementapi.controller;

import org.onecode.filemanagementapi.service.LocalFileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/local/files")
public class LocalController {

    private final LocalFileStorageService localFileStorageService;

    public LocalController(LocalFileStorageService localFileStorageService) {
        this.localFileStorageService = localFileStorageService;
    }

    @Value("${app.use-local-storage}")
    private boolean useLocalStorage;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName;
        if (useLocalStorage) {
            fileName = localFileStorageService.storeFile(file);
        } else {
            return ResponseEntity.status(850).body("Upload wont work!");
        }
        return ResponseEntity.ok("File uploaded successfully: " + fileName);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource;
        if (useLocalStorage) {
            resource = localFileStorageService.loadFileAsResource(fileName);
        } else {
            return ResponseEntity.status(850).body(null);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

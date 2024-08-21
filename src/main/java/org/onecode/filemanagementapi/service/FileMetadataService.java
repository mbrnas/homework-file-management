package org.onecode.filemanagementapi.service;

import org.onecode.filemanagementapi.model.FileMetadata;
import org.onecode.filemanagementapi.repository.FileMetadataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class FileMetadataService {
    private final FileMetadataRepository fileMetadataRepository;

    public FileMetadataService(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public FileMetadata saveFileMetadata(MultipartFile file, String userId) {
        FileMetadata metadata = new FileMetadata();
        metadata.setFileName(file.getOriginalFilename());
        metadata.setFileSize(file.getSize());
        metadata.setFileType(file.getContentType());
        metadata.setUserId(userId);
        metadata.setUploadDate(new Date());
        return fileMetadataRepository.save(metadata);
    }

    public FileMetadata getFileMetadata(String fileId) {
        return fileMetadataRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
    }

    public void deleteFileMetadata(String fileId) {
        fileMetadataRepository.deleteById(fileId);
    }

    public List<FileMetadata> listFilesByUserId(String userId) {
        return fileMetadataRepository.findAllByUserId(userId);
    }

    public FileMetadata updateFileMetadata(String fileId, String updatedFileName) {
        FileMetadata metadata = getFileMetadata(fileId);
        metadata.setFileName(updatedFileName);
        return fileMetadataRepository.save(metadata);
    }

}

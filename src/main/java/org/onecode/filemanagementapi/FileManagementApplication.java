package org.onecode.filemanagementapi;

import org.onecode.filemanagementapi.model.user.ERole;
import org.onecode.filemanagementapi.model.user.Role;
import org.onecode.filemanagementapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileManagementApplication.class, args);
    }

}

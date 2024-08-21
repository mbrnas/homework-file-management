package org.onecode.filemanagementapi.repository;


import org.onecode.filemanagementapi.model.user.ERole;
import org.onecode.filemanagementapi.model.user.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);

    boolean existsByName(ERole role);
}

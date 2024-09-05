package com.appcent.todoapp.repository;

import com.appcent.todoapp.model.Role;
import com.appcent.todoapp.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByUserRoleEquals(UserRole name);
}

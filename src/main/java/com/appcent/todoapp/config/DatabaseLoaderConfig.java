package com.appcent.todoapp.config;

import com.appcent.todoapp.model.Role;
import com.appcent.todoapp.model.enums.UserRole;
import com.appcent.todoapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DatabaseLoaderConfig {

     private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            addRoleIfNotFound(UserRole.ROLE_ADMIN);
            addRoleIfNotFound(UserRole.ROLE_USER);
        };
    }

    private void addRoleIfNotFound(UserRole roleName) {

            Role role = Role.builder()
                    .userRole(roleName)
                    .build();
            roleRepository.save(role);
            System.out.println("Added " + roleName + " to the database.");
        }
}

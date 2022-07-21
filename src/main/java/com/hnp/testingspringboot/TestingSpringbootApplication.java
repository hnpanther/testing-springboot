package com.hnp.testingspringboot;

import com.hnp.testingspringboot.entity.Permission;
import com.hnp.testingspringboot.entity.Role;
import com.hnp.testingspringboot.entity.User;
import com.hnp.testingspringboot.model.PermissionApp;
import com.hnp.testingspringboot.repository.PermissionRepostory;
import com.hnp.testingspringboot.repository.RoleRepository;
import com.hnp.testingspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hnp.testingspringboot.repository")
public class TestingSpringbootApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepostory permissionRepostory;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(TestingSpringbootApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        initData();
    }



    private void initData() {

        Permission readUserPermission = new Permission(PermissionApp.READ_USER.name());
        Permission readRolePermission = new Permission(PermissionApp.READ_ROLE.name());
        Permission readPermissionPermission = new Permission(PermissionApp.READ_PERMISSION.name());
        Permission writeUserPermission = new Permission(PermissionApp.WRITE_USER.name());
        Permission writeRolePermission = new Permission(PermissionApp.WRITE_ROLE.name());
        Permission writePermissionPermission = new Permission(PermissionApp.WRITE_PERMISSION.name());
        List<Permission> permissions = Arrays.asList(
                readUserPermission,
                readRolePermission,
                readPermissionPermission,
                writeUserPermission,
                writeRolePermission,
                writePermissionPermission
        );

        this.permissionRepostory.saveAll(permissions);


        Role userRole = new Role();
        userRole.setRoleName("ROLE_USER");
        userRole.getPermissions().add(readUserPermission);
        userRole.getPermissions().add(readRolePermission);
        userRole.getPermissions().add(readPermissionPermission);

        Role adminRole = new Role();
        adminRole.setRoleName("ROLE_ADMIN");
        adminRole.getPermissions().add(readUserPermission);
        adminRole.getPermissions().add(readRolePermission);
        adminRole.getPermissions().add(readPermissionPermission);
        adminRole.getPermissions().add(writeUserPermission);
        adminRole.getPermissions().add(writeRolePermission);
        adminRole.getPermissions().add(writePermissionPermission);

        this.roleRepository.save(userRole);
        this.roleRepository.save(adminRole);


        User user1 = new User();
        user1.setUsername("admin_user");
        user1.setFirstName("test1");
        user1.setLastName("test1");
        user1.setEmail("test1@test.com");
        user1.setPassword(this.passwordEncoder.encode("pass"));
        user1.getRoles().add(adminRole);

        User user2 = new User();
        user2.setUsername("user");
        user2.setFirstName("test2");
        user2.setLastName("test2");
        user2.setEmail("test2@test.com");
        user2.setPassword(this.passwordEncoder.encode("pass"));
        user2.getRoles().add(userRole);
        user2.getPermissions().add(writeUserPermission);

        this.userRepository.save(user1);
        this.userRepository.save(user2);

    };


}

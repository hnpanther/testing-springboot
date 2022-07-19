package com.hnp.testingspringboot.repository;

import com.hnp.testingspringboot.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //no need
public interface PermissionRepostory extends JpaRepository<Permission, Long> {

    Optional<Permission> findByPermissionName(String permissionName);
}

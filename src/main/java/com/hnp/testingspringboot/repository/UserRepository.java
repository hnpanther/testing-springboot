package com.hnp.testingspringboot.repository;

import com.hnp.testingspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

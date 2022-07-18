package com.hnp.testingspringboot.repository;

import com.hnp.testingspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //no need
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE username=:username", nativeQuery = true)
    Optional<User> findUserByUsername(@Param("username") String username);

    @Query(value = "SELECT u FROM User u WHERE u.firstName=:firstName")
    List<User> findUserByFirstName(@Param("firstName") String fristName);




}

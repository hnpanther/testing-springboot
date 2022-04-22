package com.hnp.testingspringboot.repository;

import com.hnp.testingspringboot.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setEmail("useremail@email.com");
        user.setUsername("base_user");
        user.setPassword("password");
        user.setFirstName("john");
        user.setLastName("smith");
    }



    // Junit Test for create user
    @DisplayName("JUnit Test for create and save new user")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser() {

        // given - precondition or setup
        User newUser = new User();
        newUser.setEmail("newuser@email.com");
        newUser.setUsername("new_user");
        newUser.setPassword("password");
        newUser.setFirstName("jack");
        newUser.setLastName("james");


        // when - action or behavior that we are going test
        User savedUser = this.userRepository.save(newUser);

        // then - verify the output
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    // Junit Test for find all users
    @DisplayName("Junit Test for find all users")
    @Test
    public void givenUserList_whenfindAll_thenReturnUserList() {

        // given - precondition or setup
        User newUser = new User();
        newUser.setEmail("newuser@email.com");
        newUser.setUsername("new_user");
        newUser.setPassword("password");
        newUser.setFirstName("jack");
        newUser.setLastName("james");

        this.userRepository.save(user);
        this.userRepository.save(newUser);

        // when - action or behavior that we are going test
        List<User> users = this.userRepository.findAll();

        // then - verify the output
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(2);

    }

    // Junit Test for find by user id
    @DisplayName("// Junit Test for find by user id")
    @Test
    public void givenUser_whenFindById_thenReturnUser() {

        // given - precondition or setup
        User savedUser = userRepository.save(user);
        userRepository.save(user);

        // when - action or behavior that we are going test
        User findedUser = userRepository.findById(user.getId()).get();

        // then - verify the output
        assertThat(findedUser).isNotNull();

    }

    // Junit Test for update user
    @DisplayName("Junit Test for update user")
    @Test
    public void givenUser_whenUpdateUser_thenReturnUpdatedUser() {

        // given - precondition or setup
        userRepository.save(user);

        // when - action or behavior that we are going test
        User savedUser = userRepository.findById(user.getId()).get();
        savedUser.setUsername("updatedUsername");
        savedUser.setEmail("update@email.com");
        User updatedUser = userRepository.save(savedUser);

        // then - verify the output
        assertThat(updatedUser.getUsername()).isEqualTo("updatedUsername");
        assertThat(updatedUser.getEmail()).isEqualTo("update@email.com");

    }

    // Junit Test for delete user
    @DisplayName("Junit Test for delete user")
    @Test
    public void givenUser_whenDeleteById_then() {

        // given - precondition or setup
        userRepository.save(user);

        // when - action or behavior that we are going test
        userRepository.deleteById(user.getId());
        Optional<User> optionalUser = userRepository.findById(user.getId());
        // then - verify the output
        assertThat(optionalUser).isEmpty();

    }

    // Junit Test for find user by email
    @DisplayName("Junit Test for find user by email")
    @Test
    public void givenUserEmail_whenFindByEmail_thenReturnUser() {

        // given - precondition or setup
        userRepository.save(user);
        String email = user.getEmail();

        // when - action or behavior that we are going test
        User findedUser = userRepository.findByEmail(email).get();

        // then - verify the output
        assertThat(findedUser).isNotNull();

    }

    // Junit Test for find user by username(native query)
    @DisplayName("Junit Test for find user by username(native query)")
    @Test
    public void givenUsername_whenfindUserByUsername_thenReturnUser() {

        // given - precondition or setup
        String username = user.getUsername();
        userRepository.save(user);

        // when - action or behavior that we are going test
        User findedUser = userRepository.findUserByUsername(username).get();

        // then - verify the output
        assertThat(findedUser).isNotNull();

    }

    // Junit Test for find user by first name
    @DisplayName("Junit Test for find user by first name(jpql query)")
    @Test
    public void givenUserList_whenfindUserByFirstName_thenReturnUserList() {

        // given - precondition or setup
        User newUser = new User();
        newUser.setEmail("newuser@email.com");
        newUser.setUsername("new_user");
        newUser.setPassword("password");
        newUser.setFirstName("john");
        newUser.setLastName("james");

        this.userRepository.save(user);
        this.userRepository.save(newUser);

        // when - action or behavior that we are going test
        List<User> userList = userRepository.findUserByFirstName("john");

        // then - verify the output
        assertThat(userList).isNotEmpty();
        assertThat(userList.size()).isEqualTo(2);

    }
}

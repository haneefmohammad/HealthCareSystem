package com.healthcaresystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.healthcaresystem.dto.UserDTO;
import com.healthcaresystem.entity.User;
import com.healthcaresystem.exception.InvalidLoginException;
import com.healthcaresystem.exception.InvalidPasswordException;
import com.healthcaresystem.exception.InvalidPhoneNumberException;
import com.healthcaresystem.exception.InvalidUserNameException;
import com.healthcaresystem.exception.UserNotFoundException;
import com.healthcaresystem.repository.UserRepository;
import com.healthcaresystem.serviceimpl.UserService;

@SpringBootTest
 class UserServiceTest {
	
	@Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
     void testRegisterNewUser_ValidUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Haneef");
        userDTO.setUserPassword("haneef@786");
        userDTO.setUserEmail("haneef001@gmail.com");
        userDTO.setPhoneNumber(9642515574L); 
        userDTO.setAge(22);
        userDTO.setGender("Male");

        when(userRepository.save(any(User.class))).thenReturn(new User());

        String result = userService.registerNewUser(userDTO);

        assertTrue(result.startsWith("User registered successfully with user id:"));
        // Add more specific assertions if possible
    }

    @Test
    void testRegisterNewUser_InvalidPassword() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(" hany");
        userDTO.setUserPassword("haneef786");
        userDTO.setUserEmail("haneef001@gmail.com");
        userDTO.setPhoneNumber(9642515574L); 
        userDTO.setAge(22);
        userDTO.setGender("Male");

        assertThrows(InvalidPasswordException.class, () -> userService.registerNewUser(userDTO));
    }

    @Test
    void testRegisterNewUser_InvalidPhoneNumber() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Haneef");
        userDTO.setUserPassword("haneef@786");
        userDTO.setUserEmail("haneef001@gmail.com");
        userDTO.setPhoneNumber(96425574L); 
        userDTO.setAge(22);
        userDTO.setGender("Male");

        assertThrows(InvalidPhoneNumberException.class, () -> userService.registerNewUser(userDTO));
    }

    @Test
     void testRegisterNewUser_InvalidName() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(" hany");
        userDTO.setUserPassword("haneef@7");
        userDTO.setUserEmail("haneef001@gmail.com");
        userDTO.setPhoneNumber(9642515574L); 
        userDTO.setAge(22);
        userDTO.setGender("Male");

        assertThrows(InvalidUserNameException.class, () -> userService.registerNewUser(userDTO));
    }

    @Test
     void testLoginUser_ValidCredentials() {
        Map<String, String> credentials = Map.of("email", "valid_email", "password", "valid_password");
        User user = new User();
        user.setUserPassword("valid_password");

        when(userRepository.findByUserEmailIgnoreCase("valid_email")).thenReturn(Optional.of(user));

        String result = userService.loginUser(credentials);

        assertEquals("Login Successful", result);
    }

    @Test
     void testLoginUser_InvalidEmail() {
        Map<String, String> credentials = Map.of("email", "invalid_email", "password", "valid_password");

        when(userRepository.findByUserEmailIgnoreCase("invalid_email")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.loginUser(credentials));
    }

    @Test
     void testLoginUser_InvalidPassword() {
        Map<String, String> credentials = Map.of("email", "valid_email", "password", "invalid_password");
        User user = new User();
        user.setUserPassword("valid_password");

        when(userRepository.findByUserEmailIgnoreCase("valid_email")).thenReturn(Optional.of(user));

        assertThrows(InvalidLoginException.class, () -> userService.loginUser(credentials));
    }


    @Test
     void testDeleteUserById_UserExists() {
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        String result = userService.deleteUserById(userId);

        assertTrue(result.startsWith("User deleted successfully"));
    }

    @Test
     void testDeleteUserById_UserNotExists() {
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(userId));
    }

    @Test
     void testUpdatedUserDetails_ValidUser() {
        int userId = 1;
        User updatedUser = new User();
        updatedUser.setUserName("Haneefuddin");
        updatedUser.setAge(25);
        updatedUser.setUserEmail("haneefuddin001@gmail.com");
        updatedUser.setGender("Male");
        updatedUser.setPhoneNumber(9654315574L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updatedUserDetails(userId, updatedUser);

        assertNotNull(result);
        assertEquals("Haneefuddin", result.getUserName());
        assertEquals(25, result.getAge());
        assertEquals("haneefuddin001@gmail.com", result.getUserEmail());
        assertEquals("Male", result.getGender());
        assertEquals(9654315574L, result.getPhoneNumber());
    }

    @Test
     void testUpdatedUserDetails_InvalidUser() {
        int userId = 1;
        User updatedUser = new User(/* fill with updated user details */);
        updatedUser.setUserName("Haneefuddin");
        updatedUser.setAge(25);
        updatedUser.setUserEmail("haneefuddin001@gmail.com");
        updatedUser.setGender("Male");
        updatedUser.setPhoneNumber(9654315574L);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updatedUserDetails(userId, updatedUser));
    }

    // More test cases for different scenarios in updatedUserDetails method
}


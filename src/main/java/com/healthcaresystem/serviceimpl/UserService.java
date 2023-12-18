package com.healthcaresystem.serviceimpl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthcaresystem.dto.UserDTO;
import com.healthcaresystem.entity.User;
import com.healthcaresystem.exception.InvalidLoginException;
import com.healthcaresystem.exception.InvalidPasswordException;
import com.healthcaresystem.exception.InvalidPhoneNumberException;
import com.healthcaresystem.exception.InvalidUserNameException;
import com.healthcaresystem.exception.UserNotFoundException;
import com.healthcaresystem.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Transactional
	public String registerNewUser(UserDTO userDTO) {
		validateUser(userDTO);

		User user = new User();
		user.setUserName(userDTO.getUserName());
		user.setUserPassword(userDTO.getUserPassword());
		user.setUserEmail(userDTO.getUserEmail());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setAge(userDTO.getAge());
		user.setGender(userDTO.getGender());
		user.setUserRole("user");

		User savedUser = userRepository.save(user);
		return "User registered successfully with user id:" + savedUser.getUserId();

	}

	private void validateUser(UserDTO userDTO) {
		if (!passwordValid(userDTO.getUserPassword())) {
			throw new InvalidPasswordException("Invalid Password Format");
		}
		if (!phoneNumberValid(userDTO.getPhoneNumber())) {
			throw new InvalidPhoneNumberException("The digits should be 10 and should be valid");
		}
		if (!nameValid(userDTO.getUserName())) {
			throw new InvalidUserNameException(
					"The name should not be blank and the name should should start with capital");
		}
		
	}

	private boolean passwordValid(String userPassword) {
		String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
		return userPassword.matches(passwordRegex);
	}

	private boolean phoneNumberValid(long phoneNumber) {
		String phoneNumberStr = String.valueOf(phoneNumber);
		return phoneNumberStr.matches("[6-9][0-9]{9}");
	}

	private boolean nameValid(String userName) {
		return userName != null && !userName.isBlank() && userName.matches("^[A-Z][a-zA-Z]*$");
	}

	@Transactional
	public String loginUser(Map<String, String> credentials) {
		String userEmail = credentials.get("email");
		String userPassword = credentials.get("password");
		Optional<User> user = userRepository.findByUserEmailIgnoreCase(userEmail);
	    
	    if (user.isPresent()) {
	    	
	        if (user.get().getUserPassword().equals(userPassword)) {
	        	
	            return "Login Successful";
	        } else {
	            throw new InvalidLoginException("Invalid credentials");
	        }
	    } else {
	        throw new UserNotFoundException("User not found");
	    }
	}

	@Transactional
	public String deleteUserById(int userId)
	{
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent())
		{
			userRepository.deleteById(userId);
			return "User deleted successfully "+userId ;
			
		}
		else
		{
			throw new UserNotFoundException("User not found");
		}
		
		
	}
	
	@Transactional
		public User updatedUserDetails(int userId, User updatedUserDetails)
		{
			User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user id not found"));
			
			existingUser.setUserName(updatedUserDetails.getUserName());
			existingUser.setAge(updatedUserDetails.getAge());
			existingUser.setUserEmail(updatedUserDetails.getUserEmail());
			existingUser.setGender(updatedUserDetails.getGender());
			existingUser.setPhoneNumber(updatedUserDetails.getPhoneNumber());
			
			
			return userRepository.save(existingUser);
			
		}

}

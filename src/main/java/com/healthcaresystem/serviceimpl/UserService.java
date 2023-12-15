package com.healthcaresystem.serviceimpl;

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
			throw new InvalidPhoneNumberException("The digits should be 10");
		}
		if (!nameValid(userDTO.getUserName())) {
			throw new InvalidUserNameException(
					"The name should not be blank and the name should should start with capital");

		}
	}

	private boolean passwordValid(String userPassword) {
		String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$";
		return userPassword.matches(passwordRegex);
	}

	private boolean phoneNumberValid(long phoneNumber) {
		String phoneNumberStr = String.valueOf(phoneNumber);
		return phoneNumberStr.matches("\\d{10}");
	}

	private boolean nameValid(String userName) {
		return userName != null && !userName.isBlank() && userName.matches("^[^\\s].*");
	}

	@Transactional
	public String loginUser(String userEmail, String userPassword) {
	    Optional<User> user = userRepository.findByUserEmailIgnoreCase(userEmail);
	    if (user.isPresent()) {
	    	System.out.println(user.get().getUserName());
	    	System.out.println(user.get().getUserPassword().equals(userPassword));
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
	public void deleteUserById(int userId)
	{
		userRepository.deleteById(userId);
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

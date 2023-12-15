package com.healthcaresystem.iservice;

import com.healthcaresystem.entity.User;

public interface UserService {
	
	User registerNewUser(int userId, String userName,String userPassword, long phoneNumber, String gender,int age);
	
	User loginUser(String userEmail, String userPassword);
	
	void deleteUserById(int userId);
	
	User updatedUserDetails(int userId, User updateUserDetails);

}


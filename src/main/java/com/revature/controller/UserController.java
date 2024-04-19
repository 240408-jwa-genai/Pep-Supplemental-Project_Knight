package com.revature.controller;

import com.revature.MainDriver;
import com.revature.models.User;
import com.revature.models.Authent;
import com.revature.service.UserService;


public class UserController {
	
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	public void authenticate(Authent loginRequestData) {
		// since we are checking the credentials against the database we can simply pass them to the service layer
		User possibleUser = userService.authenticate(loginRequestData);
		if (possibleUser.getId() != 0){
			MainDriver.loggedInUserId = possibleUser.getId();
			System.out.println(String.format("Hello %s! Welcome to the Planetarium", possibleUser.getUsername()));
		} else {
			System.out.println("Username/Password combo invalid, please try again");
		}
	}

	public void register(User registerRequestData) {
		/*
			Because the controller is only responsible for getting user input and returning messages/data
			to the user, we will simply pass the User data into the service layer, and then depending on
			the response back from the Service layer, tell the user their request was successful or not
		 */

		/*
			The register method is set to return a User object no matter what, so I chose to return an
			empty User (no fields set) if the registration action failed. This lets me check if the Id is
			initialized, and if not, I know the register action failed and the user should be informed.
			Alternatively, if the registration was a success, the ID should be initialized, and I can give
			a success message to the user
		 */
		User userResponse = userService.register(registerRequestData);
		if (userResponse.getId() != 0){
			System.out.println("Registration successful! Enjoy using the Planetarium!");
		} else {
			System.out.println("Registration failed: please double check your username and password and try again.");
		}
	}

	public void logout() {
		MainDriver.loggedInUserId = 0;
	}

	// Allows us to check if a userId is the same as the ID of whoever is currently logged in.
	public boolean checkAuthorization(int userId) {	
		// TODO: implement
		if(userId == MainDriver.loggedInUserId)
		{
			return true;
		}
		return false;
	}
}

package com.hcl.service;

import com.hcl.exception.RoleDoesNotExistException;
import com.hcl.exception.UsernameUnavailableException;
import com.hcl.model.User;

public interface UserService {

	User registerUser(User newUser) throws UsernameUnavailableException, RoleDoesNotExistException;
	
}

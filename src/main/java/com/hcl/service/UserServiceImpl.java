package com.hcl.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcl.exception.RoleDoesNotExistException;
import com.hcl.exception.UsernameUnavailableException;
import com.hcl.model.Role;
import com.hcl.model.User;
import com.hcl.repository.RoleRepository;
import com.hcl.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository ur;
	
	@Autowired
	RoleRepository rr;
	
	@Override
	public User registerUser(User newUser) throws UsernameUnavailableException, RoleDoesNotExistException {
		if(ur.getUserByUsername(newUser.getUsername())!=null) {
			throw new UsernameUnavailableException("the requested username is not available");
		}
		
		//the user will have only the ROLE_USER role
		Role userRole = rr.findByRole("ROLE_USER");
		if(userRole==null) {
			throw new RoleDoesNotExistException("role ROLE_USER does not exist");
		}
		newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
		newUser.setEnabled(true);
		newUser.setRoles(new HashSet<Role>());
		newUser.getRoles().add(userRole);
		
		return ur.save(newUser);
	}

}

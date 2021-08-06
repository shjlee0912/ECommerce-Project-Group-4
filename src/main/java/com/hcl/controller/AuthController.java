package com.hcl.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hcl.exception.RoleDoesNotExistException;
import com.hcl.exception.UsernameUnavailableException;
import com.hcl.model.MyUserDetails;
import com.hcl.model.User;
import com.hcl.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService us;
	
	@GetMapping("/")
	public String sendToPage(Authentication auth) {
		if(!auth.isAuthenticated()) {
			return "redirect:/login";
		} else if( ((MyUserDetails) auth.getPrincipal()).hasRole("ROLE_ADMIN")) {
			return "redirect:/admin";
		} else {
			return "redirect:/user";
		}
	}

	@GetMapping("/register")
	public String registrationForm(String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "username is unavailable");
		}
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute User user, HttpServletRequest req) throws RoleDoesNotExistException {
		try {
			String password = user.getPassword();
			us.registerUser(user);
			req.login(user.getUsername(), password);
		} catch (UsernameUnavailableException e) {
			return "redirect:/register?error";
		} catch (ServletException e) {
			e.printStackTrace();
			return "redirect:/register";
		}
		return "redirect:/user";
	}

	@GetMapping("/login")
	public String loginForm(String error, String logout, Model model) {
		if (error != null) {
			model.addAttribute("error", "invalid credentials");
		}
		if (logout != null) {
			model.addAttribute("logout", "successefully logged out");
		}
		model.addAttribute("user", new User());
		return "login";
	}

}

package com.spring.treemap.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;
@Log4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("Login Success");
		
		List<String> roleNames = new ArrayList<>();
		
		authentication.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		
		User user = (User) authentication.getPrincipal();
		
		log.info("roleName: "+roleNames);
		if(roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect("/treeMap/userMapBoard?userEmail="+user.getUsername());
		}else if(roleNames.contains("ROLE_DELETED")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/logout");
			dispatcher.forward(request, response);
		}

	}

}

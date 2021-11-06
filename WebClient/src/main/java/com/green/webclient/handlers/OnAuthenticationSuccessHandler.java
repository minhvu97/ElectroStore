package com.green.webclient.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.green.webmodels.enumerate.*;
import com.green.webmodels.formdata.CustomerData;
import com.green.webclient.security.CustomerOAuth2User;
import com.green.webclient.services.CustomerService;
import com.green.webmodels.entities.*;

@Component
public class OnAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private CustomerService customerService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("OnAuthenticationSuccessHandler: " + authentication.getName());
		
		CustomerOAuth2User oath2user = (CustomerOAuth2User) authentication.getPrincipal();
		
		String email = oath2user.getEmail();
		String name = oath2user.getName();
		String servletPath = request.getServletPath();
		String firstName = oath2user.getGivenName();
		String lastName = oath2user.getFamilyName();
		
		System.out.println("OnAuthenticationSuccessHandler: " + email + " - " + name);
		
		AuthProvider provider = AuthProvider.BASIC;
		
		if (servletPath.contains("google")) {
			provider = AuthProvider.GOOGLE;
		} else if (servletPath.contains("facebook")) {
			provider = AuthProvider.FACEBOOK;
		}
		
		Customer customer = customerService.getByEmail(email);
		
		if (customer == null) {
			customerService.registerNewCustomer(email, firstName, lastName, provider);
		} else {
			customerService.updateGoogleCustomer(customer);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}

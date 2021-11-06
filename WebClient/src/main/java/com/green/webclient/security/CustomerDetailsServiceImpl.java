package com.green.webclient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.green.webmodels.entities.*;
import com.green.webclient.repository.CustomerRepository;

public class CustomerDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(email);
		
		if (customer == null) {
			throw new UsernameNotFoundException("Email khong ton tai");
		}
		
		System.out.println(customer.getId());
		System.out.println(customer.getEmail());
		System.out.println(customer.getPassword());
		System.out.println(customer.getFirstName());
		
		return new MyCustomerDetails(customer);
	}

}

package com.green.webclient.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.webmodels.enumerate.*;
import com.green.webclient.helpers.PasswordManager;
import com.green.webclient.repository.CustomerRepository;
import com.green.webmodels.entities.*;
import com.green.webmodels.formdata.*;

import net.bytebuddy.utility.RandomString;
@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	public List<Customer> getAllCustomers() {
		return repository.findAll();
	}
	
	public Customer getByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public Customer getCustomerById(Integer id) {
		return repository.getById(id);
	}
	
	public void registerNewCustomer(String email, String firstName, String lastName, AuthProvider provider) {
		Date createDate = new Date();
		
		Customer newCustomer = new Customer();
		
		newCustomer.setEmail(email);
		newCustomer.setFirstName(firstName);
		newCustomer.setLastName(lastName);
		newCustomer.setEmailVerified(false);
		newCustomer.setCreateDate(createDate);
		newCustomer.setLastLogin(createDate);
		newCustomer.setAuthProvider(provider);
		newCustomer.setEnabled(true);
		
		repository.save(newCustomer);
	}
	
	public void saveCustomer(Customer customer) {
		repository.save(customer);
	}
	
	public Customer registerNewCustomer(CustomerData customerData) {
		
		Customer regCustomer = new Customer();
		
		regCustomer.setEmail(customerData.getEmail());
		regCustomer.setPassword(PasswordManager.getBCrypPassword(customerData.getPassword()));
		regCustomer.setFirstName(customerData.getFirstName());
		regCustomer.setLastName(customerData.getLastName());
		regCustomer.setPhoneNumber(customerData.getPhoneNumber());
		regCustomer.setCreateDate(new Date());
		regCustomer.setAuthProvider(AuthProvider.BASIC);
		regCustomer.setEnabled(false);
		
		// generate random code for email verification
		String randomCode = RandomString.make(64);
		regCustomer.setVerificationCode(randomCode);
		
		repository.save(regCustomer);
		
		return regCustomer;
	}
	
//	public void sendVerificationEmail(Customer customer)
//	{
//		String subject = "Please verify your registration";
//		String senderName = "Electro Team";
//		String mailContent = "<p>Dear " + customer.getName() + ",</p>";
//		mailContent += "<p>Please click the link below to verify to your registration:</p>";
//		
//		mailContent += "<p>Thank you<br>The Electro Team</p>";
//	}
	
	public void updateCustomer(CustomerData customerData, Integer id) {
		
		Customer customer = repository.findCustomerByID(id);
		
		Date loginDate = new Date();
		
		customer.setEmail(customerData.getEmail());
		
		if (customerData.getPassword() != null && !customerData.getPassword().isEmpty()) {
			
			customer.setPassword(PasswordManager.getBCrypPassword(customerData.getPassword()));
		}
		
		customer.setFirstName(customerData.getFirstName());
		customer.setLastName(customerData.getLastName());
		customer.setPhoneNumber(customerData.getPhoneNumber());
		customer.setLastLogin(loginDate);
		customer.setPhoto(customerData.getPhoto());
		
		repository.save(customer);
	}
	
	public void updateGoogleCustomer(Customer customer)
	{
		Date loginDate = new Date();
		customer.setLastLogin(loginDate);
		repository.save(customer);
	}

	public Customer getCustomerByPhone(String phoneNumber) {
		return repository.findByPhoneNumber(phoneNumber);
	}
	
	public boolean verify(String verificationCode)
	{
		Customer customer = repository.findByVerificationCode(verificationCode);
		
		if (customer == null || customer.isEnabled())
		{
			return false;
		}
		else
		{
			repository.enableCustomer(customer.getId());
			customer.setEnabled(true);
			return true;
		}
	}
}

package com.green.webclient.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webmodels.entities.*;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	//Jpa HQL
//	@Query("SELECT customer FROM Customer customer WHERE customer.email = :email")
//	public Customer getByEmail(@Param("email") String email);
	
	public Customer findByEmail(String email);

	public Customer findByPhoneNumber(String phoneNumber);
	
	@Query(value = "SELECT * FROM Customers c WHERE c.id = ?1", nativeQuery = true)
	public Customer findCustomerByID(Integer id);
	
	@Query("SELECT c FROM Customer c WHERE c.verificationCode = ?1")
	public Customer findByVerificationCode(String code);
	
	@Query("UPDATE Customer c SET c.enabled = true WHERE c.id = ?1")
	@Modifying
	public void enableCustomer(Integer id);
}

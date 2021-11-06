package com.green.webadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.webmodels.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}

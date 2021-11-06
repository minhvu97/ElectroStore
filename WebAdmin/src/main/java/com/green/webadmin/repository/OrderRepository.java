package com.green.webadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.webmodels.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}

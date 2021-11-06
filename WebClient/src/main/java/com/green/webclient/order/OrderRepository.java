package com.green.webclient.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.green.webmodels.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

//	@Query("SELECT o FROM Order o WHERE ")
	
	@Query(value = "SELECT * FROM Orders WHERE customer_id = ?1", nativeQuery=true)
	public List<Order> getOrdersByCustomerID(Integer customer_id);
	
	@Query(value = "SELECT * FROM Orders WHERE customer_id = ?1 AND id=(SELECT MAX(ID) FROM Orders)", nativeQuery=true)
	public Order getNewestOrderByCustomerID(Integer customer_id);	
}
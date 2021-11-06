package com.green.webadmin.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webmodels.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	@Query(value = "Select * From products Where code = ?1", nativeQuery = true)
	public Product findByCode(String code);
	
	@Query(value = "Select * From products Where name like %?1%", nativeQuery = true)
	public List<Product> searchProductByName(@Param ("name") String name);
}

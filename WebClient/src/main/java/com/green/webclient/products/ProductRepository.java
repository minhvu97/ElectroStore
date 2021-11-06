package com.green.webclient.products;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webmodels.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	public Product findByCode(String code);
	
	@Query("SELECT p FROM Product p WHERE p.code = :code")
	public Product getProductByCode(@Param("code") String code);
	
	public List<Product> findByName(String name);
	
	@Query(value = "SELECT * FROM products WHERE MATCH(name) AGAINST (?1)", nativeQuery = true)
	public List<Product> fullTextSearchProductByName(String name);
	
	@Query(value = "SELECT * FROM products  WHERE category_id like %?1%", nativeQuery = true)
	public List<Product> findByCategory(String id);
	
	@Query(value = "SELECT * FROM products WHERE MATCH(name) AGAINST (?1) LIMIT 5", nativeQuery = true)
	public List<Product> autoCompleteProductByName(String name);
	
	@Query(value = "SELECT * FROM ( SELECT * FROM products ORDER BY id DESC LIMIT 3 ) as products ORDER BY id", nativeQuery = true)
	public List<Product> getLast3Products();
	
	@Query(value = "select * from products where code like %?1%", nativeQuery = true)
	public List<Product> getProductByCategoryCode(String code);
	
}

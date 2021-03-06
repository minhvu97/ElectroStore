package com.green.webadmin.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webmodels.entities.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public List<Product> getAllProducts() {
		return repository.findAll();
	}
	
	public Product getProductByCode(String code) {
		
		return repository.findByCode(code);
	}
	
	public void saveProduct(Product product) {
		repository.save(product);
	}
	
	public void deleteProdcutById(Integer id) {
		repository.deleteById(id);
	}
	
	public List<Product> searchProductByName(String name){
		return repository.searchProductByName(name);
	}
	
}

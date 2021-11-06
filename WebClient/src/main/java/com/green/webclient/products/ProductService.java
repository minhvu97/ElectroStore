package com.green.webclient.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.green.webclient.category.CategoryRepository;
import com.green.webmodels.entities.Product;

@Service
public class ProductService {

	public static int PAGE_SIZE = 6;
	
	public static int FULL_SIZE = 50;
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Product> getAllProducts() {
		return repository.findAll();
	}
	
	public Product getProductByCode(String code) {
		
		//return repository.findByCode(code);
		return repository.getProductByCode(code);
	}
	
	public List<Product> getProductByCategoryCode(Integer id) {
		String code = categoryRepository.getCategoryById(id).getCode();
		return repository.getProductByCategoryCode(code);
	}
	
	public List<Product> getProductByName(String name)
	{
		return repository.findByName(name);
	}
	
	public List<Product> getProductByCategoryId(String id)
	{
		return repository.findByCategory(id);
	}
	
	public List<Product> fullTextSearchProductByName(String name){
		return repository.fullTextSearchProductByName(name);
	}
	
	public void saveProduct(Product product) {
		repository.save(product);
	}
	
	public void deleteProdcutByCode(String code) {
		//Viet query///
	}
	
	public void deleteProdcutById(Integer id) {
		repository.deleteById(id);
	}
	
	public Page<Product> getProductsWithPage(int pageNum, String sortType) {
		Pageable pageable;
		
		if (sortType.equals("low-to-high")) // price increase
		{
			if (pageNum >= 1) {
				pageable = PageRequest.of(pageNum - 1, ProductService.FULL_SIZE, Sort.by("salePrice"));
			} else {
				pageable = PageRequest.of(0, ProductService.FULL_SIZE, Sort.by("salePrice"));
			}
		}
		else if (sortType.equals("high-to-low")) // price decrease
		{
			if (pageNum >= 1) {
				pageable = PageRequest.of(pageNum - 1, ProductService.FULL_SIZE, Sort.by("salePrice").descending());
			} else {
				pageable = PageRequest.of(0, ProductService.FULL_SIZE, Sort.by("salePrice").descending());
			}			
		}
		else {
			if (pageNum >= 1) {
				pageable = PageRequest.of(pageNum - 1, ProductService.PAGE_SIZE);
			} else {
				pageable = PageRequest.of(0, ProductService.PAGE_SIZE);
			}
		}
		
		return repository.findAll(pageable);
	}
	
	public List<Product> getLast3Products()
	{
		return repository.getLast3Products();
	}
}

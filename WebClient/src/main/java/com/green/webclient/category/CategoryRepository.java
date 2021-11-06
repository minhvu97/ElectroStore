package com.green.webclient.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.green.webmodels.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Query("SELECT c FROM Category c WHERE c.parent = null")
	public List<Category> getRootCategory();
	
	@Query("SELECT c FROM Category c WHERE c.parent = 1")
	public List<Category> getSubRootCategory();
	
	@Query("SELECT c FROM Category c WHERE c.id = ?1")
	public Category getCategoryById(Integer id);
}

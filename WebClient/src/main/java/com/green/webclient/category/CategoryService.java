package com.green.webclient.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webmodels.entities.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repository;

	public List<Category> getAllCategory(){
		return repository.findAll();
	}
	
	public List<Category> getRootCategory() {
		return repository.getRootCategory();
	}
	
	public List<Category> getSubRootCategory() {
		return repository.getSubRootCategory();
	}
	
	public Category getById(Integer id) {
		return repository.getById(id);
	}
	
	public List<Category> getListParent(Category category){
		List<Category> listParent = new ArrayList<Category>();
		if(category != null) {
			Category parent =  category.getParent();
			while(parent != null)
			{
				listParent.add(0, parent);
				parent = parent.getParent();
			}
			listParent.add(category);
		}
		return listParent;
	}
}

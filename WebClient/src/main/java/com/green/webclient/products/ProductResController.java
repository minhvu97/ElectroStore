package com.green.webclient.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.green.webmodels.entities.Product;

@RestController
public class ProductResController {

	@Autowired
	private ProductRepository repositoryProduct;

	@GetMapping("api/products")
	public List<Product> greeting(@RequestParam(value = "name", defaultValue = "") String name) {
		List<Product> listProducts = new ArrayList<>();
		if(name.equals("")) {
			listProducts = repositoryProduct.findAll();		
		}
		else
		{
			listProducts = repositoryProduct.fullTextSearchProductByName(name);
		}

		return listProducts;
	}
	
	@GetMapping("api/products/autocomplete")
	public List<ProductAutoComplete> productAutoComplete(@RequestParam(value = "name", defaultValue = "") String name){
		List<Product> listProducts = new ArrayList<>();
		List<ProductAutoComplete> listAuto = new ArrayList<>();
		if(!name.equals("")) {			
			listProducts = repositoryProduct.autoCompleteProductByName(name);
		}

		for(Product product:listProducts) {
			ProductAutoComplete newProduct = new ProductAutoComplete();
			newProduct.setId(product.getId());
			newProduct.setName(product.getName());
			newProduct.setPhoto(product.getPhoto());
			newProduct.setCode(product.getCode());
			listAuto.add(newProduct);
		}
		return listAuto;
	}
}

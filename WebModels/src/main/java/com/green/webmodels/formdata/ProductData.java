package com.green.webmodels.formdata;

import javax.persistence.Transient;

import com.green.webmodels.entities.Category;
import com.green.webmodels.entities.Product;

public class ProductData {

	private Integer id;

	private String name;
	
	private String code;
	
	private String description;
	
	private String photo;
	
	private Float price;
	
	private Float sale_price;
	
	private Category category;

	public static ProductData copyValueFormEntity(Product entity) {
		ProductData data = new ProductData();
		
		data.id = entity.getId();
		data.name = entity.getName();
		data.code = entity.getCode();
		data.price = entity.getPrice();
		data.sale_price = entity.getSalePrice();
		data.description = entity.getDescription();
		data.category = entity.getCategory();
		
		data.photo = entity.getPhoto();
		return data;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getSale_price() {
		return sale_price;
	}

	public void setSale_price(Float sale_price) {
		this.sale_price = sale_price;
	}
	
	@Transient
	public String getPhotoPath() {
		System.out.println("get photo path: = " + photo);
		if (id != null & photo != null) {
			return "/product-photos/" + id + "/" + photo;
		}
		return null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}

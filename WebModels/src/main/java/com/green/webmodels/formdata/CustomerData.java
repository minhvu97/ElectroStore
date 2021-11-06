package com.green.webmodels.formdata;

import java.util.Set;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.green.webmodels.entities.Address;
import com.green.webmodels.entities.Customer;


public class CustomerData {

	@Email(message = "Ban phai su dung dia chi email chinh xac")
	private String email;
	@NotNull
	@Size(min = 8, max = 24)
	private String password;
	@NotNull(message = "Ten ban khong duoc de trong")
	@Size(min = 2, max = 10, message = "do dai ten ban phai tu 2 ky tu , toi da 10 ky tu")
	private String firstName;
	@Size(min = 2, max = 10, message = "do dai ten ban phai tu 2 ky tu , toi da 10 ky tu")
	private String lastName;
	@NotNull
	@Size(min = 10, max = 12, message = "do dai ten ban phai tu 10 ky tu , toi da 12 ky tu")
	private String phoneNumber;
	
	private Set<Address> addresses;

	private String imagebase64;
	
	private String photo;
	
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Address> getAddress() {
		return addresses;
	}

	public void setAddress(Set<Address> addresses) {
		this.addresses = addresses;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getImagebase64() {
		return imagebase64;
	}

	public void setImagebase64(String imagebase64) {
		this.imagebase64 = imagebase64;
	}
	
	@Override
	public String toString() {
		return email + " " + password + " " + firstName + " " + lastName;
	}
	
	@Transient
	public String getName() {
		return firstName + " " + lastName;
	}
	
	public static CustomerData getCustomerData(Customer customer) {
		CustomerData customerData = new CustomerData();
		
		customerData.setId(customer.getId());
		customerData.setEmail(customer.getEmail());
		customerData.setPassword(customer.getPassword());
		customerData.setFirstName(customer.getFirstName());
		customerData.setLastName(customer.getLastName());
		customerData.setPhoneNumber(customer.getPhoneNumber());
		customerData.setAddress(customer.getAddresses());
		customerData.setPhoto(customer.getPhoto());
//		customerData.setPhotoPath((String) attributes.get("picture"));

		return customerData;
	}
	
//	@Transient
//	public String getPhotoPath() {
//		System.out.println("get photo path: = " + photo);
//		if (id != null & photo != null) {
//			return "/product-photos/" + id + "/" + photo;
//		}
//		return null;
//	}
}

package com.green.webmodels.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.green.webmodels.enumerate.AuthProvider;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "email", unique = true, nullable = false, length = 64)
	private String email;
	private String password;
	
	@Column(name = "first_name", unique = false, nullable = false, length = 100)
	private String firstName;
	
	@Column(name = "last_name", unique = false, nullable = false, length = 100)
	private String lastName;
	
	@Column(name = "phone_number", unique = true, length = 12)
	private String phoneNumber;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Address> addresses = new HashSet<>();
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Order> orders = new HashSet<>();
	
	@Column(name = "last_login")
	private Date lastLogin;
	
	@Column(name = "create_date", updatable = false)
	private Date createDate;
	
	@Column(name = "email_verified", columnDefinition = "tinyint(1) default 0")
	private Boolean emailVerified;
	
	@Column(name = "verification_code", unique = true, length = 128, updatable = false)
	private String verificationCode;
	
	@Column(name = "photo_url")
	private String photo;
	
	@Column(name = "auth_provider")
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private DbCartInfo cartInfo;
	
	private Boolean enabled;

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

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public AuthProvider getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(AuthProvider authProvider) {
		this.authProvider = authProvider;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}
	
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	public Set<Order> getOrders() {
		return orders;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public DbCartInfo getCartInfo() {
		return cartInfo;
	}

	public void setCartInfo(DbCartInfo cartInfo) {
		this.cartInfo = cartInfo;
	}

	@Transient
	public String getName() {
		return firstName + " " + lastName;
	}
	
	@Transient
	public String getLastLoginDate() {
		if (lastLogin !=null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			return dateFormat.format(lastLogin);
		}
		return null;
	}
	
	@Transient
	public String getCreatedDate() {
		if (createDate !=null) {
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			return dateFormat.format(createDate);
		}
		return null;
	}
	
	@Transient
	public String getPhotoPath() {
		System.out.println("get photo path: = " + photo);
		if (id != null & photo != null) {
			return "/profile-photos/" + id + "/" + photo;
		}
		return null;
	}
}

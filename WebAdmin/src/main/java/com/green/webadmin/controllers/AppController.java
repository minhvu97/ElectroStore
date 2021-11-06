package com.green.webadmin.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.webadmin.helper.FileUploadHelper;
import com.green.webadmin.products.ProductService;
import com.green.webadmin.service.CategoryService;
import com.green.webadmin.service.CustomerService;
import com.green.webadmin.service.OrderService;
import com.green.webadmin.service.RoleService;
import com.green.webadmin.service.UserService;
import com.green.webmodels.entities.Category;
import com.green.webmodels.entities.Customer;
import com.green.webmodels.entities.Order;
import com.green.webmodels.entities.Product;
import com.green.webmodels.entities.Role;
import com.green.webmodels.entities.User;
import com.green.webmodels.enumerate.PhotoPath;

@Controller
public class AppController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private OrderService orderService;

	@RequestMapping("/login")
	public String showLoginView(Model model) {
		
		System.out.println("showLoginView");
		return "login";
	}
	
	@RequestMapping("/login_error")
	public String showLoginErrorView(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("loginError", "Nhap sai Username hoac Password");
		System.out.println("LoginError");
		return "redirect:/login";
	}
	
	@RequestMapping("/")
	public String showHomeView(Model model) {
		List<User> users = userService.getAllUser();
		model.addAttribute("users", users);
		
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		
		List<Customer> customers = customerService.getAllCustomer();
		model.addAttribute("customers", customers);
		
		List<Order> orders=orderService.getAllOrder();
		model.addAttribute("orders", orders);
		
		System.out.println("showIndexView");
		return "index";
	}
	
	@RequestMapping("/users")
	public String showUsersView(Model model) {
		System.out.println("showUserView");
		
		List<User> users = userService.getAllUser();
		model.addAttribute("users", users);
		return "users.html";
	}
	
	@RequestMapping("/roles")
	public String showRolesView(Model model) {
		List<Role> roles = roleService.getAllRole();
		model.addAttribute("roles", roles);
		
		System.out.println("showRolesView");
		return "roles.html";
	}
	
	@RequestMapping("/customer")
	public String showCustomerView(Model model) {
		List<Customer> customers = customerService.getAllCustomer();
		model.addAttribute("customers", customers);
		
		System.out.println("showCustomerView");
		return "customer.html";
	}
	
	@RequestMapping("/category")
	public String showCategoryView(Model model) {
		List<Category> categories = categoryService.getAllCategory();
		model.addAttribute("categories", categories);
		
		System.out.println("showCustomerView");
		return "category.html";
	}
	
	@RequestMapping("/profile")
	public String showProfileView(Model model, @RequestParam Integer id) {
		User profile = userService.getUserById(id);
		
		model.addAttribute("fullname", profile.getFullName());
		model.addAttribute("username", profile.getUsername());	
		model.addAttribute("listRole", profile.getListRole());
		return "profile.html";
	}
	
	@GetMapping("/new-user")
	public String showNewUser(Model model) {
	
		User user = new User();

		model.addAttribute("user", user);
		
		List<Role> roles = roleService.getAllRole();
		model.addAttribute("roles", roles);
		
		List<String> listName = userService.getAllUserName();
		model.addAttribute("listname", listName);
		
		return "new_user";
	}
	
	@RequestMapping(value = "/save-user", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("user") User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = user.getPassword();
		String bCryptPassword = encoder.encode(rawPassword);
		user.setPassword(bCryptPassword);
		
		userService.saveUser(user);
		
		return "redirect:/users";
	}
	
	@RequestMapping("/edit-user/{id}")
	public String updateUser(Model model, @PathVariable(name = "id") Integer id) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		
		List<Role> roles = roleService.getAllRole();
		model.addAttribute("roles", roles);
		
		List<String> listName = userService.getAllUserName();
		model.addAttribute("listname", listName);
		
		return "update_user";
	}
	
	@RequestMapping(value = "/update-user", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") User user) {
		User updateUser = userService.getUserById(user.getId());
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = user.getPassword();
		String bCryptPassword = encoder.encode(rawPassword);
		updateUser.setPassword(bCryptPassword);
		
		updateUser.setUsername(user.getUsername());
		updateUser.setFullName(user.getFullName());
		updateUser.setEnabled(user.getEnabled());
		updateUser.setRoles(user.getRoles());
		
		userService.saveUser(updateUser);
		
		return "redirect:/users";
	}
}

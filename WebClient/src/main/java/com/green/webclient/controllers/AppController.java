package com.green.webclient.controllers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.springframework.util.StringUtils;

import com.green.webmodels.enumerate.PhotoPath;
import com.green.webclient.helpers.EmailServiceImpl;
import com.green.webclient.helpers.FileUploadHelper;
import com.green.webclient.products.ProductService;
import com.green.webclient.security.CustomerOAuth2User;
import com.green.webclient.services.CustomerService;
import com.green.webmodels.entities.Category;
import com.green.webmodels.entities.Customer;
import com.green.webmodels.entities.Product;
import com.green.webmodels.formdata.CustomerData;
import com.sun.mail.imap.Utility;

@Controller
public class AppController {

	@Autowired
	private TemplateEngine htmlTemplateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	ResourceLoader resourceLoader;  

	@Autowired
	ProductService productService;
	
	@RequestMapping("/")
	public String showHomeView(Authentication authentication, Model model) {

		// for login
		if (authentication != null) {

			if (CustomerOAuth2User.class.isInstance(authentication.getPrincipal())) {
				CustomerOAuth2User oath2user = (CustomerOAuth2User) authentication.getPrincipal();
				if (oath2user != null) {
					String email = oath2user.getEmail();
					System.out.println("showHomeView princpal name: " + email);
					Customer currentCus = customerService.getByEmail(email);
					if (currentCus != null) {
						model.addAttribute("customer", currentCus);
					}
				}
			} else {
				System.out.println("showHomeView princpal name: " + authentication.getName());
				Customer currentCus = customerService.getByEmail(authentication.getName());
				if (currentCus != null) {
					model.addAttribute("customer", currentCus);
				}
			}

		}

		// for register
		CustomerData customerData = new CustomerData();
		model.addAttribute("customerData", customerData);

		List<Product> listNewProduct = productService.getLast3Products();
		
		model.addAttribute("listNewProduct",listNewProduct);
		
		return "index";
	}

	@PostMapping("/register")
	public String doRegister(@ModelAttribute("customerData") CustomerData customerData, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		System.out.println("AppController::doRegister -> " + customerData.toString());

		Customer customer = customerService.getCustomerByPhone(customerData.getPhoneNumber());
		Customer customer2 = customerService.getByEmail(customerData.getEmail());

		boolean isThisCustomerValid = true;

		if (customer != null) {
			redirectAttributes.addFlashAttribute("error_duplicate_phone", "this phone number is used");
			isThisCustomerValid = false;
		}
		if (customer2 != null) {
			redirectAttributes.addFlashAttribute("error_duplicate_email", "this email is used ");
			isThisCustomerValid = false;
		}

		if (!isThisCustomerValid) {
			return "redirect:/";
		}

		if (bindingResult.hasErrors()) {
			return "/";
		}

		Customer regCustomer = customerService.registerNewCustomer(customerData);

//		try {
//			EmailServiceImpl.sendSimpleMail(javaMailSender, htmlTemplateEngine, customerData.getName(),
//					customerData.getEmail(), Locale.getDefault());
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			EmailServiceImpl.sendVerificationEmail(regCustomer.getVerificationCode(),regCustomer.getName(), regCustomer.getEmail(), javaMailSender);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "register/register-success";
	}

	@GetMapping("/verify")
	public String verifyAccount(@Param("code")String code, Model model)
	{
		boolean verified = customerService.verify(code);
		
		String pageTitle = verified? "Verification Succeeded!" :"Verification Failed";
		
		model.addAttribute("pageTitle",pageTitle);
		
		return "register/" + (verified ? "verify-success" : "verify-fail");
	}
	
	@GetMapping("/profile")
	public String showProfileView(Model model, Authentication authentication) {

		Customer profile = new Customer();
		String profilePicture = "";
		boolean isGoogleUser = false;
		
		// if user login with google
		if (CustomerOAuth2User.class.isInstance(authentication.getPrincipal())) {
			CustomerOAuth2User oath2user = (CustomerOAuth2User) authentication.getPrincipal();
			String email = oath2user.getEmail();
			isGoogleUser = true;
			/*-------------------------------------------------------*/
//			System.out.println("showHomeView princpal name: " + email);
			
			Map<String, Object> attrs = oath2user.getAttributes();

			for (String key : attrs.keySet()) {

				String value = attrs.get(key).toString();

				System.out.println("CustomerOAuth2User::getAttributes ->  " + key + " = " + value);

			}
			
			profilePicture = attrs.get("picture").toString();
			
			oath2user.getAuthorities().forEach(authority -> {

				if (OAuth2UserAuthority.class.isInstance(authority)) {

					OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;

					Map<String, Object> attrs2 = oauth2UserAuthority.getAttributes();

					for (String key : attrs2.keySet()) {

						String value = attrs2.get(key).toString();

						System.out.println("CustomerOAuth2User::getAuthorities ->  " + key + " = " + value);

					}

				}

			});
			
			/*-------------------------------------------------------*/
			profile = customerService.getByEmail(email);
		} else // if user login with registered account
		{
			profile = customerService.getByEmail(authentication.getName());
		}
		
		
		System.out.println("profile picture = " + CustomerData.getCustomerData(profile).getPhoto());
		model.addAttribute("profile", CustomerData.getCustomerData(profile));
		model.addAttribute("isGoogleUser",isGoogleUser);
		
		String profilePicDir = PhotoPath.PROFILE_PHOTO_DIR + "/" + profile.getId() + "/" + CustomerData.getCustomerData(profile).getPhoto();
		Path path = Paths.get(profilePicDir);
		
		if(Files.exists(path)) {
			model.addAttribute("profilePicture",profile.getPhotoPath());
		} else {
			model.addAttribute("profilePicture", profilePicture);
		}
	    
		return "profile.html";
	}

	@PostMapping("/profile")
	public String doUpdateCustomer(@ModelAttribute("profile") CustomerData customerData, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile, Authentication authentication) {

		int id = customerData.getId();
		
		Customer customer = customerService.getCustomerById(id);
		
		Customer customerByPhone = customerService.getCustomerByPhone(customerData.getPhoneNumber());
		
		Customer customerByEmail = customerService.getByEmail(customerData.getEmail());

		boolean isUpdateCustomerValid = true;

		if (customerByPhone != null && customerByPhone.getId() != id) {
			redirectAttributes.addFlashAttribute("error_duplicate_phone", "this phone number is used");
			isUpdateCustomerValid = false;
		}
		if (customerByEmail != null && customerByEmail.getId() != id) {
			redirectAttributes.addFlashAttribute("error_duplicate_email", "this email is used ");
			isUpdateCustomerValid = false;
		}

		if (!isUpdateCustomerValid) {
			return "redirect:/profile";
		}

		if (bindingResult.hasErrors()) {
			return "/profile";
		}

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

//		Category category = new Category();
//		
//		category.setId(1);
//		product.setCategory(category);
//		product.setEnabled(true);
//		product.setPhoto(fileName);
		
		if(!fileName.equals("")) {
			customerData.setPhoto(fileName);
			String uploadDir = PhotoPath.PROFILE_PHOTO_DIR + "/" + customer.getId();
			
			try {
				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		customerService.updateCustomer(customerData, id);
		
		if (customerByEmail == null)
		{
			return "redirect:/logout";	
		}
		return "redirect:/";
	}
	 
	@GetMapping("/authenError")
	public String showAuthenError(Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("loginError", "No user or password");
		System.out.println("enter authen ERROR");
		return "redirect:/";
	}
	
	@GetMapping("/requireLogin")
	public String showRequireLogin(Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("loginError", "you need to login");
		System.out.println("enter require login ");
		return "redirect:/";
	}

	@GetMapping("/firstLogin")
	public String showFirstLogin(Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("loginError", "Hello!");
		return "redirect:/";
	}
}

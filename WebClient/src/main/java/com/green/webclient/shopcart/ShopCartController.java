package com.green.webclient.shopcart;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;

import com.green.webclient.category.CategoryService;
import com.green.webclient.helpers.EmailServiceImpl;
import com.green.webclient.order.OrderServices;
import com.green.webclient.products.ProductController;
import com.green.webclient.products.ProductService;
import com.green.webclient.services.CustomerService;
import com.green.webmodels.entities.Customer;
import com.green.webmodels.entities.Order;
import com.green.webmodels.entities.OrderDetail;
import com.green.webmodels.entities.Product;

@Controller
public class ShopCartController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private TemplateEngine htmlTemplateEngine;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private OrderServices orderService;
	
	@Autowired
	ProductController productController;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@RequestMapping("/addtocart")
	public String byProductHandler(HttpServletRequest request, Model model, @RequestParam(value = "code", defaultValue = "") String code) {

		Product product = productService.getProductByCode(code);

		if (product != null) {
			CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
			cartInfo.addProduct(product, 1);
		}

		return "redirect:/shopping_cart";
	}
	
	@RequestMapping("/shopping_cart")
	public String showCartView(HttpServletRequest request, Model model
			, Authentication authentication) {
		productController.CurrentUserInfo(authentication, model);
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);

		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("totalCartInfo", cartInfo.totalCartInfo());
		model.addAttribute("totalQuantityInfo", cartInfo.totalQuantityInfo());

		return "cart4";
	}
	
	@PostMapping("/update_cart")
	public String updateCart(@ModelAttribute("cartInfo") CartInfo cartFrm ,HttpServletRequest request, Model model) {
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);
		System.out.println("updateCart -> " + cartInfo.getCartLines().size());
		for (int i = 0; i < cartInfo.getCartLines().size(); i++) {
			cartInfo.getCartLines().get(i).setQuantity(cartFrm.getCartLines().get(i).getQuantity());
		}
		System.out.println("updateCart" + cartFrm.getCartLines().size());
		return "redirect:/shopping_cart";
	}
	
//	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
//	public String checkOutProduct(Model model, Authentication authentication, @RequestParam Map<String, String> customQuery) {
//		productController.CurrentUserInfo(authentication, model);
//		
//        System.out.println("customQuery = discount_amount_1 " + customQuery.containsKey("discount_amount_1"));
//        System.out.println("customQuery = quantity_1 " + customQuery.containsKey("quantity_1"));
//        System.out.println("customQuery = item_name_1 " + customQuery.containsKey("item_name_1"));
//        System.out.println("customQuery = amount_1 " + customQuery.containsKey("amount_1"));
//        System.out.println("customQuery = code " + customQuery.containsKey("item_code_1"));
//        
//		return "checkout";
//	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String checkOutProduct(Model model, Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
	
		Customer currentCustomer = productController.CurrentUserInfo(authentication, model);
		
		CartInfo cartInfo = ShopCartSessionUtil.getCartInSession(request);

		model.addAttribute("orderCode", orderService.saveOrder(cartInfo));
		
		ShopCartSessionUtil.removeCartInSession(request);
		
		try {
			EmailServiceImpl.sendOrderEmail(javaMailSender, currentCustomer, "Your Orders", 
					htmlTemplateEngine, Locale.getDefault(), orderService.getNewestOrderByCustomerID(currentCustomer.getId()));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "checkout2";
	}
	
	@GetMapping("/payment")
	public String showPayment(Model model, Authentication authentication, HttpServletRequest request) 
	{
		productController.CurrentUserInfo(authentication, model);
		
		return "payment";
	}
}

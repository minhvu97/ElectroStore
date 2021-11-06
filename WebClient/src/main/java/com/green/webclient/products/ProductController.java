package com.green.webclient.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.webclient.category.CategoryService;
import com.green.webclient.security.CustomerOAuth2User;
import com.green.webclient.services.CustomerService;
import com.green.webclient.shopcart.CartInfo;
import com.green.webclient.shopcart.CartLineInfo;
import com.green.webclient.shopcart.ShopCartSessionUtil;
import com.green.webmodels.entities.Category;
import com.green.webmodels.entities.Customer;
import com.green.webmodels.entities.Product;
import com.green.webmodels.formdata.CustomerData;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CategoryService categoryService;

	public Customer CurrentUserInfo(Authentication authentication, Model model) {
		
		Customer profile = new Customer();
		if (authentication != null) {
			
			// if user login with google
			if (CustomerOAuth2User.class.isInstance(authentication.getPrincipal())) {
				CustomerOAuth2User oath2user = (CustomerOAuth2User) authentication.getPrincipal();
				String email = oath2user.getEmail();
				System.out.println("showHomeView princpal name: " + email);
				profile = customerService.getByEmail(email);
			} else // if user login with register account
			{
				profile = customerService.getByEmail(authentication.getName());
			}
			model.addAttribute("customer", profile);
		}
		CustomerData customerData = new CustomerData();
		model.addAttribute("customerData", customerData);
		return profile;
	}

	@GetMapping("/product2")
	public String showProduct2(Model model) {
		return "product2.html";
	}

	@GetMapping("/products")
	public String showProducts(Model model, Authentication authentication) {

		return showProductPageView(model, 1, "none", authentication);
	}
	
	@PostMapping("/products")
	public String showSortedProducts(Model model, Authentication authentication, @RequestParam("sort-by-name") String sortType) {

		return showProductPageView(model, 1, sortType, authentication);
	}

	@RequestMapping(value = { "/products/{pageNum}" })
	public String showProductPageView(Model model, @PathVariable(name = "pageNum") int pageNum,
			 String sortType, Authentication authentication) {

		CurrentUserInfo(authentication, model);
		
		if (sortType == null)
		{
			sortType="none";
		}
		
		Page<Product> pageProduct = productService.getProductsWithPage(pageNum, sortType);
		List<Product> listProducts = pageProduct.getContent();

		long startCount = (pageNum - 1) * ProductService.PAGE_SIZE + 1;
		long endCount = startCount + ProductService.PAGE_SIZE - 1;

		if (endCount > pageProduct.getTotalElements()) {
			endCount = pageProduct.getTotalElements();
		}

		for (Product p : listProducts)
		{
			System.out.println("so luong san pham = " + p.getQuantity());
		}
		
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("totalPages", pageProduct.getTotalPages());
		model.addAttribute("totalProducts", pageProduct.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);

		return "product.html";
	}
	
	@GetMapping("/single")
	public String showSingleView(Model model, Authentication authentication, HttpServletRequest request) {
		String code = request.getParameter("code");
		Product product = productService.getProductByCode(code);
		model.addAttribute("product", product);

		CurrentUserInfo(authentication, model);

		return "single.html";
	}

	@GetMapping("/single2")
	public String showSingleView2(Model model, Authentication authentication) {

		CurrentUserInfo(authentication, model);

		return "single2.html";
	}

	@RequestMapping(value = "/single/{code}", method = RequestMethod.GET)
	public String showSingleViewByCode(@PathVariable(name = "code") String code, Authentication authentication,
			Model model) {

		CurrentUserInfo(authentication, model);

		Product product = productService.getProductByCode(code);
		model.addAttribute("product", product);
		String photoPath = "D:/WorkSpace/Spring-workspace/FinalProject_Green-DK-JDev-B001_QuangMinh_VuMinh_4_9/FinalProject_Green-DK-JDev-B001_QuangMinh_VuMinh/WebModels/src/main/resources/static/product-photos/1/k1.jpg";
		model.addAttribute("photoPath", photoPath);

		return "single.html";
	}

	@GetMapping(value = { "/searchproducts" })
	public String showSearchProduct(Model model, @Param(value = "value") String value, Authentication authentication) {

		CurrentUserInfo(authentication, model);

		// List<Product> listProduct = productService.getProductByName(value);

		List<Product> listProduct = productService.fullTextSearchProductByName(value);

		System.out.println("Danh sach san pham sau khi search: " + listProduct.size());

		System.out.println(value);

		model.addAttribute("listProducts", listProduct);

		return "search_product";
	}

	@GetMapping(value = { "/category" })
	public String showCategoryProduct(Model model, Authentication authentication) {

		CurrentUserInfo(authentication, model);

		// List<Category> listCategory = categoryService.getRootCategory();
		List<Category> listCategory = categoryService.getSubRootCategory();
		List<Product> listProducts = productService.getAllProducts();

		Category category = categoryService.getById(1);

		List<Category> listcategory = new ArrayList<>();
		listcategory.addAll(category.getSubCategory());

		List<Category> listParent = categoryService.getListParent(category);
		model.addAttribute("parents", listParent);
		model.addAttribute("categories", listCategory);
		model.addAttribute("listProducts", listProducts);

		boolean isListNull = false;

		if (listCategory == null) {
			isListNull = true;
		}

		model.addAttribute("isListNull", isListNull);

		return "category_product";
	}

	@GetMapping(value = { "/category/{id}" })
	public String showCategoryProductID(Model model, @PathVariable("id") Integer id, Authentication authentication) {

		CurrentUserInfo(authentication, model);

		List<Product> listProducts;
		if (id == 1) {
			listProducts = productService.getAllProducts();
		} else {
			listProducts = productService.getProductByCategoryId(id.toString());
			if (listProducts.isEmpty()) {
				listProducts = productService.getProductByCategoryCode(id);
			}
		}

		Category category = categoryService.getById(id);

		List<Category> listCategory = new ArrayList<>();
		listCategory.addAll(category.getSubCategory());

		List<Category> listParent = categoryService.getListParent(category);
		model.addAttribute("parents", listParent);
		model.addAttribute("categories", listCategory);
		model.addAttribute("listProducts", listProducts);

		boolean isListNull = false;

		if (listCategory == null || listCategory.isEmpty()) {
			isListNull = true;
		}

		model.addAttribute("isListNull", isListNull);

		return "category_product";
	}
	
	
}

package com.green.webadmin.products;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.servlet.ModelAndView;

import com.green.webadmin.helper.FileUploadHelper;
import com.green.webadmin.repository.UserRepository;
import com.green.webadmin.service.CategoryService;
import com.green.webadmin.service.UserService;
import com.green.webmodels.entities.Category;
import com.green.webmodels.entities.Product;
import com.green.webmodels.entities.User;
import com.green.webmodels.enumerate.PhotoPath;
import com.green.webmodels.formdata.ProductData;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/products")
	public String showProducts(Model model, Principal principal, @RequestParam(name = "search") Optional<String> search) {		
		List<Product> listProducts = new ArrayList<>();
		if(search.isEmpty() || search.get().equals("")) {
			listProducts = productService.getAllProducts();
		}
		else {
			listProducts = productService.searchProductByName(search.get());
		}
		
		model.addAttribute("listProducts", listProducts);
		
		return "product_list";
	}

	@GetMapping("/new-product")
	public String showNewProduct(Model model) {
	
		Product product = new Product();

		model.addAttribute("product", product);
		
		List<Category> categories = categoryService.getAllCategory();
		model.addAttribute("categories", categories);
		
		return "new_product";
	}
	
	@RequestMapping(value = "/delete-product/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable(name = "id") Integer id) {
		
		System.out.println("delete product id: " + id);
		
		productService.deleteProdcutById(id);
		
		return "redirect:/products";
		//return "product_list";
	}
	
	@RequestMapping(value = "/save-product", method = RequestMethod.POST)
	public String saveProduct(@RequestParam("fileImage") MultipartFile multipartFile,@ModelAttribute("product") Product product) {
		product.setEnabled(true);
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		
		if(fileName.equals("")) 
		{
			product.setPhoto(null);
		}
		else 
		{
			product.setPhoto(fileName);
			
			String uploadDir = PhotoPath.PRODUCT_PHOTO_DIR + "/" + product.getId();			
			try {
				FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
		productService.saveProduct(product);
		
		return "redirect:/products";
	}
	
	@RequestMapping("/edit-product/{code}")
	public String updateProduct(@PathVariable(name = "code") String code, Model model) {
		//ModelAndView modelAndView = new ModelAndView("update_product");
		
		Product entity = productService.getProductByCode(code);
		
		ProductData product = ProductData.copyValueFormEntity(entity);
		
		List<Category> categories = categoryService.getAllCategory();
		model.addAttribute("categories", categories);
		
		model.addAttribute("product", product);
		
		return "update_product";
		
	}
	
	@RequestMapping(value = "/update-product", method = RequestMethod.POST)
	public String updateProduct(@RequestParam("fileImage") MultipartFile multipartFile,@ModelAttribute("product") ProductData product) {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		product.setPhoto(fileName);
		Product entity = productService.getProductByCode(product.getCode());
		
		if (entity != null) {
			entity.updateFormData(product);
			productService.saveProduct(entity);
		}
		
		String uploadDir = PhotoPath.PRODUCT_PHOTO_DIR + "/" + product.getId();
		
		try {
			FileUploadHelper.saveFile(uploadDir, fileName, multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/products";
	}
}
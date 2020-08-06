package onlineShop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Product;
import onlineShop.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public ModelAndView getAllProducts() {
		List<Product> list =productService.getAllProducts();
		return new ModelAndView("productList","products", list);
	}
	
	@RequestMapping(value = "/getProductById/{productId}", method = RequestMethod.GET)
	public ModelAndView getProductById(@PathVariable(value="productId") int productId) {
		Product product = productService.getProductById(productId);
		return new ModelAndView("productPage","product", product);
	}
	
	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.GET)
	public ModelAndView getProductForm() {
		return new ModelAndView("addProduct","productForm", new Product());
	}
	
	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute(value="productForm") Product product, BindingResult result) {
		if(result.hasErrors()) {
			return "addProduct";
		}
		productService.addProduct(product);
		MultipartFile image = product.getProductImage(); // 读取product table里面的image数据
		if(image != null && !image.isEmpty()) {
			Path path = Paths.get("/Users/liulian/Documents/code/project_2/shopping/products_image/" + product.getId() + ".jpg");//存放image的folder？
			
			try {
				image.transferTo(new File(path.toString()));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/getAllProducts";

	}
	
	@RequestMapping(value = "/admin/delete/{productId}")
	public String deleteProduct(@PathVariable(value = "productId") int productId) {
			Path path = Paths.get("/Users/liulian/Documents/code/project_2/shopping/products_image/" + productId + ".jpg");
			
			if(Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			productService.deleteProduct(productId);
		return "redirect:/getAllProducts";

	}
	//getEditForm作用，和add product有什么区别
	@RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.GET)//读
	public ModelAndView getEditForm(@PathVariable(value = "productId") int productId) {
		Product product = productService.getProductById(productId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("editProduct");
		modelAndView.addObject("editProductObj", product);
		modelAndView.addObject("productId", productId);

		return modelAndView;
	}

	@RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.POST)//写
	public String editProduct(@ModelAttribute(value = "editProductObj") Product product,
			@PathVariable(value = "productId") int productId) {
		product.setId(productId);
		productService.updateProduct(product);
		return "redirect:/getAllProducts";
	}

	

}

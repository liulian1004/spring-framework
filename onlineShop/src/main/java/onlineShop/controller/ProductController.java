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

//control： 后端如何响应前端来的请求
@Controller
public class ProductController { 
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET) // 对应按前端“product”按钮
	public ModelAndView getAllProducts() {
		List<Product> list =productService.getAllProducts();
		return new ModelAndView("productList","products", list);//“” 对应前端页面的tag名
	}
	
	@RequestMapping(value = "/getProductById/{productId}", method = RequestMethod.GET) //{传入的内容}
	public ModelAndView getProductById(@PathVariable(value="productId") int productId) { //传入的内容需要写在PathVariable里
		Product product = productService.getProductById(productId);
		return new ModelAndView("productPage","product", product);
	}
	
	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.GET) //读请求：返回添加商品的form
	public ModelAndView getProductForm() {
		return new ModelAndView("addProduct","productForm", new Product());
	}
	
	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.POST)//写请求： 读取form
	public String addProduct(@ModelAttribute(value="productForm") Product product, BindingResult result) {//bindingResult： 检查下前端输入的类型是否是后端可以接受的
		if(result.hasErrors()) {
			return "addProduct";
		}
		productService.addProduct(product);
		MultipartFile image = product.getProductImage(); // 读取product table里面的image数据
		if(image != null && !image.isEmpty()) {
			//存放image的folder
			//上传的图片可以是任意格式
			//最后存储的格式是jpg
			Path path = Paths.get("/Users/liulian/Documents/code/project_2/shopping/products_image/" + product.getId() + ".jpg");
			
			try {
				image.transferTo(new File(path.toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "redirect:/getAllProducts"; //返回到product list page

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
			@PathVariable(value = "productId") int productId) { //传入product 和productId两个参数
		product.setId(productId);
		productService.updateProduct(product);
		return "redirect:/getAllProducts";
	}

	

}

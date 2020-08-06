package onlineShop.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Customer;
import onlineShop.service.CustomerService;

@Controller
public class RegistrationController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
	public ModelAndView getRegistrationForm() {
		Customer customer = new Customer();
		return new ModelAndView("register","customer", customer);// view name， model name , object 
	}
	
	@RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
	public ModelAndView registerCustomer(@ModelAttribute(value= "customer")Customer customer, BindingResult result) {//绑定result
		ModelAndView modelAndView = new ModelAndView();
		if(result.hasErrors()) {
			modelAndView.setViewName("register");
			return modelAndView;
		}
		customerService.addCustomer(customer);
		modelAndView.setViewName("login");
		modelAndView.addObject("registrationSucess", "Reisger Sucessfully.Login using username and password");
		return modelAndView;
	}
	
}

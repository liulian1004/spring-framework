package onlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
	
	@RequestMapping(value ="/index", method = RequestMethod.GET)	
	public String sayIndex() {
		return "index";
		
	}
	
	@RequestMapping(value ="/login")//如果不写method，默认这个request所有的功能都可以handle。 ie. post, get.....
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, // requried = false : 如果没有param传入，默认结果是false
							 @RequestParam(value="logout", required = false) String logout){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		if(error != null) {
			modelAndView.addObject("error","Invalid username and password");
		}
		if(logout != null) {
			modelAndView.addObject("logour","You have logged out successfully");
		}
		
		return modelAndView;
		
	}
	
//	@RequestMapping(value = "/aboutus", method = RequestMethod.GET)
//	public String sayAbout() {
//		return "About Us";
//	}
	
}

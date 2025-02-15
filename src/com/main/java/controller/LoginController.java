package com.main.java.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.main.java.model.AdminUser;


@Controller
public class LoginController {

//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public ModelAndView login(
//			@RequestParam(value = "error", required = false) String error,
//			@RequestParam(value = "logout", required = false) String logout) {
//
//		ModelAndView model = new ModelAndView();
//		if (error != null) {
//			model.addObject("error", "用户名或密码不正确!");
//		}
//
//		if (logout != null) {
//			model.addObject("msg", "已经退出系统.");
//		}
//		model.setViewName("login");
//
//		return model;
//
//	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {

		ModelAndView model = new ModelAndView();
		model.setViewName("login");

		return model;

	}

	                      
	@RequestMapping(value = "/login/init", method = RequestMethod.GET)
	public String loginInit(Principal adminUser) {
		try{
			
			if(adminUser == null || StringUtils.isBlank(((AdminUser)adminUser).getRoleId())) {
				return "redirect:/login";
			}else {
				//return "redirect:/login?error";
				return "redirect:/adminUser/init";
			}
		}catch(Exception e) {
			return "redirect:/login";
		}
	}
	
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
 
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
 

}

package com.parking.common.security.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultLoginController {
	@RequestMapping(value = { "/web/home" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String home(Locale locale, Model model) {
		return "home";
	}

	@RequestMapping({ "/", "/login" })
	public ModelAndView login(HttpServletRequest request,
			@RequestParam(value = "error", required = false) String error) {
		ModelAndView modelview = new ModelAndView("login");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			modelview.setViewName("home");
			return modelview;
		}

		if (error != null) {
			HttpSession session = request.getSession();
			modelview.addObject("errorObject", session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
			session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		}

		return modelview;
	}

	@RequestMapping({ "/denied" })
	public ModelAndView denied(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView("denied");
		HttpSession session = request.getSession();
		modelview.addObject("errorObject", session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
		session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		return modelview;
	}
}
package com.parking.common.security.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.parking.common.security.utilities.CommonUtility;
import com.parking.common.security.webservice.UserService;
import com.parking.common.security.webservice.request.UserRequest;
import com.parking.common.security.webservice.response.GenericResponse;
import com.parking.common.security.webservice.response.UserResponse;

public class UserAPICentricController {
	private static final Logger LOG = LogManager.getLogger(UserAPICentricController.class.getName());

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@RequestMapping(value = { "/web/user" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String home(Locale locale, Model model) {
		UserRequest userRequest = new UserRequest();
		userRequest.setUserName("sateesh");
		userRequest.setPassword("pmc1231");

		Response response = this.userService.userAuthentication(CommonUtility.getHttpHeader(), userRequest);
		GenericResponse genericResponse = (UserResponse) response.getEntity();
		model.addAttribute("invalidUserResponse", genericResponse);

		userRequest.setPassword("pmc123");

		response = this.userService.userAuthentication(CommonUtility.getHttpHeader(), userRequest);
		model.addAttribute("validUserResponse", response.getEntity());

		return "user";
	}

	@RequestMapping({ "/web/loadChangePassword" })
	public ModelAndView loadChangePassword(HttpServletRequest request, Model model) throws IOException {
		ModelAndView modelview = new ModelAndView("password");
		model.addAttribute(new UserRequest());
		return modelview;
	}

	@RequestMapping(value = { "/web/changePassword" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	public ModelAndView changePassword(UserRequest userRequest) throws IOException {
		LOG.debug("Enter into changePassword()");
		ModelAndView modelview = new ModelAndView("password");
		userRequest.setUserName(CommonUtility.getLoggedUsername());

		Response response = this.userService.changePassword(CommonUtility.getHttpHeader(), userRequest);
		modelview.addObject("errorMessage", response.getEntity());
		return modelview;
	}

	@RequestMapping(value = { "/web/store/add" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String storeAdd(Locale locale, Model model) {
		return "store";
	}

	@RequestMapping(value = { "/web/tender/add" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String tenderAdd(Locale locale, Model model) {
		return "tender";
	}

	@RequestMapping(value = { "/web/tender/view" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public String tenderview(Locale locale, Model model) {
		return "tender-view";
	}
}
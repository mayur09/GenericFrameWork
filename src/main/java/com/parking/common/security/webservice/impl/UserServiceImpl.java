/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.webservice.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.parking.common.exception.GenericDAOException;
import com.parking.common.security.manager.UserManager;
import com.parking.common.security.model.Role;
import com.parking.common.security.model.User;
import com.parking.common.security.utilities.CommonUtility;
import com.parking.common.security.webservice.UserService;
import com.parking.common.security.webservice.request.UserRequest;
import com.parking.common.security.webservice.response.GenericResponse;
import com.parking.common.security.webservice.response.UserResponse;
import com.pmcretail.framework.security.model.AccessRight;

@Component("userService")
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class.getName());

	@Autowired
	@Qualifier("userManager")
	private UserManager userManager;

	@Autowired
	@Qualifier("messageSource")
	private AbstractMessageSource messageSource;

	@Transactional(readOnly = true)
	public Response userAuthentication(HttpHeaders headers, UserRequest userRequest) {
		GenericResponse response = null;
		LOG.info("BOF: User Authentication process");
		String errorMessage;
		UserResponse userResp;
		try {
			if (validateUserRequest(userRequest)) {
				User user = this.userManager.getUserByUsernameAndPassword(userRequest.getUserName(),
						CommonUtility.convertStringIntoMD5(userRequest.getPassword()));

				if (validateUser(user)) {
					response = convertUserIntoUserResponse(user);
					LOG.info("Sent User Response.");
				}
			}

			return Response.status(Response.Status.OK).entity(response).build();
		} catch (GenericDAOException se) {
			LOG.error("ERROR : UserDetailServiceImpl.userAuthentication() :" + se);
			errorMessage = this.messageSource.getMessage(String.valueOf(se.getErrorCode()), null,
					headers.getLanguage());
			userResp = new UserResponse();
			userResp.setErrorMessage(errorMessage);
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Reason-Phrase", errorMessage)
				.entity(userResp).build();
	}

	@Transactional
	public Response changePassword(HttpHeaders headers, UserRequest userRequest) {
		LOG.info("Change Password process Started.");
		String message;
		try {
			if ((validateUserRequest(userRequest)) && (userRequest.getNewPassword() != null)
					&& (!(userRequest.getNewPassword().isEmpty()))) {
				User user = this.userManager.getUserByUsernameAndPassword(userRequest.getUserName(),
						CommonUtility.convertStringIntoMD5(userRequest.getPassword()));

				if (validateUser(user)) {
					user.setPassword(CommonUtility.convertStringIntoMD5(userRequest.getNewPassword()));
					this.userManager.save(user);
					LOG.info("Response successfully created.");
				}
			} else {
				LOG.error("New password is invalid/blank");
				throw new GenericDAOException(100);
			}
			LOG.info("Change Password process Ended.");
			message = this.messageSource.getMessage("806", null, headers.getLanguage());
			return Response.status(Response.Status.OK).entity(message).build();
		} catch (GenericDAOException se) {
			LOG.error("ERROR : UserDetailServiceImpl.changePassword() :" + se);
			message = this.messageSource.getMessage(String.valueOf(se.getErrorCode()), null, headers.getLanguage());
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Reason-Phrase", message).entity(message)
				.build();
	}

	private boolean validateUserRequest(UserRequest userRequest) throws GenericDAOException {
		if (userRequest != null) {
			if (userRequest.validateRequest()) {
				LOG.debug("Request is validated");
				return true;
			}
			LOG.error("Request is invalid");
			throw new GenericDAOException(100);
		}

		LOG.error("Null Request has been received");
		throw new GenericDAOException(100);
	}

	private boolean validateUser(User user) throws GenericDAOException {
		if (user != null) {
			LOG.info("User is been successfully found for Username : " + user.getUserName());

			if (user.getStatus().ordinal() == 1) {
				return true;
			}
			LOG.error("User is inactive");
			throw new GenericDAOException(805);
		}

		LOG.error("Request is invalid");
		throw new GenericDAOException(804);
	}

	private UserResponse convertUserIntoUserResponse(User user) {
		UserResponse userResponse = new UserResponse();

		userResponse.setUsername(user.getUserName());
		userResponse.setRoles(getUserRole(user.getUserRoles()));
		userResponse.setAccessRights(getUserAccessRights(user.getUserRoles()));
		userResponse.setSuccess(true);

		return userResponse;
	}

	private List<String> getUserRole(Set<Role> userRoles) {
		Set setRoles = new HashSet();
		for (Role userRole : userRoles) {
			setRoles.add(userRole.getName());
		}

		List roles = new ArrayList(setRoles);
		return roles;
	}

	private List<String> getUserAccessRights(Set<Role> userRoles) {
		Set setAccessRights = new HashSet();
		for (Role userRole : userRoles) {
			for (AccessRight accessRight : userRole.getRoleAccessRights()) {
				setAccessRights.add(accessRight.getName());
			}
		}

		List accessRights = new ArrayList(setAccessRights);
		return accessRights;
	}
}
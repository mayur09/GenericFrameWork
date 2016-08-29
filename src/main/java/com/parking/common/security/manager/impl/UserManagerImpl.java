/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.dao.UserDao;
import com.parking.common.security.exception.UserLoginException;
import com.parking.common.security.intercept.CustomSecurityMetadataSource;
import com.parking.common.security.manager.UserManager;
import com.parking.common.security.model.Role;
import com.parking.common.security.model.User;
import com.parking.common.security.utilities.CommonUtility;
import com.pmcretail.framework.security.model.AccessRight;

@Service("userManager")
public class UserManagerImpl extends GenericManagerImpl<User, String> implements UserManager {
	private static final Logger LOG = LogManager.getLogger(UserManagerImpl.class.getName());

	@Autowired
	@Qualifier("messageSource")
	private AbstractMessageSource messageSource;

	@Autowired
	@Qualifier("customSecurityMetadataSource")
	CustomSecurityMetadataSource customSecurityMetadataSource;

	@Autowired
	public UserManagerImpl(@Qualifier("userDao") GenericDao<User, String> genericDao) {
		super(genericDao);
	}

	@Transactional
	public User getUserByUserName(String username) {
		return ((UserDao) this.genericDao).getUserByUserName(username);
	}

	@Transactional
	public User getUserByUsernameAndPassword(String username, String password) {
		return ((UserDao) this.genericDao).getUser(username, password);
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.info("Start authenticating user with user name " + username);
		if ("".equals(username)) {
			throw new UserLoginException(this.messageSource.getMessage("802", null, Locale.getDefault()));
		}

		User user = getUserByUserName(username);

		if (user == null)
			throw new UserLoginException(this.messageSource.getMessage("800", null, Locale.getDefault()));
		if (user.getUserRoles().isEmpty()) {
			throw new UserLoginException(this.messageSource.getMessage("807", null, Locale.getDefault()));
		}
		populateSecurityMetadataSource();
		return buildUserForAuthentication(user, buildGrantedAuthority(user));
	}

	@Transactional(readOnly = true)
	public List<GrantedAuthority> getGrantedAuthority(String userName) {
		User user = getUserByUserName(userName);
		List authorities = buildGrantedAuthority(user);
		populateSecurityMetadataSource();

		return authorities;
	}

	protected org.springframework.security.core.userdetails.User buildUserForAuthentication(User user,
			List<GrantedAuthority> authorities) {
		boolean isEnabled = user.getStatus().ordinal() == 1;
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), isEnabled,
				true, true, true, authorities);
	}

	private List<GrantedAuthority> buildGrantedAuthority(User user) {
		List authorities = null;
		List filteredAuthorities = getFilteredAuthorities(user);

		if (filteredAuthorities == null)
			authorities = buildUserAuthority(user.getUserRoles());
		else {
			authorities = buildUserAuthority(user.getUserRoles(), filteredAuthorities);
		}

		return authorities;
	}

	private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {
		Set setAuths = new HashSet();

		for (Role userRole : userRoles) {
			for (AccessRight accessRight : userRole.getRoleAccessRights()) {
				setAuths.add(new SimpleGrantedAuthority(accessRight.getName()));
			}
			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
		}

		List authorities = new ArrayList(setAuths);
		LOG.debug("User Authorities :: " + authorities);
		return authorities;
	}

	protected List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles, List<AccessRight> filteredAccessRights) {
		Set setAuths = new HashSet();

		for (Role userRole : userRoles) {
			for (AccessRight accessRight : userRole.getRoleAccessRights()) {
				if (filteredAccessRights.contains(accessRight)) {
					setAuths.add(new SimpleGrantedAuthority(accessRight.getName()));
				}
			}
			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
		}

		List authorities = new ArrayList(setAuths);
		LOG.debug("User Authorities :: " + authorities);
		return authorities;
	}

	private void populateSecurityMetadataSource() {
		try {
			this.customSecurityMetadataSource.populateSecurityMetadataSource();
		} catch (Exception ex) {
			LOG.error("Error, while populating the intercept Url patterns :: " + ex);
		}
	}

	@Transactional
	public boolean changePassword(String username, String oldPassword, String newPassword) {
		boolean status = false;
		LOG.info("Start change password with this user name " + username);
		User user = getUserByUsernameAndPassword(username, CommonUtility.encodeMD5(oldPassword));
		if (user != null) {
			user.setPassword(CommonUtility.encodeMD5(newPassword));
			status = true;
			LOG.info("Change password successful " + user.getUserName());
		}

		return status;
	}

	protected List<AccessRight> getFilteredAuthorities(User user) {
		return null;
	}
}
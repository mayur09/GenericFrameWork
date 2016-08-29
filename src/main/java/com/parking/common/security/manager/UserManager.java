package com.parking.common.security.manager;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.parking.common.security.model.User;

public abstract interface UserManager extends GenericManager<User, String>, UserDetailsService {
	public abstract User getUserByUserName(String paramString);

	public abstract User getUserByUsernameAndPassword(String paramString1, String paramString2);

	public abstract boolean changePassword(String paramString1, String paramString2, String paramString3);

	public abstract List<GrantedAuthority> getGrantedAuthority(String paramString);
}
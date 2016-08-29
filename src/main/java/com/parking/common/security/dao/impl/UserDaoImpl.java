package com.parking.common.security.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.parking.common.dao.impl.GenericDaoImpl;
import com.parking.common.security.dao.UserDao;
import com.parking.common.security.model.User;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, String> implements UserDao {
	@Autowired
	public UserDaoImpl(@Value("com.pmcretail.framework.security.model.User") Class<User> persistentClass) {
		super(persistentClass);
	}

	public User getUserByUserName(String username) {
		Map params = new HashMap();
		params.put("username", username);

		List users = findByQuery("FROM User U WHERE U.userName = :username", params);

		if ((users != null) && (!(users.isEmpty()))) {
			return ((User) users.get(0));
		}
		return null;
	}

	public User getUser(String username, String password) {
		Map params = new HashMap();
		params.put("username", username);
		params.put("password", password);

		List users = findByQuery("FROM User U WHERE U.userName = :username AND U.password = :password", params);

		if ((users != null) && (!(users.isEmpty()))) {
			return ((User) users.get(0));
		}
		return null;
	}
}
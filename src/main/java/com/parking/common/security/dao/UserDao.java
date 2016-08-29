package com.parking.common.security.dao;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.model.User;

public abstract interface UserDao extends GenericDao<User, String> {
	public abstract User getUserByUserName(String paramString);

	public abstract User getUser(String paramString1, String paramString2);
}
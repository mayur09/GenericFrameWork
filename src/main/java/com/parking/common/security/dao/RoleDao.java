package com.parking.common.security.dao;

import java.util.Map;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.model.Role;

public abstract interface RoleDao extends GenericDao<Role, String> {
	public abstract int updateByNativeQuery(String paramString, Map<String, Object> paramMap);
}
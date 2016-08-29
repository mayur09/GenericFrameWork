package com.parking.common.security.dao;

import java.util.Map;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.model.URIPattern;

public abstract interface URIPatternDao extends GenericDao<URIPattern, String> {
	public abstract Map<String, StringBuilder> getAllURIPatternsWithRoles();
}
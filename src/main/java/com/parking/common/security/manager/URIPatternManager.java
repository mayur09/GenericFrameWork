package com.parking.common.security.manager;

import java.util.Map;

import com.parking.common.security.model.URIPattern;

public abstract interface URIPatternManager extends GenericManager<URIPattern, String> {
	public abstract Map<String, StringBuilder> getAllURIPatternsWithRoles();
}
/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.manager.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.dao.URIPatternDao;
import com.parking.common.security.manager.URIPatternManager;
import com.parking.common.security.model.URIPattern;

@Service("uriPatternManager")
public class URIPatternManagerImpl extends GenericManagerImpl<URIPattern, String> implements URIPatternManager {

	@Autowired
	@Qualifier("uriPatternDao")
	private URIPatternDao uriPatternDao;

	@Autowired
	public URIPatternManagerImpl(@Qualifier("uriPatternDao") GenericDao<URIPattern, String> genericDao) {
		super(genericDao);
	}

	@Transactional
	public Map<String, StringBuilder> getAllURIPatternsWithRoles() {
		return this.uriPatternDao.getAllURIPatternsWithRoles();
	}
}
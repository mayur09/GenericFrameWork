package com.parking.common.security.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.parking.common.dao.impl.GenericDaoImpl;
import com.parking.common.security.dao.URIPatternDao;
import com.parking.common.security.model.URIPattern;

@Repository("uriPatternDao")
public class URIPatternDaoImpl extends GenericDaoImpl<URIPattern, String> implements URIPatternDao {
	@Autowired
	public URIPatternDaoImpl(
			@Value("com.pmcretail.framework.security.model.URIPattern") Class<URIPattern> persistentClass) {
		super(persistentClass);
	}

	public Map<String, StringBuilder> getAllURIPatternsWithRoles() {
		Map customMetaDataMap = new HashMap();

		List<Object[]> list = getSelectedFieldsByNamedQuery("getAllURIPatternsWithRoles", null);

		for (Object[] arrAccessRight : list) {
			String strURIPattern = arrAccessRight[0].toString();
			String strAccess = arrAccessRight[1].toString();

			if (customMetaDataMap.containsKey(strURIPattern))
				((StringBuilder) customMetaDataMap.get(strURIPattern)).append(",'").append(strAccess).append("'");
			else {
				customMetaDataMap.put(strURIPattern, new StringBuilder("'").append(strAccess).append("'"));
			}
		}

		return customMetaDataMap;
	}
}
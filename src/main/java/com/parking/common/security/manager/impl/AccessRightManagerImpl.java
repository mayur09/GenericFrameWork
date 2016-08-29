package com.parking.common.security.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.manager.AccessRightManager;
import com.pmcretail.framework.security.model.AccessRight;

@Service("accessRightManager")
public class AccessRightManagerImpl extends GenericManagerImpl<AccessRight, String> implements AccessRightManager {
	private static final Logger LOG = LogManager.getLogger(AccessRightManagerImpl.class.getName());

	@Autowired
	public AccessRightManagerImpl(@Qualifier("accessRightDao") GenericDao<AccessRight, String> genericDao) {
		super(genericDao);
	}

	@Transactional
	public List<AccessRight> getAccessRightsByAscOrder() {
		Map params = new HashMap();
		return this.genericDao.findByNamedQuery("getAccessRightsByAscOrder", params);
	}

	@Transactional
	public List<AccessRight> getAccessRightsByAscMenuOrder() {
		Map params = new HashMap();
		return this.genericDao.findByNamedQuery("getAccessRightsByAscMenuOrder", params);
	}

	@Transactional
	public List<AccessRight> getAccessRightsByIds(List<String> accessRightIds) {
		Map paramsList = new HashMap();
		paramsList.put("accessRightIds", accessRightIds);
		List accessRights = this.genericDao.findByQuery("from AccessRight AR where AR.id in ( :accessRightIds )", null,
				paramsList, true);
		LOG.debug("accessRights : " + accessRights);
		return accessRights;
	}
}
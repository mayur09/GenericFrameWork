package com.parking.common.security.dao.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.parking.common.dao.impl.GenericDaoImpl;
import com.parking.common.exception.GenericRuntimeException;
import com.parking.common.security.dao.RoleDao;
import com.parking.common.security.model.Role;

@Repository("roleDao")
public class RoleDaoImpl extends GenericDaoImpl<Role, String> implements RoleDao {
	private static final Logger LOG = LogManager.getLogger(RoleDaoImpl.class.getName());

	@Autowired
	public RoleDaoImpl(@Value("com.pmcretail.framework.security.model.Role") Class<Role> persistentClass) {
		super(persistentClass);
	}

	public int updateByNativeQuery(String query, Map<String, Object> queryParams) {
		Session sess = getSession();
		Query namedQuery = sess.createSQLQuery(query);

		for (String s : queryParams.keySet()) {
			namedQuery.setParameter(s, queryParams.get(s));
		}
		try {
			return namedQuery.executeUpdate();
		} catch (HibernateException e) {
			LOG.error(e);
			throw new GenericRuntimeException();
		}
	}
}
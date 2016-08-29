/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.parking.common.dao.GenericDao;
import com.parking.common.exception.GenericDAOException;

public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {
	protected final Logger log = LogManager.getLogger(super.getClass().getName());

	@Resource
	private SessionFactory sessionFactory;
	private Class<T> persistentClass;

	public GenericDaoImpl() {
	}

	public GenericDaoImpl(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	@Autowired
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() throws HibernateException {
		Session sess = this.sessionFactory.getCurrentSession();
		if (sess == null) {
			sess = this.sessionFactory.openSession();
		}
		return sess;
	}

	public List<T> getAll() {
		Session sess = getSession();
		List list = sess.createCriteria(this.persistentClass).list();
		if ((list == null) || (list.isEmpty())) {
			this.log.error("getAll method return:" + list);
			throw new GenericDAOException();
		}
		return list;
	}

	public List<T> getAllDistinct() {
		Collection result = new LinkedHashSet(getAll());
		return new ArrayList(result);
	}

	public T get(Serializable id) {
		Session sess = getSession();
		IdentifierLoadAccess byId = sess.byId(this.persistentClass);
		Object entity = byId.load(id);

		if (entity == null) {
			this.log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
			throw new EntityNotFoundException();
		}

		return (T) entity;
	}

	public boolean exists(PK id) {
		Session sess = getSession();
		IdentifierLoadAccess byId = sess.byId(this.persistentClass);
		Object entity = byId.load(id);
		return (entity != null);
	}

	public T save(T object) {
		Session sess = getSession();
		try {
			sess.saveOrUpdate(object);
		} catch (NonUniqueObjectException e) {
			object = (T) sess.merge(object);
		} catch (HibernateException e) {
			this.log.error(e.getMessage(), e);
			throw new GenericDAOException(e);
		}
		return object;
	}

	public T merge(T object) {
		Session sess = getSession();
		try {
			sess.merge(object);
		} catch (HibernateException e) {
			this.log.error(e.getMessage(), e);
			throw new GenericDAOException(e);
		}
		return object;
	}

	public void remove(Serializable id) {
		Session sess = getSession();
		IdentifierLoadAccess byId = sess.byId(this.persistentClass);
		Object entity = byId.load(id);
		sess.delete(entity);
	}

	public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
		Session sess = getSession();
		Query namedQuery = sess.getNamedQuery(queryName);

		for (String s : queryParams.keySet())
			namedQuery.setParameter(s, queryParams.get(s));
		try {
			return namedQuery.list();
		} catch (HibernateException e) {
			this.log.error(e.getMessage(), e);
			throw new GenericDAOException(e);
		}
	}

	public Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	public int countAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(this.persistentClass);
		criteria.setProjection(Projections.rowCount());
		List results = findByCriteria(criteria);
		int count = ((Integer) results.get(0)).intValue();

		return count;
	}

	public List findByQuery(String query, Map<String, Object> parameters) {
		return findByQuery(query, parameters, null, false);
	}

	public List findByQuery(String query, Map<String, Object> parameters, Map<String, List<String>> parametersList,
			boolean isCacheble) {
		Session sess = getSession();
		Query queryObj = sess.createQuery(query);
		if (parameters != null) {
			if (isCacheble) {
				queryObj.setCacheable(true);
			}
			for (String s : parameters.keySet()) {
				queryObj.setParameter(s, parameters.get(s));
			}
		}
		if (parametersList != null) {
			for (String s : parametersList.keySet())
				queryObj.setParameterList(s, (Collection) parametersList.get(s));
		}
		try {
			return queryObj.list();
		} catch (HibernateException e) {
			this.log.error(e.getMessage(), e);
			throw new GenericDAOException(e);
		}
	}

	protected List findByCriteria(DetachedCriteria detachedCriteria) {
		return findByCriteria(detachedCriteria, -1, -1);
	}

	protected List findByCriteria(DetachedCriteria detachedCriteria, boolean isCacheble) {
		return findByCriteria(detachedCriteria, -1, -1, isCacheble);
	}

	protected List findByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResult) {
		return findByCriteria(detachedCriteria, firstResult, maxResult, false);
	}

	private List findByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResult, boolean isCacheble) {
		Session session = getSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		criteria.setCacheable(isCacheble);
		if (firstResult > 0) {
			criteria.setFirstResult(firstResult);
		}
		if (maxResult > 0)
			criteria.setMaxResults(maxResult);
		try {
			return criteria.list();
		} catch (HibernateException e) {
			this.log.error(e.getMessage(), e);
			throw new GenericDAOException(e);
		}
	}

	public int executeByQuery(String query, Map<String, Object> parameters) {
		return executeByQuery(query, parameters, null, false);
	}

	public int executeByQuery(String query, Map<String, Object> parameters, Map<String, List<String>> parametersList,
			boolean isCacheble) {
		Session sess = getSession();
		Query queryObj = sess.createQuery(query);
		if (parameters != null) {
			if (isCacheble) {
				queryObj.setCacheable(true);
			}
			for (String s : parameters.keySet()) {
				queryObj.setParameter(s, parameters.get(s));
			}
		}
		if (parametersList != null) {
			for (String s : parametersList.keySet())
				queryObj.setParameterList(s, (Collection) parametersList.get(s));
		}
		try {
			int count = queryObj.executeUpdate();
			return count;
		} catch (HibernateException e) {
			this.log.error(e.getMessage(), e);
			throw new GenericDAOException(e);
		}
	}

	public List<Object[]> getSelectedFieldsByNamedQuery(String queryName, Map<String, Object> queryParams) {
		Session sess = getSession();
		Query namedQuery = sess.getNamedQuery(queryName);

		if (queryParams != null) {
			for (String s : queryParams.keySet()) {
				namedQuery.setParameter(s, queryParams.get(s));
			}
		}
		return namedQuery.list();
	}

	public List<Object[]> findByNativeQuery(String queryName, Map<String, Object> queryParams) {
		Session sess = getSession();
		Query namedQuery = sess.createSQLQuery(queryName);

		if (queryParams != null) {
			for (String s : queryParams.keySet()) {
				namedQuery.setParameter(s, queryParams.get(s));
			}
		}
		return namedQuery.list();
	}

	public List<Object[]> findByNativeQueryWithPagination(String queryName, Map<String, Object> queryParams,
			int startRow, int maxResult) {
		Session sess = getSession();
		Query nativeQuery = sess.createSQLQuery(queryName);

		if (queryParams != null) {
			for (String s : queryParams.keySet()) {
				nativeQuery.setParameter(s, queryParams.get(s));
				nativeQuery.setFirstResult(startRow);
				nativeQuery.setMaxResults(maxResult);
			}
		}
		return nativeQuery.list();
	}

	public List findByQuery(String query, boolean isCacheble, Map<String, ? extends Object> params) {
		Session sess = getSession();
		Query queryObj = sess.createQuery(query);
		return executeQuery(queryObj, isCacheble, params);
	}

	public List findByNamedQuery(String query, boolean isCacheble, Map<String, ? extends Object> params) {
		Session sess = getSession();
		Query namedQuery = sess.getNamedQuery(query);
		return executeQuery(namedQuery, isCacheble, params);
	}

	private List executeQuery(Query queryObj, boolean isCacheble, Map<String, ? extends Object> params) {
		if (isCacheble) {
			queryObj.setCacheable(true);
		}

		for (Map.Entry param : params.entrySet()) {
			if (param.getValue() instanceof List)
				queryObj.setParameterList((String) param.getKey(), (List) param.getValue());
			else {
				queryObj.setParameter((String) param.getKey(), param.getValue());
			}
		}
		try {
			return queryObj.list();
		} catch (HibernateException e) {
			this.log.error(e.getMessage(), e);
			throw new GenericDAOException(e);
		}
	}
}
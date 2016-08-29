package com.parking.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
 
public abstract interface GenericDao<T, PK extends Serializable> {
	public abstract Session getSession() throws HibernateException;

	public abstract List<T> getAll();

	public abstract T get(PK paramPK);

	public abstract boolean exists(PK paramPK);

	public abstract T save(T paramT);

	public abstract T merge(T paramT);

	public abstract void remove(Serializable paramSerializable);

	public abstract List<T> getAllDistinct();

	public abstract List<T> findByQuery(String paramString, Map<String, Object> paramMap);

	public abstract List<T> findByQuery(String paramString, Map<String, Object> paramMap,
			Map<String, List<String>> paramMap1, boolean paramBoolean);

	public abstract List<T> findByNamedQuery(String paramString, Map<String, Object> paramMap);

	public abstract Class<T> getPersistentClass();

	public abstract int countAll();

	public abstract int executeByQuery(String paramString, Map<String, Object> paramMap);

	public abstract List<Object[]> getSelectedFieldsByNamedQuery(String paramString, Map<String, Object> paramMap);

	public abstract List<Object[]> findByNativeQuery(String paramString, Map<String, Object> paramMap);

	public abstract List<Object[]> findByNativeQueryWithPagination(String paramString, Map<String, Object> paramMap,
			int paramInt1, int paramInt2);

	public abstract List<T> findByQuery(String paramString, boolean paramBoolean,
			Map<String, ? extends Object> paramMap);

	public abstract List<T> findByNamedQuery(String paramString, boolean paramBoolean,
			Map<String, ? extends Object> paramMap);
}
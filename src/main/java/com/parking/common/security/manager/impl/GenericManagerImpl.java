/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.parking.common.dao.GenericDao;
import com.parking.common.exception.GenericDAOException;
import com.parking.common.security.manager.GenericManager;

public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {
	protected final Logger LOG = LogManager.getLogger(super.getClass().getName());
	protected GenericDao<T, PK> genericDao;

	public GenericManagerImpl(GenericDao<T, PK> genericDao) {
		this.genericDao = genericDao;
	}

	@Transactional
	public List<T> getAll() {
		return this.genericDao.getAll();
	}

	@Transactional
	public T get(PK id) {
		return this.genericDao.get(id);
	}

	@Transactional
	public boolean exists(PK id) {
		return this.genericDao.exists(id);
	}

	@Transactional
	public T save(T object) {
		return this.genericDao.save(object);
	}

	@Transactional
	public T merge(T object) {
		return this.genericDao.merge(object);
	}

	@Transactional
	public void remove(Serializable id) {
		this.genericDao.remove(id);
	}

	@Transactional
	public T create() {
		Object instance = null;
		try {
			instance = this.genericDao.getPersistentClass().newInstance();
		} catch (InstantiationException e) {
			throw new GenericDAOException(e);
		} catch (IllegalAccessException e) {
			throw new GenericDAOException(e);
		}
		return (T) instance;
	}

	@Transactional
	public int countAll() {
		return this.genericDao.countAll();
	}

	public void setDao(GenericDao<T, PK> dao) {
		this.genericDao = dao;
	}
}
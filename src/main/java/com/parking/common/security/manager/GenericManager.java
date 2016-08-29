package com.parking.common.security.manager;

import java.io.Serializable;
import java.util.List;

import com.parking.common.dao.GenericDao;

public abstract interface GenericManager<T, PK extends Serializable> {
	public abstract List<T> getAll();

	public abstract T get(PK paramPK);

	public abstract boolean exists(PK paramPK);

	public abstract T save(T paramT);

	public abstract T merge(T paramT);

	public abstract void remove(Serializable paramSerializable);

	public abstract void setDao(GenericDao<T, PK> paramGenericDao);

	public abstract T create();

	public abstract int countAll();
}
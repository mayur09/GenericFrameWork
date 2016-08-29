/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.manager;

import java.util.List;

import com.pmcretail.framework.security.model.AccessRight;

public abstract interface AccessRightManager extends GenericManager<AccessRight, String> {
	public abstract List<AccessRight> getAccessRightsByAscOrder();

	public abstract List<AccessRight> getAccessRightsByIds(List<String> paramList);

	public abstract List<AccessRight> getAccessRightsByAscMenuOrder();
}
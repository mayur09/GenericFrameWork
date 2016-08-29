package com.parking.common.security.manager;

import java.util.List;
import java.util.Set;

import com.parking.common.security.StatusType;
import com.parking.common.security.model.Role;

public abstract interface RoleManager extends GenericManager<Role, String> {
	public abstract List<Role> getRolesByStatus(StatusType paramStatusType);

	public abstract List<Role> getRolesByIds(List<String> paramList);

	public abstract boolean isRoleNameExist(String paramString1, String paramString2);

	public abstract int deleteUsersMappingOnRoleDisable(String paramString);

	public abstract int disableRole(Role paramRole);

	public abstract Role saveOrUpdateRole(String[] paramArrayOfString, Role paramRole);

	public abstract boolean isRoleChanged(Set<Role> paramSet, String[] paramArrayOfString);
}
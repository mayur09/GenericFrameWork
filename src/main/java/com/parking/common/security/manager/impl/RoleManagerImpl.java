
package com.parking.common.security.manager.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.common.dao.GenericDao;
import com.parking.common.exception.GenericDAOException;
import com.parking.common.security.StatusType;
import com.parking.common.security.dao.RoleDao;
import com.parking.common.security.manager.AccessRightManager;
import com.parking.common.security.manager.RoleManager;
import com.parking.common.security.model.Role;
import com.pmcretail.framework.security.model.AccessRight;

@Service("roleManager")
public class RoleManagerImpl extends GenericManagerImpl<Role, String> implements RoleManager {
	private static final Logger LOG = LogManager.getLogger(RoleManagerImpl.class.getName());

	@Autowired
	@Qualifier("accessRightManager")
	private AccessRightManager accessRightManager;

	@Autowired
	public RoleManagerImpl(@Qualifier("roleDao") GenericDao<Role, String> genericDao) {
		super(genericDao);
	}

	@Transactional
	public List<Role> getRolesByStatus(StatusType status) {
		List roles = null;
		Map params = new HashMap();

		if (status != null) {
			params.put("status", status);
			roles = this.genericDao.findByNamedQuery("getRolesByStatus", params);
		} else {
			roles = this.genericDao.getAll();
		}

		return roles;
	}

	@Transactional
	public List<Role> getRolesByIds(List<String> roleIds) {
		Map paramsList = new HashMap();
		paramsList.put("roleIds", roleIds);
		List roles = this.genericDao.findByQuery("from Role R where R.id in ( :roleIds )", null, paramsList, true);
		return roles;
	}

	@Transactional
	public boolean isRoleNameExist(String roleName, String roleId) {
		Map params = new HashMap();
		params.put("roleName", roleName);
		params.put("roleId", roleId);
		try {
			List roles = this.genericDao.findByNamedQuery("getRolesByName", params);
			if (roles.isEmpty())
				return false;
		} catch (GenericDAOException e) {
			LOG.error(e);
			return false;
		}

		return true;
	}

	@Transactional
	public int deleteUsersMappingOnRoleDisable(String roleId) {
		String sqlQuery = "delete from user_roles WHERE role_id = :roleId";
		Map params = new HashMap();
		params.put("roleId", roleId);

		int result = ((RoleDao) this.genericDao).updateByNativeQuery(sqlQuery, params);
		LOG.debug("Number of records deleted : " + result);
		return result;
	}

	@Transactional
	public int disableRole(Role role) {
		//role.setStatus(StatusType.DISABLED);
		save(role);
		return deleteUsersMappingOnRoleDisable(role.getId());
	}

	@Transactional
	public Role saveOrUpdateRole(String[] accessRights, Role role) {
		List accessRightIds = Arrays.asList(accessRights);
		Set roleAccessRights = getSelectedAccessRightsWithParent(accessRightIds);
		role.setRoleAccessRights(roleAccessRights);

		if (StringUtils.isEmpty(role.getId())) {
			role.setId(null);
		}
		return ((Role) save(role));
	}

	private Set<AccessRight> getSelectedAccessRightsWithParent(List<String> accessRightIds) {
		List<AccessRight> selectedAccessRights = this.accessRightManager.getAccessRightsByIds(accessRightIds);
		Set<AccessRight> roleAccessRights = new HashSet(selectedAccessRights);
		for (AccessRight accessRight : selectedAccessRights) {
			while (accessRight.getParentAccessRight() != null) {
				roleAccessRights.add(accessRight.getParentAccessRight());
				accessRight = accessRight.getParentAccessRight();
			}
		}
		return roleAccessRights;
	}

	public boolean isRoleChanged(Set<Role> oldRoles, String[] newRoles) {
		boolean matched = false;
		if (oldRoles.size() == newRoles.length) {
			for (Role role : oldRoles) {
				for (int i = 0; i < newRoles.length; ++i) {
					if (role.getId().compareTo(newRoles[i]) == 0) {
						matched = true;
						break;
					}
					matched = false;
				}
			}

			return (!(matched));
		}
		return true;
	}
}
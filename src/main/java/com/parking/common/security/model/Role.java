/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.parking.common.security.StatusType;
import com.pmcretail.framework.security.model.AccessRight;

@NamedQueries({
		@javax.persistence.NamedQuery(name = "getRolesByStatus", query = "from Role R where R.status = :status"),
		@javax.persistence.NamedQuery(name = "getRolesByName", query = "from Role R where lower(R.name) = lower(:roleName) AND R.id != :roleId") })
@Audited
@Entity
@Table(name = "role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String GET_ROLES_BY_STATUS = "getRolesByStatus";
	static final String GET_ROLES_BY_STATUS_QUERY = "from Role R where R.status = :status";
	public static final String GET_ROLES_BY_IDS_QUERY = "from Role R where R.id in ( :roleIds )";
	public static final String GET_ROLES_BY_NAME = "getRolesByName";
	static final String GET_ROLES_BY_NAME_QUERY = "from Role R where lower(R.name) = lower(:roleName) AND R.id != :roleId";

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "name", length = 25, nullable = false, unique = true)
	private String name;

	@Column(name = "status", nullable = true, length = 5, unique = false)
	private StatusType status;

	@Column(name = "auth_level", nullable = true, unique = false)
	private Integer authLevel;

	@NotAudited
	@ManyToMany
	@JoinTable(name = "role_access_rights", joinColumns = {
			@javax.persistence.JoinColumn(name = "role_id", nullable = true) }, inverseJoinColumns = {
					@javax.persistence.JoinColumn(name = "access_right_id", nullable = true) })
	private Set<AccessRight> roleAccessRights;

	public Role() {
		this.status = StatusType.ENABLED;

		this.authLevel = Integer.valueOf(-1);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StatusType getStatus() {
		return this.status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Integer getAuthLevel() {
		return this.authLevel;
	}

	public void setAuthLevel(Integer authLevel) {
		this.authLevel = authLevel;
	}

	public Set<AccessRight> getRoleAccessRights() {
		return this.roleAccessRights;
	}

	public void setRoleAccessRights(Set<AccessRight> roleAccessRights) {
		this.roleAccessRights = roleAccessRights;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	public boolean equals(Object objRole) {
		if (this == objRole) {
			return true;
		}
		if ((objRole != null) && (objRole instanceof Role)) {
			Role role = (Role) objRole;
			return ((role.getId() != null) && (role.getId().equals(getId())));
		}
		return false;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [id=").append(this.id);
		builder.append(", name=").append(this.name);
		builder.append(", status=").append(this.status);
		builder.append(", authLevel=").append(this.authLevel);
		builder.append("]");
		return builder.toString();
	}
}
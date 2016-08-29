/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.pmcretail.framework.security.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.parking.common.security.model.Role;
import com.parking.common.security.model.URIPattern;

@NamedQueries({
		@javax.persistence.NamedQuery(name = "getAccessRightsByAscOrder", query = "from AccessRight AR where AR.parentAccessRight != null ORDER BY AR.id"),
		@javax.persistence.NamedQuery(name = "getAccessRightsByAscMenuOrder", query = "from AccessRight AR where AR.parentAccessRight != null ORDER BY AR.menuOrder") })
@Entity
@Table(name = "access_right")
public class AccessRight implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String GET_ACCESS_RIGHTS_BY_ASC_ORDER = "getAccessRightsByAscOrder";
	static final String GET_ACCESS_RIGHTS_BY_ASC_ORDER_QUERY = "from AccessRight AR where AR.parentAccessRight != null ORDER BY AR.id";
	public static final String GET_ACCESS_RIGHTS_BY_IDS_QUERY = "from AccessRight AR where AR.id in ( :accessRightIds )";
	public static final String GET_ACCESS_RIGHTS_BY_ASC_MENU_ORDER = "getAccessRightsByAscMenuOrder";
	static final String GET_ACCESS_RIGHTS_BY_ASC_MENU_ORDER_QUERY = "from AccessRight AR where AR.parentAccessRight != null ORDER BY AR.menuOrder";

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "name", length = 60, nullable = false, unique = true)
	private String name;

	@Column(name = "description", length = 120, nullable = false)
	private String description;

	@OneToMany(mappedBy = "accessRight")
	private Set<URIPattern> uriPattern;

	@ManyToMany(cascade = { javax.persistence.CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "roleAccessRights")
	private Set<Role> roles;

	@ManyToOne(cascade = { javax.persistence.CascadeType.ALL })
	@JoinColumn(name = "parent")
	private AccessRight parentAccessRight;

	@Column(name = "menu_order")
	private float menuOrder;

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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<URIPattern> getUriPattern() {
		return this.uriPattern;
	}

	public void setUriPattern(Set<URIPattern> uriPattern) {
		this.uriPattern = uriPattern;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public AccessRight getParentAccessRight() {
		return this.parentAccessRight;
	}

	public void setParentAccessRight(AccessRight parentAccessRight) {
		this.parentAccessRight = parentAccessRight;
	}

	public float getMenuOrder() {
		return this.menuOrder;
	}

	public void setMenuOrder(float menuOrder) {
		this.menuOrder = menuOrder;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	public boolean equals(Object objAccessRight) {
		if (this == objAccessRight) {
			return true;
		}
		if ((objAccessRight != null) && (objAccessRight instanceof AccessRight)) {
			AccessRight accessRight = (AccessRight) objAccessRight;
			return ((accessRight.getId() != null) && (accessRight.getId().equals(getId())));
		}
		return false;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccessRight [id=").append(this.id);
		builder.append(", name=").append(this.name);
		builder.append(", description=").append(this.description);
		builder.append("]");
		return builder.toString();
	}
}
/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.pmcretail.framework.security.model.AccessRight;

@NamedQueries({
		@javax.persistence.NamedQuery(name = "getAllURIPatternsWithRoles", query = "select URI.name, R.name from URIPattern URI join URI.accessRight AR join AR.roles R where R.status = 1 ") })
@Entity
@Table(name = "uri_pattern")
public class URIPattern implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String GET_ALL_URI_PATTERNS_WITH_ROLES = "getAllURIPatternsWithRoles";
	static final String GET_ALL_URI_PATTERNS_WITH_ROLES_QUERY = "select URI.name, R.name from URIPattern URI join URI.accessRight AR join AR.roles R where R.status = 1 ";

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "name", length = 255, nullable = false, unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "access_right_id")
	private AccessRight accessRight;

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

	public AccessRight getAccessRight() {
		return this.accessRight;
	}

	public void setAccessRight(AccessRight accessRight) {
		this.accessRight = accessRight;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	public boolean equals(Object objURIPattern) {
		if (this == objURIPattern) {
			return true;
		}
		if ((objURIPattern != null) && (objURIPattern instanceof URIPattern)) {
			URIPattern uriPattern = (URIPattern) objURIPattern;
			return ((uriPattern.getId() != null) && (uriPattern.getId().equals(getId())));
		}
		return false;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("URIPattern [id=").append(this.id);
		builder.append(", name=").append(this.name);
		builder.append("]");
		return builder.toString();
	}
}
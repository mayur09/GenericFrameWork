/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import com.parking.common.security.StatusType;

@Audited
@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("-")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String GET_USER_BY_USERNAME = "FROM User U WHERE U.userName = :username";
	public static final String GET_USER_BY_USERNAME_PASSWORD = "FROM User U WHERE U.userName = :username AND U.password = :password";

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	protected String id;

	@Column(name = "username", length = 45, nullable = false, unique = true)
	protected String userName;

	@Column(name = "password", nullable = false)
	protected String password;

	@Column(name = "status", nullable = true, length = 5, unique = false)
	protected StatusType status;

	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = {
			@javax.persistence.JoinColumn(name = "user_id", nullable = true) }, inverseJoinColumns = {
					@javax.persistence.JoinColumn(name = "role_id", nullable = true) })
	protected Set<Role> userRoles;

	public User() {
		this.status = StatusType.ENABLED;

		this.userRoles = new HashSet();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public StatusType getStatus() {
		return this.status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public Set<Role> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	public boolean equals(Object objUser) {
		if (this == objUser) {
			return true;
		}
		if ((objUser != null) && (objUser instanceof User)) {
			User user = (User) objUser;
			return ((user.getId() != null) && (user.getId().equals(getId())));
		}
		return false;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(this.id);
		builder.append(", userName=").append(this.userName);
		builder.append(", status=").append(this.status);
		builder.append("]");
		return builder.toString();
	}
}
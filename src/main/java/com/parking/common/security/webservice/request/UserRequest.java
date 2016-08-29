/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.webservice.request;

import java.io.Serializable;

public class UserRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String newPassword;

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

	public String getNewPassword() {
		return this.newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean validateRequest() {
		return ((this.userName != null) && (!(this.userName.isEmpty())) && (this.password != null)
				&& (!(this.password.isEmpty())));
	}

	public String toString() {
		return "UserRequest[userName = " + this.userName + ", password = " + this.password + ", newPassword = "
				+ this.newPassword + "]";
	}
}
/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.webservice.response;

import java.io.Serializable;

public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String permission;

	public Menu(String id, String name, String permission) {
		this.id = id;
		this.name = name;
		this.permission = permission;
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

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}
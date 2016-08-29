/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.webservice;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.parking.common.security.webservice.request.UserRequest;

@WebService(serviceName = "userServices", targetNamespace = "/services")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public abstract interface UserService {
	@POST
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@Path("/login")
	public abstract Response userAuthentication(@Context HttpHeaders paramHttpHeaders, UserRequest paramUserRequest);

	@POST
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@Path("/changePassword")
	public abstract Response changePassword(@Context HttpHeaders paramHttpHeaders, UserRequest paramUserRequest);
}
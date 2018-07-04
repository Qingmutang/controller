package com.modianli.power.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthenticationToken {

	private String username;
	private Map<String, Boolean> roles;
	private String token;

	public AuthenticationToken() {
		super();
	}

	public AuthenticationToken(String username, Map<String, Boolean> roles, String token) {

		Map<String, Boolean> mapOfRoles = new ConcurrentHashMap<String, Boolean>();
		for (Map.Entry<String, Boolean> entry : roles.entrySet()) {
			mapOfRoles.put(entry.getKey(), entry.getValue());
		}

		this.roles = mapOfRoles;
		this.token = token;
		this.username = username;
	}

	public Map<String, Boolean> getRoles() {
		return this.roles;
	}

	public String getToken() {
		return this.token;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
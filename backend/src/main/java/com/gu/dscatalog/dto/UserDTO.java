package com.gu.dscatalog.dto;

import java.util.ArrayList;
import java.util.List;

import com.gu.dscatalog.entities.User;

public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;

	private List<RoleDTO> roles = new ArrayList<>();

	public UserDTO(Long id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public UserDTO(User entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		email = entity.getEmail();
		roles = entity.getRoles().stream().map(x -> new RoleDTO(x)).toList();
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

}

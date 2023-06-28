package com.gu.dscatalog.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.gu.dscatalog.entities.User;

public class UserDTO {

	private Long id;
	@NotBlank(message = "Campo requirido")
	private String firstName;
	private String lastName;
	@Email(message = "Favor entrar com email válido")
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

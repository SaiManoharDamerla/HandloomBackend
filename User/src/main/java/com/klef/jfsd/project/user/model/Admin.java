package com.klef.jfsd.project.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Admin {
	@Id
	private String username;
	private String email;
	private String password;

}

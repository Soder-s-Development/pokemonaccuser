package com.juliano.app.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

	@NotNull
	private String first_name;
	
	private String sure_name;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int sucoin = 0;
	
	private int coin = 0;
	
	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	private int age;
	
	@NotBlank
	private String email;

	@NotNull
	@Column(name = "actived")
	private boolean actived = false;
}

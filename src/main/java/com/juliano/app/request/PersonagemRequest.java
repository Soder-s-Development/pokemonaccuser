package com.juliano.app.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonagemRequest {

	private Long id_conta;
	private String nome;

	public void setId_conta(Long id) {
		this.id_conta = id;
	}
	
}

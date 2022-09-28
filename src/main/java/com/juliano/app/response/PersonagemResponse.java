package com.juliano.app.response;

import java.util.Set;

import com.juliano.app.Models.PokemonUnico;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonagemResponse {
	
private Long id;
	
	private Long id_conta;
	
	private String nome;
    
	private Set<PokemonUnico> holds;
    
    private Integer nivel;
    
    private Integer experiencia;

}

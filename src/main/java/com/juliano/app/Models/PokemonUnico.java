package com.juliano.app.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PokemonUnico {

private String tipo;
	
	@Nullable
	private String apelido;
	
	@Nullable
	private int nivel;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long id_pokemon;
	
	private String nome_pokemon;
	
	private Long personagem_id;
	
	private int novo_hp;
	private int novo_atk;
	private int novo_def;
	private int novo_spa;
	private int novo_spd;
	private int novo_spe;
	
	private int dias_de_vida;
	private int dias_vivido;
	private String conquistas;
	private String crias;
	private boolean vivo;
	private String genero;
	
	private int hp_atual;
	private int stamina;
	private int stamina_atual;
	private int evoluvao_estado;
}

package com.juliano.app.Models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springfox.documentation.spring.web.json.Json;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Personagem {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long id_conta;
	
	@NotNull
	@NotBlank
	private String nome;
	
	@ElementCollection
	@Column(columnDefinition = "json", name="pkmu_ids")
	@JsonRawValue
	private Set<Long> pkmu_ids;
	
	@ElementCollection
    @Column(columnDefinition = "json", name="hold_ids")
    @JsonRawValue
	private Set<Long> hold_ids;
    
    private Integer nivel;
    
    private Integer experiencia;
	
	public int getPartyLength() {
		return this.hold_ids != null ? this.hold_ids.size() : 0;
	}
	
	public boolean hasSpaceInParty() {
		return this.hold_ids != null 
				? this.hold_ids.size() < 6 : false;
	}
	
	public Set<Long> setPokemonIntoParty(Long id) {
		if(hasSpaceInParty()) {
			hold_ids.add(id);
			pkmu_ids.add(id);
		}else {
			pkmu_ids.add(id);
		}
		return this.pkmu_ids;
	}
}

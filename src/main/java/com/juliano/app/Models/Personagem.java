package com.juliano.app.Models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.relational.core.mapping.Embedded.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	
	@Column(name="pkmu_ids")
	private String pkmu_ids;
	
    @Column(name="hold_ids")
    private String hold_ids;
    
	@Nullable
	@Transient
	private Set<Long> _holds = new HashSet();
	@Nullable
	@Transient
	private Set<Long> _ids = new HashSet();

	@Column(name="level")
    private Integer nivel;

	@Column(name="experience")
    private Integer experiencia;
	
	public int getPartyLength() {
		converterIds();
		return this._holds != null ? this._holds.size() : 0;
	}
	
	public boolean hasSpaceInParty() {
		converterIds();
		return this._holds != null 
				? this._holds.size() < 6 : false;
	}
	
	public Set<Long> setPokemonIntoParty(Long id) {
		converterIds();
		converterHoldIds();
		if(hasSpaceInParty()) {
			_holds.add(id);
			_ids.add(id);
			this.hold_ids = this.hold_ids.split(",").length > 0 ? this.hold_ids+","+id.toString() : id.toString();
		}else {
			_ids.add(id);
		}
		this.pkmu_ids = this.pkmu_ids.split(",").length > 0 ? this.pkmu_ids+","+id.toString() : id.toString();
		return this._ids;
	}
	public Set<Long> getIds(){
		converterIds();
		return _ids;
	}
	public Set<Long> getHolds(){
		return converterHoldIds() == true ? _holds : new HashSet();
	}
	
	private Boolean converterIds(){
		System.out.println("converterIds");
		List<String> list;
		if(pkmu_ids!=null && !pkmu_ids.isEmpty()) {
			list = Arrays.asList(pkmu_ids.split(","));
			list.forEach(l -> {
				_ids.add(Long.valueOf(l));
			});
			System.out.println(_ids);
			return true;
		}
		return false;
	}
	
	private Boolean converterHoldIds(){
		System.out.println("converterHolds");
		List<String> list;
		if(hold_ids!=null && !hold_ids.isEmpty()) {
			list = Arrays.asList(hold_ids.split(","));
			
			list.forEach(l -> {
				_holds.add(Long.valueOf(l));
			});
			System.out.println(_holds);
			return true;
		}
		return false;
	}
	
	private String converterToString(Set<Long> list) {
        StringBuilder strbul=new StringBuilder();
        for(Long l : list)
        {
            strbul.append(l.toString());
            strbul.append(",");
        }
        strbul.setLength(strbul.length()-1);
        return strbul.toString();
	}
}

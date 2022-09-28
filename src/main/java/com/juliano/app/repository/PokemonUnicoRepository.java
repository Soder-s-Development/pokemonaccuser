package com.juliano.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juliano.app.Models.PokemonUnico;

@Repository
public interface PokemonUnicoRepository extends JpaRepository<PokemonUnico, Long> {

}

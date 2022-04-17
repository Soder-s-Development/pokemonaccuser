package com.juliano.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juliano.app.Models.Personagem;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {

}

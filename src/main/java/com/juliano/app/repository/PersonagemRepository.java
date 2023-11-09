package com.juliano.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.juliano.app.Models.Personagem;


@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
	
	List<Personagem> findByNome(String nome);

    @Query("SELECT p FROM Personagem p WHERE p.id_conta = :id_conta")
    List<Personagem> findAllById_conta(Long id_conta);
}

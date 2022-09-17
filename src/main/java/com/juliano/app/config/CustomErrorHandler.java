package com.juliano.app.config;

import com.juliano.app.Models.Personagem;

import lombok.var;

public class CustomErrorHandler{

	public RespostaPadrao contaNaoExistente(Personagem p){
		return new RespostaPadrao("Nao existe conta com esse id: "+p.getId_conta(), 422, null);
	}

	public RespostaPadrao PersonagemNomeJaExiste(Personagem p) {
		return new RespostaPadrao("Ja existe personagem com esse nome: "+p.getNome(), 422, null);
	}
}

package com.juliano.app.config;

import com.juliano.app.request.PersonagemRequest;

public class CustomResponse{

	public RespostaPadrao contaNaoExistente(PersonagemRequest p){
		return new RespostaPadrao("Nao existe conta com esse id: "+p.getId_conta(), 422, null);
	}

	public RespostaPadrao personagemNomeJaExiste(PersonagemRequest p) {
		return new RespostaPadrao("Ja existe personagem com esse nome: "+p.getNome(), 422, null);
	}
	
	public static RespostaPadrao naoEncontrado(String mensagem, Object object) {
		return new RespostaPadrao(mensagem, 404, object);
	}
	
}

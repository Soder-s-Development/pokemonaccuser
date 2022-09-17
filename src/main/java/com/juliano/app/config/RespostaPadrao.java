package com.juliano.app.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespostaPadrao {
	
	private String mensagem;
	
	private int status;
	
	private Object response;
		
}

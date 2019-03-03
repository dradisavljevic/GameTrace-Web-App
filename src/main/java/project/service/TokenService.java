package project.service;

import org.springframework.data.domain.Page;

import project.domain.Token;

public interface TokenService {

	Page<Token> getAll();
	
	Token getToken(Long id);
	
	Token getTokenByHash(String hash);
	
	Token getTokenByEmail(String email);
	
	Token addToken(Token token);
	
	void removeTokenById(Long id);
	
	
}
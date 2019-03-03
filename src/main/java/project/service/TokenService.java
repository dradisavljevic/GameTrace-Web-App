package project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.Token;

public interface TokenService {

	Page<Token> getAll();
	
	Token getToken(Long id);
	
	Token getTokenByHash(String hash);
	
	Token getTokenByEmail(String email);
	
	Token addToken(Token token);
	
	void removeTokenById(Long id);
	
	
}
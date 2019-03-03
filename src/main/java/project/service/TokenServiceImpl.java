package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Token;
import project.repository.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public Page<Token> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token getToken(Long id) {
		Assert.notNull(id);
		return tokenRepository.findTokenById(id);
	}

	@Override
	public Token getTokenByHash(String hash) {
		Assert.notNull(hash);
		return tokenRepository.findTokenByHash(hash);
	}

	@Override
	public Token getTokenByEmail(String email) {
		Assert.notNull(email);
		return tokenRepository.findTokenByEmail(email);
	}
	
	@Override
	public Token addToken(Token token) {
		return tokenRepository.save(token);
	}

	@Override
	public void removeTokenById(Long id) {
		Assert.notNull(id);
		tokenRepository.removeTokenById(id);
		
	}
}

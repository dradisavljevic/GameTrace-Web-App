package project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Token;

public interface TokenRepository extends Repository<Token, Long> {

	public Page<Token> findAll(Pageable page);
	
	@Query("select t from Token t where t.tokid = ?1")
	public Token findTokenById(Long id);
	
	@Query("select t from Token t where t.token = ?1")
	public Token findTokenByHash(String hash);
	
	@Query("select t from Token t where t.tokem = ?1")
	public Token findTokenByEmail(String email);
	
	public Token save(Token token);
	
	@Modifying
	@Query("delete from Token t where t.tokid = ?1 ")
	public void removeTokenById(Long id);
}

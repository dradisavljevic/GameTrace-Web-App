package project.service;



import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.Game;
import project.domain.dto.CompactGameDTO;

public interface GameService {
	
	List<Game> getAll();
	
	List<Game> getGameByName(String name);
	
	List<Game> getGameByYear(BigDecimal year);
	
	List<Game> getAllByStreamer(Long id);
	
	List<CompactGameDTO> fillGameList();
	
	Game getGameById(Long id);
	
	void removeGameById(Long id);
	
	void updateGameDescription(Long id, String desc);
	
	void updateGameDetectionRule(Long id, String detection);
	
	void updateGameImage(Long id, String gimg);
	
	void updateGameYear(Long id, BigDecimal year);
	
	void updateGameName(Long id, String name);
	
	void updateGame(Long id, String name, String desc, String rule, String gimg, BigDecimal year);
	
	Long getCurrentSequence();
	
	Game save(Game g);
	
	Game getGameByNameAndYear(String name, BigDecimal year);
	
	List<Game> getRecommendation(String username);
	
	List<Game> getAllByNameStarting(String name);
	
	List<Game> getAllByHash();
}

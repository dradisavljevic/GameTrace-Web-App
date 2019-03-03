package project.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.Game;
import project.domain.GameDeveloper;
import project.domain.Player;

public interface GameDeveloperService {

public List<GameDeveloper> findAll();
	
	GameDeveloper save(GameDeveloper g);
	
	List<GameDeveloper> getAllByName(String name);
	
	List<GameDeveloper> getAllByCount(BigDecimal count);
	
	List<GameDeveloper> getAllByGreaterCount(BigDecimal count);
	
	List<GameDeveloper> getAllByLowerCount(BigDecimal count);
	
	GameDeveloper getGameDeveloperById(Long id);
	
	GameDeveloper getGameDeveloperByName(String name);
	
	void removeGameDeveloperById(Long id);
	
	void updateGameDeveloperName(Long id, String name);
	
	List<Game> getDevelopedGames(Long id);
	
	List<GameDeveloper> getAll();
}

package project.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import project.domain.FriendsInGame;
import project.domain.Play;
import project.domain.PlayedGame;
import project.domain.Player;
import project.domain.TopGame;

public interface PlayService {

	List<Play> findAll();
	
	Play save(Play p);
	
	Play getPlayByKey(String name, Long id, Date date, Timestamp time);
	
	void removePlayByKey(String name, Long id, Date date, Timestamp time);
	
	void removePlayByUser(String name);
	
	void removePlayByGame(Long id);
	
	List<Player> getTopPlayers(Long id);
	
	List<TopGame> getTopGames(String username);
	
	List<PlayedGame> getPlayedGames(String username);
	
	List<FriendsInGame> getFriendsInGame(String username);
	
	List<Play> getPlaysByGameAndUser(String user, Long game);

}

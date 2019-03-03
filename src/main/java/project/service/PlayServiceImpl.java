package project.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.FriendsInGame;
import project.domain.Play;
import project.domain.PlayedGame;
import project.domain.Player;
import project.domain.TopGame;
import project.repository.PlayRepository;

@Service
public class PlayServiceImpl implements PlayService{
	
	@Autowired
	private PlayRepository playRepository;
	
	@Autowired
    private JpaContext jpaContext;

	@Override
	public List<Play> findAll() {
		return playRepository.findAll();
	}

	@Override
	public Play save(Play p) {
		Assert.notNull(p);
		return playRepository.save(p);
	}

	@Override
	public Play getPlayByKey(String name, Long id, Date date, Timestamp time) {
		Assert.notNull(name);
		Assert.notNull(id);
		Assert.notNull(date);
		Assert.notNull(time);
		return playRepository.getPlayByKey(name, id, date, time);
	}

	@Override
	public void removePlayByKey(String name, Long id, Date date, Timestamp time) {
		Assert.notNull(name);
		Assert.notNull(id);
		Assert.notNull(date);
		Assert.notNull(time);
		playRepository.removePlayByKey(name, id, date, time);
	}

	@Override
	public void removePlayByUser(String name) {
		Assert.notNull(name);
		playRepository.removePlayByUser(name);
	}

	@Override
	public void removePlayByGame(Long id) {
		Assert.notNull(id);
		playRepository.removePlayByGame(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Player> getTopPlayers(Long id){
		EntityManager entityM = jpaContext.getEntityManagerByManagedType(Play.class);
		
		List<Player> commentCount = (List<Player>) entityM
			    .createNamedQuery("get_top_players")
			    .setParameter(1, id)
			    .getResultList();

		

		return commentCount;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TopGame> getTopGames(String username){
		EntityManager entityM = jpaContext.getEntityManagerByManagedType(Play.class);;
		
		List<TopGame> commentCount = (List<TopGame>) entityM
			    .createNamedQuery("get_top_games")
			    .setParameter(1, username)
			    .getResultList();

		
		
		return commentCount;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlayedGame> getPlayedGames(String username){
		EntityManager entityM = jpaContext.getEntityManagerByManagedType(Play.class);;
		
		List<PlayedGame> playedGames = (List<PlayedGame>) entityM
			    .createNamedQuery("get_played_games")
			    .setParameter(1, username)
			    .getResultList();

		
	
		return playedGames;
	}

	@Override
	public List<FriendsInGame> getFriendsInGame(String username) {
		EntityManager entityM = jpaContext.getEntityManagerByManagedType(Play.class);;
		
		@SuppressWarnings("unchecked")
		List<FriendsInGame> friends = (List<FriendsInGame>) entityM
			    .createNamedQuery("get_friends_in_game")
			    .setParameter(1, username)
			    .getResultList();

		
	
		return friends;
	}

	@Override
	public List<Play> getPlaysByGameAndUser(String user, Long game) {
		Assert.notNull(user);
		Assert.notNull(game);
		return playRepository.getPlaysByGameAndUser(user, game);
	}
	
}

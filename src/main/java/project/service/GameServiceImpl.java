package project.service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Game;
import project.domain.dto.CompactGameDTO;
import project.repository.GameRepository;

@Service
public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
    private JpaContext jpaContext;
	
	@Override
	public List<Game> getAll(){
		return this.gameRepository.findAll();
	}

	@Override
	public List<Game> getGameByName(String name) {
		Assert.notNull(name);
		return gameRepository.getAllByName(name);
	}

	@Override
	public List<Game> getGameByYear(BigDecimal year) {
		Assert.notNull(year);
		return gameRepository.getAllByYear(year);
	}

	@Override
	public Game getGameById(Long id) {
		Assert.notNull(id);
		return gameRepository.getGameById(id);
	}

	@Override
	public void removeGameById(Long id) {
		Assert.notNull(id);
		gameRepository.removeGameById(id);
		
	}

	@Override
	public void updateGameDescription(Long id, String desc) {
		Assert.notNull(id);
		gameRepository.updateGameDescription(id, desc);
		
	}

	@Override
	public void updateGameDetectionRule(Long id, String detection) {
		Assert.notNull(id);
		Assert.notNull(detection);
		gameRepository.updateGameDetectionRule(id, detection);
		
	}

	@Override
	public void updateGameImage(Long id, String gimg) {
		Assert.notNull(id);
		Assert.notNull(gimg);
		gameRepository.updateGameImage(id, gimg);
		
	}

	@Override
	public void updateGameYear(Long id, BigDecimal year) {
		Assert.notNull(id);
		Assert.notNull(year);
		gameRepository.updateGameYear(id, year);
		
	}

	@Override
	public void updateGameName(Long id, String name) {
		Assert.notNull(id);
		Assert.notNull(name);
		gameRepository.updateGameName(id, name);
		
	}

	@Override
	public Game save(Game g) {
		Assert.notNull(g);
		return gameRepository.save(g);
		
	}

	@Override
	public void updateGame(Long id, String name, String desc, String rule, String gimg, BigDecimal year) {
		Assert.notNull(id);
		Assert.notNull(name);
		Assert.notNull(desc);
		Assert.notNull(rule);
		Assert.notNull(year);
		gameRepository.updateGame(id, name, desc, rule, gimg, year);
	}

	@Override
	public List<Game> getAllByStreamer(Long id) {
		Assert.notNull(id);
		return gameRepository.getAllByStreamer(id);
	}

	@Override
	public List<CompactGameDTO> fillGameList() {
		return gameRepository.fillGameList();
	}
	
	@Override
	public Long getCurrentSequence(){
		EntityManager entityM = jpaContext.getEntityManagerByManagedType(Game.class);
		Session session = entityM.unwrap( Session.class );
		Query query = 
		        session.createSQLQuery("select GAME_SEQ.CURRVAL as num from dual")
		            .addScalar("num", StandardBasicTypes.BIG_INTEGER);

		    return ((BigDecimal) query.uniqueResult()).longValue();
	}

	@Override
	public Game getGameByNameAndYear(String name, BigDecimal year) {
		Assert.notNull(name);
		Assert.notNull(year);
		return gameRepository.getGameByNameAndYear(name, year);
	}
	
	@Override
	public List<Game> getRecommendation(String username) {
		Assert.notNull(username);
		EntityManager entityM = jpaContext.getEntityManagerByManagedType(Game.class);;
		
		@SuppressWarnings("unchecked")
		List<Game> commentCount = (List<Game>) entityM
			    .createNamedQuery("get_recommendation")
			    .setParameter(1, username)
			    .getResultList();

		
		
		return commentCount;
	}

	@Override
	public List<Game> getAllByNameStarting(String name) {
		Assert.notNull(name);
		return gameRepository.getAllByNameStarting(name);
	}

	@Override
	public List<Game> getAllByHash() {
		return gameRepository.getAllByHash();
	}
	

}

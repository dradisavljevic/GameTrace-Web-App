package project.service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Game;
import project.domain.GameDeveloper;
import project.repository.GameDeveloperRepository;

@Service
public class GameDeveloperServiceImpl implements GameDeveloperService{
	
	@Autowired
	private GameDeveloperRepository gameDeveloperRepository;
	
	@Autowired
    private JpaContext jpaContext;

	@Override
	public List<GameDeveloper> findAll() {
		return gameDeveloperRepository.findAll();
	}

	@Override
	public GameDeveloper save(GameDeveloper g) {
		Assert.notNull(g);
		return gameDeveloperRepository.save(g);
	}

	@Override
	public List<GameDeveloper> getAllByName(String name) {
		Assert.notNull(name);
		return gameDeveloperRepository.getAllByName(name);
	}

	@Override
	public List<GameDeveloper> getAllByCount(BigDecimal count) {
		Assert.notNull(count);
		return gameDeveloperRepository.getAllByCount(count);
	}

	@Override
	public List<GameDeveloper> getAllByGreaterCount(BigDecimal count) {
		Assert.notNull(count);
		return gameDeveloperRepository.getAllByGreaterCount(count);
	}

	@Override
	public List<GameDeveloper> getAllByLowerCount(BigDecimal count) {
		Assert.notNull(count);
		return gameDeveloperRepository.getAllByLowerCount(count);
	}

	@Override
	public GameDeveloper getGameDeveloperById(Long id) {
		Assert.notNull(id);
		return gameDeveloperRepository.getGameDeveloperById(id);
	}

	@Override
	public void removeGameDeveloperById(Long id) {
		Assert.notNull(id);
		gameDeveloperRepository.removeGameDeveloperById(id);
		
	}

	@Override
	public void updateGameDeveloperName(Long id, String name) {
		Assert.notNull(id);
		Assert.notNull(name);
		gameDeveloperRepository.updateGameDeveloperName(id, name);
		
	}

	@Override
	public GameDeveloper getGameDeveloperByName(String name) {
		Assert.notNull(name);
		return gameDeveloperRepository.getGameDeveloperByName(name);
	}

	@Override
	public List<Game> getDevelopedGames(Long id) {
		Assert.notNull(id);
		EntityManager entityM = jpaContext.getEntityManagerByManagedType(GameDeveloper.class);;
		
		@SuppressWarnings("unchecked")
		List<Game> commentCount = (List<Game>) entityM
			    .createNamedQuery("get_developed_games")
			    .setParameter(1, id)
			    .getResultList();

		
		
		return commentCount;
	}

	@Override
	public List<GameDeveloper> getAll() {
		return gameDeveloperRepository.getAll();
	}

}

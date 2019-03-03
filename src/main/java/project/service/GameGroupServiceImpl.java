package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.GameGroup;
import project.repository.GameDeveloperRepository;
import project.repository.GameGroupRepository;

@Service
public class GameGroupServiceImpl implements GameGroupService {
	
	@Autowired
	private GameGroupRepository gameGroupRepository;

	@Override
	public List<GameGroup> findAll() {
		return gameGroupRepository.findAll();
	}

	@Override
	public GameGroup save(GameGroup g) {
		Assert.notNull(g);
		return gameGroupRepository.save(g);
	}

	@Override
	public List<GameGroup> getAllByName(String name) {
		Assert.notNull(name);
		return gameGroupRepository.getAllByName(name);
	}

	@Override
	public List<GameGroup> getAllByMaster(String name) {
		Assert.notNull(name);
		return gameGroupRepository.getAllByMaster(name);
	}

	@Override
	public GameGroup getGameGroupByKey(Long id, String name) {
		Assert.notNull(name);
		Assert.notNull(id);
		return gameGroupRepository.getGameGroupByKey(id, name);
	}

	@Override
	public void removeGameGroupByKey(Long id, String name) {
		Assert.notNull(name);
		Assert.notNull(id);
		gameGroupRepository.removeGameGroupByKey(id, name);
		
	}

	@Override
	public void updateGameGroupName(Long id, String master, String name) {
		Assert.notNull(name);
		Assert.notNull(master);
		Assert.notNull(id);
		gameGroupRepository.updateGameGroupName(id, master, name);
	}

	@Override
	public GameGroup getGameGroupByName(String name) {
		Assert.notNull(name);
		return gameGroupRepository.getGameGroupByName(name);
	}

}

package project.service;

import java.util.List;

import project.domain.GameGroup;

public interface GameGroupService {
	
	List<GameGroup> findAll();
	
	GameGroup save(GameGroup g);
	
	List<GameGroup> getAllByName(String name);
	
	List<GameGroup> getAllByMaster(String name);
	
	GameGroup getGameGroupByKey(Long id, String name);
	
	void removeGameGroupByKey(Long id, String name);
	
	void updateGameGroupName(Long id, String master, String name);
	
	GameGroup getGameGroupByName(String name);

}

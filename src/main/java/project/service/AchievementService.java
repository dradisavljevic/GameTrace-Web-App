package project.service;

import java.util.List;

import project.domain.Achievement;

public interface AchievementService {
	

	List<Achievement> findAll();
	
	Achievement save(Achievement a);
	
	List<Achievement> getAllByName(String name);
	
	List<Achievement> getAllByCondition(String condition);
	
	Achievement getAchievementById(Long id);
	
	void removeAchievementById(Long id);
	
	void updateAchievementCondition(Long id, String condition);
	
	void updateAchievementName(Long id, String name);
	
	void updateAchievement(Long id, String name, String condition);
	
	List<Achievement> getLatestAchievements(String username);

}

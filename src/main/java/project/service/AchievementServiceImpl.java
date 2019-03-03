package project.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Achievement;
import project.repository.AchievementRepository;

@Service
public class AchievementServiceImpl implements AchievementService {

	@Autowired
	private AchievementRepository achievementRepository;
	
	@Autowired
    private JpaContext jpaContext;
	
	@Override
	public List<Achievement> findAll() {
		return achievementRepository.findAll();
	}

	@Override
	public Achievement save(Achievement a) {
		Assert.notNull(a);
		return achievementRepository.save(a);
	}

	@Override
	public List<Achievement> getAllByName(String name) {
		Assert.notNull(name);
		return achievementRepository.getAllByName(name);
	}

	@Override
	public List<Achievement> getAllByCondition(String condition) {
		Assert.notNull(condition);
		return achievementRepository.getAllByCondition(condition);
	}

	@Override
	public Achievement getAchievementById(Long id) {
		Assert.notNull(id);
		return achievementRepository.getAchievementById(id);
	}

	@Override
	public void removeAchievementById(Long id) {
		Assert.notNull(id);
		achievementRepository.removeAchievementById(id);
		
	}

	@Override
	public void updateAchievementCondition(Long id, String condition) {
		Assert.notNull(id);
		Assert.notNull(condition);
		achievementRepository.updateAchievementCondition(id, condition);
		
	}

	@Override
	public void updateAchievementName(Long id, String name) {
		Assert.notNull(id);
		Assert.notNull(name);
		achievementRepository.updateAchievementName(id, name);
		
	}

	@Override
	public void updateAchievement(Long id, String name, String condition) {
		Assert.notNull(id);
		Assert.notNull(name);
		Assert.notNull(condition);
		achievementRepository.updateAchievement(id, name, condition);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Achievement> getLatestAchievements(String username){
		EntityManager entityM = jpaContext.getEntityManagerByManagedType(Achievement.class);;
		
		List<Achievement> achievements = (List<Achievement>) entityM
			    .createNamedQuery("get_latest_achievements")
			    .setParameter(1, username)
			    .getResultList();

		
	
		return achievements;
	}

}

package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Achievement;
import project.domain.Game;

public interface AchievementRepository extends Repository<Achievement, Long> {
	
	public List<Achievement> findAll();
	
	public Achievement save(Achievement a);
	
	@Query("select a from Achievement a where UPPER(a.achname) LIKE UPPER( ?1 )")
	public List<Achievement> getAllByName(String name);
	
	@Query("select a from Achievement a where UPPER(a.achcond) LIKE UPPER( ?1 )")
	public List<Achievement> getAllByCondition(String condition);
	
	@Query("select a from Achievement a where a.achid = ?1")
	public Achievement getAchievementById(Long id);
	
	@Modifying
	@Query("delete from Achievement a where a.achid = ?1")
	public void removeAchievementById(Long id);
	
	@Modifying
	@Query("update Achievement a set a.achcond = ?2 where a.achid = ?1 ")
	public void updateAchievementCondition(Long id, String condition);
	
	@Modifying
	@Query("update Achievement a set a.achname = ?2 where a.achid = ?1 ")
	public void updateAchievementName(Long id, String name);
	
	@Modifying
	@Query("update Achievement a set a.achname = ?2, a.achcond = ?3 where a.achid = ?1 ")
	public void updateAchievement(Long id, String name, String condition);

}

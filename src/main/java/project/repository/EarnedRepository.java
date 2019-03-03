package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Earned;
import project.domain.EarnedPK;
import project.domain.Game;

public interface EarnedRepository extends Repository<Earned, EarnedPK> {
	public List<Earned> findAll();
	
	public Earned save(Earned e);
	
	@Query("select e from Earned e where e.id.achievementAchid = ?1 AND e.id.gameUserUname = ?2")
	public Earned getEarnedByKey(Long id, String name);
	
	@Query("select e from Earned e where e.id.gameUserUname = ?1")
	public Earned getEarnedByUser(String name);
	
	@Modifying
	@Query("delete from Earned e where e.id.achievementAchid = ?1 AND e.id.gameUserUname = ?2")
	public Earned removeEarnedByKey(Long id, String name);
}

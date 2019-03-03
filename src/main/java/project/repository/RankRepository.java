package project.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Rank;

public interface RankRepository extends Repository<Rank, Long>{
	
	public List<Rank> findAll();
	
	public Rank save(Rank r);
	
	@Query("select r from Rank r where UPPER(r.rankname) LIKE UPPER( ?1 )")
	public List<Rank> getAllByName(String name);
	
	@Query("select r from Rank r where r.rankid = ?1")
	public Rank getRankById(Long id);
	
	@Modifying
	@Query("delete from Rank r where r.rankid = ?1")
	public void removeRankById(Long id);
	
	@Modifying
	@Query("update Rank r set r.rankname = ?2 where r.rankid = ?1 ")
	public void updateRankName(Long id, String name);
	
	@Modifying
	@Query("update Rank r set r.rankh = ?2 where r.rankid = ?1 ")
	public void updateRankHours(Long id, BigDecimal hours);
	
	@Modifying
	@Query("update Rank r set r.rankname = ?2 , r.rankh = ?3 where r.rankid = ?1 ")
	public void updateRank(Long id, String name, BigDecimal hours);

}

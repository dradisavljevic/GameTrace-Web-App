package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Ranks;
import project.domain.RanksPK;

public interface RanksRepository extends Repository<Ranks, RanksPK> {
	
	public List<Ranks> findAll();
	
	public Ranks save(Ranks r);
	
	@Query("select r from Ranks r where r.id.gameGameid = ?1 AND r.id.gameUserUname = ?2 AND r.id.rankRankid = ?3")
	public Ranks getRanksByKey(Long gameId, String uname, Long rankId);
	
	@Query("select r from Ranks r where r.id.gameUserUname = ?1")
	public Ranks getRanksByUser(String uname);
	
	@Query("select r from Ranks r where r.id.gameGameid = ?1 AND  r.id.rankRankid = ?3")
	public Ranks getRanksByRankAndGame(Long gameId, Long rankId);
	
	@Modifying
	@Query("delete from Ranks r where r.id.gameGameid = ?1 AND r.id.gameUserUname = ?2 AND r.id.rankRankid = ?3")
	public void removeRanksByKey(Long gameId, String uname, Long rankId);

}

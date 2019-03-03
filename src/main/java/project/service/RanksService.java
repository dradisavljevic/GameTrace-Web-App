package project.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.Ranks;

public interface RanksService {

	List<Ranks> findAll();
	
	Ranks save(Ranks r);
	
	Ranks getRanksByKey(Long gameId, String uname, Long rankId);
	
	Ranks getRanksByUser(String uname);
	
	Ranks getRanksByRankAndGame(Long gameId, Long rankId);
	
	void removeRanksByKey(Long gameId, String uname, Long rankId);
}

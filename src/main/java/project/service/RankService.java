package project.service;

import java.math.BigDecimal;
import java.util.List;

import project.domain.Rank;

public interface RankService {

	List<Rank> findAll();
	
	Rank save(Rank r);
	
	List<Rank> getAllByName(String name);
	
	Rank getRankById(Long id);
	
	void removeRankById(Long id);
	
	void updateRankName(Long id, String name);
	
	void updateRankHours(Long id, BigDecimal hours);
	
	void updateRank(Long id, String name, BigDecimal hours);
}

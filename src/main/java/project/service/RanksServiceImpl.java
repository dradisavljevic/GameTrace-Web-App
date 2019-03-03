package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Ranks;
import project.repository.RankRepository;
import project.repository.RanksRepository;

@Service
public class RanksServiceImpl implements RanksService {
	
	@Autowired
	private RanksRepository ranksRepository;

	@Override
	public List<Ranks> findAll() {
		return ranksRepository.findAll();
	}

	@Override
	public Ranks save(Ranks r) {
		Assert.notNull(r);
		return ranksRepository.save(r);
	}

	@Override
	public Ranks getRanksByKey(Long gameId, String uname, Long rankId) {
		Assert.notNull(gameId);
		Assert.notNull(uname);
		Assert.notNull(rankId);
		return ranksRepository.getRanksByKey(gameId, uname, rankId);
	}

	@Override
	public Ranks getRanksByUser(String uname) {
		Assert.notNull(uname);
		return ranksRepository.getRanksByUser(uname);
	}

	@Override
	public Ranks getRanksByRankAndGame(Long gameId, Long rankId) {
		Assert.notNull(gameId);
		Assert.notNull(rankId);
		return ranksRepository.getRanksByRankAndGame(gameId, rankId);
	}

	@Override
	public void removeRanksByKey(Long gameId, String uname, Long rankId) {
		Assert.notNull(gameId);
		Assert.notNull(uname);
		Assert.notNull(rankId);
		ranksRepository.removeRanksByKey(gameId, uname, rankId);
	}

}

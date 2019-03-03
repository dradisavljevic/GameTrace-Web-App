package project.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Rank;
import project.repository.RankRepository;

@Service
public class RankServiceImpl implements RankService {
	
	@Autowired
	private RankRepository rankRepository;

	@Override
	public List<Rank> findAll() {
		return rankRepository.findAll();
	}

	@Override
	public Rank save(Rank r) {
		Assert.notNull(r);
		return rankRepository.save(r);
	}

	@Override
	public List<Rank> getAllByName(String name) {
		Assert.notNull(name);
		return rankRepository.getAllByName(name);
	}

	@Override
	public Rank getRankById(Long id) {
		Assert.notNull(id);
		return rankRepository.getRankById(id);
	}

	@Override
	public void removeRankById(Long id) {
		Assert.notNull(id);
		rankRepository.removeRankById(id);
	}

	@Override
	public void updateRankName(Long id, String name) {
		Assert.notNull(id);
		Assert.notNull(name);
		rankRepository.updateRankName(id, name);
	}

	@Override
	public void updateRankHours(Long id, BigDecimal hours) {
		Assert.notNull(id);
		Assert.notNull(hours);
		rankRepository.updateRankHours(id, hours);
	}

	@Override
	public void updateRank(Long id, String name, BigDecimal hours) {
		Assert.notNull(id);
		Assert.notNull(name);
		Assert.notNull(hours);
		rankRepository.updateRank(id, name, hours);
	}

}

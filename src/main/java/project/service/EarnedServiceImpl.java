package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Earned;
import project.repository.EarnedRepository;

@Service
public class EarnedServiceImpl implements EarnedService {
	
	@Autowired
	private EarnedRepository earnedRepository;

	@Override
	public List<Earned> findAll() {
		return earnedRepository.findAll();
	}

	@Override
	public Earned save(Earned e) {
		Assert.notNull(e);
		return earnedRepository.save(e);
	}

	@Override
	public Earned getEarnedByKey(Long id, String name) {
		Assert.notNull(id);
		Assert.notNull(name);
		return earnedRepository.getEarnedByKey(id, name);
	}

	@Override
	public Earned getEarnedByUser(String name) {
		Assert.notNull(name);
		return earnedRepository.getEarnedByUser(name);
	}

	@Override
	public Earned removeEarnedByKey(Long id, String name) {
		Assert.notNull(id);
		Assert.notNull(name);
		return earnedRepository.removeEarnedByKey(id, name);
	}

}

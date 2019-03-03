package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Active;
import project.repository.ActiveRepository;


@Service
public class ActiveServiceImpl implements ActiveService {
	
	@Autowired
	private ActiveRepository activeRepository;

	@Override
	public Page<Active> findAll(Pageable page) {
		return activeRepository.findAll(page);
	}

	@Override
	public Active findActiveById(Long id) {
		Assert.notNull(id, "ID cannot be Null");
		return activeRepository.findActiveById(id);
	}

	@Override
	public Active findActiveByUsername(String uname) {
		Assert.notNull(uname, "Username cannot be Null");
		return activeRepository.findActiveByUsername(uname);
	}

	@Override
	public void removeActiveById(Long id) {
		Assert.notNull(id, "ID cannot be Null");
		activeRepository.removeActiveById(id);
		
	}

	@Override
	public void removeActiveByUsername(String uname) {
		Assert.notNull(uname, "Username cannot be Null");
		activeRepository.removeActiveByUsername(uname);
		
	}

	@Override
	public Active save(Active active) {
		Assert.notNull(active,"No active found");
		return activeRepository.save(active);
	}

	@Override
	public List<Active> findAllActivebyUsername(String uname) {
		Assert.notNull(uname, "Username cannot be Null");
		return activeRepository.findAllActivebyUsername(uname);
	}

}

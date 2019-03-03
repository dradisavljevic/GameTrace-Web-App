package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Administrator;
import project.repository.AdministratorRepository;

@Service
public class AdministratorServiceImpl implements AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorRepository;

	@Override
	public List<Administrator> findAll() {
		return administratorRepository.findAll();
	}

	@Override
	public Administrator save(Administrator a) {
		Assert.notNull(a);
		return administratorRepository.save(a);
	}

	@Override
	public Administrator getAdministratorByName(String name) {
		Assert.notNull(name);
		return administratorRepository.getAdministratorByName(name);
	}

}

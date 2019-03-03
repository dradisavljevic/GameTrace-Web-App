package project.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.GameUser;
import project.repository.GameUserRepository;

@Service
public class GameUserServiceImpl implements GameUserService {
	
	@Autowired
	private GameUserRepository gameUserRepository;

	@Override
	public List<GameUser> findAll() {
		return gameUserRepository.findAll();
	}

	@Override
	public GameUser save(GameUser g) {
		Assert.notNull(g);
		return gameUserRepository.save(g);
	}

	@Override
	public List<GameUser> getAllByName(String name) {
		Assert.notNull(name);
		return gameUserRepository.getAllByName(name);
	}

	@Override
	public GameUser getGameUserByName(String name) {
		Assert.notNull(name);
		return gameUserRepository.getGameUserByName(name);
	}

	@Override
	public void removeGameUserByName(String name) {
		Assert.notNull(name);
		gameUserRepository.removeGameUserByName(name);
	}

	@Override
	public void updateGameUserRealName(String uname, String rname) {
		Assert.notNull(uname);
		gameUserRepository.updateGameUserRealName(uname, rname);
	}

	@Override
	public void updateGameUserCountry(String uname, String country) {
		Assert.notNull(uname);
		Assert.notNull(country);
		gameUserRepository.updateGameUserCountry(uname, country);
	}

	@Override
	public void updateGameUserBiography(String uname, String ubio) {
		Assert.notNull(uname);
		gameUserRepository.updateGameUserBiography(uname, ubio);
	}

	@Override
	public void updateGameUserRealNameAndCountry(String uname, String rname, String country) {
		Assert.notNull(uname);
		Assert.notNull(country);
		gameUserRepository.updateGameUserRealNameAndCountry(uname, rname, country);
	}

	@Override
	public void updateGameUserRealNameAndBiography(String uname, String rname, String ubio) {
		Assert.notNull(uname);
		gameUserRepository.updateGameUserRealNameAndBiography(uname, rname, ubio);
	}

	@Override
	public void updateGameUserBiographyAndCountry(String uname, String ubio, String country) {
		Assert.notNull(uname);
		Assert.notNull(country);
		gameUserRepository.updateGameUserBiographyAndCountry(uname, ubio, country);
	}

	@Override
	public void updateGameUserRealNameAndCountryAndBiography(String uname, String rname, String country, String ubio) {
		Assert.notNull(uname);
		Assert.notNull(country);
		gameUserRepository.updateGameUserRealNameAndCountryAndBiography(uname, rname, country, ubio);
	}

	@Override
	public void updateGameUserDOB(String uname, Date dob) {
		Assert.notNull(uname);
		Assert.notNull(dob);
		gameUserRepository.updateGameUserDOB(uname, dob);
	}

}

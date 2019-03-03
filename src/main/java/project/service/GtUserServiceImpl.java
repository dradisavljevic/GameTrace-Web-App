package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.GtUser;
import project.repository.GameUserRepository;
import project.repository.GtUserRepository;

@Service
public class GtUserServiceImpl implements GtUserService {
	
	@Autowired
	private GtUserRepository gtUserRepository;

	@Override
	public List<GtUser> findAll() {
		return gtUserRepository.findAll();
	}

	@Override
	public GtUser save(GtUser g) {
		Assert.notNull(g);
		return gtUserRepository.save(g);
	}

	@Override
	public List<GtUser> getAllByName(String name) {
		Assert.notNull(name);
		return gtUserRepository.getAllByName(name);
	}

	@Override
	public GtUser getGtUserByName(String name) {
		Assert.notNull(name);
		return gtUserRepository.getGtUserByName(name);
	}

	@Override
	public void removeGtUserByName(String name) {
		Assert.notNull(name);
		gtUserRepository.removeGtUserByName(name);
		
	}

	@Override
	public void updateGtUserPassword(String uname, String pword) {
		Assert.notNull(uname);
		Assert.notNull(pword);
		gtUserRepository.updateGtUserPassword(uname, pword);
		
	}

	@Override
	public void updateGtUserEmail(String uname, String email) {
		Assert.notNull(uname);
		Assert.notNull(email);
		gtUserRepository.updateGtUserEmail(uname, email);
		
	}

	@Override
	public void updateGtUserAvatar(String uname, String uavat) {
		Assert.notNull(uname);
		gtUserRepository.updateGtUserAvatar(uname, uavat);
		
	}

	@Override
	public void updateGtUserPasswordAndEmail(String uname, String pword, String email) {
		Assert.notNull(uname);
		Assert.notNull(pword);
		Assert.notNull(email);
		gtUserRepository.updateGtUserPasswordAndEmail(uname, pword, email);
		
	}

	@Override
	public void updateGtUserPasswordAndAvatar(String uname, String pword, String uavat) {
		Assert.notNull(uname);
		Assert.notNull(pword);
		gtUserRepository.updateGtUserPasswordAndAvatar(uname, pword, uavat);
		
	}

	@Override
	public void updateGtUserEmailAndAvatar(String uname, String email, String uavat) {
		Assert.notNull(uname);
		Assert.notNull(email);
		gtUserRepository.updateGtUserEmailAndAvatar(uname, email, uavat);
		
	}

	@Override
	public void updateGtUserPasswordAndEmailAndAvatar(String uname, String pword, String email, String uavat) {
		Assert.notNull(uname);
		Assert.notNull(pword);
		Assert.notNull(email);
		gtUserRepository.updateGtUserPasswordAndEmailAndAvatar(uname, pword, email, uavat);
		
	}

	@Override
	public GtUser getGtUserByEmail(String email) {
		Assert.notNull(email);
		return gtUserRepository.getGtUserByEmail(email);
	}

}

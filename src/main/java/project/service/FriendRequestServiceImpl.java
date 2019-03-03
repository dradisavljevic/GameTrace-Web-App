package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.FriendRequest;
import project.repository.FriendRequestRepository;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {
	
	@Autowired
	private FriendRequestRepository friendRequestRepository;

	@Override
	public List<FriendRequest> findAll() {
		// TODO Auto-generated method stub
		return friendRequestRepository.findAll();
	}

	@Override
	public FriendRequest save(FriendRequest f) {
		Assert.notNull(f);
		return friendRequestRepository.save(f);
	}

	@Override
	public FriendRequest getRequestById(Long id) {
		Assert.notNull(id);
		return friendRequestRepository.getRequestById(id);
	}

	@Override
	public void removeFriendRequestById(Long id) {
		Assert.notNull(id);
		friendRequestRepository.removeFriendRequestById(id);
		
	}

	@Override
	public List<FriendRequest> getAllByReceiver(String name, String stat) {
		Assert.notNull(name);
		Assert.notNull(stat);
		return friendRequestRepository.getAllByReceiver(name,stat);
	}

	@Override
	public FriendRequest getRequestBySenderReceiver(String rec, String send, String stat) {
		Assert.notNull(rec);
		Assert.notNull(send);
		Assert.notNull(stat);
		return friendRequestRepository.getRequestBySenderReceiver(rec, send, stat);
	}

}

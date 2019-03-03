package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.GroupInvite;
import project.repository.FriendRequestRepository;
import project.repository.GroupInviteRepository;

@Service
public class GroupInviteServiceImpl implements GroupInviteService {
	
	@Autowired
	private GroupInviteRepository groupInviteRepository;

	@Override
	public List<GroupInvite> findAll() {
		return groupInviteRepository.findAll();
	}

	@Override
	public GroupInvite save(GroupInvite g) {
		Assert.notNull(g);
		return groupInviteRepository.save(g);
	}

	@Override
	public GroupInvite getInviteById(Long id) {
		Assert.notNull(id);
		return groupInviteRepository.getInviteById(id);
	}

	@Override
	public void removeGroupInviteById(Long id) {
		Assert.notNull(id);
		groupInviteRepository.removeGroupInviteById(id);
		
	}

	@Override
	public List<GroupInvite> getAllByReceiver(String name, String stat) {
		Assert.notNull(name);
		Assert.notNull(stat);
		return groupInviteRepository.getAllByReceiver(name,stat);
	}

	@Override
	public GroupInvite getRequestBySenderReceiver(String rec, Long id, String send, String stat) {
		Assert.notNull(rec);
		Assert.notNull(id);
		Assert.notNull(send);
		Assert.notNull(stat);
		return groupInviteRepository.getRequestBySenderReceiver(rec, id, send, stat);
	}

}

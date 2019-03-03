package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.FriendRequest;

public interface FriendRequestRepository extends Repository<FriendRequest, Long>{
	
	
	public List<FriendRequest> findAll();
	
	public FriendRequest save(FriendRequest f);
	
	@Query("select f from FriendRequest f where f.frid = ?1 ")
	public FriendRequest getRequestById(Long id);
	
	@Query("select f from FriendRequest f where f.receiver = ?1 AND f.sender = ?2 AND f.frstat = ?3")
	public FriendRequest getRequestBySenderReceiver(String rec, String send, String stat);
	
	@Query("select f from FriendRequest f where f.receiver = ?1 AND f.frstat = ?2 ")
	public List<FriendRequest> getAllByReceiver(String name, String stat);
	
	@Modifying
	@Query("delete from FriendRequest f where f.frid = ?1")
	public void removeFriendRequestById(Long id);
	
	


}

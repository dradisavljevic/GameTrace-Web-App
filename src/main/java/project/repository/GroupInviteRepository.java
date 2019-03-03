package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.GroupInvite;

public interface GroupInviteRepository extends Repository<GroupInvite, Long> {
	
	public List<GroupInvite> findAll();
	
	public GroupInvite save(GroupInvite g);
	
	@Query("select g from GroupInvite g where g.giid = ?1 ")
	public GroupInvite getInviteById(Long id);
	
	@Query("select g from GroupInvite g where g.girec = ?1 AND g.gistat = ?2")
	public List<GroupInvite> getAllByReceiver(String name, String stat);
	
	@Query("select g from GroupInvite g where g.girec = ?1 AND g.groupId = ?2 AND g.groupMaster = ?3 AND g.gistat = ?4")
	public GroupInvite getRequestBySenderReceiver(String rec, Long id, String send, String stat);
	
	@Modifying
	@Query("delete from GroupInvite g where g.giid = ?1")
	public void removeGroupInviteById(Long id);

}

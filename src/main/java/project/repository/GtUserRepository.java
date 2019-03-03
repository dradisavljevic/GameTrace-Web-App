package project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.GtUser;

public interface GtUserRepository extends Repository<GtUser, String> {
	
	public List<GtUser> findAll();
	
	public GtUser save(GtUser g);
	
	@Query("select g from GtUser g where UPPER(g.uname) LIKE UPPER( ?1 )")
	public List<GtUser> getAllByName(String name);
	
	@Query("select g from GtUser g where UPPER(g.uname) LIKE UPPER(?1)")
	public GtUser getGtUserByName(String name);
	
	@Query("select g from GtUser g where UPPER(g.uemail) LIKE UPPER(?1)")
	public GtUser getGtUserByEmail(String email);
	
	@Modifying
	@Query("delete from GtUser g where g.uname = ?1")
	public void removeGtUserByName(String name);
	
	@Modifying
	@Query("update GtUser g set g.pword = ?2 where g.uname = ?1 ")
	public void updateGtUserPassword(String uname, String pword);
	
	@Modifying
	@Query("update GtUser g set g.uemail = ?2 where g.uname = ?1 ")
	public void updateGtUserEmail(String uname, String email);
	
	@Modifying
	@Query("update GtUser g set g.uavat = ?2 where g.uname = ?1 ")
	public void updateGtUserAvatar(String uname, String uavat);
	
	@Modifying
	@Query("update GtUser g set g.pword = ?2 , g.uemail = ?3 where g.uname = ?1 ")
	public void updateGtUserPasswordAndEmail(String uname, String pword, String email);
	
	@Modifying
	@Query("update GtUser g set g.pword = ?2 , g.uavat = ?3 where g.uname = ?1 ")
	public void updateGtUserPasswordAndAvatar(String uname, String pword, String uavat);
	
	@Modifying
	@Query("update GtUser g set g.uemail = ?2 , g.uavat = ?3 where g.uname = ?1 ")
	public void updateGtUserEmailAndAvatar(String uname, String email, String uavat);
	
	@Modifying
	@Query("update GtUser g set g.pword = ?2 , g.uemail = ?3 , g.uavat = ?4 where g.uname = ?1 ")
	public void updateGtUserPasswordAndEmailAndAvatar(String uname, String pword, String email, String uavat);

}

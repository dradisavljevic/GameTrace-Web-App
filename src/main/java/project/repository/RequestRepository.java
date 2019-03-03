package project.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import project.domain.Request;

public interface RequestRepository extends Repository<Request, Long>{

	public List<Request> findAll();
	
	public Request save(Request r);
	
	@Query("select r from Request r where UPPER(r.reqgname) LIKE UPPER( ?1 )")
	public List<Request> getAllByName(String name);
	
	@Query("select r from Request r where UPPER(r.gameUser.uname) LIKE UPPER( ?1 )")
	public List<Request> getAllBySubmitter(String name);
	
	@Query("select r from Request r where UPPER(r.reqstatus) LIKE UPPER( ?1 )")
	public List<Request> getAllByStatus(String name);
	
	@Query("select r from Request r where r.reqgrd = TO_NUMBER ( ?1 )")
	public List<Request> getAllByYear(BigDecimal year);
	
	@Query("select r from Request r where r.reqid = ?1")
	public Request getRequestById(Long id);
	
	@Query("select r from Request r where UPPER(r.reqgname) = UPPER( ?1) AND r.reqgrd = TO_NUMBER ( ?2 ) AND UPPER(r.gameUser.uname) = UPPER( ?3 )")
	public Request getAlreadySubmitted(String gameName, BigDecimal year, String uname);
	
	@Modifying
	@Query("delete from Request r where r.reqid = ?1")
	public void removeRequestById(Long id);
	
	@Modifying
	@Query("update Request r set r.reqgname = ?2 where r.reqid = ?1 ")
	public void updateRequestGameName(Long id, String name);
	
	@Modifying
	@Query("update Request r set r.reqgrd = ?2 where r.reqid = ?1 ")
	public void updateRequestGameYear(Long id, BigDecimal year);
	
	@Modifying
	@Query("update Request r set r.reqdesc = ?2 where r.reqid = ?1 ")
	public void updateRequestGameDescription(Long id, String desc);
	
	@Modifying
	@Query("update Request r set r.reqimg = ?2 where r.reqid = ?1 ")
	public void updateRequestGameImage(Long id, String img);
	
	@Modifying
	@Query("update Request r set r.reqdr = ?2 where r.reqid = ?1 ")
	public void updateRequestGameDetectionRule(Long id, String dr);
	
	@Modifying
	@Query("update Request r set r.reqstatus = ?2 where r.reqid = ?1 ")
	public void updateRequestStatus(Long id, String status);
	
	@Modifying
	@Query("update Request r set r.reqgname = ?2 , r.reqdesc = ?3 , r.reqimg = ?4 , r.reqgrd = ?5, r.reqdr = ?6, r.reqstatus = ?7  where r.reqid = ?1 ")
	public void updateRequest(Long id, String name, String desc, String img, BigDecimal year, String dr, String status);
	
}

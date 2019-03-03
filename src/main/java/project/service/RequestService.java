package project.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import project.domain.Request;

public interface RequestService {

	List<Request> findAll();
	
	Request save(Request r);
	
	List<Request> getAllByName(String name);
	
	List<Request> getAllBySubmitter(String name);
	
	List<Request> getAllByStatus(String name);
	
	List<Request> getAllByYear(BigDecimal year);
	
	Request getRequestById(Long id);
	
	Request getAlreadySubmitted(String gameName, BigDecimal year, String uname);
	
	void removeRequestById(Long id);
	
	void updateRequestGameName(Long id, String name);
	
	void updateRequestGameYear(Long id, BigDecimal year);
	
	void updateRequestGameDescription(Long id, String desc);
	
	void updateRequestGameImage(Long id, String img);
	
	void updateRequestGameDetectionRule(Long id, String dr);
	
	void updateRequestStatus(Long id, String status);
	
	void updateRequest(Long id, String name, String desc, String img, BigDecimal year, String dr, String status);
	
}

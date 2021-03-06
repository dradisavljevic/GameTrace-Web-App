package project.service;

import java.util.List;

import project.domain.Earned;

public interface EarnedService {
	
	List<Earned> findAll();
	
	Earned save(Earned e);
	
	Earned getEarnedByKey(Long id, String name);
	
	Earned getEarnedByUser(String name);
	
	Earned removeEarnedByKey(Long id, String name);

}

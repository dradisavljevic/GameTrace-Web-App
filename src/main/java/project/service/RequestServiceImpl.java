package project.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import project.domain.Request;
import project.repository.RanksRepository;
import project.repository.RequestRepository;

@Service
public class RequestServiceImpl implements RequestService {
	
	@Autowired
	private RequestRepository requestRepository;

	@Override
	public List<Request> findAll() {
		return requestRepository.findAll();
	}

	@Override
	public Request save(Request r) {
		Assert.notNull(r);
		return requestRepository.save(r);
	}

	@Override
	public List<Request> getAllByName(String name) {
		Assert.notNull(name);
		return requestRepository.getAllByName(name);
	}

	@Override
	public List<Request> getAllByYear(BigDecimal year) {
		Assert.notNull(year);
		return requestRepository.getAllByYear(year);
	}

	@Override
	public Request getRequestById(Long id) {
		Assert.notNull(id);
		return requestRepository.getRequestById(id);
	}

	@Override
	public void removeRequestById(Long id) {
		Assert.notNull(id);
		requestRepository.removeRequestById(id);
	}

	@Override
	public void updateRequestGameName(Long id, String name) {
		Assert.notNull(id);
		Assert.notNull(name);
		requestRepository.updateRequestGameName(id, name);
	}

	@Override
	public void updateRequestGameYear(Long id, BigDecimal year) {
		Assert.notNull(id);
		Assert.notNull(year);
		requestRepository.updateRequestGameYear(id, year);
	}

	@Override
	public void updateRequestGameDescription(Long id, String desc) {
		Assert.notNull(id);
		Assert.notNull(desc);
		requestRepository.updateRequestGameDescription(id, desc);
	}

	@Override
	public void updateRequestGameImage(Long id, String img) {
		Assert.notNull(id);
		Assert.notNull(img);
		requestRepository.updateRequestGameImage(id, img);
	}

	@Override
	public void updateRequestGameDetectionRule(Long id, String dr) {
		Assert.notNull(id);
		Assert.notNull(dr);
		requestRepository.updateRequestGameDetectionRule(id, dr);
	}

	@Override
	public void updateRequestStatus(Long id, String status) {
		Assert.notNull(id);
		Assert.notNull(status);
		requestRepository.updateRequestStatus(id, status);
	}

	@Override
	public void updateRequest(Long id, String name, String desc, String img, BigDecimal year,
			String dr, String status) {
		Assert.notNull(id);
		Assert.notNull(name);
		Assert.notNull(desc);
		Assert.notNull(year);
		Assert.notNull(dr);
		Assert.notNull(status);
		requestRepository.updateRequest(id, name, desc, img, year, dr, status);
	}

	@Override
	public List<Request> getAllBySubmitter(String name) {
		Assert.notNull(name);
		return requestRepository.getAllBySubmitter(name);
	}

	@Override
	public List<Request> getAllByStatus(String name) {
		Assert.notNull(name);
		return requestRepository.getAllByStatus(name);
	}

	@Override
	public Request getAlreadySubmitted(String gameName, BigDecimal year, String uname) {
		Assert.notNull(gameName);
		Assert.notNull(year);
		Assert.notNull(uname);
		return requestRepository.getAlreadySubmitted(gameName, year, uname);
	}

}

package project.domain.dto;

import java.math.BigDecimal;

import javax.persistence.Id;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.EntityResult;

import org.hibernate.annotations.NamedNativeQuery;






public class PlayerDTO {
	
	
	private String username;

	private BigDecimal ptday;

	private BigDecimal pthour;

	private BigDecimal ptmin;

	private BigDecimal ptsec;
	
	
	protected PlayerDTO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getPtday() {
		return ptday;
	}

	public void setPtday(BigDecimal ptday) {
		this.ptday = ptday;
	}

	public BigDecimal getPthour() {
		return pthour;
	}

	public void setPthour(BigDecimal pthour) {
		this.pthour = pthour;
	}

	public BigDecimal getPtmin() {
		return ptmin;
	}

	public void setPtmin(BigDecimal ptmin) {
		this.ptmin = ptmin;
	}

	public BigDecimal getPtsec() {
		return ptsec;
	}

	public void setPtsec(BigDecimal ptsec) {
		this.ptsec = ptsec;
	}

}

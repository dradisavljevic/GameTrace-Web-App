package project.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the ADMINISTRATOR database table.
 * 
 */
@Entity
@NamedQuery(name="Administrator.findAll", query="SELECT a FROM Administrator a")
public class Administrator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uname;

	//bi-directional one-to-one association to GtUser
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UNAME", insertable=false, updatable=false)
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GtUser gtUser;

	//bi-directional many-to-one association to Request
	@OneToMany(mappedBy="administrator")
	@JsonIgnore
	private List<Request> requests;
	
	//bi-directional many-to-one association to TicketAnswer
	@OneToMany(mappedBy="administrator")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<TicketAnswer> ticketAnswers;

	public Administrator() {
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public GtUser getGtUser() {
		return this.gtUser;
	}

	public void setGtUser(GtUser gtUser) {
		this.gtUser = gtUser;
	}

	public List<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public Request addRequest(Request request) {
		getRequests().add(request);
		request.setAdministrator(this);

		return request;
	}

	public Request removeRequest(Request request) {
		getRequests().remove(request);
		request.setAdministrator(null);

		return request;
	}

	public List<TicketAnswer> getTicketAnswers() {
		return ticketAnswers;
	}

	public void setTicketAnswers(List<TicketAnswer> ticketAnswers) {
		this.ticketAnswers = ticketAnswers;
	}
	
	

}
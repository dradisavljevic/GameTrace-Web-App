package project.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the TICKET_ANSWER database table.
 * 
 */
@Entity
@Table(name="TICKET_ANSWER")
@NamedQuery(name="TicketAnswer.findAll", query="SELECT t FROM TicketAnswer t")
public class TicketAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TICKET_ANSWER_TANSID_GENERATOR", sequenceName="ANSWER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TICKET_ANSWER_TANSID_GENERATOR")
	private long tansid;

	private String tanscont;
	
	@Column(name="TANSRESP")
	private String tansresp;

	@Column(name="TANSTICK")
	private long tanstick;

	//bi-directional many-to-one association to Administrator
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TANSRESP", insertable=false, updatable=false)
	@JsonIgnore
	private Administrator administrator;

	//bi-directional many-to-one association to Ticket
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TANSTICK", insertable=false, updatable=false)
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Ticket ticket;

	public TicketAnswer() {
	}

	public long getTansid() {
		return this.tansid;
	}

	public void setTansid(long tansid) {
		this.tansid = tansid;
	}

	public String getTanscont() {
		return this.tanscont;
	}

	public void setTanscont(String tanscont) {
		this.tanscont = tanscont;
	}

	

	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public Ticket getTicket() {
		return this.ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getTansresp() {
		return tansresp;
	}

	public void setTansresp(String tansresp) {
		this.tansresp = tansresp;
	}

	public long getTanstick() {
		return tanstick;
	}

	public void setTanstick(long tanstick) {
		this.tanstick = tanstick;
	}
	
	

}
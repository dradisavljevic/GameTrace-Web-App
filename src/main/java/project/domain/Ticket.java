package project.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;


/**
 * The persistent class for the TICKET database table.
 * 
 */
@Entity
@NamedQuery(name="Ticket.findAll", query="SELECT t FROM Ticket t")
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TICKET_TICKID_GENERATOR", sequenceName="TICKET_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TICKET_TICKID_GENERATOR")
	private long tickid;

	private String tickdesc;

	private String tickstat;

	private String tickt;
	
	@Column(name="TICKSUB")
	private String ticksub;


	//bi-directional many-to-one association to GameUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TICKSUB", insertable=false, updatable=false)
	@JsonIgnore
	private GameUser gameUser;

	//bi-directional many-to-one association to TicketAnswer
	@OneToMany(mappedBy="ticket")
	private List<TicketAnswer> ticketAnswers;



	public Ticket() {
	}

	public long getTickid() {
		return this.tickid;
	}

	public void setTickid(long tickid) {
		this.tickid = tickid;
	}

	public String getTickdesc() {
		return this.tickdesc;
	}

	public void setTickdesc(String tickdesc) {
		this.tickdesc = tickdesc;
	}

	public String getTickstat() {
		return this.tickstat;
	}

	public void setTickstat(String tickstat) {
		this.tickstat = tickstat;
	}

	public String getTickt() {
		return this.tickt;
	}

	public void setTickt(String tickt) {
		this.tickt = tickt;
	}

	public GameUser getGameUser() {
		return this.gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}

	

	public String getTicksub() {
		return ticksub;
	}

	public void setTicksub(String ticksub) {
		this.ticksub = ticksub;
	}

	public List<TicketAnswer> getTicketAnswers() {
		return ticketAnswers;
	}

	public void setTicketAnswers(List<TicketAnswer> ticketAnswers) {
		this.ticketAnswers = ticketAnswers;
	}

	

	


}
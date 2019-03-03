package project.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="FRIEND_REQUEST")
@NamedQuery(name="FriendRequest.findAll", query="SELECT f FROM FriendRequest f")
public class FriendRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FRIEND_REQUEST_FRID_GENERATOR", sequenceName="FRIEND_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FRIEND_REQUEST_FRID_GENERATOR")
	private long frid;
	
	private String frstat;
	
	@Column(name = "reqdate", insertable=false)
	private Date reqdate;
	
	@Column(name="SENDER")
	private String sender;
	
	@Column(name="RECEIVER")
	private String receiver;

	//bi-directional many-to-one association to GameUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SENDER", insertable=false, updatable=false)
	@JsonIgnore
	private GameUser gameUser1;

	//bi-directional many-to-one association to GameUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RECEIVER", insertable=false, updatable=false)
	@JsonIgnore
	private GameUser gameUser2;

	public FriendRequest() {
	}

	public String getFrstat() {
		return this.frstat;
	}

	public void setFrstat(String frstat) {
		this.frstat = frstat;
	}

	public Date getReqdate() {
		return this.reqdate;
	}

	public void setReqdate(Date reqdate) {
		this.reqdate = reqdate;
	}

	public GameUser getGameUser1() {
		return this.gameUser1;
	}

	public void setGameUser1(GameUser gameUser1) {
		this.gameUser1 = gameUser1;
	}

	public GameUser getGameUser2() {
		return this.gameUser2;
	}

	public void setGameUser2(GameUser gameUser2) {
		this.gameUser2 = gameUser2;
	}

	public long getFrid() {
		return frid;
	}

	public void setFrid(long frid) {
		this.frid = frid;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	

}
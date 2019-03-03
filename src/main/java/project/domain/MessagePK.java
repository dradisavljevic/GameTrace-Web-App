package project.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

/**
 * The primary key class for the "MESSAGE" database table.
 * 
 */
@Embeddable
public class MessagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="MSGSENT")
	private String msgsent;

	@Column(name="MSGDATE", insertable=false,updatable=false,nullable = true, columnDefinition = "date default sysdate")
	@CreationTimestamp
	private Date msgdate;

	@Column(name="MSGTIME", insertable=false,updatable=false,nullable = true, columnDefinition = "timestamp default systimestamp")
	@CreationTimestamp
	private Timestamp msgtime;

	public MessagePK() {
	}
	public String getMsgsent() {
		return this.msgsent;
	}
	public void setMsgsent(String msgsent) {
		this.msgsent = msgsent;
	}
	public Date getMsgdate() {
		return this.msgdate;
	}
	public void setMsgdate(Date msgdate) {
		this.msgdate = msgdate;
	}
	public Timestamp getMsgtime() {
		return this.msgtime;
	}
	public void setMsgtime(Timestamp msgtime) {
		this.msgtime = msgtime;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MessagePK)) {
			return false;
		}
		MessagePK castOther = (MessagePK)other;
		return 
			this.msgsent.equals(castOther.msgsent)
			&& this.msgdate.equals(castOther.msgdate)
			&& this.msgtime.equals(castOther.msgtime);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.msgsent.hashCode();
		hash = hash * prime + this.msgdate.hashCode();
		hash = hash * prime + this.msgtime.hashCode();
		
		return hash;
	}
}
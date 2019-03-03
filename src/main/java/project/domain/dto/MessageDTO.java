package project.domain.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import project.domain.GtUser;

public class MessageDTO {
	
	private String msgsent;
	
	private String msgcont;

	private Date msgdate;

	private String msgtime;

	private String msgrec;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GtUser sender;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GtUser receiver;
	
	

	public MessageDTO() {

	}

	public String getMsgsent() {
		return msgsent;
	}

	public void setMsgsent(String msgsent) {
		this.msgsent = msgsent;
	}

	public String getMsgcont() {
		return msgcont;
	}

	public void setMsgcont(String msgcont) {
		this.msgcont = msgcont;
	}

	public Date getMsgdate() {
		return msgdate;
	}

	public void setMsgdate(Date msgdate) {
		this.msgdate = msgdate;
	}

	public String getMsgtime() {
		return msgtime;
	}

	public void setMsgtime(String msgtime) {
		this.msgtime = msgtime;
	}

	public String getMsgrec() {
		return msgrec;
	}

	public void setMsgrec(String msgrec) {
		this.msgrec = msgrec;
	}

	public GtUser getSender() {
		return sender;
	}

	public void setSender(GtUser sender) {
		this.sender = sender;
	}

	public GtUser getReceiver() {
		return receiver;
	}

	public void setReceiver(GtUser receiver) {
		this.receiver = receiver;
	}
	
	

}

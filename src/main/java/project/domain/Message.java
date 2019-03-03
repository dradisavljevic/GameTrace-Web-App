package project.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the "MESSAGE" database table.
 * 
 */
@Entity
@Table(name="MESSAGE")
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MessagePK id;

	private String msgcont;

	//bi-directional many-to-one association to GtUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MSGSENT", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GtUser gtUser;

	//bi-directional many-to-many association to GtUser
	@ManyToMany
	@JoinTable(
		name="RECEIVED"
		, joinColumns={
			@JoinColumn(name="MESSAGE_MSGDATE", referencedColumnName="MSGDATE"),
			@JoinColumn(name="MESSAGE_MSGSENT", referencedColumnName="MSGSENT"),
			@JoinColumn(name="MESSAGE_MSGTIME", referencedColumnName="MSGTIME")
			}
		, inverseJoinColumns={
			@JoinColumn(name="USER_UNAME")
			}
		)
	@JsonIgnore
	private List<GtUser> gtUsers;

	public Message() {
	}

	public MessagePK getId() {
		return this.id;
	}

	public void setId(MessagePK id) {
		this.id = id;
	}

	public String getMsgcont() {
		return this.msgcont;
	}

	public void setMsgcont(String msgcont) {
		this.msgcont = msgcont;
	}

	public GtUser getGtUser() {
		return this.gtUser;
	}

	public void setGtUser(GtUser gtUser) {
		this.gtUser = gtUser;
	}

	public List<GtUser> getGtUsers() {
		return this.gtUsers;
	}

	public void setGtUsers(List<GtUser> gtUsers) {
		this.gtUsers = gtUsers;
	}

}
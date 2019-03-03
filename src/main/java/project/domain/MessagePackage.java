package project.domain;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQuery;


@NamedNativeQuery(
	    name = "get_messages_between",
	    query = "{ ? = call forumpkg.GET_MESSAGES( ?, ? ) }",
	    callable = true,
	    resultSetMapping = "communication"
	)
	@SqlResultSetMapping(
	    name = "communication",
	    entities = {
	        @EntityResult(
	            entityClass = MessagePackage.class,
	            fields = {
	                @FieldResult( 
	                    name = "msgcont", 
	                    column = "msgcont"
	                ),
	                @FieldResult( 
		                    name = "msgsent", 
		                    column = "msgsent"
		                ),
	                @FieldResult( 
	                    name = "msgrec", 
	                    column = "msgrec"
	                ),
	                @FieldResult( 
		                    name = "msgdate", 
		                    column = "msgdate"
		                ),
	                @FieldResult( 
		                    name = "msgtime", 
		                    column = "msgtime"
		                )
	            }
	        )
	    }
	)
@Entity
@Table(name="MESSAGE_PACKAGE")
@NamedQuery(name="MessagePackage.findAll", query="SELECT m FROM MessagePackage m")
public class MessagePackage {
	
	@Id
	private String msgcont;
	

	private String msgsent;


	private Date msgdate;

	private Timestamp msgtime;
	
	private String msgrec;

	public String getMsgcont() {
		return msgcont;
	}

	public void setMsgcont(String msgcont) {
		this.msgcont = msgcont;
	}

	

	public String getMsgsent() {
		return msgsent;
	}

	public void setMsgsent(String msgsent) {
		this.msgsent = msgsent;
	}

	public Date getMsgdate() {
		return msgdate;
	}

	public void setMsgdate(Date msgdate) {
		this.msgdate = msgdate;
	}

	public Timestamp getMsgtime() {
		return msgtime;
	}

	public void setMsgtime(Timestamp msgtime) {
		this.msgtime = msgtime;
	}

	public String getMsgrec() {
		return msgrec;
	}

	public void setMsgrec(String msgrec) {
		this.msgrec = msgrec;
	}
	
	
	

}

package project.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the POST database table.
 * 
 */
@Embeddable
public class PostPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="POSTPB", insertable=false, updatable=false)
	private String postpb;

	@Column(name="POSTTID", insertable=false, updatable=false)
	private Long posttid;


	private Date postdate;


	private Timestamp posttime;

	public PostPK() {
	}
	public String getPostpb() {
		return this.postpb;
	}
	public void setPostpb(String postpb) {
		this.postpb = postpb;
	}
	public Long getPosttid() {
		return this.posttid;
	}
	public void setPosttid(Long posttid) {
		this.posttid = posttid;
	}
	public Date getPostdate() {
		return this.postdate;
	}
	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}
	public Timestamp getPosttime() {
		return this.posttime;
	}
	public void setPosttime(Timestamp posttime) {
		this.posttime = posttime;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PostPK)) {
			return false;
		}
		PostPK castOther = (PostPK)other;
		return 
			this.postpb.equals(castOther.postpb)
			&& this.posttid.equals(castOther.posttid)
			&& this.postdate.equals(castOther.postdate)
			&& this.posttime.equals(castOther.posttime);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.postpb.hashCode();
		hash = hash * prime + this.posttid.hashCode();
		hash = hash * prime + this.postdate.hashCode();
		hash = hash * prime + this.posttime.hashCode();
		
		return hash;
	}
}
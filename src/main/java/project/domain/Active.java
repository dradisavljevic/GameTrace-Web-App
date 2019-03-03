package project.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@NamedQuery(name="Active.findAll", query="SELECT a FROM Active a")
public class Active {
	
	@Id
	@SequenceGenerator(name="ACTIVE_ACTID_GENERATOR", sequenceName="ACTIVE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACTIVE_ACTID_GENERATOR")
    private long actid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTUSER")
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private GtUser actuser;

	public long getActid() {
		return actid;
	}

	public void setActid(long actid) {
		this.actid = actid;
	}

	public GtUser getActuser() {
		return actuser;
	}

	public void setActuser(GtUser actuser) {
		this.actuser = actuser;
	}

}

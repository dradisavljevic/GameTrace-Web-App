package project.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;

import org.hibernate.annotations.NamedNativeQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the ACHIEVEMENT database table.
 * 
 */
@NamedNativeQuery(
	    name = "get_latest_achievements",
	    query = "{ ? = call eventpkg.GET_LATEST_ACHIEVEMENTS( ? ) }",
	    callable = true,
	    resultSetMapping = "latest_achievements"
	)
	@SqlResultSetMapping(
	    name = "latest_achievements",
	    entities = {
	        @EntityResult(
	            entityClass = Achievement.class,
	            fields = {
	                @FieldResult( 
	                    name = "achid", 
	                    column = "achid"
	                ),
	                @FieldResult( 
		                    name = "achname", 
		                    column = "achname"
		                ),
	                @FieldResult( 
	                    name = "achcond", 
	                    column = "achcond"
	                )
	            }
	        )
	    }
	)
@Entity
@NamedQuery(name="Achievement.findAll", query="SELECT a FROM Achievement a")
public class Achievement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACHIEVEMENT_ACHID_GENERATOR", sequenceName="ACH_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACHIEVEMENT_ACHID_GENERATOR")
	private long achid;

	private String achcond;

	private String achname;

	//bi-directional many-to-one association to Earned
	@OneToMany(mappedBy="achievement")
	@JsonIgnore
	private List<Earned> earneds;

	public Achievement() {
	}

	public long getAchid() {
		return this.achid;
	}

	public void setAchid(long achid) {
		this.achid = achid;
	}

	public String getAchcond() {
		return this.achcond;
	}

	public void setAchcond(String achcond) {
		this.achcond = achcond;
	}

	public String getAchname() {
		return this.achname;
	}

	public void setAchname(String achname) {
		this.achname = achname;
	}

	public List<Earned> getEarneds() {
		return this.earneds;
	}

	public void setEarneds(List<Earned> earneds) {
		this.earneds = earneds;
	}

	public Earned addEarned(Earned earned) {
		getEarneds().add(earned);
		earned.setAchievement(this);

		return earned;
	}

	public Earned removeEarned(Earned earned) {
		getEarneds().remove(earned);
		earned.setAchievement(null);

		return earned;
	}

}
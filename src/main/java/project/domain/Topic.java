package project.domain;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the TOPIC database table.
 * 
 */
@Entity
@NamedQuery(name="Topic.findAll", query="SELECT t FROM Topic t")
public class Topic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TOPIC_TPID_GENERATOR", sequenceName="TOPIC_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOPIC_TPID_GENERATOR")
	private long tpid;
	
	private String tptitle;


	private Date tplmd;


	private Date tpod;

	private BigDecimal tppc;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="topic")
	@JsonIgnore
	private List<Post> posts;

	//bi-directional many-to-one association to GtUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TOPOP", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GtUser gtUser;

	public Topic() {
	}

	public String getTptitle() {
		return this.tptitle;
	}

	public void setTptitle(String tptitle) {
		this.tptitle = tptitle;
	}

	public Date getTplmd() {
		return this.tplmd;
	}

	public void setTplmd(Date tplmd) {
		this.tplmd = tplmd;
	}

	public Date getTpod() {
		return this.tpod;
	}

	public void setTpod(Date tpod) {
		this.tpod = tpod;
	}

	public BigDecimal getTppc() {
		return this.tppc;
	}

	public void setTppc(BigDecimal tppc) {
		this.tppc = tppc;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setTopic(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setTopic(null);

		return post;
	}

	public GtUser getGtUser() {
		return this.gtUser;
	}

	public void setGtUser(GtUser gtUser) {
		this.gtUser = gtUser;
	}

}
package project.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the GT_USER database table.
 * 
 */
@Entity
@Table(name="GT_USER")
@NamedQuery(name="GtUser.findAll", query="SELECT g FROM GtUser g")
@DynamicInsert
public class GtUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String uname;

	private String pword;

	private String uavat;

	@Column(name = "udate", insertable=false)
	private Date udate;

	private String uemail;

	private BigDecimal userut;

	//bi-directional one-to-one association to Administrator
	@OneToOne(mappedBy="gtUser", fetch=FetchType.LAZY)
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Administrator administrator;

	//bi-directional many-to-one association to GameGroup
	@OneToMany(mappedBy="gtUser")
	private List<GameGroup> gameGroups;

	//bi-directional one-to-one association to GameUser
	@OneToOne(mappedBy="gtUser", fetch=FetchType.LAZY)
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GameUser gameUser;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="gtUser")
	@JsonIgnore
	private List<Message> messages1;

	//bi-directional many-to-many association to Message
	@ManyToMany(mappedBy="gtUsers")
	@JsonIgnore
	private List<Message> messages2;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="gtUser")
	@JsonIgnore
	private List<Post> posts;

	//bi-directional many-to-one association to Ranks
	@OneToMany(mappedBy="gtUser")
	@JsonIgnore
	private List<Ranks> ranks;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="gtUser")
	@JsonIgnore
	private List<Review> reviews;

	//bi-directional many-to-one association to Topic
	@OneToMany(mappedBy="gtUser")
	@JsonIgnore
	private List<Topic> topics;

	public GtUser() {
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPword() {
		return this.pword;
	}

	public void setPword(String pword) {
		this.pword = pword;
	}

	public String getUavat() {
		return this.uavat;
	}

	public void setUavat(String uavat) {
		this.uavat = uavat;
	}

	public Date getUdate() {
		return this.udate;
	}

	public void setUdate(Date udate) {
		this.udate = udate;
	}

	public String getUemail() {
		return this.uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public BigDecimal getUserut() {
		return this.userut;
	}

	public void setUserut(BigDecimal userut) {
		this.userut = userut;
	}

	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public List<GameGroup> getGameGroups() {
		return this.gameGroups;
	}

	public void setGameGroups(List<GameGroup> gameGroups) {
		this.gameGroups = gameGroups;
	}

	public GameGroup addGameGroup(GameGroup gameGroup) {
		getGameGroups().add(gameGroup);
		gameGroup.setGtUser(this);

		return gameGroup;
	}

	public GameGroup removeGameGroup(GameGroup gameGroup) {
		getGameGroups().remove(gameGroup);
		gameGroup.setGtUser(null);

		return gameGroup;
	}

	public GameUser getGameUser() {
		return this.gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}

	public List<Message> getMessages1() {
		return this.messages1;
	}

	public void setMessages1(List<Message> messages1) {
		this.messages1 = messages1;
	}

	public Message addMessages1(Message messages1) {
		getMessages1().add(messages1);
		messages1.setGtUser(this);

		return messages1;
	}

	public Message removeMessages1(Message messages1) {
		getMessages1().remove(messages1);
		messages1.setGtUser(null);

		return messages1;
	}

	public List<Message> getMessages2() {
		return this.messages2;
	}

	public void setMessages2(List<Message> messages2) {
		this.messages2 = messages2;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setGtUser(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setGtUser(null);

		return post;
	}

	public List<Ranks> getRanks() {
		return this.ranks;
	}

	public void setRanks(List<Ranks> ranks) {
		this.ranks = ranks;
	}

	public Ranks addRank(Ranks rank) {
		getRanks().add(rank);
		rank.setGtUser(this);

		return rank;
	}

	public Ranks removeRank(Ranks rank) {
		getRanks().remove(rank);
		rank.setGtUser(null);

		return rank;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setGtUser(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setGtUser(null);

		return review;
	}

	public List<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Topic addTopic(Topic topic) {
		getTopics().add(topic);
		topic.setGtUser(this);

		return topic;
	}

	public Topic removeTopic(Topic topic) {
		getTopics().remove(topic);
		topic.setGtUser(null);

		return topic;
	}

}
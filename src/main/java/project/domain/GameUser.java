package project.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;
import java.util.List;


/**
 * The persistent class for the GAME_USER database table.
 * 
 */
@Entity
@Table(name="GAME_USER")
@NamedQuery(name="GameUser.findAll", query="SELECT g FROM GameUser g")
public class GameUser implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String uname;

	private String rname;

	private String ubio;

	private String ucountry;


	private Date udob;
	
	private BigDecimal playing;
	
	//bi-directional many-to-one association to Game
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PLAYING_GAME_ID")
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Game game;
	
	//bi-directional many-to-one association to FriendRequest
	@OneToMany(mappedBy="gameUser1")
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<FriendRequest> friendRequests1;

	//bi-directional many-to-one association to FriendRequest
	@OneToMany(mappedBy="gameUser2")
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<FriendRequest> friendRequests2;
	
	//bi-directional many-to-one association to GroupInvite
	@OneToMany(mappedBy="gameUser")
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<GroupInvite> groupInvites;

	//bi-directional many-to-one association to Earned
	@OneToMany(mappedBy="gameUser")
	@JsonIgnore
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Earned> earneds;

	//bi-directional many-to-many association to GameGroup
	@ManyToMany
	@JoinTable(
		name="PARTICIPATING_IN"
		, joinColumns={
			@JoinColumn(name="GAME_USER_UNAME")
			}
		, inverseJoinColumns={
			@JoinColumn(name="GROUP_GRID", referencedColumnName="GRID")
			}
		)
	@JsonIgnore
	private List<GameGroup> gameGroups;

	//bi-directional many-to-many association to GameUser
	@ManyToMany
	@JoinTable(
		name="BEFRIENDS"
		, joinColumns={
			@JoinColumn(name="GAME_USER_UNAME1")
			}
		, inverseJoinColumns={
			@JoinColumn(name="GAME_USER_UNAME")
			}
		)
	@JsonIgnore
	private List<GameUser> gameUsers1;

	//bi-directional many-to-many association to GameUser
	@ManyToMany(mappedBy="gameUsers1")
	@JsonIgnore
	private List<GameUser> gameUsers2;

	//bi-directional one-to-one association to GtUser
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UNAME", insertable=false, updatable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private GtUser gtUser;

	//bi-directional many-to-one association to Guide
	@OneToMany(mappedBy="gameUser")
	@JsonIgnore
	private List<Guide> guides;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="gameUser")
	@JsonIgnore
	private List<Item> items;

	//bi-directional many-to-one association to Play
	@OneToMany(mappedBy="gameUser")
	@JsonIgnore
	private List<Play> plays;

	//bi-directional many-to-one association to Request
	@OneToMany(mappedBy="gameUser")
	@JsonIgnore
	private List<Request> requests;
	
	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="gameUser")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<Ticket> tickets;
	
	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="gameUser2")
	@JsonIgnore
	private List<Item> items2;

	public GameUser() {
	}

	public String getUname() {
		return this.uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getRname() {
		return this.rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getUbio() {
		return this.ubio;
	}

	public void setUbio(String ubio) {
		this.ubio = ubio;
	}

	public String getUcountry() {
		return this.ucountry;
	}

	public void setUcountry(String ucountry) {
		this.ucountry = ucountry;
	}

	public Date getUdob() {
		return this.udob;
	}

	public void setUdob(Date udob) {
		this.udob = udob;
	}

	public List<Earned> getEarneds() {
		return this.earneds;
	}

	public void setEarneds(List<Earned> earneds) {
		this.earneds = earneds;
	}

	public Earned addEarned(Earned earned) {
		getEarneds().add(earned);
		earned.setGameUser(this);

		return earned;
	}

	public Earned removeEarned(Earned earned) {
		getEarneds().remove(earned);
		earned.setGameUser(null);

		return earned;
	}

	public List<GameGroup> getGameGroups() {
		return this.gameGroups;
	}

	public void setGameGroups(List<GameGroup> gameGroups) {
		this.gameGroups = gameGroups;
	}

	public List<GameUser> getGameUsers1() {
		return this.gameUsers1;
	}

	public void setGameUsers1(List<GameUser> gameUsers1) {
		this.gameUsers1 = gameUsers1;
	}

	public List<GameUser> getGameUsers2() {
		return this.gameUsers2;
	}

	public void setGameUsers2(List<GameUser> gameUsers2) {
		this.gameUsers2 = gameUsers2;
	}

	public GtUser getGtUser() {
		return this.gtUser;
	}

	public void setGtUser(GtUser gtUser) {
		this.gtUser = gtUser;
	}

	public List<Guide> getGuides() {
		return this.guides;
	}

	public void setGuides(List<Guide> guides) {
		this.guides = guides;
	}

	public Guide addGuide(Guide guide) {
		getGuides().add(guide);
		guide.setGameUser(this);

		return guide;
	}

	public Guide removeGuide(Guide guide) {
		getGuides().remove(guide);
		guide.setGameUser(null);

		return guide;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setGameUser(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setGameUser(null);

		return item;
	}

	public List<Play> getPlays() {
		return this.plays;
	}

	public void setPlays(List<Play> plays) {
		this.plays = plays;
	}

	public Play addPlay(Play play) {
		getPlays().add(play);
		play.setGameUser(this);

		return play;
	}

	public Play removePlay(Play play) {
		getPlays().remove(play);
		play.setGameUser(null);

		return play;
	}

	public List<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public Request addRequest(Request request) {
		getRequests().add(request);
		request.setGameUser(this);

		return request;
	}

	public Request removeRequest(Request request) {
		getRequests().remove(request);
		request.setGameUser(null);

		return request;
	}
	
	public List<FriendRequest> getFriendRequests1() {
		return this.friendRequests1;
	}

	public void setFriendRequests1(List<FriendRequest> friendRequests1) {
		this.friendRequests1 = friendRequests1;
	}

	public FriendRequest addFriendRequests1(FriendRequest friendRequests1) {
		getFriendRequests1().add(friendRequests1);
		friendRequests1.setGameUser1(this);

		return friendRequests1;
	}

	public FriendRequest removeFriendRequests1(FriendRequest friendRequests1) {
		getFriendRequests1().remove(friendRequests1);
		friendRequests1.setGameUser1(null);

		return friendRequests1;
	}

	public List<FriendRequest> getFriendRequests2() {
		return this.friendRequests2;
	}

	public void setFriendRequests2(List<FriendRequest> friendRequests2) {
		this.friendRequests2 = friendRequests2;
	}

	public FriendRequest addFriendRequests2(FriendRequest friendRequests2) {
		getFriendRequests2().add(friendRequests2);
		friendRequests2.setGameUser2(this);

		return friendRequests2;
	}

	public FriendRequest removeFriendRequests2(FriendRequest friendRequests2) {
		getFriendRequests2().remove(friendRequests2);
		friendRequests2.setGameUser2(null);

		return friendRequests2;
	}
	
	public List<GroupInvite> getGroupInvites() {
		return this.groupInvites;
	}

	public void setGroupInvites(List<GroupInvite> groupInvites) {
		this.groupInvites = groupInvites;
	}

	public GroupInvite addGroupInvite(GroupInvite groupInvite) {
		getGroupInvites().add(groupInvite);
		groupInvite.setGameUser(this);

		return groupInvite;
	}

	public GroupInvite removeGroupInvite(GroupInvite groupInvite) {
		getGroupInvites().remove(groupInvite);
		groupInvite.setGameUser(null);

		return groupInvite;
	}

	public BigDecimal getPlaying() {
		return playing;
	}

	public void setPlaying(BigDecimal playing) {
		this.playing = playing;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Item> getItems2() {
		return items2;
	}

	public void setItems2(List<Item> items2) {
		this.items2 = items2;
	}
	
	

}
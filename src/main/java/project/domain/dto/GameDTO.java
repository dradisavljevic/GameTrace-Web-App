package project.domain.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import project.domain.GameDeveloper;

public class GameDTO {
	
	String gamename;
	
	  String gamedesc;
	  String gamepc;
	  String gamepsec;
	  String gamepmin;
	  String gamephour;
	  String gamepday;
	  String gamepn;
	  String gimg;
	  ArrayList<String> gameDevelopers;
	  String gamery;
	  String gamedr;
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	public String getGamedesc() {
		return gamedesc;
	}
	public void setGamedesc(String gamedesc) {
		this.gamedesc = gamedesc;
	}
	public String getGamepc() {
		return gamepc;
	}
	public void setGamepc(String gamepc) {
		this.gamepc = gamepc;
	}
	public String getGamepsec() {
		return gamepsec;
	}
	public void setGamepsec(String gamepsec) {
		this.gamepsec = gamepsec;
	}
	public String getGamepmin() {
		return gamepmin;
	}
	public void setGamepmin(String gamepmin) {
		this.gamepmin = gamepmin;
	}
	public String getGamephour() {
		return gamephour;
	}
	public void setGamephour(String gamephour) {
		this.gamephour = gamephour;
	}
	public String getGamepday() {
		return gamepday;
	}
	public void setGamepday(String gamepday) {
		this.gamepday = gamepday;
	}
	public String getGamepn() {
		return gamepn;
	}
	public void setGamepn(String gamepn) {
		this.gamepn = gamepn;
	}
	public String getGimg() {
		return gimg;
	}
	public void setGimg(String gimg) {
		this.gimg = gimg;
	}
	public ArrayList<String> getGameDevelopers() {
		return gameDevelopers;
	}
	public void setGameDevelopers(ArrayList<String> gameDevelopers) {
		this.gameDevelopers = gameDevelopers;
	}
	public String getGamery() {
		return gamery;
	}
	public void setGamery(String gamery) {
		this.gamery = gamery;
	}
	public String getGamedr() {
		return gamedr;
	}
	public void setGamedr(String gamedr) {
		this.gamedr = gamedr;
	}
	protected GameDTO() {

	}
	  
	  

}

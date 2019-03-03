package project.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQuery(name="Token.findAll", query="SELECT t FROM Token t")
public class Token {
	
	private static final int EXPIRATION = 60 * 24;
	 
	@Id
	@SequenceGenerator(name="TOKEN_TOKID_GENERATOR", sequenceName="TOKEN_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOKEN_TOKID_GENERATOR")
    private Long tokid;
    
    private String token; 
    
    private Date tokexp;
    
    private String tokem;
    
    private String tokpw;
    
    private String tokun;
    
    private String tokavat;
    
    private String tokbio;
    
    private String tokdob;
    
    private String tokrn;
    
    private String tokcntry;
    
    public Token() {}
    
    public Token(String token, String email, String password, String username, String avatar, String biography, String dob, String realName, String country) {
    	this.token = token;
    	this.tokem = email;
    	this.tokpw = password;
    	this.tokun = username;
    	this.tokavat = avatar;
    	this.tokbio = biography;
    	this.tokdob = dob;
    	this.tokrn = realName;
    	this.tokcntry = country;
    }
    
    
    public String getTokcntry() {
		return tokcntry;
	}

	public void setTokcntry(String tokcntry) {
		this.tokcntry = tokcntry;
	}

	public String getTokrn() {
		return tokrn;
	}

	public void setTokrn(String tokrn) {
		this.tokrn = tokrn;
	}

	public Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

	public Long getTokid() {
		return tokid;
	}

	public void setTokid(Long tokid) {
		this.tokid = tokid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokexp() {
		return tokexp;
	}

	public void setTokexp(Date tokexp) {
		this.tokexp = tokexp;
	}

	public String getTokem() {
		return tokem;
	}

	public void setTokem(String tokem) {
		this.tokem = tokem;
	}

	public String getTokpw() {
		return tokpw;
	}

	public void setTokpw(String tokpw) {
		this.tokpw = tokpw;
	}

	public String getTokun() {
		return tokun;
	}

	public void setTokun(String tokun) {
		this.tokun = tokun;
	}

	public String getTokavat() {
		return tokavat;
	}

	public void setTokavat(String tokavat) {
		this.tokavat = tokavat;
	}

	public String getTokbio() {
		return tokbio;
	}

	public void setTokbio(String tokbio) {
		this.tokbio = tokbio;
	}

	public String getTokdob() {
		return tokdob;
	}

	public void setTokdob(String tokdob) {
		this.tokdob = tokdob;
	}

}

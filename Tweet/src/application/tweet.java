package application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.nio.charset.StandardCharsets.*;

import java.nio.charset.Charset;

public class tweet {
	private  String Id; //id du tweet
	private  String UserId; //Nom de l'utilisateur
	private  LocalDateTime Date; // date du tweet
	private  String Texte; // texte du tweet
	private  String RTID; // nom de l'utilisateur ayant retweeter
	private Integer rt; // Nombre de retweet
	
	
	public Integer getRt() {
		return rt;
	}


	public void setRt(Integer rt) {
		this.rt = rt;
	}


	public tweet(String id, String userId, LocalDateTime date, String texte, String rTID) { //constructeur de la classe Tweet
		super();
		Id = id;
		UserId = userId;
		Date = date;
		Texte = texte;
		RTID = rTID;
	}


	public String getId() {
		return Id;
	}


	@Override
	public String toString() {
		return "id : "+this.Id+"UserID : "+this.UserId+ "Date : "+this.Date +"Texte : " +this.Texte+"RTID : "+ this.RTID;
		
	}

	public void setId(String id) {
		Id = id;
	}



	public void setUserId(String userId) {
		UserId = userId;
	}


	public void setDate(LocalDateTime date) {
		Date = date;
	}



	public void setTexte(String texte) {
		Texte = texte;
	}



	public String getUserId() {
		return UserId;
	}


	public LocalDateTime getDate() {
		return Date;
	}


	public String getTexte() {
		byte[] text = Texte.getBytes();
		String value = new String(text, Charset.forName("UTF-8"));  //Passage du texte en UTF8 afin d'accepter les caractères spéciaux 
		return value;
	}


	public String getRTID() {
		return RTID;
	}


	public void setRTID(String rTID) {
		RTID = rTID;
	}
	
	
}

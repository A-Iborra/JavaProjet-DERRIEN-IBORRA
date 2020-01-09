package application;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Création de la classe Base
public class base 
{
	ArrayList<tweet> tweet;  // Création d'une arraylist héritant de la class Tweet
	ArrayList<tweet> tweet2; // Création d'une arraylist héritant de la class Tweet
	public void initialise() // Fonction initialisant l'arraylist
	{ 
		tweet = new ArrayList<tweet>();
	}
	public void ajouter(tweet n) // Fonction ajoutant une ligne à l'arraylist
	{ 
		tweet.add(n);
	}	

	public void trirt() throws Exception // Fonction comptant le nombre de retweet
	{ 
		int n = size()-1, n1=0, i=0, i2=1, c=0;
		tweet.get(0).setRt(0);
		while(n1!=n)
		{
		  tweet t1 = tweet.get(i); // Récupère la ligne i
		  tweet t2 = tweet.get(i2); // Récupère la ligne i2
		  String p =t1.getTexte(); // Récupère le texte a la ieme ligne 
		  String m =t2.getTexte();
		  if (p.length()>60) {
			  p = p.substring(0, 50);
		  }
		  if (m.length()>60) {
			  m = m.substring(0, 50);
		  }
		  p = p.replaceAll("[^\\w]|_|[0-9]"," "); //Enleve les caractère spéciaux (emoji par exemple)
		  m = m.replaceAll("[^\\w]|_|[0-9]"," ");
		  pattern = Pattern.compile(p);
		  matcher = pattern.matcher(m); // Regarde si les deux textes sont les mêmes 
		  if (matcher.find()) 
		  {
			  c=c+1; // Ajoute +1 au compteur
			  t1.setRt(c); // Ajoute le compteur a la base
			  t2.setRt(0);
			  i2=i2+1;
			  n1=n1+1;
		  } 
		  else 
		  {
			  t2.setRt(0);
			  i=i2;
			  i2=i2+1;
			  n1=n1+1;
			  c=0;
		  }
		}
	}

	public ArrayList<tweet> getTweet() {
		return tweet;
	}
	public ObservableList<application.tweet> change(){ // Fonction ajoutant les ligne d'une arraylist a une observablelist 
			ObservableList<tweet> obase = FXCollections.observableArrayList(getTweet());
			return obase;
	}
	public void charger(String fichier)  throws Exception //Fonction de chargement de fichier
	{ 
		   FileInputStream in = new FileInputStream(fichier); // Récupère le nom du fichier 
		   ObjectInputStream read = new ObjectInputStream(in); // Lecture du fichier
		   Object lecture =read.read();
		   tweet = (ArrayList<tweet>)lecture;
		   read.close();
		   in.close();	  
	}
	public void delete()  throws Exception //Supprime les lignes de l'arraylist
	{ 
		   tweet.clear();
	}
	public int size()  throws Exception // Retourne la taille de l'arraylist
	{ 
		   return tweet.size();
	}
	ObservableList<tweet> bas = FXCollections.observableArrayList();
	private static Pattern pattern;
	private static Matcher matcher;
	public ObservableList<application.tweet> rechercher(String texte) //Recherche dans l'arraylist
	{	 
		bas = FXCollections.observableArrayList();
		pattern = Pattern.compile(texte.toLowerCase()); //Récupère le texte recherché et le passe que en minuscule
		int i = 1;
		Iterator it = tweet.iterator(); //Crée un itérator de la liste
		while (it.hasNext()) {
			tweet d=(tweet) it.next();
			String G=d.toString();
			matcher = pattern.matcher(G.toLowerCase()); //Vérifie si le mot recherché est présent 
			while(matcher.find()) {
				bas.add(d); // Ajoute le tweet contenant le mot dans l'observablelist
			}
			i=i+1;
		}
		return bas;
		
	}
	public ObservableList<application.tweet> rechercher2(String texte) { //Recherche dans l'arraylist (même fonction que recherche)
		tweet2=new ArrayList<tweet>(bas);
		bas.clear();
		pattern = Pattern.compile(texte.toLowerCase());
		int i = 1;
		Iterator it = tweet2.iterator();
		while (it.hasNext()) {
			tweet d=(tweet) it.next();
			String G=d.toString();	
			matcher = pattern.matcher(G.toLowerCase());
			while(matcher.find()) {
				bas.add(d);
			}
			i=i+1;
		}
		return bas;
		
	}

}

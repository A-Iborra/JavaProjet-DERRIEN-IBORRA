package application;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Collections;
import java.util.Comparator;

public class maincontrol implements Initializable{
	 static base base = new base(); // Alloue un espace mémoire à l'objet base de type base
	 static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"); // format de date entrante
	 DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("\"yyyy-MM-dd HH:mm:ss"); // format de date sortante
	 ObservableList<tweet> obase = FXCollections.observableArrayList(); // Création d'une observablelist 
	 @FXML
	 private Button but0,but1,but2,but3,but4,but5,but6,but7,but11; //Initialisation des différents bouton,label ... faisant reférence au fichier FXML
	 @FXML
	 private Label l;
	 @FXML
	 private TextField lab1,lab2;
	 @FXML
	 private TableView<tweet> liste;
	 @FXML
	 private TableColumn<tweet, String> columnOne,columnTwo,columnThree,columnFour,columnFive,columnsix;
	 @FXML
	 private ProgressBar pb;
	 @FXML
	 protected void ouvrir(ActionEvent e) throws Exception {// Fonction de clique qui ouvre le fichier
		 String texte =lab2.getText();
		 if (texte.isEmpty()) { // si le texte est null envoi d'un message
				alertechargement();
		 }else {
				new Thread() {      
			        @Override
			        public void run() {
			        	pb.setVisible(true);
			            int j = CSV();
			            /*if (j==1) { 
			            	Platform.runLater(()->{
			            		alertechargement(); 
					    	});			 				
			 			}*/
			            //if (j==0) {
					    	 lab2.setDisable(true);
					    	 l.setDisable(true);;
					    	 pb.setVisible(false);
					    	 but2.setDisable(false);
					    	 but4.setDisable(false);
					    	 but0.setDisable(true);
					    	 /*Platform.runLater(()->{
					    		 chargement(); 
					    		});*/
			 			//}
			         };         
			    }.start();
			}
	 }
	 
	 private int CSV() { //Fonction permettant la lecture des fichiers CSV 
		 double n=0;
		 base.initialise(); //Initialistion de la base
		 tweet t;
		 	columnOne.setCellValueFactory(new PropertyValueFactory<>("id")); // Permet la correspondance entre les éléments et sa colonne 
			columnTwo.setCellValueFactory(new PropertyValueFactory<>("userId"));
			columnThree.setCellValueFactory(new PropertyValueFactory<>("date"));
			columnFour.setCellValueFactory(new PropertyValueFactory<>("texte")) ;
			columnFive.setCellValueFactory(new PropertyValueFactory<>("rTID"));
			try {
			FileReader r = new FileReader(lab2.getText()); //ouvre le dossier au chemin indiqué
			BufferedReader br = new BufferedReader(r);			
			String ligne;
			ligne = br.readLine();
			do {
			if(ligne.isEmpty() ){			
			} else {
				String[] l = ligne.split("	", 5); //création d'un string comprennant 5 "case"
				if(Character.isDigit(l[0].charAt(0))) {
			    try {
			    	LocalDateTime date=LocalDateTime.parse(l[2],formatter); 
			    	String startDate = date.format(outputFormat);
			    	LocalDateTime date1=LocalDateTime.parse(startDate,outputFormat); //remplace la date d'entrée par celle de sortie
			    	t= new tweet(l[0],l[1],date1,l[3],l[4]); // créee un tweet comprennant toutes les informations de la ligne chargée
			    	base.ajouter(t); //ajoute cette ligne a notre base
			    }catch(DateTimeParseException e1){
			    	e1.printStackTrace(); //affiche les warnings des lignes non chargées si celle-ci ne correspondent pas au format attendu
			    }
				}
			}
			pb.setProgress(n); // ajoute n a notre barre de chargement
	    	n=n+0.0000005;
			ligne = br.readLine();
			}while(ligne !=null); //repète l'opération du chargement des lignes tant qu'il y en a 
			}catch(Exception e1) {
				e1.printStackTrace(); 
				return 1;
			}
			return 0;		
	 }
	 
	@FXML
	protected void afficher(ActionEvent e) throws Exception { //Fonction clique permettant d'afficher la base
		obase=base.change(); // charge notre ObservableList avec notre base actuelle
		liste.getItems().setAll(obase); // affiche notre liste 
    	liste.setVisible(true); 
    	but3.setDisable(false);
    	but11.setVisible(false);
    	but1.setVisible(true);
    	but1.setDisable(false);
   	 	but5.setDisable(false);
   	 	but6.setDisable(false);
   	 	lab1.setDisable(false);
	}	
	 
	@FXML
	protected void rechercher(ActionEvent e) throws Exception { //fonction clique permettant d'effectuer la recherche
		String texte =lab1.getText(); // récupère le texte a rechercher 
		if (texte.isEmpty()) { // si le texte est null envoi d'un message
			alerteRecherche();
		}else { //sinon recherche dans la base
			obase=base.rechercher(texte); 
			liste.getItems().setAll(obase); //affiche la base une fois recherché
			but7.setDisable(false);
			but11.setVisible(true);
			but11.setDisable(false);
			but1.setVisible(false);
			rech(); //indique le nombre de tweet trouvé 
		}
	 }
	@FXML
	protected void rechercher2(ActionEvent e) throws Exception { //fonction clique permettant de continuer une recherche (même chose que recherche)
		String texte =lab1.getText();
		if (texte.isEmpty()) {
			alerteRecherche();
		}else {
		obase=base.rechercher2(texte); 
		liste.getItems().setAll(obase);}
		rech();
	 }
	
	@FXML
	protected void triuser(ActionEvent e) throws Exception { //fonction clique permettant de trier par rapport au nom d'utilisateur 	
		Comparator<tweet> comparator = Comparator.comparing(tweet::getUserId); // crée un comparateur pour la classe tweet pour UserID
		Collections.sort(obase, comparator); // applique ce comparateur 
		liste.getItems().setAll(obase); // affiche la nouvelle base triée 
		but7.setDisable(false);
	 }
	
	@FXML
	protected void tridate(ActionEvent e) throws Exception {	//fonction clique permettant de trier par rapport à la date
		Comparator<tweet> comparator = Comparator.comparing(tweet::getDate); // crée un comparateur pour la classe tweet pour Date
		Collections.sort(obase, comparator); // applique ce comparateur 
		liste.getItems().setAll(obase); // affiche la nouvelle base triée
		but7.setDisable(false);
	 }	
	
	@FXML
	protected void trinbretweet(ActionEvent e) throws Exception {	 //fonction clique permettant de trier par rapport au nombre de retweet
		Comparator<tweet> comparator = Comparator.comparing(tweet::getTexte); //  crée un comparateur pour la classe tweet pour Texte
		Collections.sort(base.getTweet(), comparator); // applique ce comparateur 
		base.trirt(); //effectue les comparaison de texte
		columnsix.setCellValueFactory(new PropertyValueFactory<>("rt")); // affiche la nouvelle colonne contenant les nombre de RT
		columnsix.setVisible(true);
		obase=base.change();
		/*int n = obase.size()-1;
		//Integer n = 5;
		int n1=0;
		while(n1!=n)
		{
			tweet t1 = base.getTweet().get(n1);
			Integer r =t1.getRt();
			if (r==0) {
			obase.remove(n1);}else {n1=n1+1;}
			n = obase.size()-1;
		  }*/
		Comparator<tweet> comparator2 = Comparator.comparing(tweet::getRt);  //crée un comparateur pour la classe tweet pour Rt
		Collections.sort(obase, Collections.reverseOrder(comparator2)); // applique ce comparateur dans l'ordre décroissant 
		liste.getItems().setAll(obase); //affiche la base triée

	}
	
	@FXML
	protected void reset(ActionEvent e) throws Exception {	//fonction clique permettant de reset 
		liste.setVisible(false); 
    	but0.setDisable(false);
    	lab1.setDisable(true);
   	 	lab2.setDisable(false);
   	 	but1.setDisable(true);
   	 	but2.setDisable(true);
   	 	but3.setDisable(true);
   	 	but7.setDisable(true);
   	 	but11.setDisable(true);
   	 	but5.setDisable(true);
	 	but6.setDisable(true);
   	 	pb.setProgress(0);
   	 	l.setDisable(true);;
   	 	but4.setDisable(true);;
   	 	obase.clear(); //supprime les lignes de obase
   	 	base.delete(); //supprime les lignes de base 
	 }
	
	private void alerteRecherche() { //fonction du messagebox si une recherche n'est pas correct
		 Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Recherche impossible");
	        alert.setHeaderText(null);
	        alert.setContentText("veuillez ecrire une recherche !");
	        alert.showAndWait();
	    }
	
	private void chargement() {//fonction du messagebox pour indiquer la fin du chargement de la base
		 Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Base Chargé");
	        alert.setHeaderText(null);
	        alert.setContentText("Base chargé avec succès");
	        alert.showAndWait();
	    }
	
	private void alertechargement() { //fonction du messagebox si une recherche n'est pas correct
		 Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Chargement impossible");
	        alert.setHeaderText(null);
	        alert.setContentText("Le fichier spécifié est introuvable !");
	        alert.showAndWait();
	    }
	
	private void rech() {//fonction du messagebox si une recherche est réussie
		 Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Recherche");
	        alert.setHeaderText(null);
	        alert.setContentText("Recherche effectuée. nombres de tweets trouvé : "+obase.size());
	        alert.showAndWait();
	    }
	
@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		}
}
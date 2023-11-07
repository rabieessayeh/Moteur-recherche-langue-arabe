package com.mr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StopWords {
	
	public String chemin;
	
	public StopWords() {
		this.chemin = "data\\stop_words_arabic.txt";
	}
	
	public StopWords(String chemin) {
		this.chemin = chemin;
	}
	
	public List<String> get(){
		File f = new File(this.chemin);
		Fichier F = new Fichier();
		
		return F.ExtractMotsFichier(f);
	}
	
	public  Map<String, String> Start(Map<String, ArrayList<String>> MotsParFichier){
		
		Map<String, String > MotsParFichierNeContientPas_SW = new HashMap<>();
		
				
        for (Entry<String, ArrayList<String>> entry : MotsParFichier.entrySet()) {
        	
        	// Recuperer le nom de fichier et la liste des mots
        	String fileName = entry.getKey();
            List<String> mots = entry.getValue();      
            
		// supprimer les stop words et les stocker dans la liste NoStopWords
            List<String> SW = get();
            List<String> NoStopWords = new ArrayList<String>() ;
			
			for (String mot : mots) {
				if (! SW.contains(mot)) {
					NoStopWords.add(mot);
				}
			}
			
	        StringBuilder resultat = new StringBuilder();
	        
	        for (String mot : NoStopWords) {
	            resultat.append(mot);
	            resultat.append(" "); // Ajouter un espace entre les mots
	        }
	        
	        // Supprimer l'espace final s'il y en a un
	        if (resultat.length() > 0) {
	            resultat.deleteCharAt(resultat.length() - 1);
	        }
	        
	        
			
			MotsParFichierNeContientPas_SW.put(fileName,  resultat.toString());	
        
        }
      return MotsParFichierNeContientPas_SW ;
	}
	
	
	
}

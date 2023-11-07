package com.mr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Indexation {
	
	public Map<String, List<String>> MotsParFichier;
	
	public Indexation(Map<String, List<String>> stemming ) {
		this.MotsParFichier = stemming;
	}
	
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> wordCountMap = new HashMap<>();

        for (String word : words) {
            // Retirez la ponctuation et convertissez en minuscules si nécessaire
            
            String[] wordArray = word.split(" ");

            for (String w : wordArray) {
                if (!w.isEmpty()) {
                    if (wordCountMap.containsKey(w)) {
                        // Le mot existe déjà, augmentez le compteur
                        int count = wordCountMap.get(w);
                        wordCountMap.put(w, count + 1);
                    } else {
                        // Le mot n'existe pas encore, ajoutez-le au dictionnaire
                        wordCountMap.put(w, 1);
                    }
                }
            }
        }

        return wordCountMap;
    }
	
	public Map<String,Double> Occurance(List<String> listeDeMots){
	    Map<String, Double> occurrenceMap = new HashMap<>();
	    
	    for (String mot : listeDeMots) {
	        Double occurrence = occurrenceMap.getOrDefault(mot, (double) 0);
	        occurrenceMap.put(mot, (double) (occurrence + 1));
	    }    
	    
	    return occurrenceMap;
	}
	
	public Map<String,Double> Frequences(List<String> listeDeMots) {
		
	    Map<String, Double> FrequenceMap = new HashMap<>();
	    int D = listeDeMots.size();

	    for (String mot : listeDeMots) {
	        Double occurrence = FrequenceMap.getOrDefault(mot, (double) 0);
	        FrequenceMap.put(mot, (double) (occurrence + 1));
	    }
	    
	    //Calculer le frequence
        for (String mot : FrequenceMap.keySet()) {
            double frequenceRelative = FrequenceMap.get(mot) / D;
            FrequenceMap.put(mot, frequenceRelative);	    	
	    }
	    

	    return FrequenceMap;
	}

	

	public Map<String, Map<String, Double>> TF() {
		
		Map<String, Map<String, Double> > TF_Finale = new HashMap<>();
		
	   for (Entry<String, List<String>> entry : MotsParFichier.entrySet()) {
        	
        	// Recuperer le nom de fichier et la liste des mots
        	String fileName = entry.getKey();
            List<String> mots = entry.getValue(); 
	        
	        TF_Finale.put(fileName,Frequences(mots));
	   
	   }
		
		return TF_Finale;
	}

	
	public Map<String, Double> IDF(){ 
		
		Map<String, Double> IDF = new HashMap<>();
				
		List<String> MotsTF = new ArrayList<String>();
		
		Fichier f = new Fichier();
				
		double D = f.NombreFichier();
		
						
		for (Map<String, Double> map : TF().values()) { // Extrait tout les mots de TF et le stocker dans une liste
			for (String mot : map.keySet()) {
				MotsTF.add(mot);
            }
			
		}
				
		IDF = Occurance(MotsTF);
		
		for(Entry<String, Double> entry : IDF.entrySet()) {
			
			String mot = entry.getKey();
			Double occ = entry.getValue();
			double idf = Math.log(D/occ);
			IDF.put(mot, idf);
			
		}

	    return IDF;
	}
	
	public Map<String, Map<String, Double>> TF_IDF(){
		
		Map<String, Map<String, Double> > TF = TF();
		Map<String, Double> IDF = IDF();
		
		double tfidf;
		Map<String, Map<String, Double> > TFIDF = new HashMap<>();
		
		for (Entry<String, Map<String, Double>> entry : TF.entrySet()) {
			Map<String, Double> temp = new HashMap<>();
			
        	String NomFichier = entry.getKey();
            Map<String, Double> TFF = entry.getValue();
            
            for (Entry<String, Double> en : TFF.entrySet()) {
            	String mot = en.getKey().toString();
            	Double tf = en.getValue();
            	
            	tfidf = tf * IDF.get(mot);
            	
            	temp.put(mot, tfidf);
            }
            
            TFIDF.put(NomFichier, temp);
            
		}
		
		return TFIDF;
		
	}
	
}

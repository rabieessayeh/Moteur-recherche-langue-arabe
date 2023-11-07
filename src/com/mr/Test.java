package com.mr;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Map.Entry;

public class Test {
	
	public Test() {
		
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
	
    public  double cosines(Map<String, Double> v1, Map<String, Double> v2) {
        double dotProduct = 0.0;
        double magnitudeV1 = 0.0;
        double magnitudeV2 = 0.0;
        
        

        for (String term : v1.keySet()) {
            if (v2.containsKey(term)) {
                dotProduct += v1.get(term) * v2.get(term);
            }
            magnitudeV1 += Math.pow(v1.get(term), 2);
        }

        for (String term : v2.keySet()) {
            magnitudeV2 += Math.pow(v2.get(term), 2);
        }

        if (magnitudeV1 == 0.0 || magnitudeV2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(magnitudeV1) * Math.sqrt(magnitudeV2));
    }
    
    public  Map<String,Double> ResultatFinale(Map<String, Map<String, Double>> tfIdfMap, Map<String, Double> query) {
    	Map<String,Double> fileNames = new HashMap();

        for (String fileName : tfIdfMap.keySet()) {
            Map<String, Double> fileVector = tfIdfMap.get(fileName);
            double similarity = cosines(query, fileVector);
            fileNames.put(fileName, similarity);

        }
        
        
     // Triez la liste en fonction des valeurs décroissantes
        
        List<Map.Entry<String, Double>> list = new ArrayList<>(fileNames.entrySet());
        
        Collections.sort(list, (entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));
        
        Map<String, Double> fileNameFinale = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
        	fileNameFinale.put(entry.getKey(), entry.getValue());
        }
        
        return fileNameFinale;
                
    }
    

	

	public void start() {
		
		System.out.println("Entrer votre texte : ");
		
		Scanner sc = new Scanner(System.in);
		String  txt = sc.nextLine();
		List<String> texte = new ArrayList<>();
		
		// Steming & Supprimer les stopwords
		Lemmatisation lm = new Lemmatisation(txt);
		texte = lm.Stemming();
		
		System.out.println("Resultat : ");
		
		Map requette = Frequences(texte);
		Map<String, Map<String, Double>> tfidf = XMLMap.loadMapFromXML("data/tfidf.xml");
		Map<String, Double> F = ResultatFinale(tfidf,requette );
		int i = 0;
		
		for (Entry<String, Double> f : F.entrySet()) {
			String file = f.getKey();
			Double cos = f.getValue();
			System.out.println(file+" == > "+cos);
			
			i++;
			if(i == 10) {
				break;
			}
			
		}
		
	}
}

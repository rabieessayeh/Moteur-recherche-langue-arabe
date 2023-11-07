package com.mr;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

class Main  {


	public static void main(String[] args) throws IOException {
				
		Scanner sc = new Scanner(System.in);
		
		System.out.println("0 pour reparer un TFIDF \n1 pour un test");
		
		int i = sc.nextInt();
		if(i == 0) {
			Process_TFIDF();
		}else{
			Test();
		}

	}
	
	public static void Process_TFIDF() {
		// Lire les fichiers*************************************************
		System.out.println("Preparer un TF_IDF");
		System.out.println("Chargement des fichiers ...");
		Fichier f = new Fichier(); 
	
		
		//Supprimer les stop words ******************************************
		System.out.println("Suppression des stopwords.....");
		StopWords sw = new StopWords(); 
		Map<String, String> NoStopWOrds = sw.Start(f.MotsParFichier());
		
		
		
		
		// Lemmatisation ****************************************************
		System.out.println("Stemming ......");
		Map<String, List<String> >Stemming = new HashMap<>();
		
		for (Entry<String, String> en : NoStopWOrds.entrySet()) {
        	String NomFichier = en.getKey().toString();
        	String mots = en.getValue();
  	
        	Lemmatisation lm = new Lemmatisation(mots);        	
        	Stemming.put(NomFichier, lm.Stemming()); 
         }
		
		
		// Indexation 
		
		Indexation in = new Indexation(Stemming);
		System.out.println("TFIDF ....");
		
		Map<String, Map<String, Double>> TFIDF = in.TF_IDF();
		
		// Enregistrer le TFIDF dans un fichier
		
		XMLMap.storeMapToXML(TFIDF, "data/tfidf.xml");		
		
		
		Map<String, Map<String, Double>> tfidf = XMLMap.loadMapFromXML("data/tfidf.xml");
		System.out.println("TFIDF sauvegarder dans data/tfidf.xml");
		
	}
	
	
	public static void Test() {
		System.out.println("------------Test--------------");
		Scanner sc = new Scanner(System.in);
		System.out.println("Exemple : \t يطالب في جميع الأحوال بأي مدة عند وجود ولد أو عدة أولاد من الزواج");
		
		boolean T = true;
		
		while (T == true) {
			Test t = new Test();
			t.start();
			
			System.out.println("1 => pour contunuer \t 0 pour exit ");
			int i = sc.nextInt();
			if(i == 0) {
				T =false;
				System.out.println("A bien tot ! ");
			}
			
		}
	}

}

package com.mr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fichier  {
	
	public String chemin;
	
	public Fichier() {
		this.chemin = "data\\نظام المعاشات العسكرية" ;	
	}
	
	public Fichier(String chemin) {
		this.chemin = chemin;
		}

	
	public  Map<String, ArrayList<String>> MotsParFichier() {
		
        File dossier = new File(chemin);
        File[] files = dossier.listFiles();
        
        
        Map<String, ArrayList<String>> fileContents = new HashMap<>();
        
        for (File file : files) {
            if (file.isFile()) {  //&& file.getName().endsWith(".txt")
            	
                String fileName = file.getName();
                ArrayList<String> words = new ArrayList<>();
                
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] lineWords = line.split("\\s+"); // Sépare les mots par espace(s)
                        for (String word : lineWords) {
                            words.add(word);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Erreur de lecture du fichier: " + fileName);
                    e.printStackTrace();
                }
                
                fileContents.put(fileName, words);
            }
        }
        
        return fileContents;
    }
	
	
    
    public  List<String> ExtractMotsFichier(File fichier) {
        List<String> mots = new ArrayList<>();
  
        try {
            BufferedReader lecteur = new BufferedReader(new FileReader(fichier));
            String ligne;

            while ((ligne = lecteur.readLine()) != null) {
         
                String[] motsLigne = ligne.split("\\s+");
                
                for (String mot : motsLigne) {
                    mots.add(mot);
                }
            }

            lecteur.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mots;
    }

	
	
public  List<String> MotsDossier() throws IOException {
		
        File dossier = new File(chemin);
        File[] files = dossier.listFiles();
        
        
        List<String> Mots = new ArrayList<String>();
        
        for (File file : files) {
              
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] lineWords = line.split("\\s+"); // Sépare les mots par espace(s)
                    for (String word : lineWords) {
                    	Mots.add(word);
                    }
                }
                
            } 
            
            
        }
        
        
        return Mots;
    }




public int NombreFichier() {
    File dossier = new File(chemin);
    File[] files = dossier.listFiles();
    
    return files.length; 
}
	
	
	
	
	
	
	
	
	
	
	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//    public  void AfficherDossier(File dossier) {
//        if (dossier.exists() && dossier.isDirectory()) {
//        	
//            File[] fichiers = dossier.listFiles();
//
//           
//            for (File fichier : fichiers) {
//                
//                if (fichier.isFile()) { // && fichier.getName().endsWith(".txt")
//                    
//                    System.out.println("Nom du fichier : " + fichier.getName());
//
//                    try {
//                        List<String> lignes = Files.readAllLines(fichier.toPath());
//                        for (String ligne : lignes) {
//                            System.out.println(ligne);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println();
//                }
//            }
//        } else {
//            System.err.println("Le dossier spécifié n'existe pas ou n'est pas un dossier.");
//        }
//    }
//    
//    
//
//    
//    public  List<String> ExtractMotsDossier() {
//    	File dossier = new File(chemin);
//        List<String> mots = new ArrayList<>();
//
//
//        File[] fichiers = dossier.listFiles();
//
//        for (File fichier : fichiers) {
//           
//            try {
//                BufferedReader lecteur = new BufferedReader(new FileReader(fichier));
//                String ligne;
//
//                while ((ligne = lecteur.readLine()) != null) {
//             
//                    String[] motsLigne = ligne.split("\\s+");
//                    
//                    for (String mot : motsLigne) {
//                        mots.add(mot);
//                    }
//                }
//
//                lecteur.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return mots;
//    }
//    

//    
//    public List<File> ListsFichiers(){
//    	File dossier = new File(chemin);
//    	
//    	List<File> files = new ArrayList<>();
//    	File[] fichiers = dossier.listFiles();
//    	
//    	for (File fichier : fichiers) {
//    		files.add(fichier);
//    	}
//    	
//    	return files;
//    }
//    

}
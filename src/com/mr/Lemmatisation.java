package com.mr;

import java.util.ArrayList;
import java.util.List;

import safar.basic.morphology.stemmer.factory.StemmerFactory;
import safar.basic.morphology.stemmer.interfaces.IStemmer;
import safar.basic.morphology.stemmer.model.WordStemmerAnalysis;

public class Lemmatisation  {
	
	String list ;
	
	public Lemmatisation(String list) {
		this.list = list;
	}
	
    public  List<String> Stemming() {
    	
    	List<String> ST = new ArrayList<String>();
    	IStemmer stemmer = StemmerFactory.getKhojaImplementation();
    	List<WordStemmerAnalysis> listResult = stemmer.stem(list);
    	for ( WordStemmerAnalysis sw : listResult ) {
    		String stm = sw.getListStemmerAnalysis().get(0).getMorpheme();
    		if(stm != null) {
    			ST.add(stm);
    		}
    	}
        
        return ST;	
    }

}

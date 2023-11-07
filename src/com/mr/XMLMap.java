package com.mr;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLMap {
	
    // Fonction pour stocker une carte dans un fichier XML
    public static void storeMapToXML(Map<String, Map<String, Double>> map, String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement("map");
            document.appendChild(rootElement);

            for (String file : map.keySet()) {
                Element fileElement = document.createElement("file");
                fileElement.setAttribute("name", file);
                rootElement.appendChild(fileElement);

                Map<String, Double> innerMap = map.get(file);
                for (String word : innerMap.keySet()) {
                    Element wordElement = document.createElement("word");
                    wordElement.setAttribute("name", word);
                    Double tfidf = innerMap.get(word);
                    
                    if(tfidf > 0.005) {
                    	
                    	wordElement.setTextContent(innerMap.get(word).toString());
                        fileElement.appendChild(wordElement);
                    }
                    
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fonction pour récupérer une carte depuis un fichier XML
    public static Map<String, Map<String, Double>> loadMapFromXML(String filePath) {
        Map<String, Map<String, Double>> map = new HashMap<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            NodeList fileNodes = document.getElementsByTagName("file");

            for (int i = 0; i < fileNodes.getLength(); i++) {
                Element fileElement = (Element) fileNodes.item(i);
                String file = fileElement.getAttribute("name");

                NodeList wordNodes = fileElement.getElementsByTagName("word");
                Map<String, Double> innerMap = new HashMap<>();

                for (int j = 0; j < wordNodes.getLength(); j++) {
                    Element wordElement = (Element) wordNodes.item(j);
                    String word = wordElement.getAttribute("name");
                    double tfidf = Double.parseDouble(wordElement.getTextContent());
                    innerMap.put(word, tfidf);
                }

                map.put(file, innerMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}

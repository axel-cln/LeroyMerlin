package com.example.leroymerlin.utils;

import android.content.Context;
import android.util.Log;

import com.example.leroymerlin.models.Ad;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLManager {

    /**
     * Permet de lire un fichier XML.
     * @param context Le contexte de l'application.
     * @param fileName Le nom du fichier à lire.
     */
    public static List<Ad> readFile(Context context, String fileName){

        List<Ad> listAds = new ArrayList<>();

        // Lecture du fichier
        try {
            InputStream is = null;
            try {
                is = context.openFileInput(fileName);
            }catch (FileNotFoundException e){
                try {
                    is = context.getAssets().open(fileName);
                }catch (FileNotFoundException e1){
                    Log.e("Leroy","Fichier non trouvé");
                }
            }

            // Lecture XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            // On récupère les offres
            NodeList ads = element.getElementsByTagName("ad");
            for (int i=0; i<ads.getLength(); i++){

                Element ad = (Element)ads.item(i);
                int id = Integer.parseInt(ad.getAttribute("id"));

                Ad advert = new Ad(id);

                String title = ad.getElementsByTagName("title").item(0).getTextContent();
                String description = ad.getElementsByTagName("description").item(0).getTextContent();

                advert.setTitle(title);
                advert.setDescription(description);

                NodeList tasks = ((Element)ad.getElementsByTagName("tasks").item(0)).getElementsByTagName("task");

                for (int j=0; j< tasks.getLength(); j++){
                    String taskText = tasks.item(j).getTextContent();
                    advert.addTask(taskText);
                }

                listAds.add(advert);

            }
        }catch (Exception e){
            Log.e("erreur", "impossible");
            e.printStackTrace();
        }

        return listAds;
    }
}

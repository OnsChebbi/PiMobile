/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entities.Event;
import com.mycompany.gui.ListEvent;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ComputerT
 */
public class ServiceEvent {
    
      public ArrayList<Event> events;
    
    public static ServiceEvent instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    String json;
  

    public ServiceEvent() {
         req = new ConnectionRequest();
    }

    public static ServiceEvent getInstance() {
        if (instance == null) {
            instance = new ServiceEvent();
        }
        return instance;
    }
     
    public void ajouterEvent(TextField nomE,int organisateur,String d1,TextField nb1,String d2,TextField nb2,ComboBox Domaines,TextArea description,String path,TextField prix,TextField lat,TextField lng)
    { 
        Double prixT=Double.parseDouble(prix.getText());
        String url = Statics.BASE_URL +"/AddEv?nom_e=" + nomE.getText()+ "&organisateur=" + organisateur+ "&date1=" + d1
        + "&nb1=" +nb1.getText()+ "&date2=" + d2 + "&nb2="+nb2.getText()
        +"&evenement_type=" + Domaines.getSelectedItem().toString()+ 
        "&description=" + description.getText()+ "&photo=" +path+ 
        "&prix="+prixT+"&lat=" + lat.getText()+ "&lng=" + lng.getText();
                
                ; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this); 
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    
      public void SupprimerEvent(int id)
    { 
        String url = Statics.BASE_URL + "/SupprimerEvent?id="+id;
   //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this); 
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println("supprimé avec succès.");
        
        
    }
      
  public ArrayList<Event>AffichageEvents(){
         ArrayList<Event> result=new ArrayList<>();
         String url = Statics.BASE_URL + "/ShowAllEvents";
         req.setUrl(url);
         
         req.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp =new JSONParser();
               //  UIBuilder.registerCustomComponent("Picker", Picker.class);
                try{
                    Map<String,Object>mapEvents = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapEvents.get("root");
                    for(Map<String ,Object> obj : listOfMaps){
                        Event ev = new Event();
                        float id =Float.parseFloat(obj.get("id").toString());
                        String nomE = obj.get("nomE").toString();
                        float org =Float.parseFloat(obj.get("organisateur").toString());
                        String date1 = obj.get("date1").toString();
                        float nb1 =Float.parseFloat(obj.get("nb1").toString());
                        String date2 = obj.get("date2").toString();
                        float nb2 =Float.parseFloat(obj.get("nb2").toString());
                        String type = obj.get("evenementType").toString();
                        String description = obj.get("description").toString();
                        String photo= obj.get("photo").toString();
                        double prix=Double.parseDouble(obj.get("prix").toString());
                        String lat=obj.get("lat").toString();
                        String lng=obj.get("lng").toString();

                           Container c =new Container(new BoxLayout(BoxLayout.Y_AXIS));
                          ev.setId((int)id);
                          ev.setNom_e(nomE);
                          ev.setOrganisateur((int)org);
                          ev.setDate1(date1);
                          ev.setNb1((int)nb1);
                          ev.setDate2(date2);
                          ev.setNb2((int)nb2);
                          ev.setEvenement_type(type);
                          ev.setDescription(description);
                          ev.setPhoto(photo);
                          ev.setPrix(prix);
                          ev.setLat(lat);
                          ev.setLng(lng);
                        
                         
                          
                   
                        result.add(ev);
                        
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                 }
             }
         });
          NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
     }
  
  public ArrayList<Event>RechercheEvent(String nomE){
         ArrayList<Event> result=new ArrayList<>();
         String url = Statics.BASE_URL + "/SearchEvents?searchkey="+nomE;
         req.setUrl(url);
         
         req.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp =new JSONParser();
               //  UIBuilder.registerCustomComponent("Picker", Picker.class);
                try{
                    Map<String,Object>mapEvents = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapEvents.get("root");
                    for(Map<String ,Object> obj : listOfMaps){
                        Event ev = new Event();
                        float id =Float.parseFloat(obj.get("id").toString());
                        String nomE = obj.get("nomE").toString();
                        float org =Float.parseFloat(obj.get("organisateur").toString());
                        String date1 = obj.get("date1").toString();
                        float nb1 =Float.parseFloat(obj.get("nb1").toString());
                        String date2 = obj.get("date2").toString();
                        float nb2 =Float.parseFloat(obj.get("nb2").toString());
                        String type = obj.get("evenementType").toString();
                        String description = obj.get("description").toString();
                        String photo= obj.get("photo").toString();
                        double prix=Double.parseDouble(obj.get("prix").toString());
                        String lat=obj.get("lat").toString();
                        String lng=obj.get("lng").toString();

                           Container c =new Container(new BoxLayout(BoxLayout.Y_AXIS));
                          ev.setId((int)id);
                          ev.setNom_e(nomE);
                          ev.setOrganisateur((int)org);
                          ev.setDate1(date1);
                          ev.setNb1((int)nb1);
                          ev.setDate2(date2);
                          ev.setNb2((int)nb2);
                          ev.setEvenement_type(type);
                          ev.setDescription(description);
                          ev.setPhoto(photo);
                          ev.setPrix(prix);
                          ev.setLat(lat);
                          ev.setLng(lng);
                        
                         
                          
                   
                        result.add(ev);
                        
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                 }
             }
         });
          NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
     }
    
}

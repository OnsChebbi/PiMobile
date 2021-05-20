/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import models.ReclamationP;
import utils.Statics;

/**
 *
 * @author a
 */
public class ServiceReclamationP {
    
    //var
    boolean resultOK;
    ConnectionRequest req;
    static ServiceReclamationP instance;
    ArrayList<ReclamationP> CompetitionssP = new ArrayList<>();
    
    
    //constructor
    private ServiceReclamationP() {
        req = new ConnectionRequest();
    }
    
    //SINGLETON
    public static ServiceReclamationP getInstance(){
        
        if (instance == null) {
            instance = new ServiceReclamationP();
        }
        
        return instance;
    }
    
    
    
    
    //ADD TASK 
    public boolean addReclamationAction(ReclamationP ec){
        
        String url = Statics.BASE_URL + "/competitions_p/"+ ec.getNom() + "/" + ec.getPrenom()+ "/" + ec.getEmail()+ "/" + ec.getTel()+ "/" + ec.getDescription()+ "/" + ec.getEtat();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener((evt) -> {
            resultOK = req.getResponseCode()==200;
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    
    
    //PARSE TASKS JSON : convert JSON to java objects
    public ArrayList<ReclamationP> parseJSONAction(String textJson){
        
        JSONParser j = new JSONParser();
        
        try {
            
            Map<String, Object> competitionssPListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
            ArrayList<Map<String,Object>> competitionssPList = (ArrayList<Map<String,Object>>) competitionssPListJson.get("root");
            
            for (Map<String, Object> obj : competitionssPList) {
                
                ReclamationP ec = new ReclamationP();
                float id = Float.parseFloat(obj.get("id").toString());
                ec.setId((int) id);
                ec.setNom(obj.get("nom").toString());
                ec.setPrenom(obj.get("prenom").toString());
                ec.setEmail(obj.get("email").toString());
                ec.setTel(obj.get("tel").toString());
                ec.setDescription(obj.get("description").toString());
                 ec.setEtat(obj.get("etat").toString());

                
                CompetitionssP.add(ec);

            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return CompetitionssP;  
    }



    //GET TASKS
    public ArrayList<ReclamationP> getCompetitions(){
        
         String url = Statics.BASE_URL+"/affichemobile";
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 
             CompetitionssP = parseJSONAction(new String(request.getResponseData()));
             request.removeResponseListener((ActionListener<NetworkEvent>) this);
             }
         });
         
        
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return CompetitionssP;
    }
    
    
    
    
    
    
    
    

}

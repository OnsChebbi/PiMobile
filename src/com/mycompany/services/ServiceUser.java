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
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.gui.NewsfeedForm;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 *
 * @author ComputerT
 */
public class ServiceUser {
       public ArrayList<User> users;
    
    public static ServiceUser instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    String json;


    private ServiceUser() {
         req = new ConnectionRequest();
    }

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }
    
    
    public void ajouterUser(User t)
    {
        String url = Statics.BASE_URL + "/Create?nom=" + t.getNom() + "&prenom=" + t.getPrenom()+ "&email=" + t.getEmail()
        + "&password=" + t.getPassword()+ "&domaine=" + t.getDomaine()+ "&type=" + t.getType(); //création de l'URL
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
    
    
    
    public void SignUp(TextField nom,TextField prenom,TextField email,TextField password,ComboBox<String> domaine,ComboBox<String> type,Resources res)
    {
        String url = Statics.BASE_URL + "/user/signup?nom=" + nom.getText() + 
        "&prenom=" + prenom.getText()+ "&email=" + email.getText()
        + "&password=" +password.getText().toString()+ 
        "&domaine=" +domaine.getSelectedItem().toString()+ "&type=" + type.getSelectedItem().toString(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        if ((nom.getText().length()==0)||(prenom.getText().length()==0)||(email.getText().length()==0)
                        ||(password.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
        
        req.addResponseListener(e ->{
        
        byte[]data= (byte[])e.getMetaData(); //awel haja nhadherha metadata bch nekhou l id taa l forms
        String responseData= new String(data);// nekhou l content mtaa l forms
            System.out.println("Data ===>"+responseData);
        
        });
        
        //execution de la requete w nestanew l reponse taa serveur
        NetworkManager.getInstance().addToQueueAndWait(req);

      
    }
    
    
   
    public void SignIn(TextField email,TextField password,Resources res)
    {
        String url = Statics.BASE_URL + "/user/signin?email=" +email.getText()
        + "&password=" + password.getText().toString(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(e -> {
          
            JSONParser j = new JSONParser();
            String json= new String(req.getResponseData())+"";
           try {
               if (json.equals("failed"))
               {
                   Dialog.show("Echec d'authentification","Email ou mot de passe incorrect", "Ok",null);
               }
               else
               {
                   System.out.println("Data =====>"+json);
                   Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                   if (user.size()>0)
                   {
                       new NewsfeedForm(res).show();
                   }
      
               }
           } catch (IOException ex) {  
               ex.printStackTrace();
            }           
          
        });
    
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public String getPasswordByEmail(String email, Resources rs){
            
            String url = Statics.BASE_URL + "/getPasswordByEmail?email=" +email;   //création de l'URL
            req.setUrl(url);// Insertion de l'URL de notre demande de connexion
         
            req.addResponseListener(e -> {
          
            JSONParser j = new JSONParser();
            json= new String(req.getResponseData())+"";
            
             try {
                   System.out.println("Data =====>"+json);
                   Map<String,Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));
                   
              
               }
            catch (IOException ex) {  
               ex.printStackTrace();
            }           
          
         });
          NetworkManager.getInstance().addToQueueAndWait(req);

        
        return json;
        
        
        
    }
    
    
    
    
    
     public ArrayList<User> parseUsers(String jsonText){
        try {
           users=new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String,Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
           
            List<Map<String,Object>> list = (List<Map<String,Object>>)usersListJson.get("root");
            for(Map<String,Object> obj : list){
                User t = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setNom(obj.get("nom").toString());
                t.setPrenom(obj.get("prenom").toString());
                t.setEmail(obj.get("email").toString());
                t.setDomaine(obj.get("domaine").toString());
                t.setType(obj.get("type").toString());
                
                users.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }

        return users;
    }
    
    public ArrayList<User> afficherUser()
    {
        String url = Statics.BASE_URL+"/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    
    }
    
    
}

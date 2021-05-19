/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Event;
import com.mycompany.services.ServiceEvent;
import com.mycompany.services.ServiceUser;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author ComputerT
 */
public class AddEventForm extends BaseForm {

    String path = "";
    public AddEventForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
                
        TextField nomE = new TextField("", "Nom Event", 20, TextField.ANY);
        Label org=new Label();
        Picker datePicker1 = new Picker();
        datePicker1.setType(Display.PICKER_TYPE_DATE);
        datePicker1.setUIID("TextFieldBlack");
        TextField nb1 = new TextField("", "Nombre de places à la d1", 20, TextField.ANY);
        Picker datePicker2 = new Picker();
        datePicker2.setType(Display.PICKER_TYPE_DATE);
        datePicker2.setUIID("TextFieldBlack");
        TextField nb2 = new TextField("", "Nombre de places à la d2", 20, TextField.ANY);
        Vector<String> VectorDomaine;
        VectorDomaine= new Vector();
        VectorDomaine.add("Peinture");
        VectorDomaine.add("Photographie");
        VectorDomaine.add("Cinéma");
        VectorDomaine.add("Cuisine");
        VectorDomaine.add("Musique");
        ComboBox<String> Domaines= new ComboBox<>(VectorDomaine);
        TextArea description = new TextField("", "Description de l'event",100 , TextField.ANY);
        TextField prix = new TextField("", "prix ticket", 20, TextField.ANY);
        TextField lat = new TextField("", "latitude", 20, TextField.NUMERIC);
        TextField lng=  new TextField("", "longtitude", 20, TextField.NUMERIC);
        Button capture= new Button("Capture");
        Label lblImage=new Label();
        

        Button next = new Button("Ajouter");
        Button liste = new Button("Allez vers la liste des events");
        liste.addActionListener(e -> new SignInForm(res).show());
        liste.setUIID("Link");
        
        Container content = BoxLayout.encloseY(
                new Label("Ajouter", "LogoLabel"),
                new FloatingHint(nomE),
                createLineSeparator(),
                datePicker1,
                new FloatingHint(nb1),
                createLineSeparator(),
                datePicker2,
                new FloatingHint(nb2),
                createLineSeparator(),
                Domaines,
                new FloatingHint(description),
                createLineSeparator(),
                capture,
                lblImage,
                prix,
                lat,
                lng
              
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(liste)
        ));
          capture.addActionListener(e ->{
           path= Capture.capturePhoto(Display.getInstance().getDisplayWidth(),-1);
           if (path != null)
           {
               try {
                   Image img = Image.createImage(path);
                   lblImage.setIcon(img);
                   revalidate();
                   System.out.println(path);
                   
                   
               } catch (IOException ex) {
                   ex.printStackTrace();
               }
           }
        } 
        );
        next.requestFocus();
        next.addActionListener(e ->{
                               System.out.println(path);
            
            SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");  
            format.format(datePicker1.getDate());
            Event ev= new Event(String.valueOf(nomE.getText().toString()),
                    Integer.valueOf(SessionManager.getId()),
                    format.format(datePicker1.getDate()),
                    Integer.valueOf(Integer.parseInt(nb1.getText())),
                    format.format(datePicker2.getDate()),
                    Integer.valueOf(Integer.parseInt(nb2.getText())),
                    String.valueOf(Domaines.getSelectedItem().toString()),
                    String.valueOf(description.getText().toString()),
                    String.valueOf(path),
                    Double.valueOf(Double.parseDouble(prix.getText())), 
                    String.valueOf(lat.toString()),                    
                    String.valueOf(lng.toString())
                      );
                    String d1=format.format(datePicker1.getDate());
                    String d2=format.format(datePicker2.getDate());
            
        ServiceEvent.getInstance().ajouterEvent(nomE,SessionManager.getId(),d1,nb1,d2,nb2,Domaines,description,path,prix,lat,lng);
        Dialog.show("Success","Event ajouté!","OK",null);
            try {
                new ListEvent(previous,res).show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } 
        );
    }    
    /* private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }*/
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.mycompany.entities.Event;
import com.mycompany.services.ServiceEvent;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.util.ArrayList;

//import static java.awt.SystemColor.text;
/**
 *
 * @author ComputerT
 */

public class ListEvent extends BaseForm{

        private Form f,current;
        private Resources theme;
        private Resources themelabel;
        private UIBuilder uiBuilder;
        SpanLabel lb;
        
    public ListEvent(Form previous,Resources theme) throws IOException{
        current=this;
        this.theme = theme;
          this.setLayout(BoxLayout.y());
        Container ct4 = new Container(BoxLayout.x());
      //  setUIID("SignIn");
//        EncodedImage enc = EncodedImage.create("backgrounduser.jpg");
      //  Image image = URLImage.createToStorage(enc, "packgo.png", "http://localhost/offres/image/packgo.jpg");
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        add(ct4);
        Component[] result = new Component[50];
        ServiceEvent sp = new ServiceEvent();
         Toolbar.setGlobalToolbar(true);
        add(new InfiniteProgress());
        
                Display.getInstance().scheduleBackgroundTask(()-> {
                    // this will take a while...
                    Display.getInstance().callSerially(() -> {
                        removeAll();
                        ArrayList<Event> listepays= sp.AffichageEvents();
                        for(Event p : listepays)
                        {
                            Container ct = new Container(BoxLayout.y());  
                            ct.setUIID("SignIn");
                            MultiButton m = new MultiButton();
                            m.setTextLine1("Nom:"+p.getNom_e());
                            m.setTextLine2("Description:"+p.getDescription());
                            m.setTextLine3("Prix:"+p.getPrix());
                            add(m);
                            m.addPointerReleasedListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent evt) {
                                      int id=0;
                                        if (Dialog.show("Confirmation", "Que voulez vous faire ?", "Supprimer", "Modifier")) {
                                             id= p.getId();
                                           ServiceEvent.getInstance().SupprimerEvent(id);
                                          try {
                                              new ListEvent(previous,theme).show();
                                          } catch (IOException ex) {
                                           ex.printStackTrace();
                                          }
                                           Dialog.show("Success","supprimer",new Command("OK"));
                                                   

                                    }
                                        else{
                                                 new UpdateEventForm(current,id).show();
                                        }
                                    }
                                });
                        }
                getToolbar().addSearchCommand(e -> {
    String text = (String)e.getSource();
    if(text == null || text.length() == 0) {
        // clear search
        for(Component cmp : getContentPane()) {
            cmp.setHidden(false);
            cmp.setVisible(true);
        }
        getContentPane().animateLayout(150);
    } else {
        text = text.toLowerCase();
        for(Component cmp : getContentPane()) {
            MultiButton mb = (MultiButton)cmp;
            String line1 = mb.getTextLine1();
            String line2 = mb.getTextLine2();
            boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
            line2 != null && line2.toLowerCase().indexOf(text) > -1;
            mb.setHidden(!show);
            mb.setVisible(show);
        }
        getContentPane().animateLayout(150);
    }
        }, 4);
                        
        
        
      
        setTitle("liste des events.");
     //   getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK,e->previous.showBack());

    });
    
    
    
                });
    }
}
                       

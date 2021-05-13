/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.User;
import com.mycompany.services.ServiceUser;

/**
 *
 * @author ComputerT
 */
public class AddUserForm extends BaseForm{
    
    Form current;
    
    
    
    
     public AddUserForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current =this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter utilisateur");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e ->{
        
        
        
        
        });
        
        Tabs swipe=new Tabs();
        Label s1=new Label();
        Label s2=new Label();
        addTab(swipe,s1,res.getImage("back.png"),"","",res);
        //
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg=new ButtonGroup();
        int size=Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size,size,0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0,0,size,size,0,360);
        Image selectedWalkthru = Image.createImage(size,size,0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0,0,size,size,0,360);
RadioButton[] rbs= new RadioButton[swipe.getTabCount()];
FlowLayout flow= new FlowLayout(CENTER);
flow.setValign(BOTTOM);
Container radioContainer = new Container(flow);
for (int iter=0; iter< rbs.length; iter++)
{
rbs[iter]=RadioButton.createToggle(unselectedWalkthru, bg);
rbs[iter].setPressedIcon(selectedWalkthru);
rbs[iter].setUIID("Label");
radioContainer.add(rbs[iter]);
}
rbs[0].setSelected(true);
swipe.addSelectionListener((i, ii) -> {
if (!rbs[ii].isSelected())
{
rbs[ii].setSelected(true);
}

});

Component.setSameSize(radioContainer,s1, s2);
add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup= new ButtonGroup();
RadioButton mesListes= RadioButton.createToggle("Users",barGroup);
mesListes.setUIID("SelectedBar");
RadioButton listes= RadioButton.createToggle("Autres",barGroup);
listes.setUIID("SelectedBar");
RadioButton add= RadioButton.createToggle("Ajouter",barGroup);
add.setUIID("SelectedBar");
        
    Label arrow= new Label(res.getImage("news-tab-down-arrow.png"),"Container");


mesListes.addActionListener(e ->
{
InfiniteProgress ip=new InfiniteProgress();
final Dialog ipDig= ip.showInifiniteBlocking();
    refreshTheme();
});
        add(LayeredLayout.encloseIn(
        GridLayout.encloseIn(3, mesListes,listes,add),
        FlowLayout.encloseBottom(arrow)
        ));
        //
        add.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e->{
            arrow.setVisible(true);
            updateArrowPosition(add,arrow);
        });
        
         bindButtonSelection(mesListes,arrow);
         bindButtonSelection(listes,arrow);
         bindButtonSelection(add,arrow);
         
         addOrientationListener(e->{
         
             updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()),arrow);
         
         });


        TextField nom = new TextField("", "Entrer Nom", 20, TextField.ANY);
        nom.setUIID("TextFieldBlack");
        addStringValue("Nom",nom);
        TextField prenom = new TextField("", "Entrer Prénom", 20, TextField.ANY);
        prenom.setUIID("TextFieldBlack");
        addStringValue("Prénom",prenom);
        TextField email = new TextField("", "Entrer E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("E-mail",email);
        TextField password = new TextField("", "Entrer Password", 20, TextField.PASSWORD);
        password.setUIID("TextFieldBlack");
        addStringValue("Password",password);
        TextField domaine = new TextField("", "Entrer Domaine", 20, TextField.ANY);
        domaine.setUIID("TextFieldBlack");
        addStringValue("Domaine",domaine);
        TextField type = new TextField("", "Entrer Type", 20, TextField.ANY);
        type.setUIID("TextFieldBlack");
        addStringValue("Type",type);
        

        Button ajouter = new Button("Ajouter");
        addStringValue("",ajouter);

        ajouter.addActionListener((e) -> {
            try {
              if ((nom.getText().length()==0)||(prenom.getText().length()==0)||(email.getText().length()==0)
                        ||(password.getText().length()==0)||(domaine.getText().length()==0)||(type.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
               else
              {
                  InfiniteProgress ip= new InfiniteProgress();
                  final Dialog iDialog= ip.showInfiniteBlocking();
                  User u=new User(String.valueOf(nom.getText()).toString(),
                  String.valueOf(prenom.getText()).toString(),
                  String.valueOf(email.getText()).toString(),
                  String.valueOf(password.getText()).toString(),
                  String.valueOf(domaine.getText()).toString(),
                  String.valueOf(type.getText()).toString());
                  System.out.println("data user"+u);

                  ServiceUser.getInstance().ajouterUser(u);
                  iDialog.dispose();
                  refreshTheme();
                 
              }
            }
            catch(Exception Ex){
                Ex.printStackTrace();
            }
           
        });
     }
     
     
     private void addStringValue(String s,Component v)
     {
        add(BorderLayout.west(new Label(s,"FaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
        
     }

    private void addTab(Tabs swipe,Label spacer, Image image, String string, String text, Resources res) {
        
        int size= Math.min(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayWidth());
        
        if(image.getHeight() < size)
        {
            image=image.scaledHeight(size);
        }
        
        if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2)
        {
            image=image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
            
        }
        ScaleImageLabel imageScale= new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label OverLay = new Label("","ImageOverLay");
        
        
        Container page1=
                LayeredLayout.encloseIn(
                        imageScale,
                        OverLay,
                        BorderLayout.south(
                        BoxLayout.encloseY(
                        new SpanLabel(text, "LargeWhiteText"),spacer
                        )
                        )
                
                
                );
        
        swipe.addTab("",res.getImage("back.png"),page1);
        
                
    }
    
    public void bindButtonSelection(Button btn, Label l)
    {
        btn.addActionListener(e -> { 
        if(btn.isSelected())
        {
            updateArrowPosition(btn,l);
        }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX()+ btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }
}

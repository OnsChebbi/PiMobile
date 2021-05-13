/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author ComputerT
 */
public class VerifyCodeForm extends BaseForm {
TextField CodeField;

    public VerifyCodeForm(Resources res,int RandomNumber) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        //setUIID("Activate");
        
        add(BorderLayout.NORTH, 
                BoxLayout.encloseY(
                        new Label(res.getImage("smily.png"), "LogoLabel"),
                        new Label("Awsome Thanks!", "LogoLabel")
                )
        );
        
        
         CodeField= new TextField("","Entrez le code reçu par mail",20,TextField.ANY);
         CodeField.setSingleLineTextArea(false);
         Button valider = new Button("Valider");
         Label alreadHaveAnAccount = new Label("Already have an account?");
         Button signIn=new Button("Se Connecter");
         signIn.addActionListener(e -> previous.showBack());
         signIn.setUIID("CenterLink");
          Container content = BoxLayout.encloseY(
                new Label(res.getImage("oublier.png"),"CenterLabel"),
                new FloatingHint(CodeField),
                createLineSeparator(),
                valider,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        valider.requestFocus();
        valider.addActionListener(e -> {
            try {
                System.out.println(RandomNumber);
                InfiniteProgress ip= new InfiniteProgress();
                final Dialog ipDialog=ip.showInfiniteBlocking();
                int codef=Integer.parseInt(CodeField.getText());
                if(codef==RandomNumber)
                {
                ipDialog.dispose();
                Dialog.show("Succès!","Vous devez maintenant choisir un nouveau mot de passe","Ok",null);
                new SignInForm(res).show();
                }
                else
                {
                ipDialog.dispose();
                Dialog.show("OOps!","Code incorrect, vous devez repetez l'opération","Ok",null);
                new ActivateForm(res).show();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        });
    }
    
}
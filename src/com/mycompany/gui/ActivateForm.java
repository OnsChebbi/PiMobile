/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
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
import com.mycompany.services.ServiceUser;
import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Account activation UI
 *
 * @author Shai Almog
 */
public class ActivateForm extends BaseForm {
TextField email;
String FROM="artistickreativ@gmail.com";
                
final String fromEmail = "artistickreativ@gmail.com"; //requires valid gmail id
final String password = "kreativ123456"; // correct password for gmail id
    public ActivateForm(Resources res) {
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
        
        
        email= new TextField("","Saisir votre adresse email.",20,TextField.ANY);
        email.setSingleLineTextArea(false);
         Button valider = new Button("Valider");
         Label alreadHaveAnAccount = new Label("Already have an account?");
         Button signIn=new Button("Se Connecter");
         signIn.addActionListener(e -> previous.showBack());
         signIn.setUIID("CenterLink");
          Container content = BoxLayout.encloseY(
                new Label(res.getImage("oublier.png"),"CenterLabel"),
                new FloatingHint(email),
                createLineSeparator(),
                valider,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        valider.requestFocus();
        valider.addActionListener(e -> {
            try {
                InfiniteProgress ip= new InfiniteProgress();
                final Dialog ipDialog=ip.showInfiniteBlocking();
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
               @Override			
                         protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		MimeMessage msg = new MimeMessage(session);
	      //set message headers
	        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	        msg.addHeader("format", "flowed");
	        msg.addHeader("Content-Transfer-Encoding", "8bit");

	        msg.setFrom(new InternetAddress("artistickreativ@gmail.com", "Kreativ"));

	        msg.setReplyTo(InternetAddress.parse("artistickreativ@gmail.com", false));

	        msg.setSubject("Réinitialisation de votre mot de passe", "UTF-8");
              
                
             Random rand = new Random();
             int RandomNumber;
             RandomNumber= rand.nextInt(10000000);         
              
	        msg.setText("Bonjour, veuillez entrer ce code : "+RandomNumber+" Pour pouvoir"
                        + "réinitialiser votre compte.", "UTF-8");

	        msg.setSentDate(new Date());
                     
            
	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getText().toString(), false));
	        System.out.println("Message is ready");
    	        Transport.send(msg);  

	        System.out.println("EMail Sent Successfully!!");
                ipDialog.dispose();
                Dialog.show("Mot de passe","Nous avons envoyé votre mdp par e-mail","Ok",null);
                new VerifyCodeForm(res,RandomNumber).show();
                refreshTheme();
        }
             catch (Exception ex) {
                ex.printStackTrace();
            }
            
        });
    }
 /*   
    
    public void SendMail(Resources rs,String toEmail) throws MessagingException{
                final String fromEmail = "artistickreativ@gmail.com"; //requires valid gmail id
		final String password = "kreativ123456"; // correct password for gmail id
        try{
           
		
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
               @Override			
                         protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress("artistickreativ@gmail.com", "Kreativ"));

	      msg.setReplyTo(InternetAddress.parse("artistickreativ@gmail.com", false));

	      msg.setSubject("Réinitialisation de votre mot de passe", "UTF-8");
              
              
	      msg.setText("Bonjour", "UTF-8");

	      msg.setSentDate(new Date());
             Random rand = new Random();
             int RandomNumber;
             RandomNumber= rand.nextInt(10000000);              
             String body="Bonjour, Vous trouverez ci dessous un code que vous"
                      + "devez entrer pour pouvoir se connecter de nouveau /n /n"
                      + RandomNumber ;
	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	      Transport.send(msg);  

	      System.out.println("EMail Sent Successfully!!");
		EmailUtil.sendEmail(session, toEmail,msg.getSubject(), body);
                 //System.out.println("Server Response:"+st.getLastServerResponse());
            
                 
         }
        catch (Exception ex) {  
               ex.printStackTrace();
            } 
    }*/
}

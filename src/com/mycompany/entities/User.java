/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author ComputerT
 */
public class User {
    private int id;
    private String nom,prenom,email,password,domaine,type,photo;


    private String etat="on";
    private boolean is_verified=false;

        public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public boolean getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public User(String nom, String prenom, String email, String password, String domaine, String type, String photo) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.domaine = domaine;
        this.type = type;
        this.photo = photo;
    }

    public User(String nom, String prenom, String email, String password, String domaine, String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.domaine = domaine;
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", password=" + password + ", domaine=" + domaine + ", type=" + type + ", photo=" + photo + ", etat=" + etat + ", is_verified=" + is_verified + '}';
    }

   

    
    
    
}

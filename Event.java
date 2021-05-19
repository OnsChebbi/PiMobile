/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author ComputerT
 */
public class Event {
   int id;
   String nom_e;
   int organisateur;
   String date1;
   int nb1;
   String date2;
   int nb2;
   String evenement_type;
   String description;
   String photo;
   Double prix;
   String lat=null;
   String lng=null;

    public Event() {
    }

    public Event(int id) {
        this.id = id;
    }

   
    public Event(String valueOf, Integer valueOf0, String format, Integer valueOf1, String format0, Integer valueOf2, String valueOf3, String valueOf4, Integer valueOf5) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", nom_e=" + nom_e + ", organisateur=" + organisateur + ", date1=" + date1 + ", nb1=" + nb1 + ", date2=" + date2 + ", nb2=" + nb2 + ", evenement_type=" + evenement_type + ", description=" + description + ", photo=" + photo + ", prix=" + prix + ", lat=" + lat + ", lng=" + lng + '}';
    }

   
   
   
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

   
   
   
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_e() {
        return nom_e;
    }

    public void setNom_e(String nom_e) {
        this.nom_e = nom_e;
    }

    public int getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(int organisateur) {
        this.organisateur = organisateur;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public int getNb1() {
        return nb1;
    }

    public void setNb1(int nb1) {
        this.nb1 = nb1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public int getNb2() {
        return nb2;
    }

    public void setNb2(int nb2) {
        this.nb2 = nb2;
    }

    public String getEvenement_type() {
        return evenement_type;
    }

    public void setEvenement_type(String evenement_type) {
        this.evenement_type = evenement_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

   
   
   
   
    public Event(String nom_e, int organisateur, String date1, int nb1, String date2, int nb2, String evenement_type, String description, String photo, Double prix, String lat, String lng) {
        this.nom_e = nom_e;
        this.organisateur = organisateur;
        this.date1 = date1;
        this.nb1 = nb1;
        this.date2 = date2;
        this.nb2 = nb2;
        this.evenement_type = evenement_type;
        this.description = description;
        this.photo = photo;
        this.prix = prix;
        this.lat = lat;
        this.lng = lng;
    }

    public Event(int id, String nom_e, int organisateur, String date1, int nb1, String date2, int nb2, String evenement_type, String description, String photo, Double prix, String lat, String lng) {
        this.id = id;
        this.nom_e = nom_e;
        this.organisateur = organisateur;
        this.date1 = date1;
        this.nb1 = nb1;
        this.date2 = date2;
        this.nb2 = nb2;
        this.evenement_type = evenement_type;
        this.description = description;
        this.photo = photo;
        this.prix = prix;
        this.lat = lat;
        this.lng = lng;
    }


   
}

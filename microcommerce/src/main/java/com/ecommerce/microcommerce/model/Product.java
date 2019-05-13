package com.ecommerce.microcommerce.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFilter;

//import com.fasterxml.jackson.annotation.JsonFilter;

//@JsonIgnoreProperties(value= {"prixAchat", "id"})
@JsonFilter("monFiltreDynamique")
@Entity
public class Product {
	@Id
	@GeneratedValue
	private int id;
	@Length(min = 3, max = 20, message = "ici votre message")
	private String nom;
	private int prix;

	// a ne pas afficher
	private int prixAchat;
	private int marge;

	public Product() {
		super();
	}

	public Product(int id, String nom, int prix, int prixAchat, int marge) {
		super();
		this.id = id;
		this.nom = nom;
		this.prix = prix;
		this.prixAchat = prixAchat;
		this.marge = prix-prixAchat;
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

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public int getPrixAchat() {
		return prixAchat;
	}

	public void setPrixAchat(int prixAchat) {
		this.prixAchat = prixAchat;
	}

	public int getMarge() {
		return marge;
	}
	public void setMarge(int marge) {
		this.marge = marge;
	}
}

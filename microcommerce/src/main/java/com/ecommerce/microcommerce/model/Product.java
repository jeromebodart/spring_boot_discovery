package com.ecommerce.microcommerce.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@NotEmpty
	private String nom;
	@Min(0)
	private int prix;

	// a ne pas afficher
	@Min(0)
	private int prixAchat;
	private int marge;

	public Product() {
		super();
	}
	
	public Product(int id, @Length(min = 3, max = 20, message = "ici votre message") @NotEmpty String nom,
			@Min(0) int prix, @Min(0) int prixAchat) {
		super();
		this.id = id;
		this.nom = nom;
		this.prix = prix;
		this.prixAchat = prixAchat;
		this.marge = prix - prixAchat;
	}

	
	public Product(int id, @Length(min = 3, max = 20, message = "ici votre message") @NotEmpty String nom,
			@Min(0) int prix, @Min(0) int prixAchat, int marge) {
		super();
		this.id = id;
		this.nom = nom;
		this.prix = prix;
		this.prixAchat = prixAchat;
		this.marge = marge;
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

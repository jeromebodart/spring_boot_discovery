package com.ecommerce.microcommerce.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

public class ProduitUser {


	private int id;
	private String nom;
	private int prix;



	//constructeur par défaut
	public ProduitUser() {
	}

	//constructeur pour nos tests
	public ProduitUser(Product prod) {
		this.id = prod.getId();
		this.nom = prod.getNom();
		this.prix = prod.getPrix();

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

	@Override
	public String toString() {
		return "id=" + id + ", nom=" + nom + ", prix=" + prix;
	}

	

}

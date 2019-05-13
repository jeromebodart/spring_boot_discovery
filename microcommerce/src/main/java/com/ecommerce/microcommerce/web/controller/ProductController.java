package com.ecommerce.microcommerce.web.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.exceptions.ProduitIntrouvableException;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.model.ProduitUser;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Gestion des produits")
@RestController
public class ProductController {

	@Autowired
	private ProductDao productDao;
	SimpleBeanPropertyFilter monFiltre;
	//	@RequestMapping(value = "/Produits", method = RequestMethod.GET)
	//	public List<Product> listeProduits() {
	//		monFiltre =
	//				SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
	//		return productDao.findAll();
	//	}

	@GetMapping(value = "/AdminLesProduits") 
	public MappingJacksonValue listeProduits() {
		Iterable<Product> produits = productDao.findAll();
		monFiltre = SimpleBeanPropertyFilter.serializeAllExcept(Collections.<String>emptySet());
		FilterProvider listeDeNosFiltres = new
				SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
		produitsFiltres.setFilters(listeDeNosFiltres);
		return produitsFiltres;
	} 

	@RequestMapping(value = "/Produits", method = RequestMethod.GET)
	public MappingJacksonValue listeProduitsfiltre() {
		Iterable<Product> produits = productDao.findAll();
		monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "marge");
		FilterProvider listeDeNosFiltres = new
				SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
		produitsFiltres.setFilters(listeDeNosFiltres);
		return produitsFiltres;
	}

	@RequestMapping(value = "/ProduitsOrdonnnerParNom", method = RequestMethod.GET)
	public MappingJacksonValue trierProduitsParOrdreAlphabetique () {
		List<Product> produits = productDao.findAllByOrderByNomAsc();
		monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("marge");
		FilterProvider listeDeNosFiltres = new
				SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
		produitsFiltres.setFilters(listeDeNosFiltres);
		return produitsFiltres;
	}


	@GetMapping(value = "/AdminProduits") 
	public HashMap<String, Integer> calculerMargeProduit() { 
		HashMap<String, Integer>  product_marges = new HashMap<String, Integer>();  
		ArrayList<Product> products = (ArrayList<Product>) productDao.findAll();
		ArrayList<ProduitUser> productsusers = new ArrayList<>();
		for (Product product : products) {
			productsusers.add((new ProduitUser(product)));
		}
		for (int i=0; i < productsusers.size(); i++) { 
			product_marges.put(productsusers.get(i).toString(), products.get(i).getMarge()); 
		}  
		return product_marges; 
	} 

	///Produits/{id}
	@ApiOperation(value = "Récupère un produit selon son ID")
	@GetMapping(value = "/Produits/{id}")
	public MappingJacksonValue afficherUnProduit(@PathVariable int id) throws ProduitIntrouvableException {
		Product product = productDao.findById(id);
		if(product == null) throw new ProduitIntrouvableException("Le produit avec l'id "+ id+" n'existe pas");
		monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "marge");
		FilterProvider listeDeNosFiltres = new
				SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(product);
		produitsFiltres.setFilters(listeDeNosFiltres);
		return produitsFiltres;
	}

	@PostMapping(value = "/Produits")
	public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {
		Product product1 = productDao.save(product);
		if (product == null) {
			return ResponseEntity.noContent().build();
		}
		if(product.getPrix() == 0) throw new ProduitGratuitException("Le prix du produit doit être strictement supérieure à 0 voyons!");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{:id}").buildAndExpand(product1.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(value = "/Produits/{id}")
	public void supprimerProduit(@PathVariable int id) {
		productDao.deleteById(id);
	}

	@GetMapping(value = "/test2/produits/{recherche}")
	public MappingJacksonValue testeDeRequetes(@PathVariable String recherche) {
		Iterable<Product> produits = productDao.findByNomLike("%" + recherche + "%");
		monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "marge");
		FilterProvider listeDeNosFiltres = new
				SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
		produitsFiltres.setFilters(listeDeNosFiltres);
		return produitsFiltres;
	}
	


	@GetMapping(value = "/test/produits/{prixLimit}") 
	public MappingJacksonValue testeDeRequetes(@PathVariable int prixLimit) { 
		Iterable<Product> produits = productDao.findByPrixGreaterThan(prixLimit);
		monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "marge");
		FilterProvider listeDeNosFiltres = new
				SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
		produitsFiltres.setFilters(listeDeNosFiltres);
		return produitsFiltres;
	}

}
package com.ecommerce.microcommerce.web.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.ecommerce.microcommerce.exceptions.ProduitIntrouvableException;
import com.ecommerce.microcommerce.model.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Gestion des produits")
@RestController
public class ProductController {

	@Autowired
	private ProductDao productDao;

	@RequestMapping(value = "/Produits", method = RequestMethod.GET)
	public List<Product> listeProduits() {
		return productDao.findAll();
	}
	// public MappingJacksonValue listeProduits() {
	// List<Product> produits = productDao.findAll();
	// SimpleBeanPropertyFilter monFiltre =
	// SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
	// FilterProvider listeDeNosFiltres = new
	// SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
	// MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
	// produitsFiltres.setFilters(listeDeNosFiltres);
	// return produitsFiltres;
	// }
	
	///Produits/{id}
	@ApiOperation(value = "Récupère un produit selon son ID")
	@GetMapping(value = "/Produits/{id}")
	public Product afficherUnProduit(@PathVariable int id) throws ProduitIntrouvableException {
		Product product = productDao.findById(id);
		if(product == null) throw new ProduitIntrouvableException("Le produit avec l'id "+ id+" n'existe pas");
		return product;
	}

	@PostMapping(value = "/Produits")
	public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {
		Product product1 = productDao.save(product);
		if (product == null) {
			return ResponseEntity.noContent().build();
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{:id}").buildAndExpand(product1.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(value = "/Produits/{id}")
	public void supprimerProduit(@PathVariable int id) {
		productDao.deleteById(id);
	}

	@GetMapping(value = "/test2/produits/{recherche}")
	public List<Product> testeDeRequetes(@PathVariable String recherche) {
		return productDao.findByNomLike("%" + recherche + "%");
	}

	
	  @GetMapping(value = "/test/produits/{prixLimit}") public List<Product>
	  testeDeRequetes(@PathVariable int prixLimit) { return
	  productDao.findByPrixGreaterThan(prixLimit); }
	 
}
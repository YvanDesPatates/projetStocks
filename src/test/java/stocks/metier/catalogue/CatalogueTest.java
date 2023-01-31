package stocks.metier.catalogue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stocks.metier.produit.I_Produit;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogueTest {
    Catalogue catalogue;

    @BeforeEach
    public void baseCatalogue(){
        I_Produit soupiere; //mettre 4 à 5€
        I_Produit pompom; //mettre 10 à 3,5€
        I_Produit percolateur; // mettre 30 à 10€

        catalogue = new Catalogue();
        catalogue.addProduits(List.of(soupiere, pompom, percolateur));
    }

    @Test
    void addProduit() {
        I_Produit steroides;
        List<String> noms = Arrays.asList(catalogue.getNomProduits());
        assertFalse(noms.contains(steroides.getNom()));

        catalogue.addProduit(steroides);
        noms = Arrays.asList(catalogue.getNomProduits());
        assertTrue(noms.contains(steroides.getNom()));
    }

    @Test
    void AddProduitInfos() {
        String nom = "anabolisants";
        List<String> noms = Arrays.asList(catalogue.getNomProduits());
        assertFalse(noms.contains(nom));

        catalogue.addProduit(nom, 3.45, 15685);
        noms = Arrays.asList(catalogue.getNomProduits());
        assertTrue(noms.contains(nom));
    }

    @Test
    void addProduits() {
        I_Produit dopamine;
        I_Produit pocheDeSang;
        Map<String, I_Produit> produits = Map.of("dopamine", dopamine,
                                                    "poche de sang", pocheDeSang);
        List<String> noms = Arrays.asList(catalogue.getNomProduits());
        assertFalse(noms.contains(dopamine.getNom()) || noms.contains(pocheDeSang.getNom()));

        catalogue.addProduits(new ArrayList<>(produits.values()));
        assertTrue(noms.containsAll(produits.keySet()));
    }

    @Test
    void removeProduit() {
    }

    @Test
    void acheterStock() {
    }

    @Test
    void vendreStock() {
    }

    @Test
    void getNomProduits() {
    }

    @Test
    void getMontantTotalTTC() {
    }

    @Test
    void clear() {
    }
}
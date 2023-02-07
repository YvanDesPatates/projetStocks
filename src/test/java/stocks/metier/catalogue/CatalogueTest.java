package stocks.metier.catalogue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stocks.metier.produit.I_Produit;
import stocks.metier.produit.Produit;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CatalogueTest {

    Catalogue catalogue;
    I_Produit soupiere;
    I_Produit pompom;
    I_Produit percolateur;

    @BeforeEach
    public void baseCatalogue(){
        soupiere = new Produit("soupière", 5, 4);
        pompom = new Produit("pompom", 3.5, 10);
        percolateur = new Produit("percolateur", 10, 30);

        catalogue = new Catalogue();
        catalogue.addProduits(List.of(soupiere, pompom, percolateur));
    }

    @Test
    void addProduit() {
        I_Produit steroides = new Produit("streroides", 18, 25);
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
        I_Produit dopamine = new Produit("dopamine", 12.36, 5);
        I_Produit pocheDeSang = new Produit("poche de sang", 28.56, 7);
        Map<String, I_Produit> produits = Map.of("dopamine", dopamine,
                                                    "poche de sang", pocheDeSang);
        List<String> noms = Arrays.asList(catalogue.getNomProduits());
        assertFalse(noms.contains(dopamine.getNom()) || noms.contains(pocheDeSang.getNom()));

        catalogue.addProduits(new ArrayList<>(produits.values()));
        noms = Arrays.asList(catalogue.getNomProduits());
        assertTrue(noms.containsAll(produits.keySet()));
    }

    @Test
    void removeProduit() {
        assertTrue( Arrays.asList(catalogue.getNomProduits()).contains(soupiere.getNom()));
        assertTrue( catalogue.removeProduit(soupiere.getNom()) );
        assertFalse( Arrays.asList(catalogue.getNomProduits()).contains(soupiere.getNom()));
        assertFalse( catalogue.removeProduit("foo") );
    }
    @Test
    void getNomProduits() {
        List<String> nomsAttendus = List.of(soupiere.getNom(), pompom.getNom(), percolateur.getNom());
        List<String> nomsExistants = Arrays.asList(catalogue.getNomProduits());
        assertTrue( nomsAttendus.containsAll(nomsExistants) );
        assertTrue( nomsExistants.containsAll(nomsAttendus) );
    }

    @Test
    void getMontantTotalTTC() {
        double montant = soupiere.getPrixStockTTC() + pompom.getPrixStockTTC() + percolateur.getPrixStockTTC();
        assertEquals(montant, catalogue.getMontantTotalTTC());
    }

    @Test
    void clear() {
        catalogue.clear();
        assertTrue(Arrays.asList(catalogue.getNomProduits()).isEmpty());
        assertEquals(0.0, catalogue.getMontantTotalTTC());
    }
}
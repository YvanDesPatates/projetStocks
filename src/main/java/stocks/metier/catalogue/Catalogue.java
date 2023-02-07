package stocks.metier.catalogue;

import stocks.metier.produit.I_Produit;
import stocks.metier.produit.Produit;

import java.util.*;
import java.util.concurrent.atomic.DoubleAdder;

public class Catalogue implements I_Catalogue{
    private final Map<String, I_Produit> produits;

    public Catalogue() {
        this.produits = new HashMap<>();
    }

    @Override
    public boolean addProduit(I_Produit produit) {
        try {
            if (produits.containsKey(produit.getNom()) ) {
                return false;
            }
            produits.put(produit.getNom(), produit);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean addProduit(String nom, double prix, int qte) {
        I_Produit produit = new Produit(nom, prix, qte);
        return addProduit(produit);
    }

    @Override
    public int addProduits(List<I_Produit> l) {
        if(Objects.isNull(l)){
            return 0;
        }
        l.forEach(produit -> addProduit(produit));
        return produits.size();
    }

    @Override
    public boolean removeProduit(String nom) {
        return produits.remove(nom) != null;
    }

    @Override
    public boolean acheterStock(String nomProduit, int qteAchetee) {
        I_Produit produit = produits.get(nomProduit);
        if (Objects.isNull(produit)){
            return false;
        }
        return produit.ajouter(qteAchetee);
    }

    @Override
    public boolean vendreStock(String nomProduit, int qteVendue) {
        I_Produit produit = produits.get(nomProduit);
        if (Objects.isNull(produit)){
            return false;
        }
        return produit.enlever(qteVendue);
    }

    @Override
    public String[] getNomProduits() {
        return produits.keySet().toArray(new String[0]);
    }

    @Override
    public double getMontantTotalTTC() {
        DoubleAdder total = new DoubleAdder();
        produits.forEach( (nom, produit) -> total.add(produit.getPrixStockTTC()));
        return total.sum();
    }

    @Override
    public void clear() {
        produits.clear();
    }
}

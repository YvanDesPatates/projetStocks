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
        try {
            I_Produit produit = new Produit(nom, prix, qte);
            return addProduit(produit);
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public int addProduits(List<I_Produit> l) {
        if(Objects.isNull(l)){
            return 0;
        }
        int nbProduitAjoute = 0;
        for (I_Produit produit : l) {
            nbProduitAjoute += addProduit(produit) ? 1 : 0;
        }
        return nbProduitAjoute;
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

    @Override
    public String toString(){

        String resultat = "";

        String resultatAttendu = "Mars - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 5" + "\n"
                + "Treets - prix HT : 10,00 € - prix TTC : 12,00 € - quantité en stock : 4" + "\n"
                + "Raider - prix HT : 1,00 € - prix TTC : 1,20 € - quantité en stock : 10" + "\n" + "\n"
                + "Montant total TTC du stock : 120,00 €";

        /*


        if (getMontantTotalTTC()<=0){
            return "\n" + "Montant total TTC du stock : 0,00 €";
        }
        else {


        for (I_Produit produit : produits) {
            nbProduitAjoute += addProduit(produit) ? 1 : 0;
        }


        produits.forEach((nom,produit) -> {
            resultat = resultat + produit.toString();
        });
            resultat =  "\n"
                    + "Montant total TTC du stock : "+ getMontantTotalTTC()+" €";

         */
        //}
        return resultat;
    }
}

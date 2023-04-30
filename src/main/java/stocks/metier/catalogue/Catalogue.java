package stocks.metier.catalogue;

import stocks.DAO.CatalogueDAOInterface;
import stocks.DAO.DAOFabriqueAbstraite;
import stocks.metier.produit.I_Produit;
import stocks.metier.produit.Produit;

import java.util.*;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Catalogue implements I_Catalogue{
    private final Map<String, I_Produit> produits;
    private final String nom;
    public Catalogue(String nom){
        List<I_Produit> produits = Produit.getAllFromCatalogue(nom);
        //map (key, value) -> key: produit.getNom(), value: produit
        this.produits = new HashMap<>( produits.stream().collect(Collectors.toMap((I_Produit::getNom), Function.identity())) );
        this.nom = nom;
    }

    public static Map<String, Integer> getListeEtQuantite() {
        CatalogueDAOInterface dao = DAOFabriqueAbstraite.getInstance().getCatalogueDAO();
        return dao.getNomsEtQuantite();
    }

    public static Catalogue getCatalogueParNom(String nomCatalogue) {
        CatalogueDAOInterface dao = DAOFabriqueAbstraite.getInstance().getCatalogueDAO();
        return dao.getParNom(nomCatalogue);
    }

    public static boolean create(String nom) {
        CatalogueDAOInterface dao = DAOFabriqueAbstraite.getInstance().getCatalogueDAO();
        return dao.create(nom);
    }

    public static boolean supprimerParNom(String nom) {
        CatalogueDAOInterface dao = DAOFabriqueAbstraite.getInstance().getCatalogueDAO();
        return dao.supprimer(nom);
    }

    @Override
    public boolean addProduit(I_Produit produit) {
        try {
            if (produits.containsKey(produit.getNom()) || !produit.save()) {
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
            I_Produit produit = new Produit(nom, prix, qte, this.nom);
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
        if (Objects.isNull(nom) || ! produits.get(nom).delete()){
            return false;
        }
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
        return produits.keySet().stream().sorted().toList().toArray(new String[0]);
    }

    public String getNom(){
        return nom;
    }

    @Override
    public double getMontantTotalTTC() {
        DoubleAdder total = new DoubleAdder();
        produits.forEach( (nom, produit) -> total.add(produit.getPrixStockTTC()));
        return (double) Math.round(total.sum()*100)/100;
    }

    @Override
    public void clear() {
        produits.clear();
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("\nCatalogue : ").append(nom).append("\n");
        for (I_Produit produit : produits.values()) {
            res.append(produit.toString()).append("\n");
        }
        res.append("\n")
                .append("Montant total TTC du stock : ")
                .append(String.format(Locale.FRANCE, "%,.2f", getMontantTotalTTC()))
                .append(" €");
        return res.toString();
    }
}

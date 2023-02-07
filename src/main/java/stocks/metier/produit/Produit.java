package stocks.metier.produit;

import java.util.Objects;

public class Produit implements I_Produit {

    private int quantiteStock;
    private final String nom;
    private final double prixUnitaireHT;
    private final static double tauxTVA = 0.2;

    public Produit(String nom, double prixUnitaireHT, int quantiteStock) {
        if (Objects.isNull(nom) || Objects.isNull(prixUnitaireHT) || Objects.isNull(quantiteStock)){
            throw new NullPointerException("Une des valeurs est null");
        }
        if (prixUnitaireHT <= 0 || quantiteStock < 0){
            throw new RuntimeException("le prix et le stock doivent être positif");
        }
        else {
            this.nom = nom.strip();
            this.prixUnitaireHT = prixUnitaireHT;
            this.quantiteStock = quantiteStock;
        }

    }

    @Override
    public boolean ajouter(int qteAchetee) {
        if (qteAchetee <= 0){
            return false;
        }
        quantiteStock += qteAchetee;
        return true;
    }

    @Override
    public boolean enlever(int qteVendue) {
        if (quantiteStock >= qteVendue) {
            quantiteStock -= qteVendue;
            return true;
        }
        return false;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public int getQuantite() {
        return quantiteStock;
    }

    @Override
    public double getPrixUnitaireHT() {
        return prixUnitaireHT;
    }

    @Override
    public double getPrixUnitaireTTC() {
        return prixUnitaireHT * (1 + tauxTVA);
    }

    @Override
    public double getPrixStockTTC() {
        return quantiteStock * getPrixUnitaireTTC();
    }

    @Override
    public String toString(){
        return this.nom + " - prix HT : " + getPrixUnitaireHT() + " € - prix TTC : " + getPrixUnitaireTTC() + " € - quantité en stock : " + quantiteStock + "\n";
    }
}

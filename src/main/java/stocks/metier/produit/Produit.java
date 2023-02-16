package stocks.metier.produit;

import stocks.DAO.ProduitDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Produit implements I_Produit {

    private int quantiteStock;
    private final String nom;
    private final double prixUnitaireHT;
    private static final double TAUX_TVA = 0.2;
    private static ProduitDAO produitDAO = null;

    public Produit(String nom, double prixUnitaireHT, int quantiteStock){
        if (Objects.isNull(nom)){
            throw new NullPointerException("Une des valeurs est null");
        }
        if (prixUnitaireHT <= 0 || quantiteStock < 0){
            throw new RuntimeException("le prix et le stock doivent être positif");
        }
        else {
            char[] nomFiltre = nom.strip().toCharArray();
            this.nom = nom.strip().replaceAll("\t", " ");
            this.prixUnitaireHT = prixUnitaireHT;
            this.quantiteStock = quantiteStock;
        }

        if ( produitDAO == null){
            try {
                produitDAO = new ProduitDAO();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
        if (quantiteStock >= qteVendue && qteVendue > 0) {
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
        return prixUnitaireHT * (1 + TAUX_TVA);
    }

    @Override
    public double getPrixStockTTC() {
        return quantiteStock * getPrixUnitaireTTC();
    }

    public static List<Produit> getAll() throws SQLException, ClassNotFoundException {
        if ( produitDAO == null){
            produitDAO = new ProduitDAO();
        }
        return produitDAO.getAll();
    }

    public boolean save() throws SQLException {
        return produitDAO.save(this);
    }

    public boolean delete() throws SQLException {
        return produitDAO.delete(this);
    }

    @Override
    public String toString(){
        return this.nom +
                " - prix HT : " +
                String.format(Locale.FRANCE, "%,.2f" ,getPrixUnitaireHT()) +
                " € - prix TTC : " +
                String.format(Locale.FRANCE, "%,.2f", getPrixUnitaireTTC()) +
                " € - quantité en stock : " +
                quantiteStock;
    }
}

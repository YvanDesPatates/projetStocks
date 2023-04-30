package stocks.metier.produit;

public interface I_Produit {

    boolean ajouter(int qteAchetee);
    boolean enlever(int qteVendue);
    String getNom();
    int getQuantite();
    double getPrixUnitaireHT();
    double getPrixUnitaireTTC();
    double getPrixStockTTC();

    boolean save();
    boolean delete();

    String toString();

    String getNomCatalogue();
}
package stocks.controleur;

public class ControllerAchatVente extends ControllerBase{

    public boolean achatProduit(String nomProduit, int qte){
        return getCatalogue().acheterStock(nomProduit, qte);
    }

    public boolean vendreStock(String nomProduit, int qte){
        return getCatalogue().vendreStock(nomProduit, qte);
    }

    public String[] getNomProduits() {
        return getCatalogue().getNomProduits();
    }
}

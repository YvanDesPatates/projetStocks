package stocks.controleur;

public class ControllerCreationSupression extends ControllerBase{

    public boolean creationProduit(String nomProduit, double prixHT, int qteStock){
        return getCatalogue().addProduit(nomProduit, prixHT, qteStock);
    }

    public boolean supressionProduit(String nomProduit){
        return getCatalogue().removeProduit(nomProduit);
    }
}

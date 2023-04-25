package stocks.controleur;

import stocks.controleur.exception.VisualisableException;

public class ControllerAchatVente extends ControllerBase{

    public boolean achatProduit(String nomProduit, String qte) throws VisualisableException {
        int quantite;
        try {
            quantite = Integer.parseInt(qte);
        } catch (Exception e){
            throw new VisualisableException("Erreur de quantité", " La quantité doit être un entier\n Exemple : 12 ");
        }
        if (quantite <= 0){throw new VisualisableException("Erreur de quantité", " La quantité doit être strictement supérieur à zéro ");}
        return getCatalogue().acheterStock(nomProduit, quantite);
    }

    public boolean vendreStock(String nomProduit, String qte) throws VisualisableException {
        int quantite;
        try {
            quantite = Integer.parseInt(qte);
        } catch (Exception e){
            throw new VisualisableException("Erreur de quantité", " La quantité doit être un entier\n Exemple : 12 ");
        }
        if (quantite <= 0){throw new VisualisableException("Erreur de quantité", " La quantité doit être strictement supérieur à zéro ");}
        return getCatalogue().vendreStock(nomProduit, quantite);
    }

    public String[] getNomProduits() {
        return getCatalogue().getNomProduits();
    }
}

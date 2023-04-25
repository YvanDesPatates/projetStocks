package stocks.controleur;

import stocks.controleur.exception.VisualisableException;

public class ControllerCreationSupression extends ControllerBase{

    public boolean creationProduit(String nomProduit, String prixHT, String qteStock) throws VisualisableException {
        double prix;
        int qte;
        try{
            prix = Double.parseDouble(prixHT);
        } catch (Exception ex){
            throw new VisualisableException("Erreur de prix", " Le prix doit être renseigner sous la forme d'un réel\n exemple : 12.33");
        }
        try{
            qte = Integer.parseInt(qteStock);
        } catch (Exception ex){
            throw new VisualisableException("Erreur de quantité", " La quantité doit être renseignée sous la forme d'un entier\n exemple : 13");
        }
        if (nomProduit.isBlank()){throw new VisualisableException("Erreur de nom", "Le nom doit être renseigné");}
        if (qte < 0 ) {throw new VisualisableException("Erreur de quantité", "La quantité ne peut être négative");}
        if (prix <= 0 ) {throw new VisualisableException("Erreur de prix", "Le prix doit être strictement positif");}
        return getCatalogue().addProduit(nomProduit, prix, qte);
    }

    public boolean supressionProduit(String nomProduit){
        return getCatalogue().removeProduit(nomProduit);
    }

    public String[] getNomProduits() {
        return  getCatalogue().getNomProduits();
    }
}

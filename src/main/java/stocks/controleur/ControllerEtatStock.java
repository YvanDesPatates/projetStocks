package stocks.controleur;

import stocks.metier.catalogue.Catalogue;

public class ControllerEtatStock extends ControllerProduit {

    public String getEtatStock(){
        return getCatalogue().toString();
    }
}

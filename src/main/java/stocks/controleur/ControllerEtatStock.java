package stocks.controleur;

import stocks.metier.catalogue.Catalogue;

public class ControllerEtatStock extends ControllerProduit {

    public ControllerEtatStock(Catalogue catalogue) {
        super(catalogue);
    }

    public String getEtatStock(){
        return getCatalogue().toString();
    }
}

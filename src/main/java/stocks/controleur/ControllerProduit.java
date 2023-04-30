package stocks.controleur;

import stocks.metier.catalogue.Catalogue;

import java.util.Objects;

public abstract class ControllerProduit {
    private static Catalogue catalogue;

    public ControllerProduit(Catalogue catalogue){
        if (Objects.isNull(ControllerProduit.catalogue)){
            ControllerProduit.catalogue = catalogue;
        }
    }

    protected static Catalogue getCatalogue() {
        return catalogue;
    }

}

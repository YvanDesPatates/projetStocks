package stocks.controleur;

import stocks.metier.catalogue.Catalogue;

import java.util.Objects;

public abstract class ControllerBase {
    private static Catalogue catalogue = null;

    public ControllerBase(){
        if (Objects.isNull(catalogue)){
            catalogue = new Catalogue();
        }
    }

    public static Catalogue getCatalogue() {
        return catalogue;
    }

}

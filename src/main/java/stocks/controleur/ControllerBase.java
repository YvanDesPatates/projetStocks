package stocks.controleur;

import stocks.metier.catalogue.Catalogue;

import java.util.Objects;

public abstract class ControllerBase {

    public static Catalogue catalogue = null;

    public static Catalogue getCatalogue(){
        if (Objects.isNull(catalogue)){
            catalogue = new Catalogue();
        }
        return catalogue;
    }

}

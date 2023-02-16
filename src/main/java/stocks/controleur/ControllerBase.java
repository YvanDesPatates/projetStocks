package stocks.controleur;

import stocks.metier.catalogue.Catalogue;

import java.util.Objects;

public abstract class ControllerBase {

    private static Catalogue catalogue = null;

    public static Catalogue getCatalogue() {
        if (Objects.isNull(catalogue)){
            try {
                catalogue = new Catalogue();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return catalogue;
    }

}

package stocks.DAO;

import java.util.Objects;

public abstract class DAOFabriqueAbstraite {
    private static DAOFabriqueAbstraite instance;

    protected DAOFabriqueAbstraite(){}

    public static DAOFabriqueAbstraite getInstance(){
        if (Objects.isNull(instance)){
            instance = new DAOSQLFabrique();
        }
        return instance;
    }

    public abstract ProduitDAOInterface getProduitDAO();

    public abstract CatalogueDAOInterface getCatalogueDAO();

}

package stocks.DAO;

import java.sql.*;
import java.util.Objects;
import java.util.logging.Logger;

public class DAOSQLFabrique extends DAOFabriqueAbstraite {
    private ProduitDAOSQL produitDAO;
    private CatalogueDAOSQL catalogueDAO;
    private Connection connection;

    protected DAOSQLFabrique(){}

    @Override
    public ProduitDAOInterface getProduitDAO() {
        if (Objects.isNull(produitDAO)){
            produitDAO = new ProduitDAOSQL(getConnexion());
        }
        return produitDAO;
    }

    @Override
    public CatalogueDAOInterface getCatalogueDAO() {
        if (Objects.isNull(catalogueDAO)){
            catalogueDAO = new CatalogueDAOSQL(getConnexion());
        }
        return catalogueDAO;
    }

    private Connection getConnexion(){
        if (Objects.isNull(connection)){
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String login = "rouxy";
                this.connection = DriverManager.getConnection("jdbc:oracle:thin:@162.38.222.149:1521:iut", login, "24051999");
                Logger.getAnonymousLogger().info("connection à la base de donnée, user : "+login);
                return connection;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return connection;
    }

}

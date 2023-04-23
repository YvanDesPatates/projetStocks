package stocks.DAO;

import java.sql.SQLException;

public class ProduitDAOFactory {

    private static ProduitDAOInterface produitDAO;

    public ProduitDAOInterface createProduitDAO() throws SQLException, ClassNotFoundException {
        if (produitDAO == null){
            produitDAO = new ProduitDAOXMLAdaptateur();
        }
        return produitDAO;
    }
}

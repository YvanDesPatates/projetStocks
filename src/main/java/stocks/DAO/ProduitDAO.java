package stocks.DAO;

import stocks.metier.produit.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {
    private final ResultSet resultSet;
    private final Connection connection;

    private static final String TABLE = "Produits";
    private static final String NOM_FIELD = "nom";
    private static final String PRIX_FIELD = "prix";
    private static final String QUANTITE_FIELD = "quantite";

    public ProduitDAO() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.connection = DriverManager.getConnection("jdbc:oracle:thin:@162.38.222.149:1521:iut", "rouxy", "24051999");
        Statement statement = null;
        try {
            this.connection.setAutoCommit(false);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            this.resultSet = statement.executeQuery("SELECT * FROM " + TABLE + " ORDER BY "+ NOM_FIELD);
        } catch (SQLException e) {
            connection.close();
            throw e;
        }
    }

    public List<Produit> getAll() throws SQLException, ClassNotFoundException {
        List<Produit> result = new ArrayList<>();
        resultSet.beforeFirst();
        while (resultSet.next()){
            result.add(getProduitFromResultSet());
        }
        return result;
    }

    private Produit getProduitFromResultSet() throws SQLException, ClassNotFoundException {
        String nom = resultSet.getString(NOM_FIELD);
        double prix = resultSet.getDouble(PRIX_FIELD);
        int quantite = resultSet.getInt(QUANTITE_FIELD);
        return new Produit(nom, prix, quantite);
    }

    public boolean save(Produit produit) throws SQLException {
        try {
            resultSet.moveToInsertRow();
            resultSet.updateString(NOM_FIELD, produit.getNom());
            resultSet.updateDouble(PRIX_FIELD, produit.getPrixUnitaireHT());
            resultSet.updateInt(QUANTITE_FIELD, produit.getQuantite());
            resultSet.insertRow();
            connection.commit();
            return resultSet.rowInserted();
        } catch (SQLException e){
            resultSet.cancelRowUpdates();
            return false;
        }
    }

    public boolean delete(Produit produit) throws SQLException {
        if (goToRowProduct(produit)){
            try {
                resultSet.deleteRow();
                connection.commit();
                return resultSet.rowDeleted();
            }catch (SQLException e){
                return false;
            }
        }
        return false;
    }

    private boolean goToRowProduct(Produit produit) throws SQLException {
        resultSet.beforeFirst();
        while (resultSet.next()){
            if ( resultSet.getString(NOM_FIELD).equals(produit.getNom()) ){
                return true;
            }
        }
        return false;
    }
}

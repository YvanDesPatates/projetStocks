package stocks.DAO;

import stocks.metier.produit.I_Produit;
import stocks.metier.produit.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAOSQL implements ProduitDAOInterface{
    private final ResultSet resultSet;
    private final Connection connection;
    private final PreparedStatement createStatement;
    private final PreparedStatement deleteStatement;
    private final PreparedStatement updateStatement;

    private static final String TABLE = "Produits";
    private static final String NOM_FIELD = "nomProduit";
    private static final String PRIX_FIELD = "prixProduitHT";
    private static final String QUANTITE_FIELD = "quantiteStockProduit";
    private static final String procedureCreation = "createProduit";

    protected ProduitDAOSQL() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.connection = DriverManager.getConnection("jdbc:oracle:thin:@162.38.222.149:1521:iut", "burillec", "09012000");
        Statement statement;
        try {
            this.connection.setAutoCommit(false);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            this.resultSet = statement.executeQuery("SELECT produit.* FROM " + TABLE + " produit ORDER BY "+ NOM_FIELD);
            createStatement = connection.prepareStatement(
                    "CALL "+procedureCreation+"(?, ?, ?)");
            deleteStatement = connection.prepareStatement(
                    "DELETE "+TABLE+" WHERE "+NOM_FIELD+" = ?");
            updateStatement = connection.prepareStatement(
                    "UPDATE "+TABLE+" SET "+QUANTITE_FIELD+" = ? WHERE "+NOM_FIELD+" = ?");
        } catch (SQLException e) {
            connection.close();
            throw e;
        }
    }

    public List<I_Produit> getAll() throws SQLException {
        List<I_Produit> result = new ArrayList<>();
        resultSet.beforeFirst();
        while (resultSet.next()){
            result.add(getProduitFromResultSet());
        }
        return result;
    }

    private Produit getProduitFromResultSet() throws SQLException {
        String nom = resultSet.getString(NOM_FIELD);
        double prix = resultSet.getDouble(PRIX_FIELD);
        int quantite = resultSet.getInt(QUANTITE_FIELD);
        return new Produit(nom, prix, quantite);
    }

    public boolean create(I_Produit produit) {
        try {
            createStatement.setString(1, produit.getNom());
            createStatement.setDouble(2, produit.getPrixUnitaireHT());
            createStatement.setInt(3, produit.getQuantite());
            createStatement.execute();
            connection.commit();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(I_Produit produit) {
        try {
            deleteStatement.setString(1, produit.getNom());
            deleteStatement.execute();
            connection.commit();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(I_Produit produit){
        try {
            updateStatement.setDouble(1, produit.getQuantite());
            updateStatement.setString(2, produit.getNom());
            updateStatement.execute();
            connection.commit();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}

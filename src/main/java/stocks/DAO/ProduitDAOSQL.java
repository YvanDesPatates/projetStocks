package stocks.DAO;

import stocks.metier.produit.I_Produit;
import stocks.metier.produit.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProduitDAOSQL implements ProduitDAOInterface{
    private final Connection connection;
    private PreparedStatement createStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement allByCatalogueIDSt;
    private PreparedStatement getIdCatalogueByName;

    protected static final String TABLE = "Produits";
    protected static final String ID_CATALOGUE = "idCatalogue";
    protected static final String NOM_FIELD = "nomProduit";
    private static final String PRIX_FIELD = "prixProduitHT";
    private static final String QUANTITE_FIELD = "quantiteStockProduit";
    private static final String procedureCreation = "createProduit";

    protected ProduitDAOSQL(Connection connection) {
        this.connection = connection;
        try {
            createStatement = connection.prepareStatement(
                    "CALL "+procedureCreation+"(?, ?, ?, ?)");
            deleteStatement = connection.prepareStatement(
                    "DELETE "+TABLE+" WHERE "+NOM_FIELD+" = ? AND "+ID_CATALOGUE+" = ?");
            updateStatement = connection.prepareStatement(
                    "UPDATE "+TABLE+" SET "+QUANTITE_FIELD+" = ? WHERE "+NOM_FIELD+" = ? AND "+ID_CATALOGUE+" = ?");
            allByCatalogueIDSt = connection.prepareStatement(
                    "SELECT * FROM "+TABLE+" WHERE "+ID_CATALOGUE+" = ? ORDER BY "+NOM_FIELD);
            getIdCatalogueByName = connection.prepareStatement(
                    "SELECT "+CatalogueDAOSQL.ID_FIELD+" AS "+ID_CATALOGUE+
                            " FROM "+CatalogueDAOSQL.TABLE+
                            " Where "+CatalogueDAOSQL.NOM_FIELD+" = ?");
        } catch (Exception e) {
            if (Objects.nonNull(connection)){
                try {
                    connection.close();
                } catch (SQLException sqlE){
                    sqlE.printStackTrace();
                }
            }
            e.printStackTrace();
            System.exit(1);
        }
    }

    private int getIdCatalogueParNom(String nomCatalogue) throws SQLException {
        getIdCatalogueByName.setString(1, nomCatalogue);
        ResultSet rs = getIdCatalogueByName.executeQuery();
        rs.next();
        return rs.getInt(ID_CATALOGUE);
    }

    public  List<I_Produit> getAllFromCatalogue(String nomCatalogue){
        try {
            int idCatalogue = getIdCatalogueParNom(nomCatalogue);
            allByCatalogueIDSt.setInt(1, idCatalogue);
            ResultSet resultS = allByCatalogueIDSt.executeQuery();
            List<I_Produit> result = new ArrayList<>();
            while (resultS.next()) {
                result.add(getProduitFromResultSet(resultS, nomCatalogue));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private Produit getProduitFromResultSet(ResultSet resultS, String nomCataloge) throws SQLException {
        String nom = resultS.getString(NOM_FIELD);
        double prix = resultS.getDouble(PRIX_FIELD);
        int quantite = resultS.getInt(QUANTITE_FIELD);
        return new Produit(nom, prix, quantite, nomCataloge);
    }

    public boolean create(I_Produit produit) {
        try {
            int idCatalogue = getIdCatalogueParNom(produit.getNomCatalogue());
            createStatement.setString(1, produit.getNom());
            createStatement.setDouble(2, produit.getPrixUnitaireHT());
            createStatement.setInt(3, produit.getQuantite());
            createStatement.setInt(4, idCatalogue);
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
            int idCatalogue = getIdCatalogueParNom(produit.getNomCatalogue());
            deleteStatement.setString(1, produit.getNom());
            deleteStatement.setInt(2, idCatalogue);
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
            int idCatalogue = getIdCatalogueParNom(produit.getNomCatalogue());
            updateStatement.setDouble(1, produit.getQuantite());
            updateStatement.setString(2, produit.getNom());
            updateStatement.setInt(3, idCatalogue);
            updateStatement.execute();
            connection.commit();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

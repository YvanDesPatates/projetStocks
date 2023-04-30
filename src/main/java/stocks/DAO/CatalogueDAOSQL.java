package stocks.DAO;

import stocks.metier.catalogue.Catalogue;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CatalogueDAOSQL implements CatalogueDAOInterface{
    private final Connection connection;
    protected static final String TABLE = "Catalogues";
    protected static final String ID_FIELD = "id";
    protected static final String NOM_FIELD = "nom";
    private static final String QUANTITE_SELECT = "quantite";
    private static final String PROCEDURE_CREATION = "createCatalogue";

    private PreparedStatement createSt;
    private PreparedStatement deleteSt;
    private PreparedStatement checkCatalogueExists;
    private ResultSet nomsEtQuantiteRS;

    public CatalogueDAOSQL(Connection connection) {
        this.connection = connection;
        try {
            Statement statement;
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            this.nomsEtQuantiteRS = statement.executeQuery("SELECT c."+NOM_FIELD+", COUNT("+ProduitDAOSQL.NOM_FIELD+") AS "+QUANTITE_SELECT +
                    " FROM " + TABLE + " c" +
                    " LEFT JOIN "+ProduitDAOSQL.TABLE+" p ON c."+ID_FIELD+" = p."+ProduitDAOSQL.ID_CATALOGUE+
                    " GROUP BY "+NOM_FIELD+
                    " ORDER BY "+ NOM_FIELD);
            createSt = connection.prepareStatement(
                    "CALL "+PROCEDURE_CREATION+"(?)");
            deleteSt = connection.prepareStatement(
                    "DELETE "+TABLE+" WHERE "+NOM_FIELD+" = ?");
            checkCatalogueExists = connection.prepareStatement(
                    "SELECT * FROM "+TABLE+" WHERE "+NOM_FIELD+" = ?");
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

    @Override
    public Map<String, Integer> getNomsEtQuantite() {
        Map<String, Integer> nomEtQuantites = new HashMap<>();
        try {
            nomsEtQuantiteRS.beforeFirst();
            while (nomsEtQuantiteRS.next()){
                String nom = nomsEtQuantiteRS.getString(NOM_FIELD);
                Integer quantite = nomsEtQuantiteRS.getInt(QUANTITE_SELECT);
                nomEtQuantites.put(nom, quantite);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return nomEtQuantites;
    }

    @Override
    public Catalogue getParNom(String nomCatalogue) {
        try {

            checkCatalogueExists.setString(1, nomCatalogue);
            ResultSet resultSet = checkCatalogueExists.executeQuery();
            if ( ! resultSet.next() ){
                throw new Exception();
            }
            return new Catalogue( nomCatalogue );

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean create(String nom) {
        try {
            createSt.setString(1, nom);
            createSt.execute();
            connection.commit();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean supprimer(String nom) {
        try {
            deleteSt.setString(1, nom);
            deleteSt.execute();
            connection.commit();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}

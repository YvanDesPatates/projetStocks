package stocks.DAO;

import stocks.metier.produit.Produit;

import java.sql.SQLException;
import java.util.List;

public interface ProduitDAOInterface {
    public List<Produit> getAll() throws SQLException, ClassNotFoundException;
    public boolean create(Produit produit) throws SQLException;
    public boolean delete(Produit produit) throws SQLException;
}

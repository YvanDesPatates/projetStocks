package stocks.DAO;

import stocks.metier.produit.Produit;

import java.sql.SQLException;
import java.util.List;

public interface ProduitDAOInterface {
    List<Produit> getAll() throws SQLException, ClassNotFoundException;
    boolean create(Produit produit);
    boolean delete(Produit produit);
    boolean update(Produit produit);
}

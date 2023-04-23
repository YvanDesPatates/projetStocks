package stocks.DAO;

import stocks.metier.produit.I_Produit;

import java.sql.SQLException;
import java.util.List;

public interface ProduitDAOInterface {
    List<I_Produit> getAll() throws SQLException, ClassNotFoundException;
    boolean create(I_Produit produit);
    boolean delete(I_Produit produit);
    boolean update(I_Produit produit);
}

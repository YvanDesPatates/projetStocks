package stocks.DAO;

import stocks.metier.produit.I_Produit;

import java.util.List;

public interface ProduitDAOInterface {
    boolean create(I_Produit produit);
    boolean delete(I_Produit produit);
    boolean update(I_Produit produit);
    List<I_Produit> getAllFromCatalogue(String nomCatalogue);
}

package stocks.DAO;

import stocks.metier.produit.I_Produit;

import java.sql.SQLException;
import java.util.List;

public class ProduitDAOXMLAdaptateur implements ProduitDAOInterface{
    ProduitDAO_XML dao = new ProduitDAO_XML();

    @Override
    public List<I_Produit> getAll() throws SQLException, ClassNotFoundException {
        return dao.lireTous();
    }

    @Override
    public boolean create(I_Produit produit) {
        return dao.creer(produit);
    }

    @Override
    public boolean delete(I_Produit produit) {
        return dao.supprimer(produit);
    }

    @Override
    public boolean update(I_Produit produit) {
        return dao.maj(produit);
    }
}

package stocks.DAO;

public class ProduitDAOFactory {

    private static ProduitDAOInterface produitDAO;

    public ProduitDAOInterface createProduitDAO(){
        if (produitDAO == null){
            produitDAO = new ProduitDAOSQL();
        }
        return produitDAO;
    }
}

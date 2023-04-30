package stocks.DAO;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.util.Objects;

public class DAOXMLFabrique extends DAOFabriqueAbstraite{

    private final String path = "./src/main/resources/CataloguesEtProduits.xml";
    private Document doc;

    @Override
    public ProduitDAOInterface getProduitDAO() {
        return new ProduitDAOXML(getDoc(), path);
    }

    @Override
    public CatalogueDAOInterface getCatalogueDAO() {
        return new CatalogueDAOXML(getDoc(), path);
    }

    private Document getDoc() {
        if (Objects.isNull(doc)) {
            SAXBuilder sdoc = new SAXBuilder();
            try {
                doc = sdoc.build(path);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return doc;
    }
}

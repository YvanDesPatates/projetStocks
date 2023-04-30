package stocks.DAO;

import org.jdom2.Document;
import org.jdom2.Element;
import stocks.metier.catalogue.Catalogue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CatalogueDAOXML extends DAOXML implements CatalogueDAOInterface {

    public CatalogueDAOXML(Document doc, String path) { super(doc, path); }

    @Override
    public boolean create(String nomCatalogue) {
        try {
            Element existingCatalogue = getCatalogue(nomCatalogue);
            if (existingCatalogue != null) {
                return false;
            }

            Element root = getDoc().getRootElement();
            Element catalogueElement = new Element("catalogue");
            catalogueElement.setAttribute("nomC", nomCatalogue);
            root.addContent(catalogueElement);
            save();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Integer> getNomsEtQuantite(){
        Map<String, Integer> nomsEtQuantites = new HashMap<>();

            Element root = getDoc().getRootElement();
            List<Element> lCatalogues = root.getChildren("catalogue");

            for (Element catalogueElement : lCatalogues) {
                String nomCatalogue = catalogueElement.getAttributeValue("nomC");

                List<Element> lProduits = catalogueElement.getChildren("produit");
                int nbProduits = lProduits.size();
                nomsEtQuantites.put(nomCatalogue, nbProduits);
            }

        return nomsEtQuantites;
    }

    @Override
    public Catalogue getParNom(String nomCatalogue) {
        Element catalogueElement = getCatalogue(nomCatalogue);
        if (catalogueElement != null) {
            return new Catalogue(nomCatalogue);
        } else {
            Logger.getAnonymousLogger().info("le catalogue "+nomCatalogue+" n'existe pas");
            return null;
        }
    }

    @Override
    public boolean supprimer(String nomCatalogue) {
        try {
            Element root = getDoc().getRootElement();
            Element catalogue = getCatalogue(nomCatalogue);
            if (catalogue != null) {
                root.removeContent(catalogue);
                return save();
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

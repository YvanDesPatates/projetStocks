package stocks.DAO;

import org.jdom2.Document;
import org.jdom2.Element;
import stocks.metier.produit.I_Produit;
import stocks.metier.produit.Produit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProduitDAOXML extends DAOXML implements ProduitDAOInterface {

    public ProduitDAOXML(Document doc, String path) {
        super(doc, path);
    }

    @Override
    public boolean create(I_Produit produit) {
        try {
            Element catalogue = getCatalogue(produit.getNomCatalogue());
            AtomicBoolean nomProduitUnique = new AtomicBoolean(true);
            catalogue.getChildren("produit").forEach(element -> {
                if (element.getAttributeValue("nomP").equals(produit.getNom())) {
                    nomProduitUnique.set(false);
                }
            });
            if ( ! nomProduitUnique.get() ) {
                return false;
            }

            Element produitElt = new Element("produit");
            produitElt.setAttribute("nomP", produit.getNom());

            Element prixElt = new Element("prixHT");
            prixElt.setText(String.valueOf(produit.getPrixUnitaireHT()));
            produitElt.addContent(prixElt);
            Element quantiteElt = new Element("quantite");
            quantiteElt.setText(String.valueOf(produit.getQuantite()));
            produitElt.addContent(quantiteElt);

            catalogue.addContent(produitElt);
            save();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(I_Produit produit) {
        try {
            Element catalogue = getCatalogue(produit.getNomCatalogue());
            boolean deleted = catalogue.getChildren("produit").removeIf(
                    element -> element.getAttributeValue("nomP").equals(produit.getNom())
            );
            save();
            return deleted;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(I_Produit produit) {
        try {
            Element catalogue = getCatalogue(produit.getNomCatalogue());
            AtomicBoolean updated = new AtomicBoolean(false);
            catalogue.getChildren("produit").forEach(element -> {
                if (element.getAttributeValue("nomP").equals(produit.getNom())){
                    element.getChild("quantite").setText(String.valueOf(produit.getQuantite()));
                    updated.set(true);
                }
                    });
            save();
            return updated.get();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<I_Produit> getAllFromCatalogue(String nomCatalogue) {
        List<I_Produit> produits = new ArrayList<>();
        Element catalogue = getCatalogue(nomCatalogue);
        List<Element> produitsElt = catalogue.getChildren("produit");
        for (Element produit : produitsElt) {
            String nom = produit.getAttributeValue("nomP");
            double prix = Double.parseDouble(produit.getChildText("prixHT"));
            int quantite = Integer.parseInt(produit.getChildText("quantite"));
            produits.add(new Produit(nom, prix, quantite, nomCatalogue));
        }
        return produits;
    }
}

package stocks.controleur;

import stocks.exception.VisualisableException;
import stocks.metier.catalogue.Catalogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ControllerCatalogues {
    private final Map<String, Integer> cataloguesEtQuantites;

    public ControllerCatalogues() {
        cataloguesEtQuantites = new TreeMap<>(Catalogue.getListeEtQuantite());
    }

    public Catalogue validerChoixCatalogue(String nomCatalogue){
        return Catalogue.getCatalogueParNom(nomCatalogue);
    }

    public String[] getNomsCatalogues(){
        return cataloguesEtQuantites.keySet().toArray(new String[0]);
    }

    public String[] getCataloguesDetail(){
        List<String> catalogues = new ArrayList<>();
        cataloguesEtQuantites.forEach( (nom, qte) -> catalogues.add(nom+" : "+qte+" produits"));
        return catalogues.toArray(new String[0]);
    }

    public int getNbCatalogues(){
        return cataloguesEtQuantites.size();
    }

    public boolean creerCatalogue(String nom) throws VisualisableException {
            if ( Catalogue.create(nom)){
                cataloguesEtQuantites.put(nom, 0);
                return true;
            }
            else {
                throw new VisualisableException("Erreur création catalogue", "un catalogue doit avoir un nom unique\nVérifiez qu'aucun catalogue ne porte déjà ce nom !");
            }
    }

    public boolean supprimerCatalogue(String nom){
        if ( Catalogue.supprimerParNom(nom) ){
            cataloguesEtQuantites.remove(nom);
            return true;
        }
        return false;
    }
}

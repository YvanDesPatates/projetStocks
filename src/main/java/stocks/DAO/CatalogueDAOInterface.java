package stocks.DAO;

import stocks.metier.catalogue.Catalogue;

import java.util.Map;

public interface CatalogueDAOInterface {

    Map<String, Integer> getNomsEtQuantite();

    Catalogue getParNom(String nomCatalogue);

    boolean create(String nom);

    boolean supprimer(String nom);
}

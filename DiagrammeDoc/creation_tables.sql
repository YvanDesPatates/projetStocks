DROP TABLE Produits;
DROP TABLE Catalogues;

CREATE TABLE Catalogues (
                          id NUMBER,
                          nom VARCHAR(20),
                          CONSTRAINT pk_catalogue PRIMARY KEY (id),
                          CONSTRAINT uni_nomCatalogue UNIQUE (nom)
);

CREATE TABLE Produits (
                          idProduit NUMBER,
                          nomProduit VARCHAR(20),
                          prixProduitHT NUMBER,
                          quantiteStockProduit NUMBER,
                          idCatalogue NUMBER,
                          CONSTRAINT pk_produit PRIMARY KEY (idProduit),
                          CONSTRAINT fk_catalogue FOREIGN KEY (idCatalogue) REFERENCES Catalogues(id) ON DELETE CASCADE
);


CREATE SEQUENCE produits_sequence
START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE catalogues_sequence
START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE PROCEDURE createProduit
(nom VARCHAR, prixHT NUMBER, quantiteStock NUMBER, catalogueId NUMBER) IS
nom_unique_par_catalogue NUMBER;
BEGIN

	SELECT COUNT(idProduit) INTO nom_unique_par_catalogue
	FROM Produits
	WHERE idCatalogue = catalogueId
	AND nomProduit = nom;

	IF nom_unique_par_catalogue = 0 THEN
		INSERT INTO Produits 
		(idProduit, nomProduit, prixProduitHT, quantiteStockProduit, idCatalogue) 
		VALUES (produits_sequence.NEXTVAL, nom, prixHT, quantiteStock, catalogueId);
	ELSE
		RAISE_APPLICATION_ERROR (-20001, 'ajout impossible : le catalogue ' || catalogueId || ' contient déjà un produit nommé ' || nom);
	END IF;
END;
/
SHOW ERRORS

CREATE OR REPLACE PROCEDURE createCatalogue
(nom VARCHAR) IS
BEGIN
	 INSERT INTO Catalogues (id, nom) VALUES (catalogues_sequence.NEXTVAL, nom);
END;
/
SHOW ERRORS

call createProduit('produit 1', 5.38, 9, 1)
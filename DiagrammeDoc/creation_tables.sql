CREATE SEQUENCE produits_sequence
INCREMENT BY   1;

CREATE OR REPLACE PROCEDURE createProduit
(nom VARCHAR, prixHT NUMBER, quantiteStock NUMBER) IS
BEGIN
	 INSERT INTO Produits (idProduit, nomProduit, prixProduitHT, quantiteStockProduit) VALUES (produits_sequence.NEXTVAL, nom, prixHT, quantiteStock);
END;
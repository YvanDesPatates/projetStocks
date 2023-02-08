package stocks.dialog;

import stocks.controleur.ControllerCreationSupression;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetreNouveauProduit extends JFrame implements ActionListener {

	private JTextField txtPrixHT;
	private JTextField txtNom;
	private JTextField txtQte;
//	private JComboBox<String> combo;
	private JButton btValider;

	private ControllerCreationSupression controller;

//	public FenetreNouveauProduit(String[] lesCategories) {
	public FenetreNouveauProduit(ControllerCreationSupression controller) {
		this.controller = controller;

		setTitle("Creation Produit");
		setBounds(500, 250, 200, 250);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());

		JLabel labNom = new JLabel("Nom produit");
		JLabel labPrixHT = new JLabel("Prix Hors Taxe");
		JLabel labQte = new JLabel("Quantité en stock");
//		JLabel labCategorie = new JLabel("Categorie");
		contentPane.add(labNom);
		txtNom = new JTextField(15);
		contentPane.add(txtNom);
		contentPane.add(labPrixHT);
		txtPrixHT = new JTextField(15);
		contentPane.add(txtPrixHT);
		contentPane.add(labQte);
		txtQte = new JTextField(15);
		contentPane.add(txtQte);

//		combo = new JComboBox<String>(lesCategories);
//		combo.setPreferredSize(new Dimension(100, 20));
//		contentPane.add(labCategorie);
//		contentPane.add(combo);

		
		btValider = new JButton("Valider");
		contentPane.add(btValider);

		btValider.addActionListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btValider){
			try{
				double prix = Double.parseDouble(txtPrixHT.getText());
				int qte = Integer.parseInt(txtQte.getText());
				if (controller.creationProduit(txtNom.getText(), prix, qte)) {
					new FenetrePopUp("création validée", "le produit " + txtNom.getText() + " à bien été ajouté au catalogue");
				} else {
					new FenetrePopUp("erreur", " erreur lors de la création du produit\n vérifiez qu'un autre produit ne porte pas déjà le même nom");
				}
			} catch (Exception ex){
				new FenetrePopUp("erreur", "le prix doit être entré sous la forme '12.00' \n et la quantité sous la forme '12'");
			}
		}
	}

}
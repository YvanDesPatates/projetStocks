package stocks.dialog;

import stocks.controleur.ControllerCreationSupression;
import stocks.controleur.exception.VisualisableException;

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
				if (controller.creationProduit(txtNom.getText(), txtPrixHT.getText(), txtQte.getText())) {
					new FenetrePopUp("création validée", "le produit " + txtNom.getText() + " à bien été ajouté au catalogue");
				} else {
					new FenetrePopUp("Erreur création produit", " Erreur lors de la création du produit\n vérifiez qu'aucun produit ne porte pas déjà le même nom\n ou que le nom de votre produit ne soit pas trop long !");
				}
			} catch (VisualisableException ex){
				new FenetrePopUp(ex);
			}
		}
	}

}
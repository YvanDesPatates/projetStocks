package stocks.dialog;

import stocks.controleur.ControllerAchatVente;
import stocks.exception.VisualisableException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetreVente extends JFrame implements ActionListener {

	private JButton btVente;
	private JTextField txtQuantite;
	private JComboBox<String> combo;

	private ControllerAchatVente controller;


	public FenetreVente(ControllerAchatVente controller) {

		this.controller = controller;

		String [] lesProduits = controller.getNomProduits();


		setTitle("Vente");
		setBounds(500, 500, 200, 125);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btVente = new JButton("Vente");
		txtQuantite = new JTextField(5);
		txtQuantite.setText("0");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(new JLabel("Quantité vendue"));
		contentPane.add(txtQuantite);
		contentPane.add(btVente);

		btVente.addActionListener(this);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btVente){
			String nom = (String) combo.getSelectedItem();
			try {
				if (controller.vendreStock(nom, txtQuantite.getText())) {
					new FenetrePopUp(" Vente réussi", " La vente de "+txtQuantite.getText()+" "+nom+" à bien été effectué");
				} else {
					new FenetrePopUp(" Erreur", " Une erreur est survenue lors de la vente\n\n Vérifiez vos stocks !\n Vous ne pouvez vendre plus de produits que vous en avez");
				}
			} catch (VisualisableException ex){
				new FenetrePopUp(ex);
			}
		}
	}

}

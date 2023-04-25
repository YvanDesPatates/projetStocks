package stocks.dialog;

import stocks.controleur.ControllerAchatVente;
import stocks.controleur.exception.VisualisableException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetreAchat extends JFrame implements ActionListener {

	private JButton btAchat;
	private JTextField txtQuantite;
	private JComboBox<String> combo;

	private ControllerAchatVente controller;

	public FenetreAchat(ControllerAchatVente controller) {

		this.controller = controller;

		String [] lesProduits = controller.getNomProduits();

		setTitle("Achat");
		setBounds(500, 250, 200, 125);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btAchat = new JButton("Achat");
		txtQuantite = new JTextField(5);
		txtQuantite.setText("0");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(new JLabel("Quantité achetée"));
		contentPane.add(txtQuantite);
		contentPane.add(btAchat);

		btAchat.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btAchat){
			String nom = (String) combo.getSelectedItem();
			try {
				if (controller.achatProduit(nom, txtQuantite.getText())) {
					new FenetrePopUp("Achat réussi", " L'achat de "+txtQuantite.getText()+" "+nom+" à bien été effectué");
				} else {
					new FenetrePopUp(" Erreur", " Une erreur est survenue lors de l'achat");
				}
			} catch (VisualisableException ex){
				new FenetrePopUp(ex);
			}
		}
	}

}

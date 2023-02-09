package stocks.dialog;

import stocks.controleur.ControllerCreationSupression;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static stocks.controleur.ControllerBase.getCatalogue;

public class FenetreSuppressionProduit extends JFrame implements ActionListener {

	private JButton btSupprimer;
	private JComboBox<String> combo;

	private ControllerCreationSupression controller;


	public FenetreSuppressionProduit( ControllerCreationSupression controller) {
		this.controller = controller;

		String [] lesProduits = controller.getNomProduits();
		
		setTitle("Suppression produit");
		setBounds(500, 500, 200, 105);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btSupprimer = new JButton("Supprimer");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(btSupprimer);

		btSupprimer.addActionListener(this);


		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(combo.getSelectedItem());

		String nom = (String) combo.getSelectedItem();

		if (e.getSource() == btSupprimer){
			try{
				if (controller.supressionProduit(nom)) {
					new FenetrePopUp("suppresion validée", "le produit " + nom + " à bien été retiré au catalogue");
					combo.removeItem(nom);
				} else {
					new FenetrePopUp("erreur", " Merci de selectionner un produit\n ");
				}


			} catch (Exception ex){
				new FenetrePopUp("erreur", "une erreur est survenur impossible de supprimer l'article");
			}

		}
	}

}

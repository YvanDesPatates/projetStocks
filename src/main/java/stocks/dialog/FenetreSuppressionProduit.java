package stocks.dialog;

import stocks.controleur.ControllerCreationSupression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class FenetreSuppressionProduit extends JFrame implements ActionListener {

	private final JButton btSupprimer;
	private final JComboBox<String> combo;

	private final ControllerCreationSupression controller;


	public FenetreSuppressionProduit( ControllerCreationSupression controller) {
		this.controller = controller;

		setTitle("Suppression produit");
		setBounds(500, 500, 200, 105);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btSupprimer = new JButton("Supprimer");

		combo = new JComboBox<>();
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(btSupprimer);

		btSupprimer.addActionListener(this);

		refreshList();

		this.setVisible(true);
	}

	private void refreshList() {
		String[] lesProduits = controller.getNomProduits();
		combo.removeAll();
		Arrays.asList(lesProduits).forEach(combo::addItem);
	}

	public void actionPerformed(ActionEvent e) {
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
			refreshList();
		}
	}

}

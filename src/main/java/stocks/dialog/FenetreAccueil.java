package stocks.dialog;

import stocks.controleur.ControllerCatalogues;
import stocks.controleur.exception.VisualisableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FenetreAccueil extends JFrame implements ActionListener, WindowListener {

    private JButton btAjouter, btSupprimer, btSelectionner;
    private JTextField txtAjouter;
    private JLabel lbNbCatalogues;
    private JComboBox cmbSupprimer, cmbSelectionner;
    private TextArea taDetailCatalogues;
    private ControllerCatalogues controllerCatalogues = new ControllerCatalogues();

    public FenetreAccueil() {
        setTitle("Catalogues");
        setBounds(500, 500, 200, 125);
        Container contentPane = getContentPane();
        JPanel panInfosCatalogues = new JPanel();
        JPanel panNbCatalogues = new JPanel();
        JPanel panDetailCatalogues = new JPanel();
        JPanel panGestionCatalogues = new JPanel();
        JPanel panAjouter = new JPanel();
        JPanel panSupprimer = new JPanel();
        JPanel panSelectionner = new JPanel();
        panNbCatalogues.setBackground(Color.white);
        panDetailCatalogues.setBackground(Color.white);
        panAjouter.setBackground(Color.gray);
        panSupprimer.setBackground(Color.lightGray);
        panSelectionner.setBackground(Color.gray);

        panNbCatalogues.add(new JLabel("Nous avons actuellement : "));
        lbNbCatalogues = new JLabel();
        panNbCatalogues.add(lbNbCatalogues);

        taDetailCatalogues = new TextArea();
        taDetailCatalogues.setEditable(false);
        new JScrollPane(taDetailCatalogues);
        taDetailCatalogues.setPreferredSize(new Dimension(300, 100));
        panDetailCatalogues.add(taDetailCatalogues);

        panAjouter.add(new JLabel("Ajouter un catalogue : "));
        txtAjouter = new JTextField(10);
        panAjouter.add(txtAjouter);
        btAjouter = new JButton("Ajouter");
        panAjouter.add(btAjouter);

        panSupprimer.add(new JLabel("Supprimer un catalogue : "));
        cmbSupprimer = new JComboBox();
        cmbSupprimer.setPreferredSize(new Dimension(100, 20));
        panSupprimer.add(cmbSupprimer);
        btSupprimer = new JButton("Supprimer");
        panSupprimer.add(btSupprimer);

        panSelectionner.add(new JLabel("Selectionner un catalogue : "));
        cmbSelectionner = new JComboBox();
        cmbSelectionner.setPreferredSize(new Dimension(100, 20));
        panSelectionner.add(cmbSelectionner);
        btSelectionner = new JButton("Selectionner");
        panSelectionner.add(btSelectionner);

        panGestionCatalogues.setLayout (new BorderLayout());
        panGestionCatalogues.add(panAjouter, "North");
        panGestionCatalogues.add(panSupprimer);
        panGestionCatalogues.add(panSelectionner, "South");

        panInfosCatalogues.setLayout(new BorderLayout());
        panInfosCatalogues.add(panNbCatalogues, "North");
        panInfosCatalogues.add(panDetailCatalogues, "South");

        contentPane.add(panInfosCatalogues, "North");
        contentPane.add(panGestionCatalogues, "South");
        pack();

        btAjouter.addActionListener(this);
        btSupprimer.addActionListener(this);
        btSelectionner.addActionListener(this);

        chargerInformationsCatalogues();

        setVisible(true);
    }

    private void chargerInformationsCatalogues() {
        String[] tab  = controllerCatalogues.getNomsCatalogues();
        modifierListesCatalogues(tab);
        String[] tab2 = controllerCatalogues.getCataloguesDetail();
        modifierDetailCatalogues(tab2);
        modifierNbCatalogues(controllerCatalogues.getNbCatalogues());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAjouter)
        {
            String texteAjout = txtAjouter.getText();
            if (!texteAjout.equals(""))
            {
                try {
                    if (controllerCatalogues.creerCatalogue(texteAjout)) {
                        chargerInformationsCatalogues();
                    } else {
                        new FenetrePopUp("erreur", "le catalogue n'as pas pu être créé");
                    }
                } catch (VisualisableException ex){
                    new FenetrePopUp(ex);
                }
                txtAjouter.setText(null);
            }
        }
        if (e.getSource() == btSupprimer)
        {
            String texteSupprime = (String)cmbSupprimer.getSelectedItem();
            if (texteSupprime != null)
                if (controllerCatalogues.supprimerCatalogue(texteSupprime)) {
                    chargerInformationsCatalogues();
                }
            else {
                    new FenetrePopUp("erreur", "la supression du catalogue "+texteSupprime+" à échouée");
                }
        }
        if (e.getSource() == btSelectionner)
        {
            String texteSelection = (String)cmbSelectionner.getSelectedItem();
            if (texteSelection != null && controllerCatalogues.validerChoixCatalogue(texteSelection))
            {
                new FenetrePrincipale();
                this.dispose();
            }
        }
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        System.exit(0);
    }
    @Override
    public void windowOpened(WindowEvent windowEvent) {    }
    @Override
    public void windowClosed(WindowEvent windowEvent) {    }
    @Override
    public void windowIconified(WindowEvent windowEvent) {    }
    @Override
    public void windowDeiconified(WindowEvent windowEvent) {    }
    @Override
    public void windowActivated(WindowEvent windowEvent) {    }
    @Override
    public void windowDeactivated(WindowEvent windowEvent) {    }

    private void modifierListesCatalogues(String[] nomsCatalogues) {
        cmbSupprimer.removeAllItems();
        cmbSelectionner.removeAllItems();
        if (nomsCatalogues != null)
            for (int i=0 ; i<nomsCatalogues.length; i++)
            {
                cmbSupprimer.addItem(nomsCatalogues[i]);
                cmbSelectionner.addItem(nomsCatalogues[i]);
            }
    }

    private void modifierNbCatalogues(int nb)
    {
        lbNbCatalogues.setText(nb + " catalogues");
    }

    private void modifierDetailCatalogues(String[] detailCatalogues) {
        taDetailCatalogues.setText("");
        if (detailCatalogues != null) {
            for (int i=0 ; i<detailCatalogues.length; i++)
                taDetailCatalogues.append(detailCatalogues[i]+"\n");
        }
    }

}
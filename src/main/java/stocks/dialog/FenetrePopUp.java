package stocks.dialog;

import stocks.controleur.exception.VisualisableException;

import javax.swing.*;
import java.awt.*;

public class FenetrePopUp extends JFrame {

    public FenetrePopUp(String title, String text){
        setTitle(title);
        setBounds(500, 250, 450, 250);
        JPanel panHaut = new JPanel();
        JPanel panBas = new JPanel();
        panHaut.setLayout(new BorderLayout());
        panBas.setLayout(new FlowLayout());

        JTextArea jtaSortie = new JTextArea(text,10,5);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        panHaut.add(jtaSortie);

        contentPane.add(panHaut,"North");
        contentPane.add(panBas, "South");

        this.setVisible(true);
    }

    public FenetrePopUp(VisualisableException ex){
        this(ex.getTitle(), ex.getMessage());
    }
}

package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.observer.Observable;
import com.observer.Observateur;
import javax.swing.*;
import java.awt.*;

/**
 * La classe qui constitue la console dans l'interface graphique.
 *
 */
public class PanneauConsole extends JPanel implements Observateur{


    private JTextArea console;
    private final GestionnaireLivraisons gestionnaireLivraisons;


    /**
     * Constructeur pour cette classe.
     *
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public PanneauConsole(GestionnaireLivraisons gestionnaireLivraisons) {

        this.gestionnaireLivraisons = gestionnaireLivraisons;

        // Zone de texte non-éditable, fond noir, texte vert
        this.console = new JTextArea();
        this.console.setEditable(false);
        this.console.setBackground(Color.BLACK);
        this.console.setForeground(Color.GREEN);
        this.console.setFont(Config.fonte);

        // Panneau de défilement
        JScrollPane defilement = new JScrollPane(this.console);

        // Mise en page
        this.setLayout(new BorderLayout());
        this.add(defilement, BorderLayout.CENTER);

        // s'enregistrer comme observateur
        this.gestionnaireLivraisons.ajouterObservateur(this);

    }

    /**
     * ajoute un message a la console
     * @param message
     */
    private void afficherMessage(String message) {
        if (message != null && !message.isEmpty()) {
            this.console.append(message + "\n");
            // Faire défiler vers le bas automatiquement
            this.console.setCaretPosition(this.console.getDocument().getLength());
        }
    }
    /**
     * Mise à jour lors d'une notification de l'observable.
     *
     * @param observable L'objet observable qui notifie.
     */
    @Override
    public void seMettreAJour(Observable observable) {
        if (observable instanceof GestionnaireLivraisons) {
            String trace = this.gestionnaireLivraisons.consommerTrace();
            if (trace != null && !trace.isEmpty()) {
                SwingUtilities.invokeLater(() -> afficherMessage(trace));
            }
        }
    }
}
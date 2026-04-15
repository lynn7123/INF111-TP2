package com.controleur;

import com.vue.AjoutLivraisonDialogue;
import com.vue.MiniServerUI;
import com.vue.StatistiquesDialogue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Écouteur pour les items "Ajouter Livraison" et "Statistiques"
 * du Menu Application. Un seul écouteur gère les deux via getActionCommand().
 */
public class EcouteurMenuApplication implements ActionListener {

    /** Commande pour l'ajout d'une livraison. */
    public static final String CMD_AJOUTER = "AJOUTER_LIVRAISON";

    /** Commande pour l'affichage des statistiques. */
    public static final String CMD_STATISTIQUES = "STATISTIQUES";

    private final MiniServerUI miniServerUI;

    /**
     * Constructeur.
     *
     * @param miniServerUI La fenêtre principale de l'application.
     */
    public EcouteurMenuApplication(MiniServerUI miniServerUI) {
        this.miniServerUI = miniServerUI;
    }

    /**
     * Traite les événements des items du menu Application.
     *
     * @param e L'événement d'action reçu.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case CMD_AJOUTER:
                new AjoutLivraisonDialogue(this.miniServerUI,
                        this.miniServerUI.getGestionnaireLivraisons());
                break;
            case CMD_STATISTIQUES:
                new StatistiquesDialogue(this.miniServerUI,
                        this.miniServerUI.getGestionnaireLivraisons());
                break;
        }
    }
}

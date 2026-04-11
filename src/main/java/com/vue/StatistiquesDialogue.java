package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;

import javax.swing.*;

/**
 * La classe des boites de dialogues pour l'affichage des statistiques.
 *
 */
public class StatistiquesDialogue extends JDialog {

    final private GestionnaireLivraisons gestionnaireLivraisons;
    private JPanel infos;   // regroupe les informations de statistiques à afficher

    /**
     * Le constructeur pour une boite de dialogue de statistiques.
     *
     * @param miniServerUI La fenêtre propriétaire de cette boite de dialogue.
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public StatistiquesDialogue(MiniServerUI miniServerUI, GestionnaireLivraisons gestionnaireLivraisons) {
        super(miniServerUI, "Statistiques", true);
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.initialiserComposants();

        // TODO : À compléter/modifier
    }

    /**
     * Prépare la boite de dialogue pour les statistiques
     */
    private void initialiserComposants() {
        // TODO : À compléter/modifier
        System.err.println("Méthode StatistiquesDialogue.initialiserComposants non implémentée.");
    }

    // TODO : À compléter/modifier
}

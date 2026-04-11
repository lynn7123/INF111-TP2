package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.gestionnaireLivraisons.Priorite;

import javax.swing.*;

/**
 * La classe pour les boites de dialogues d'ajout d'une livraison.
 *
 */
public class AjoutLivraisonDialogue extends JDialog {
    final private MiniServerUI miniServerUI;
    final private GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Le constructeur pour la boite de dialogue d'ajout d'une livrason.
     *
     * @param miniServerUI La fenêtre propriétaire de cette boite de dialogue.
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public AjoutLivraisonDialogue(MiniServerUI miniServerUI, GestionnaireLivraisons gestionnaireLivraisons) {
        super(miniServerUI, "Ajout d'une livraison", true);
        this.miniServerUI = miniServerUI;
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.initialiserComposants();
    }

    /**
     * Affichage et gestion de la boite de dialogue pour l'ajout d'une livraison.
     *
     */
    public void initialiserComposants() {
        // TODO : À compléter/modifier
        System.err.println("Méthode AjoutLivraisonDialogue.initialiserComposants non implémentée.");

    }

    /**
     * Affiche une boite de dialogue pour les erreurs de saisies.
     *
     * @param msg Le message à afficher dans la boite.
     */
    private void afficherErreur(String msg) {
        String[] erreurOptions = {"Opps..."};
        JOptionPane.showOptionDialog(this.miniServerUI, msg,
                "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, erreurOptions, erreurOptions[0]);
    }

    /**
     * Ajoute une livraison à la liste des livraisons à effectuer (mise à jour du modèle)
     *
     * @param lot      Le lot de la livraison.
     * @param priorite La priorité de la livraison.
     */
    private void ajouterLivraison(int lot, Priorite priorite) {
        // TODO : À compléter/modifier
        System.err.println("Méthode AjoutLivraisonDialogue.ajouterLivraison non implémentée.");
    }

}

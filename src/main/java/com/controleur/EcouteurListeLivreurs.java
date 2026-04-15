package com.controleur;

import com.gestionnaireLivraisons.Livreur;
import com.vue.InfoLivreurDialogue;
import com.vue.PanneauLivreurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La classe des écouteurs de la liste de livreurs.
 * Détecte le double-clic et ouvre la fenêtre d'informations du livreur sélectionné.
 */
public class EcouteurListeLivreurs extends MouseAdapter {

    private final PanneauLivreurs panneauLivreurs;

    /**
     * Le constructeur pour un tel écouteur.
     *
     * @param panneauLivreurs Le panneau à écouter.
     */
    public EcouteurListeLivreurs(PanneauLivreurs panneauLivreurs) {
        this.panneauLivreurs = panneauLivreurs;
    }

    /**
     * Détecte le double-clic et ouvre la fenêtre de détails du livreur.
     *
     * @param e L'événement souris.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Livreur livreur = this.panneauLivreurs.livreurSelectionne();
            if (livreur != null) {
                new InfoLivreurDialogue(this.panneauLivreurs.getMiniServerUI(), livreur);
            }
        }
    }
}
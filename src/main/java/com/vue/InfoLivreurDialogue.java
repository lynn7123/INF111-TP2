package com.vue;

import com.gestionnaireLivraisons.*;
import java.util.Vector;

/**
 * Classe qui affiche la boite de dialogue pour les informations d'un livreur.
 *
 */
public class InfoLivreurDialogue {

    private Livreur livreur;

    private ComposantTable grilleLivraisonsEnCours;
    private ComposantTable grilleLivraisonsEffectuees;

    /**
     * Le constructeur de cette boite de dialogue.
     *
     * @param miniServerUI           La fenêtre contenant cette boite de dialogue.
     * @param livreur                Le livreur dont on veut afficher les données (infos personnelles et livraisons).
     */
    public InfoLivreurDialogue(MiniServerUI miniServerUI, Livreur livreur) {
        // TODO : À compléter/modifier
        System.err.println("Méthode InfoLivreurDialogue.InfoLivreurDialogue non implémentée.");
    }

    /**
     * Afficher la boite de dialogue contenant les infos du livreur.
     * Cette méthode est invoquée par le constructeur
     *
     */
    private void initialiserComposants() {
        // TODO : À compléter/modifier
        System.err.println("Méthode InfoLivreurDialogue.initialiserComposants non implémentée.");
    }

    /**
     * Prépare les données à afficher pour une liste de livraisons.
     *
     * @param livraisons La liste de livraisons.
     * @return La matrice des données relatives à la liste de livraisons.
     */
    private Vector<Vector<String>> calculerDonnees(IListeLivraisons livraisons) {
        // TODO : À compléter/modifier
        return null;
    }

    // TODO : À compléter/modifier
}

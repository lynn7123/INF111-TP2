package com.vue;

import com.atoudeft.serveur.Serveur;
import com.gestionnaireLivraisons.GestionnaireLivraisons;

import javax.swing.*;

/**
 * La classe principale de l'interface utilisateur de l'application MiniServer
 *
 */
public class MiniServerUI extends JFrame {
    final private Serveur serveur;
    final private GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Constructeur pour l'interface graphique
     *
     * @param serveur La référence du serveur utilisé.
     * @param gestionnaireLivraisons  Le gestionnaire de livraisons.
     */
    public MiniServerUI(Serveur serveur, GestionnaireLivraisons gestionnaireLivraisons) {
        this.serveur = serveur;
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.initialiserComposants();
        this.configurerFenetrePrincipale();
    }

    /**
     * Getter pour le gestionnaire de livraisons.
     *
     * @return Le gestionnaire de livraisons.
     */
    public GestionnaireLivraisons getGestionnaireLivraisons() {
        return gestionnaireLivraisons;
    }

    /**
     * Configure la fenêtre graphique.
     *
     */
    private void configurerFenetrePrincipale() {
        // Configuration de la fenêtre
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Gestionnaire de livraisons");
        this.setSize(980, 450);
        this.setLocation(100, 100);
    }

    /**
     * Création et placement des composants de la fenêtre principale :
     *   - Création du menu Application est de ses items
     *   - Création des trois panneaux de la fenêtre
     */
    private void initialiserComposants() {
        // Le menu
        JMenuBar menuBar = new JMenuBar();
        // TODO : À compléter/modifier
        // se reporter à la documentation de JMenu et JMenuBar pour cette partie...
        System.err.println("Menu non implémenté dans la méthode MiniServerUI.initialiserComposants.");


        //  Préparer le contenu de la fenêtre.
        // TODO : À compléter/modifier
        System.err.println("Contenu de la fenêtre manquant dans la méthode MiniServerUI.initialiserComposants.");

        // TODO : À compléter/modifier
        System.err.println("Ecouteur de fermeture de fenêtre manquant dans la méthode MiniServerUI.initialiserComposants.");

        // TODO : À compléter/modifier


    }

    /**
     * Quitter l'application :
     * - arrêter le serveur
     * - quitter le gestionnaire de livraisons
     * - libérer la fenêtre
     * - quitter l'application
     */
    public void quitter() {
        this.serveur.arreter();
        this.gestionnaireLivraisons.quitter();
        this.dispose();
        System.exit(0);
    }
}

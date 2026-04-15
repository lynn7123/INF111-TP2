package com.vue;

import com.atoudeft.serveur.Serveur;
import com.controleur.EcouteurMenuApplication;
import com.gestionnaireLivraisons.GestionnaireLivraisons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La classe principale de l'interface utilisateur de l'application MiniServer.
 * Fenêtre principale avec barre de menu et trois panneaux : livreurs, livraisons, console.
 */
public class MiniServerUI extends JFrame {

    private final Serveur serveur;
    private final GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Constructeur pour l'interface graphique.
     *
     * @param serveur                La référence du serveur utilisé.
     * @param gestionnaireLivraisons Le gestionnaire de livraisons.
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
     */
    private void configurerFenetrePrincipale() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Gestionnaire de livraisons");
        this.setSize(980, 450);
        this.setLocation(100, 100);

        // Écouteur de fermeture — classe anonyme
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitter();
            }
        });
    }

    /**
     * Création et placement des composants de la fenêtre principale.
     */
    private void initialiserComposants() {
        // Barre de menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuApplication = new JMenu("Application");

        EcouteurMenuApplication ecouteurMenu = new EcouteurMenuApplication(this);

        JMenuItem itemAjouter = new JMenuItem("Ajouter Livraison");
        itemAjouter.setActionCommand(EcouteurMenuApplication.CMD_AJOUTER);
        itemAjouter.addActionListener(ecouteurMenu);

        JMenuItem itemStatistiques = new JMenuItem("Statistiques");
        itemStatistiques.setActionCommand(EcouteurMenuApplication.CMD_STATISTIQUES);
        itemStatistiques.addActionListener(ecouteurMenu);

        // Item Quitter — classe anonyme
        JMenuItem itemQuitter = new JMenuItem("Quitter");
        itemQuitter.addActionListener(e -> quitter());

        menuApplication.add(itemAjouter);
        menuApplication.add(itemStatistiques);
        menuApplication.addSeparator();
        menuApplication.add(itemQuitter);
        menuBar.add(menuApplication);
        this.setJMenuBar(menuBar);

        //  Trois panneaux
        PanneauLivreurs panneauLivreurs     = new PanneauLivreurs(this, this.gestionnaireLivraisons);
        PanneauLivraisons panneauLivraisons = new PanneauLivraisons(this.gestionnaireLivraisons);
        PanneauConsole panneauConsole       = new PanneauConsole(this.gestionnaireLivraisons);

        // Panneau supérieur : livreurs (gauche) + livraisons (droite)
        JPanel panneauSuperieur = new JPanel(new GridLayout(1, 2, 5, 0));
        panneauSuperieur.add(panneauLivreurs);
        panneauSuperieur.add(panneauLivraisons);

        // Séparation verticale : panneaux du haut / console en bas
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                panneauSuperieur, panneauConsole);
        splitPane.setResizeWeight(0.6);
        splitPane.setDividerSize(5);

        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);

        this.setVisible(true);
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

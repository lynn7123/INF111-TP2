package com.vue;

import com.gestionnaireLivraisons.IListeLivraisons;
import com.gestionnaireLivraisons.Livraison;
import com.gestionnaireLivraisons.Livreur;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Classe qui affiche la boite de dialogue pour les informations d'un livreur.
 * Observatrice de Livreur pour se mettre à jour lorsque ses livraisons changent.
 */
public class InfoLivreurDialogue extends JDialog implements Observateur {

    private final Livreur livreur;

    private final ComposantTable grilleLivraisonsEnCours;
    private final ComposantTable grilleLivraisonsEffectuees;

    private static final String[] NOMS_COLONNES = {"Id", "Lot", "Priorité", "Tentatives"};
    private static final boolean[] COLONNES_CENTREES = {true, true, true, true};

    /**
     * Le constructeur de cette boite de dialogue.
     *
     * @param miniServerUI La fenêtre contenant cette boite de dialogue.
     * @param livreur      Le livreur dont on veut afficher les données.
     */
    public InfoLivreurDialogue(MiniServerUI miniServerUI, Livreur livreur) {
        super(miniServerUI, "Informations livreur", true);
        this.livreur = livreur;

        this.grilleLivraisonsEnCours = new ComposantTable(
                "Livraisons en cours", 300, 200, NOMS_COLONNES, COLONNES_CENTREES);
        this.grilleLivraisonsEffectuees = new ComposantTable(
                "Livraisons effectuées", 300, 200, NOMS_COLONNES, COLONNES_CENTREES);

        this.initialiserComposants();

        // S'enregistrer comme observateur du livreur
        this.livreur.ajouterObservateur(this);
    }

    /**
     * Initialise et affiche les composants de la boite de dialogue.
     */
    private void initialiserComposants() {
        this.setLayout(new BorderLayout(10, 10));
        this.setSize(700, 400);
        this.setLocationRelativeTo(getOwner());

        // Panneau des informations personnelles (gauche)
        JPanel panneauInfos = new JPanel();
        panneauInfos.setLayout(new BoxLayout(panneauInfos, BoxLayout.Y_AXIS));
        panneauInfos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panneauInfos.add(new JLabel("Id : " + this.livreur.getId()));
        panneauInfos.add(new JLabel("Nom : " + this.livreur.getNom()));
        panneauInfos.add(new JLabel("Type : " + this.livreur.type()));
        panneauInfos.add(new JLabel("Capacité : " + this.livreur.capaciteLivraison()));

        // Panneau des deux tables (centre)
        JPanel panneauTables = new JPanel(new GridLayout(1, 2, 10, 0));
        panneauTables.add(this.grilleLivraisonsEnCours);
        panneauTables.add(this.grilleLivraisonsEffectuees);

        JPanel panneauPrincipal = new JPanel(new BorderLayout(10, 0));
        panneauPrincipal.add(panneauInfos, BorderLayout.WEST);
        panneauPrincipal.add(panneauTables, BorderLayout.CENTER);

        // Bouton Fermer — classe anonyme
        JButton boutonFermer = new JButton("Fermer");
        boutonFermer.addActionListener(e -> dispose());

        JPanel panneauBouton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panneauBouton.add(boutonFermer);

        this.add(panneauPrincipal, BorderLayout.CENTER);
        this.add(panneauBouton, BorderLayout.SOUTH);

        mettreAJourTables();

        this.setVisible(true);
    }

    /**
     * Prépare les données à afficher pour une liste de livraisons.
     *
     * @param livraisons La liste de livraisons.
     * @return La matrice des données.
     */
    private Vector<Vector<String>> calculerDonnees(IListeLivraisons livraisons) {
        Vector<Vector<String>> donnees = new Vector<>();
        for (Livraison livraison : livraisons) {
            Vector<String> ligne = new Vector<>();
            ligne.add(String.valueOf(livraison.getId()));
            ligne.add(String.valueOf(livraison.getLot()));
            ligne.add(livraison.getPriorite().toString());
            ligne.add(String.valueOf(livraison.getTentative()));
            donnees.add(ligne);
        }
        return donnees;
    }

    /**
     * Met à jour les deux tables avec les données actuelles du livreur.
     */
    private void mettreAJourTables() {
        this.grilleLivraisonsEnCours.mettreAJour(calculerDonnees(this.livreur.getLivraisonsEnCours()));
        this.grilleLivraisonsEffectuees.mettreAJour(calculerDonnees(this.livreur.getLivraisonsEffectuees()));
    }

    /**
     * Mise à jour lors d'une notification de l'observable (le livreur observé).
     *
     * @param observable L'objet observable qui notifie.
     */
    @Override
    public void seMettreAJour(Observable observable) {
        if (observable instanceof Livreur) {
            SwingUtilities.invokeLater(this::mettreAJourTables);
        }
    }
}

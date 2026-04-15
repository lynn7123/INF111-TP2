package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.gestionnaireLivraisons.Livreur;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;

/**
 * La classe des boites de dialogues pour l'affichage des statistiques.
 * Observatrice de GestionnaireLivraisons pour se mettre à jour dynamiquement.
 */
public class StatistiquesDialogue extends JDialog implements Observateur {

    private final GestionnaireLivraisons gestionnaireLivraisons;
    private JPanel infos;

    private JLabel labelTotal;
    private JLabel labelEffectuees;
    private JLabel labelEnCours;
    private JLabel labelEchouees;

    /**
     * Le constructeur pour une boite de dialogue de statistiques.
     *
     * @param miniServerUI           La fenêtre propriétaire de cette boite de dialogue.
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public StatistiquesDialogue(MiniServerUI miniServerUI, GestionnaireLivraisons gestionnaireLivraisons) {
        super(miniServerUI, "Statistiques", true);
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.initialiserComposants();
        this.gestionnaireLivraisons.ajouterObservateur(this);
    }

    /**
     * Prépare la boite de dialogue pour les statistiques.
     */
    private void initialiserComposants() {
        this.setLayout(new BorderLayout(10, 10));
        this.setSize(300, 200);
        this.setLocationRelativeTo(getOwner());

        this.infos = new JPanel(new GridLayout(4, 2, 10, 5));
        this.infos.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        this.labelTotal      = new JLabel();
        this.labelEffectuees = new JLabel();
        this.labelEnCours    = new JLabel();
        this.labelEchouees   = new JLabel();

        this.infos.add(new JLabel("Total des livraisons :"));
        this.infos.add(this.labelTotal);
        this.infos.add(new JLabel("Livraisons effectuées :"));
        this.infos.add(this.labelEffectuees);
        this.infos.add(new JLabel("Livraisons en cours :"));
        this.infos.add(this.labelEnCours);
        this.infos.add(new JLabel("Livraisons échouées :"));
        this.infos.add(this.labelEchouees);

        JButton boutonFermer = new JButton("Fermer");
        boutonFermer.addActionListener(e -> dispose());
        JPanel panneauBouton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panneauBouton.add(boutonFermer);

        this.add(this.infos, BorderLayout.CENTER);
        this.add(panneauBouton, BorderLayout.SOUTH);

        mettreAJourStatistiques();

        this.setVisible(true);
    }

    /**
     * Calcule et met à jour l'affichage des statistiques.
     */
    private void mettreAJourStatistiques() {
        int enAttente = this.gestionnaireLivraisons.getLivraisonsAEffectuer().taille();
        int echouees  = this.gestionnaireLivraisons.getLivraisonsEchouees().taille();
        int enCours   = 0;
        int effectuees = 0;

        for (Livreur livreur : this.gestionnaireLivraisons.getLivreursEnregistres()) {
            enCours    += livreur.nbLivraisonsEnCours();
            effectuees += livreur.nbLivraisonsEffectuees();
        }

        int total = enAttente + enCours + effectuees + echouees;

        this.labelTotal.setText(String.valueOf(total));
        this.labelEffectuees.setText(String.valueOf(effectuees));
        this.labelEnCours.setText(String.valueOf(enCours));
        this.labelEchouees.setText(String.valueOf(echouees));
    }

    /**
     * Mise à jour lors d'une notification de l'observable.
     *
     * @param observable L'objet observable qui notifie.
     */
    @Override
    public void seMettreAJour(Observable observable) {
        if (observable instanceof GestionnaireLivraisons) {
            SwingUtilities.invokeLater(this::mettreAJourStatistiques);
        }
    }
}

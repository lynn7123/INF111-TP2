package com.vue;

import com.gestionnaireLivraisons.FilePrioriteLivraisons;
import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.gestionnaireLivraisons.Livraison;
import com.gestionnaireLivraisons.Livreur;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * La classe qui constitue le panneau des livraisons.
 * Observatrice de GestionnaireLivraisons pour se mettre à jour automatiquement.
 */
public class PanneauLivraisons extends JPanel implements Observateur {

    private final ComposantTable tableLivraisons;

    private final String[] nomsColonnes = {"Id", "Lot", "Priorité", "Tentatives", "Statut"};
    private final boolean[] donneesCentrees = {true, true, true, true, true};

    private final GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Constructeur pour la classe PanneauLivraisons.
     *
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public PanneauLivraisons(GestionnaireLivraisons gestionnaireLivraisons) {
        this.gestionnaireLivraisons = gestionnaireLivraisons;

        this.tableLivraisons = new ComposantTable("Liste des livraisons", 450, 200,
                nomsColonnes, donneesCentrees);

        this.setLayout(new BorderLayout());
        this.add(this.tableLivraisons, BorderLayout.CENTER);

        this.gestionnaireLivraisons.ajouterObservateur(this);
    }

    /**
     * Calcule les données à afficher dans la table des livraisons.
     *
     * @return La matrice de données.
     */
    private Vector<Vector<String>> calculerDonnees() {
        Vector<Vector<String>> donnees = new Vector<>();

        // Livraisons en attente (dans la file de priorité)
        FilePrioriteLivraisons enAttente = this.gestionnaireLivraisons.getLivraisonsAEffectuer();
        for (Livraison livraison : enAttente) {
            donnees.add(creerLigneLivraison(livraison));
        }

        // Livraisons en cours et effectuées (chez les livreurs)
        for (Livreur livreur : this.gestionnaireLivraisons.getLivreursEnregistres()) {
            for (Livraison livraison : livreur.getLivraisonsEnCours()) {
                donnees.add(creerLigneLivraison(livraison));
            }
            for (Livraison livraison : livreur.getLivraisonsEffectuees()) {
                donnees.add(creerLigneLivraison(livraison));
            }
        }

        // Livraisons échouées
        for (Livraison livraison : this.gestionnaireLivraisons.getLivraisonsEchouees()) {
            donnees.add(creerLigneLivraison(livraison));
        }

        return donnees;
    }

    /**
     * Crée une ligne de données pour une livraison donnée.
     *
     * @param livraison La livraison dont on veut créer la ligne.
     * @return La ligne de données.
     */
    private Vector<String> creerLigneLivraison(Livraison livraison) {
        Vector<String> ligne = new Vector<>();
        ligne.add(String.valueOf(livraison.getId()));
        ligne.add(String.valueOf(livraison.getLot()));
        ligne.add(livraison.getPriorite().toString());
        ligne.add(String.valueOf(livraison.getTentative()));
        ligne.add(livraison.getStatut().toString().replace("_", " "));
        return ligne;
    }

    /**
     * Mise à jour lors d'une notification de l'observable.
     *
     * @param observable L'objet observable qui notifie.
     */
    @Override
    public void seMettreAJour(Observable observable) {
        if (observable instanceof GestionnaireLivraisons) {
            SwingUtilities.invokeLater(() -> this.tableLivraisons.mettreAJour(calculerDonnees()));
        }
    }
}

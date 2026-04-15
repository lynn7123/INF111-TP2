package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.gestionnaireLivraisons.IListeChaineeLivreurs;
import com.gestionnaireLivraisons.Livreur;
import com.controleur.EcouteurListeLivreurs;
import com.observer.Observable;
import com.observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
/**
 * Classe de type JPanel pour lister les livreurs enregistrés.
 * Observatrice de GestionnaireLivraisons pour se mettre à jour automatiquement.
 */
public class PanneauLivreurs extends JPanel implements Observateur {

    private final ComposantTable tableLivreurs;

    private final String[] nomsColonnes = {"Id", "Nom", "Type", "Authentifié"};
    private final boolean[] donneesCentrees = {true, false, true, true};

    private final MiniServerUI miniServerUI;
    private final GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Constructeur pour cette classe.
     *
     * @param miniServerUI           La fenêtre qui contient ce panneau.
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public PanneauLivreurs(MiniServerUI miniServerUI, GestionnaireLivraisons gestionnaireLivraisons) {
        this.miniServerUI = miniServerUI;
        this.gestionnaireLivraisons = gestionnaireLivraisons;

        // Créer le composant table
        this.tableLivreurs = new ComposantTable("Liste des livreurs", 350, 200,
                nomsColonnes, donneesCentrees);

        // Mise en page
        this.setLayout(new BorderLayout());
        this.add(this.tableLivreurs, BorderLayout.CENTER);

        // S'enregistrer comme observateur
        this.gestionnaireLivraisons.ajouterObservateur(this);

        // Enregistrer l'écouteur de double-clic
        EcouteurListeLivreurs ecouteur = new EcouteurListeLivreurs(this);
        this.tableLivreurs.enregistrerEcouteur(ecouteur);
    }

    /**
     * Getter pour l'attribut MiniServerUI de cette classe.
     *
     * @return La fenêtre graphique principale.
     */
    public MiniServerUI getMiniServerUI() {
        return this.miniServerUI;
    }

    /**
     * Enregistre un écouteur pour ce panneau de livreurs.
     *
     * @param ecouteurLL L'écouteur à ajouter.
     */
    public void enregisterEcouteur(EcouteurListeLivreurs ecouteurLL) {
        this.tableLivreurs.enregistrerEcouteur(ecouteurLL);
    }

    /**
     * Retourne l'objet Livreur sélectionné dans la table.
     *
     * @return Le livreur sélectionné, ou null si aucun.
     */
    public Livreur livreurSelectionne() {
        int ligne = this.tableLivreurs.ligneSelectionnee();
        if (ligne == -1) {
            return null;
        }
        String idStr = this.tableLivreurs.lireCase(ligne, 0);
        int id = Integer.parseInt(idStr);
        return this.gestionnaireLivraisons.getLivreursEnregistres().rechercher(id);
    }

    /**
     * Calcule les données à afficher dans la table des livreurs.
     *
     * @return La matrice de données.
     */
    private Vector<Vector<String>> calculerDonnees() {
        Vector<Vector<String>> donnees = new Vector<>();
        IListeChaineeLivreurs livreurs = this.gestionnaireLivraisons.getLivreursEnregistres();

        for (Livreur livreur : livreurs) {
            Vector<String> ligne = new Vector<>();
            ligne.add(String.valueOf(livreur.getId()));
            ligne.add(livreur.getNom());
            ligne.add(livreur.type());
            ligne.add(livreur.isAuthentifie() ? "✔" : "✘");
            donnees.add(ligne);
        }
        return donnees;
    }

    /**
     * Mise à jour lors d'une notification de l'observable.
     *
     * @param observable L'objet observable qui notifie.
     */
    @Override
    public void seMettreAJour(Observable observable) {
        if (observable instanceof GestionnaireLivraisons) {
            SwingUtilities.invokeLater(() -> this.tableLivreurs.mettreAJour(calculerDonnees()));
        }
    }
}

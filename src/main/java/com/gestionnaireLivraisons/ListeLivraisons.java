package com.gestionnaireLivraisons;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * La classe qui liste des livraisons.
 */
public class ListeLivraisons implements IListeLivraisons {
    // Les livraisons stockées dans une ArrayList
    private final ArrayList<Livraison> livraisons = new ArrayList<>();

    /**
     * Ajout d'une livraison à la liste de livraisons.
     *
     * @param livraison La livraison à ajouter.
     */
    public void ajouter(Livraison livraison) {
        this.livraisons.add(livraison);
    }

    /**
     * Supprime une livraison par son id.
     *
     * @param idLivraison L'id de la livraison à supprimer.
     * @return La livraison supprimée ou null si non trouvée.
     */
    public Livraison supprimer(int idLivraison) {
        Livraison livraisonTrouvee = null;
        int i = this.chercher(idLivraison);

        if (i != -1) {
            livraisonTrouvee = this.livraisons.get(i);
            this.livraisons.remove(i);
        }
        return livraisonTrouvee;
    }

    /**
     * Recherche une livraison par son id et la retourne.
     *
     * @param idLivraison L'id de la livraison à chercher.
     * @return La livraison trouvée ou null si non trouvée.
     */
    public Livraison rechercher(int idLivraison) {
        Livraison livraisonTrouvee;
        int i = this.chercher(idLivraison);
        livraisonTrouvee = i != -1 ? this.livraisons.get(i) : null;
        return livraisonTrouvee;
    }

    /**
     * Supprime toutes les livraisons en cours pour ce livreur.
     *
     */
    public void vider() {
        this.livraisons.clear();
    }

    /**
     * Teste si la liste est vide.
     *
     * @return true si la liste est vide, false sinon.
     */
    public boolean estVide() {
        return this.livraisons.isEmpty();
    }

    /**
     * Retourne le nombre de livraisons présentes dans cette liste.
     *
     * @return Le nombre de livraisons.
     */
    public int taille() {
        return this.livraisons.size();
    }

    /**
     * Retourne un itérateur pour cette liste.
     *
     * @return Un itérateur pour cette liste.
     */
    @Override
    public ListIterator<Livraison> iterator() {
        return this.livraisons.listIterator();
    }

    /**
     * Méthode privée pour chercher l'indice d'une livraison.
     *
     * @param idLivraison L'id de la livraison à chercher.
     * @return L'indice de la livraison trouvée ou -1 si non trouvée.
     */
    private int chercher(int idLivraison) {
        int idLivraisonTrouvee = 0;
        while (idLivraisonTrouvee < this.livraisons.size() && this.livraisons.get(idLivraisonTrouvee).getId() != idLivraison) {
            idLivraisonTrouvee++;
        }
        idLivraisonTrouvee = idLivraisonTrouvee < this.livraisons.size() ? idLivraisonTrouvee : -1;
        return idLivraisonTrouvee;
    }
}

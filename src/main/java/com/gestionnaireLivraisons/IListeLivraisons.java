package com.gestionnaireLivraisons;

/**
 * CETTE INTERFACE NE DOIT PAS ÊTRE MODIFIÉE
 * Déclare les méthodes nécéssaires à la manipulation d'une liste de livraisons
 *
 */
public interface IListeLivraisons extends Iterable<Livraison> {
    /**
     * Ajoute un objet Livraison dans la liste.
     *
     * @param livraison : L'objet Livraison à ajouter.
     */
    void ajouter(Livraison livraison);

    /**
     * Supprime une livraison par son id.
     *
     * @param idLivraison L'id de la livraison à supprimer.
     * @return La livraison supprimée ou null si non trouvée.
     */
    Livraison supprimer(int idLivraison);

    /**
     * Recherche une livraison par son id et la retourne.
     *
     * @param idLivraison L'id de la livraison à chercher.
     * @return La livraison trouvée ou null si non trouvée.
     */
    Livraison rechercher(int idLivraison);

    /**
     * Supprime toutes les livraisons en cours pour ce livreur.
     *
     */
    void vider();

    /**
     * Teste si la liste est vide.
     *
     * @return true si la liste est vide, false sinon.
     */
    boolean estVide();

    /**
     * Retourne le nombre de livraisons présentes dans cette liste.
     *
     * @return Le nombre de livraisons.
     */
    int taille();
}

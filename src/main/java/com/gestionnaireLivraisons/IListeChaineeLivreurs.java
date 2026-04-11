package com.gestionnaireLivraisons;

public interface IListeChaineeLivreurs extends Iterable<Livreur> {
    /**
     * Ajouter un nouvel objet livreur à la liste.
     * L'ajout se fait en fin de liste.
     * <p>
     * Précondition : Il ne faut pas avoir DEUX livreurs ayant le même ID
     * dans la liste.
     *
     * @param unLivreur Objet Livreur à ajouter.
     * @throws ListeChaineeException lancée s'il existe un livreur avec le ID.
     */
    void ajouter(Livreur unLivreur) throws ListeChaineeException;

    /**
     * Supprime un livreur à partir de son identifiant.
     *
     * @param idLivreur : identifiant du livreur à supprimer.
     * @throws ListeChaineeException si le livreur n'existe pas dans la liste.
     */
    void supprimer(int idLivreur) throws ListeChaineeException;

    /**
     * Recherche l'objet Livreur à partir de son identifiant
     *
     * @param idLivreur identifiant du livreur à retrouver.
     * @return l'objet livreur trouvé, ou null s'il n'existe pas.
     */
    Livreur rechercher(int idLivreur);

    /**
     * Retourne le nombre d'éléments se trouvant dans la liste chaînée.
     *
     * @return Le nombre d'éléments.
     */
    int taille();
}

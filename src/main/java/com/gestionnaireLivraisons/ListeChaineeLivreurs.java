package com.gestionnaireLivraisons;


import java.util.Iterator;

public class ListeChaineeLivreurs implements IListeChaineeLivreurs, Iterable<Livreur> {
    private Noeud tete;
    private Noeud dernier;
    private int nbreElements;

    /**
     * Constructeur
     */
    public ListeChaineeLivreurs() {
        tete = null;
        dernier = null;
        nbreElements = 0;
    }

    /**
     * Ajoute un objet Livreur à la fin de la liste
     *
     * @param unLivreur Le livreur à ajouter.
     * @throws ListeChaineeException Si le livreur est déjà dans la liste.
     */
    @Override
    public void ajouter(Livreur unLivreur) throws ListeChaineeException {
        int idLivreur = unLivreur.getId();
        if (rechercher(idLivreur) == null) {
            Noeud nouveau = new Noeud(unLivreur);
            if (tete == null) { // si la liste est vide, on ajoute en tête.
                tete = nouveau;
            } else {    // sinon, ajouter à la fin
                dernier.suivant = nouveau;
            }
            dernier = nouveau;
            nbreElements++;
        } else {
            throw new ListeChaineeException("Livreur avec même ID déjà dans la liste : " + idLivreur);
        }
    }

    /**
     * Supprime un livreur à partir de son identifiant.
     *
     * @param idLivreur identifiant du livreur à supprimer.
     * @throws ListeChaineeException si le livreur n'existe pas dans la liste.
     */
    @Override
    public void supprimer(int idLivreur) throws ListeChaineeException {
        Noeud precedent = null;
        Noeud courant = this.tete;

        while (courant != null && courant.livreur.getId() != idLivreur) {
            precedent = courant;
            courant = courant.suivant;
        }

        if (courant == null) {  // pas trouvé
            throw new ListeChaineeException("Livreur non trouvé dans la liste : " + idLivreur);
        }

        if (courant == this.tete) {  // extrait en tête
            this.tete = courant.suivant;
        }

        if (courant.suivant == null) {  // extrait en queue
            this.dernier = precedent;
        }

        if (precedent != null) {
            precedent.suivant = courant.suivant;
        }

        this.nbreElements--;
    }

    /**
     * Recherche un livreur par son id et le retourne.
     *
     * @param idLivreur identifiant du livreur à retrouver.
     * @return Le livreur ou null si non trouvé.
     */
    @Override
    public Livreur rechercher(int idLivreur) {
        Livreur trouve;
        Noeud courant = this.tete;
        while (courant != null && courant.livreur.getId() != idLivreur) {
            courant = courant.suivant;
        }
        trouve = courant != null ? courant.livreur : null;
        return trouve;
    }

    /**
     * Retourne le nombre d'éléments se trouvant dans la liste chaînée.
     *
     * @return Le nombre d'éléments.
     */
    @Override
    public int taille() {
        return this.nbreElements;
    }


    /**
     * Implémente un neoud de la liste chainée
     *
     */
    private static class Noeud {
        private final Livreur livreur;
        private Noeud suivant;

        Noeud(Livreur livreur) {
            this.livreur = livreur;
            this.suivant = null;
        }
    }

    // implémentation de l'interface Iterable
    @Override
    public Iterator<Livreur> iterator() {
        return new IterateurListeChaineeLivreur();
    }

    private class IterateurListeChaineeLivreur implements Iterator<Livreur> {
        Noeud courant = ListeChaineeLivreurs.this.tete;

        @Override
        public boolean hasNext() {
            return courant != null;
        }

        @Override
        public Livreur next() {
            Livreur res = this.courant.livreur;
            courant = courant.suivant;
            return res;
        }
    }
}

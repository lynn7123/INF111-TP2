package com.gestionnaireLivraisons;

import com.observer.Observable;

import java.util.Iterator;

/**
 * La classe qui modélise un livreur.
 */
public abstract class Livreur extends Observable {
    // Les attrbuts d'un livreur
    private final int id;
    private final String nom;
    private boolean authentifie;

    private final IListeLivraisons livraisonsEnCours;
    private final IListeLivraisons livraisonsEffectuees;

    /**
     * Construit un nouveau livreur.
     *
     * @param id  L'id du nouveau livreur.
     * @param nom Le nom du nouveau livreur.
     */
    public Livreur(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.authentifie = false;

        this.livraisonsEnCours = new ListeLivraisons();
        this.livraisonsEffectuees = new ListeLivraisons();
    }

    /**
     * Getter pour l'id du livreur.
     *
     * @return L'id de ce livreur.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter pour le nom du livreur.
     *
     * @return Le nom de ce livreur.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Getter pour les livraisons en cours de ce livreur.
     *
     * @return La liste des livraisons en cours pour ce livreur.
     */
    public IListeLivraisons getLivraisonsEnCours() {
        return this.livraisonsEnCours;
    }

    /**
     * Getter pour les livraisons effectuées par ce livreur.
     *
     * @return La liste des livraisons effectuées par ce livreur.
     */
    public IListeLivraisons getLivraisonsEffectuees() {
        return this.livraisonsEffectuees;
    }

    /**
     * Getter pour l'authentification de ce livreur.
     *
     * @return true si ce livreur est authentifié, false sinon.
     */
    public boolean isAuthentifie() {
        return this.authentifie;
    }

    /**
     * Setter pour l'authentification de ce livreur.
     *
     * @param authentifie La valeur d'authentification à placer pour ce livreur.
     */
    public void setAuthentifie(boolean authentifie) {
        this.authentifie = authentifie;
    }

    /**
     * Vérifie si un livreur a des livraisons en cours.
     *
     * @return true si oui, false sinon.
     */
    public boolean estSansLivraisonsEnCours() {
        return this.livraisonsEnCours.estVide();
    }

    /**
     * Ajoute une livraison aux livraisons en cours.
     *
     * @param livraison La livraison à ajouter.
     */
    public void ajouterLivraisonEnCours(Livraison livraison) {
        this.livraisonsEnCours.ajouter(livraison);
        notifierObservateurs();
    }

    /**
     * Ajoute une livraison aux livraisons effectuées.
     *
     * @param livraison La livraison à ajouter.
     */
    public void ajouterLivraisonEffectuee(Livraison livraison) {
        this.livraisonsEffectuees.ajouter(livraison);
        notifierObservateurs();
    }

    /**
     * Supprimer une livraison des livraisons en cours.
     *
     * @param idLivraison L'i de la livraison à supprimer.
     * @return La livraison supprimée ou null si non trouvée.
     */
    public Livraison supprimerLivraisonEnCours(int idLivraison) {
        Livraison livraison = this.livraisonsEnCours.supprimer(idLivraison);
        if (livraison != null) notifierObservateurs();
        return livraison  ;
    }

    /**
     * Supprime et retourne toutes les livraisons en cours pour ce livreur.
     *
     * @return La liste des livraisons en cours avant suppressions.
     */
    public IListeLivraisons supprimerToutesLesLivraisonsEnCours() {
        IListeLivraisons liste = new ListeLivraisons();
        for (Livraison livraison : this.livraisonsEnCours) {
            liste.ajouter(livraison);
        }
        this.livraisonsEnCours.vider();
        notifierObservateurs();
        return liste;
    }

    /**
     * Retourne une livraison d'après son id.
     *
     * @param idLivraison L'id de la livraison à trouver.
     * @return La livraison si trouvée, null sinon.
     */
    public Livraison rechercherLivraisonEnCours(int idLivraison) {
        return this.livraisonsEnCours.rechercher(idLivraison);
    }

    /**
     * Calcule et retourne les revenus générés par ce livreur.
     *
     * @return Le revenu calculé.
     */
    public abstract double calculerRevenu();

    /**
     * Retourne la capacité de livraison pour ce livreur.
     *
     * @return La capacité du livreur.
     */
    public abstract int capaciteLivraison();

    /**
     * Retourne une chaîne de caractères qui correspond au type de livreur.
     *
     * @return La chaîne correspondant au moyen de transport.
     */
    public abstract String type();

    /**
     * Retourne le nombre de livraisons en cours pour ce livreur.
     *
     * @return Le nombre de livraisons en cours.
     */
    public int nbLivraisonsEnCours() {
        return this.livraisonsEnCours.taille();
    }


    /**
     * Retourne un itérateur sur les livraisons en cours de ce livreur.
     *
     * @return L'itérateur.
     */
    public Iterator<Livraison> donneIterateurLivraisonsEnCours() {
        return this.livraisonsEnCours.iterator();
    }

    /**
     * Retourne le nombre de livraisons que ce livreur a effectué
     *
     * @return Le nombre de livraisons.
     */
    public int nbLivraisonsEffectuees() {
        return this.livraisonsEffectuees.taille();
    }

    @Override
    public String toString() {
        return String.format("%d %-12s {type} %s", this.id, this.nom, this.authentifie ? "authentifié" : "non authentifié");
    }
}

package com.gestionnaireLivraisons;

/**
 * La classe de livreur à vélo
 */
public class LivreurVelo extends Livreur {
    // L'attribut de classe pour la capacité d'un tel livreur
    static final private int CAPACITE_LIVRAISON = 2;

    /**
     * Construit un nouveau livreur à vélo
     *
     * @param id  L'id du nouveau livreur.
     * @param nom Le nom du nouveau livreur.
     */
    public LivreurVelo(int id, String nom) {
        super(id, nom);
    }

    /**
     * Construit et retourne une chaîne de caractères qui caractérise ce livreur
     *
     * @return La chaîne caractérisant ce livreur
     */
    @Override
    public String toString() {
        return String.format("%d VELO %s", this.getId(), this.getNom());
    }

    /**
     * Calcule et retourne le revenu total de ce livreur pour cette session.
     *
     * @return Le revenu calculé.
     */
    @Override
    public double calculerRevenu() {
        return 5 * this.nbLivraisonsEffectuees();
    }

    /**
     * Retourne la capacité de livraison pour un livreur à vélo.
     *
     * @return La capacité de ce livreur à vélo.
     */
    @Override
    public int capaciteLivraison() {
        return LivreurVelo.CAPACITE_LIVRAISON;
    }

    @Override
    public String type() {
        return "vélo";
    }
}

package com.gestionnaireLivraisons;

/**
 * La classe de livreur en camion
 */
public class LivreurCamion extends Livreur {
    // L'attribut de classe pour la capacité d'un tel livreur
    private static final int CAPACITE_LIVRAISON = 8;

    /**
     * Construit un nouveau livreur en camion
     *
     * @param id  L'id du nouveau livreur.
     * @param nom Le nom du nouveau livreur.
     */
    public LivreurCamion(int id, String nom) {
        super(id, nom);
    }

    /**
     * Construit et retourne une chaîne de caractères qui caractérise ce livreur
     *
     * @return La chaîne caractérisant ce livreur
     */
    @Override
    public String toString() {
        return String.format("%d CAMION %s", this.getId(), this.getNom());
    }

    /**
     * Calcule et retourne le revenu total de ce livreur pour cette session.
     *
     * @return Le revenu calculé.
     */
    @Override
    public double calculerRevenu() {
        return this.nbLivraisonsEffectuees() * 10.0;
    }

    /**
     * Retourne la capacité de livraison pour un livreur en camion.
     *
     * @return La capacité de ce livreur en camion.
     */
    @Override
    public int capaciteLivraison() {
        return LivreurCamion.CAPACITE_LIVRAISON;
    }

    @Override
    public String type() {
        return "camion";
    }
}

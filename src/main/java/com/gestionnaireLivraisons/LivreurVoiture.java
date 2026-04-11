package com.gestionnaireLivraisons;

/**
 * La classe de livreur en voiture
 */
public class LivreurVoiture extends Livreur {
    // L'attribut de classe pour la capacité d'un tel livreur
    static final int CAPACITE_LIVRAISON = 5;

    /**
     * Construit un nouveau livreur en voiture.
     *
     * @param id  L'id du nouveau livreur.
     * @param nom Le nom du nouveau livreur.
     */
    public LivreurVoiture(int id, String nom) {
        super(id, nom);
    }

    /**
     * Construit et retourne une chaîne de caractères qui caractérise ce livreur.
     *
     * @return La chaîne caractérisant ce livreur.
     */
    @Override
    public String toString() {
        return String.format("%d VOITURE %s", this.getId(), this.getNom());
    }

    /**
     * Calcule et retourne le revenu total de ce livreur pour cette session.
     *
     * @return Le revenu calculé.
     */
    @Override
    public double calculerRevenu() {
        return 7.5 * this.nbLivraisonsEffectuees();
    }

    /**
     * Retourne la capacité de livraison pour un livreur en voiture.
     *
     * @return La capacité de ce livreur en voiture.
     */
    @Override
    public int capaciteLivraison() {
        return LivreurVoiture.CAPACITE_LIVRAISON;
    }

    @Override
    public String type() {
        return "voiture";
    }
}

package com.gestionnaireLivraisons;

/**
 * Implémente la création d'objets Livraison pour l'initialisation du système.
 * Au démarrage du serveur, un nombre d'objets Livraisons sont créés.
 *
 */
public class LivraisonFactory {

    // Pour stocker les données des livraisons, sous la forme de chaines de caractères.
    // Chaque chaine de caractères contient les données d'une livraison
    // Les données d'une livraison sont séparées par le 2 points ":"
    private static String[] dataLivraisons;

    // Initialisation d'un tableau des données de livraison
    static {
        // Possibilité 1 : pour l'initialisation des données de livraison
        livraisonsSerieI();

        // Possibilité 2 : pour l'initialisation des données de livraison
        // livraisonsSerieII();
    }

    /**
     * Données pour 6 livraisons
     * Le format : id:lot:priorité
     */
    private static void livraisonsSerieI() {
        dataLivraisons = new String[6];
        dataLivraisons[0] = "1:NORMALE";
        dataLivraisons[1] = "1:URGENTE";
        dataLivraisons[2] = "1:NORMALE";
        dataLivraisons[3] = "2:URGENTE";
        dataLivraisons[4] = "2:NORMALE";
        dataLivraisons[5] = "2:NORMALE";
    }

    /**
     * Données pour 10 livraisons
     * Le format : id:lot:priorité
     */
    private static void livraisonsSerieII() {
        dataLivraisons = new String[10];
        dataLivraisons[0] = "1:NORMALE";
        dataLivraisons[1] = "1:URGENTE";
        dataLivraisons[2] = "1:NORMALE";
        dataLivraisons[3] = "1:URGENTE";
        dataLivraisons[4] = "1:NORMALE";
        dataLivraisons[5] = "2:NORMALE";
        dataLivraisons[6] = "2:URGENTE";
        dataLivraisons[7] = "2:URGENTE";
        dataLivraisons[8] = "2:NORMALE";
        dataLivraisons[9] = "2:NORMALE";
    }

    /**
     * Méthode pour la création d'objets Livraison à partir de données prédéfinies
     * sous la forme de chaines de caractères.
     *
     * @return La file de priorité de livraisons calculée.
     */
    public static FilePrioriteLivraisons populateFileLivraisons() {
        FilePrioriteLivraisons filePrioriteLivraisons = new FilePrioriteLivraisons();
        int lot;
        Livraison uneLivraison;
        for (String dataLivr : LivraisonFactory.dataLivraisons) {
            String[] dataList = dataLivr.split(":");
            try {
                lot = Integer.parseInt(dataList[0]);
                Priorite priorite = Priorite.valueOf(dataList[1]);
                uneLivraison = new Livraison(priorite, lot);
                filePrioriteLivraisons.ajouter(uneLivraison);
            } catch (IllegalArgumentException e) {
                System.err.println("Données de livraison non conforme : " + dataLivr);
            }
        }
        return filePrioriteLivraisons;
    }
}

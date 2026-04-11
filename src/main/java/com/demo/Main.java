package com.demo;

import com.atoudeft.serveur.Config;
import com.atoudeft.serveur.Serveur;
import com.gestionnaireLivraisons.*;

import java.util.Scanner;

/**
 * Programme simple de démonstration d'un serveur. Le programme démarre un serveur qui se met à écouter
 * l'arrivée de connexions.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Main {
    /**
     * Affiche la liste des commandes utilisables dans la console du serveur.
     *
     */
    private static void afficherCommandes() {
        System.out.println("Liste des commandes disponibles :");
        System.out.println("  - HELP : afficher la liste des commandes.");
        System.out.println("  - LIST : afficher les livraisons à faire.");
        System.out.println("  - STATS : afficher les statistiques de livraison.");  //  TODO
        System.out.println("  - EXIT : arrêter le serveur.");
        System.out.println();
    }

    /**
     * Méthode principale du programme.
     *
     * @param args Arguments du programme
     */
    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        Serveur serveur = new Serveur(Config.PORT_SERVEUR);
        GestionnaireLivraisons gestionnaireLivraisons = new GestionnaireLivraisons();

        gestionnaireLivraisons.initialiser();

        // Démarrage du serveur
        if (serveur.demarrer(gestionnaireLivraisons)) {
            System.out.println("Serveur a l'écoute sur le port " + serveur.getPort());
        }

        // Affichage des commandes console disponibles
        Main.afficherCommandes();

        // Gestion de la console
        boolean fin = false;
        do {
            System.out.print("GESTION > ");
            String saisie = clavier.nextLine();

            switch (saisie.toUpperCase()) {
                case "EXIT":
                    fin = true;
                    break;
                case "HELP":
                    Main.afficherCommandes();
                    break;
                case "LIST":
                    gestionnaireLivraisons.afficherLivraisonsAEffectuer();
                    break;
                case "STATS":
                    gestionnaireLivraisons.afficherStatistiques();
                    break;
                default:
                    System.out.printf("Commande '%s' inconnue.", saisie);
            }
        } while (!fin);

        // Arrêt du serveur
        serveur.arreter();
        gestionnaireLivraisons.quitter();
    }
}
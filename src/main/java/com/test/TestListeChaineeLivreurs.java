package com.test;

import com.gestionnaireLivraisons.*;

import java.util.Arrays;

public class TestListeChaineeLivreurs {

    /**
     * Test de la méthode ajouter.
     */
    private static void testAjouter() {
        System.out.print("Test ajouter : ");

        ListeChaineeLivreurs liste = new ListeChaineeLivreurs();
        assert liste.taille() == 0;

        Livreur livreur;
        try {
            livreur = new LivreurCamion(34, "Pierre");
            liste.ajouter(livreur);
            assert liste.taille() == 1;

            livreur = new LivreurCamion(12, "Pierre");
            liste.ajouter(livreur);
            assert liste.taille() == 2;

            livreur = new LivreurCamion(34, "Pierre");
            liste.ajouter(livreur);
            assert false;
        } catch (ListeChaineeException ignored) {
        }

        System.out.println("PASSED");
    }

    /**
     * Test de la méthode supprimer.
     */
    private static void testSupprimer() {
        System.out.print("Test supprimer : ");

        ListeChaineeLivreurs liste = new ListeChaineeLivreurs();
        assert liste.taille() == 0;

        Livreur livreur;
        try {
            livreur = new LivreurCamion(34, "Pierre");
            liste.ajouter(livreur);
            assert liste.taille() == 1;

            liste.supprimer(67);
            assert liste.taille() == 1;

            liste.supprimer(34);
            assert liste.taille() == 0;
        } catch (ListeChaineeException ignored) {
        }

        System.out.println("PASSED");
    }

    /**
     * Test de la méthode rechercher.
     */
    private static void testRechercher() {
        System.out.print("Test rechercher : ");

        ListeChaineeLivreurs liste = new ListeChaineeLivreurs();
        assert liste.taille() == 0;

        try {
            Livreur pierre = new LivreurCamion(34, "Pierre");
            liste.ajouter(pierre);
            Livreur paul = new LivreurVelo(45, "Paul");
            liste.ajouter(paul);
            Livreur jacques = new LivreurVelo(89, "Jacques");
            liste.ajouter(jacques);

            Livreur trouve = liste.rechercher(30);
            assert trouve == null;

            trouve = liste.rechercher(34);
            assert trouve == pierre;
            trouve = liste.rechercher(45);
            assert trouve == paul;
            trouve = liste.rechercher(89);
            assert trouve == jacques;
        } catch (ListeChaineeException ignored) {
        }

        System.out.println("PASSED");
    }

    /**
     * Test de la méthode taille.
     */
    private static void testTaille() {
        System.out.print("Test taille : ");

        ListeChaineeLivreurs liste = new ListeChaineeLivreurs();
        assert liste.taille() == 0;

        System.out.println("PASSED");
    }

    /**
     * Test de la méthode toArray.
     */
    private static void testToArray() {
        System.out.print("Test toArray : ");

        ListeChaineeLivreurs liste = new ListeChaineeLivreurs();
        assert liste.taille() == 0;

        try {
            Livreur pierre = new LivreurCamion(34, "Pierre");
            liste.ajouter(pierre);
            Livreur paul = new LivreurVelo(45, "Paul");
            liste.ajouter(paul);
            Livreur jacques = new LivreurVelo(89, "Jacques");
            liste.ajouter(jacques);

            var iter = liste.iterator();

            for (Livreur livreur : Arrays.asList(pierre, paul, jacques)) {
                assert iter.next() == livreur;
            }
        } catch (ListeChaineeException ignored) {
        }

        System.out.println("PASSED");
    }

    /**
     * Lancements des tests pour la classe ListeChaineeLivreurs.
     */
    public static void tests() {
        System.out.println("----- TEST ListeChaineeLivreurs -----");

        TestListeChaineeLivreurs.testTaille();
        TestListeChaineeLivreurs.testAjouter();
        TestListeChaineeLivreurs.testSupprimer();
        TestListeChaineeLivreurs.testRechercher();
        TestListeChaineeLivreurs.testToArray();
    }
}

package com.test;

import com.gestionnaireLivraisons.FilePrioriteLivraisons;
import com.gestionnaireLivraisons.Livraison;
import com.gestionnaireLivraisons.Priorite;

import java.util.Arrays;
import java.util.Random;

public class TestFilePrioriteLivraisons {
    /**
     * Test de la méthode estVide.
     */
    private static void testEstVide() {
        System.out.print("Test estVide : ");

        FilePrioriteLivraisons file = new FilePrioriteLivraisons();
        assert file.estVide();

        file.ajouter(new Livraison(Priorite.NORMALE, 0));
        assert !file.estVide();

        System.out.println("PASSED");
    }

    /**
     * Test de la méthode ajouter.
     */
    private static void testAjouter() {
        System.out.print("Test ajouter : ");

        FilePrioriteLivraisons file = new FilePrioriteLivraisons();

        Livraison livraison = new Livraison(Priorite.NORMALE, 0);
        file.ajouter(livraison);
        assert file.taille() == 1;
        assert file.retirer() == livraison;

        System.out.println("PASSED");
    }

    /**
     * Test de la méthode ajouterTout.
     */
    private static void testAjouterTout() {
        System.out.print("Test ajouterTout : ");

        FilePrioriteLivraisons file = new FilePrioriteLivraisons();

        Priorite[] priorites = {Priorite.NORMALE, Priorite.URGENTE};
        Livraison[] tab = new Livraison[5];
        Random rnd = new Random();
        for (int i = 0; i < tab.length; i++) {
            Priorite priorite = priorites[Math.abs(rnd.nextInt()) % Priorite.values().length];
            int lot = 1 + rnd.nextInt() % 5;
            tab[i] = new Livraison(priorite, lot);
        }
        Livraison[] listeOrdonnee = tab.clone();
        Arrays.sort(listeOrdonnee);

        file.ajouterTout(Arrays.asList(tab));
        assert file.taille() == tab.length;

        int i = 0;
        while (!file.estVide()) {
            Livraison valeur = file.retirer();
            assert valeur.compareTo(listeOrdonnee[i]) == 0;
            i++;
        }

        System.out.println("PASSED");
    }

    /**
     * Test de la méthode taille.
     */
    private static void testTaille() {
        System.out.print("Test taille : ");

        FilePrioriteLivraisons file = new FilePrioriteLivraisons();
        assert file.taille() == 0;
        file.ajouter(new Livraison(Priorite.NORMALE, 0));
        assert file.taille() == 1;

        System.out.println("PASSED");
    }

    /**
     * Test de la méthode retirer.
     */
    private static void testRetirer() {
        System.out.print("Test retirer : ");

        FilePrioriteLivraisons file = new FilePrioriteLivraisons();

        Priorite[] priorites = {Priorite.NORMALE, Priorite.URGENTE};
        Livraison[] tab = new Livraison[5];
        Random rnd = new Random();
        for (int i = 0; i < tab.length; i++) {
            Priorite priorite = priorites[Math.abs(rnd.nextInt()) % Priorite.values().length];
            int lot = 1 + rnd.nextInt() % 5;
            tab[i] = new Livraison(priorite, lot);
        }
        Livraison[] listeOrdonnee = tab.clone();
        Arrays.sort(listeOrdonnee);

        file.ajouterTout(Arrays.asList(tab));
        assert file.taille() == tab.length;

        int i = 0;
        while (!file.estVide()) {
            Livraison valeur = file.retirer();
            assert valeur.compareTo(listeOrdonnee[i]) == 0;
            i++;
        }

        System.out.println("PASSED");
    }

    /**
     * Lancements des tests pour la classe FilePrioriteLivraisons.
     */
    public static void tests() {
        System.out.println("----- TEST FilePrioriteLivraisons -----");

        TestFilePrioriteLivraisons.testEstVide();
        TestFilePrioriteLivraisons.testAjouter();
        TestFilePrioriteLivraisons.testAjouterTout();
        TestFilePrioriteLivraisons.testTaille();
        TestFilePrioriteLivraisons.testRetirer();
    }
}

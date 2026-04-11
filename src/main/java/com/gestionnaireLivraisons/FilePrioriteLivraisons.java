package com.gestionnaireLivraisons;

import java.util.Iterator;
import java.util.PriorityQueue;

// import static com.gestionnaireLivraisons.Livraison;

/**
 * La classe générique des files de priorité.
 *
 */
public class FilePrioriteLivraisons implements Iterable<Livraison> {
    // Les livraisons stockées dans une file de priorité
    private final PriorityQueue<Livraison> file;

    /**
     * Construit une file de priorité.
     */
    public FilePrioriteLivraisons() {
        this.file = new PriorityQueue<>();
    }

    /**
     * Retire et retourne l'élément le plus prioritaire de la file.
     *
     * @return L'élément le plus prioritaire.
     */
    public Livraison retirer() {
        return this.file.remove();
    }

    /**
     * Ajoute un élément à la file.
     *
     * @param element L'élément à ajouter à la file.
     */
    public void ajouter(Livraison element) {
        this.file.add(element);
    }

    /**
     * Ajoute un ensemble d'éléments à la file.
     *
     * @param elements L'ensemble des éléments à ajouter à la file.
     */
    public void ajouterTout(Iterable<Livraison> elements) {
        for (Livraison elt : elements) {
            this.file.add(elt);
        }
    }

    /**
     * Indique si la file est vide.
     *
     * @return True si la file est vide. False sinon.
     */
    public boolean estVide() {
        return this.file.isEmpty();
    }

    /**
     * Retourne le nombre d'éléments dans cette file de priorité.
     *
     * @return Le nombre d'éléments de la file.
     */
    public int taille() {
        return this.file.size();
    }

    /**
     * Affiche les éléments contenus dans la file.
     *
     */
    public void afficher() {
        for (Livraison elt : this.file) {
            System.out.println(elt);
        }
    }

    @Override
    public Iterator<Livraison> iterator() {
        return this.file.stream().iterator();
    }
}

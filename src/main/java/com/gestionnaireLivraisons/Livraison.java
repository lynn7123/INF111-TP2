package com.gestionnaireLivraisons;

/**
 * La classe qui modélise une livraison.
 */
public class Livraison
        implements Comparable<Livraison>
{
    // Les données membres statiques
    private static int prochainId = 1;              // id de la prochaine livraison
    private static int lotMaximal = -1;
    private static final int MAX_TENTATIVES = 3;    // nombre maximal de tentatives pour effectuer une livraison

    // Les attributs d'instance
    private final int id;               // identifiant unique d'une livraison
    private final Priorite priorite;    // priorite de la livraison (NORMAL ou URGENT)
    private int tentative;              // numéro de tentative pour cette livraison
    private final int lot;              // numéro du lot dans lequel cette livraison a été créé

    private Statut statut;              // le statut de la commande (EN_ATTENTE, EN_COURS, LIVREE ou ECHOUEE)

    /**
     * Constructeur d'une livraison.
     *
     * @param priorite La priorité de la nouvelle livraison.
     * @param lot      Le lot auquel cette livraison appartient.
     */
    public Livraison(Priorite priorite, int lot) {
        this.id = prochainID(); // Livraison.prochainI;

        this.priorite = priorite;
        this.tentative = 0;
        this.lot = lot;
        if (this.lot > Livraison.lotMaximal) {
            Livraison.lotMaximal = this.lot;
        }
        this.statut = Statut.EN_ATTENTE;
    }

    /**
     * Produit un nouvel ID pour la Livraison
     */
    private static int prochainID() {
        return prochainId++;
    }

    /**
     * Retourne le lot maximal utilisé à date.
     *
     * @return Le lot maximal.
     */
    public static int lotMaximal() {
        return Livraison.lotMaximal;
    }

    /**
     * Valide un numéro de lot selon le lot maximal de la classe Livraison
     *
     * @param lot Le numétor de lot à valider.
     * @return true si le numéro est valide, false sinon.
     */
    public static boolean validerLotLivraison(int lot) {
        return lot > 0 && lot <= Livraison.lotMaximal() + 1;
    }


    /**
     * Retourne l'identifiant de cette livraison.
     *
     * @return L'id de cette livraison.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retourne la priorité de cette livraison.
     *
     * @return La priorite de cette livraison.
     */
    public Priorite getPriorite() {
        return this.priorite;
    }

    /**
     * Retourne la tentative pour cette livraison.
     *
     * @return La tentative de cette livraison.
     */
    public int getTentative() {
        return this.tentative;
    }

    /**
     * Retourne le lot auquel appartient cette livraison.
     *
     * @return Le lot de cette livraison.
     */
    public int getLot() {
        return this.lot;
    }

    /**
     * Mutateur pour le statut de la livraison.
     *
     */
    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    /**
     * Getter pour le statut d'une livraison.
     *
     * @return Le statut de cette livraison.
     */
    public Statut getStatut() {
        return this.statut;
    }

    /**
     * Ajoute UN au numéro de tentative pour cette livraison.
     *
     */
    public void nouvelleTentative() {
        this.tentative++;
    }

    /**
     * Annule la tentative courante pour cette livraison.
     *
     */
    public void annulerTentative() {
        this.tentative--;
    }

    /**
     * Vérifie s'il reste des tentatives ou non pour cette livraison.
     *
     * @return true s'il reste des tentatives, false sinon.
     */
    public boolean resteTentatives() {
        return this.tentative < Livraison.MAX_TENTATIVES;
    }

    /**
     * Construit et retourne une chaîne de caractères équivalente à cette livraison.
     *
     * @return String La chaîne qui représente cette livraison.
     */
    @Override
    public String toString() {
        return String.format("Livraison ID : %d Lot : %d Priorité : %s Tentative : %d",
                this.id,
                this.lot,
                this.priorite.toString(), //  == Priorite.NORMALE ? "NORMAL" : "URGENT",
                this.tentative);
    }

    /**
     * Compare cette livraison avec une autre livraison.
     *
     * @param autreLivraison La seconde livraison à comparer avec cette livraison.
     * @return Le résultat de la comparaison au sens de l'interface Comparable<T>.
     */
    @Override
    public int compareTo(Livraison autreLivraison) {
        int resultat = 0;
        final int plusPrioritaire = -1;
        final int moinsPrioritaire = 1;

        //  critères : No du lot, puis priorité et enfin numéro de tentative
        if (this.lot != autreLivraison.lot) {
            resultat = this.lot < autreLivraison.lot ? plusPrioritaire : moinsPrioritaire;
        } else if (this.priorite != autreLivraison.priorite) {
            resultat = this.priorite == Priorite.URGENTE ? plusPrioritaire : moinsPrioritaire;
        } else if (this.tentative > autreLivraison.tentative) {
            resultat = plusPrioritaire;
        } else if (this.tentative < autreLivraison.tentative) {
            resultat = moinsPrioritaire;
        }
        return resultat;
    }
}

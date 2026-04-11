package com.atoudeft.commun.evenement;

/**
 * La classe pour gérer les arguments d'un évènement.
 */
public class Arguments {
    private String arguments;

    /**
     * Constructeur pour ce type.
     *
     * @param evenement L'évènement duquel on veut extraire les arguments.
     */
    public Arguments(Evenement evenement) {
        this.arguments = evenement.getArgument().trim();
    }

    /**
     * Enlève et retourne le prochain argument.
     *
     * @return Le mot suivant dans la chaîne d'arguments, ou null si la chaîne d'arguments est vide.
     */
    public String extraireArgumentSuivant() {
        String resultat = null;

        if (!this.arguments.isEmpty()) {
            int i = this.arguments.indexOf(' ');
            if (i == -1) {
                resultat = this.arguments;
                this.arguments = "";
            } else {
                resultat = this.arguments.substring(0, i);
                this.arguments = this.arguments.substring(i).trim();
            }
        }

        return resultat;
    }

    /**
     * Retourne la chaîne des arguments restants.
     *
     * @return La chaîne de caractères restante.
     */
    public String lire() {
        return this.arguments;
    }
}

package com.observer;

import java.util.ArrayList;

/**
 * @author A. Toudeft
 * @since 2002
 * @version 1.0
 */
public class Observable {
    //Liste des objets qui observent cet Observable :
    final private ArrayList<Observateur> observateurs = new ArrayList<>();

    //Ajoute un observateur à la liste, s'il n'y est pas déjà :
    public boolean ajouterObservateur(Observateur observateur) {

        boolean ajoutEffectue;

        if (this.observateurs.contains(observateur))
            ajoutEffectue = false;
        else {
            this.observateurs.add(observateur);
            ajoutEffectue = true;
        }
        return ajoutEffectue;
    }

    //Demande à tous les observateurs de se mettre à jour :
    public void notifierObservateurs() {

        for (Observateur observateur : this.observateurs) observateur.seMettreAJour(this);
    }
}

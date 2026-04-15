package com.gestionnaireLivraisons;

import com.atoudeft.commun.evenement.Arguments;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;
import com.observer.Observable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * La classe pour gérer les erreurs d'identification des clients
 */
class AuthenticationException extends Exception {
}

/**
 * La classe qui permet de gérer des livraisons selon des évènements envoyés par un livreur (client).
 */
public class GestionnaireLivraisons extends Observable implements GestionnaireEvenement {
    // Attributs d'ínstance pour un GestionnaireLivraisons
    final private IListeChaineeLivreurs livreursEnregistres;
    final private Hashtable<Connexion, Livreur> livreursAuthentifies;
    private FilePrioriteLivraisons livraisonsAEffectuer;
    final private IListeLivraisons livraisonsEchouees;

    final private ArrayList<String> messagesId;

    private String trace;

    /**
     * Construit un gestionnaire de livraisons.
     *
     */
    public GestionnaireLivraisons() {
        this.livraisonsAEffectuer = new FilePrioriteLivraisons();
        this.livreursEnregistres = new ListeChaineeLivreurs();
        this.livreursAuthentifies = new Hashtable<>();
        this.livraisonsEchouees = new ListeLivraisons();

        this.messagesId = new ArrayList<>();
    }

    /**
     * Lit les livreurs enregistrés et prépare des livraisons à effectuer.
     *
     */
    public void initialiser() {
        this.lireFichierLivreurs();
        this.livraisonsAEffectuer = LivraisonFactory.populateFileLivraisons();
        this.notifierObservateurs();
    }

    /**
     * Retourne la liste des livreurs enregistrés.
     *
     * @return La liste des livreurs enregistrés.
     */
    public IListeChaineeLivreurs getLivreursEnregistres() {
        return this.livreursEnregistres;
    }

    /**
     * Retourne la table de hachage des livreurs authentifiés.
     *
     * @return La table de hachage des livreurs authentifiés.
     */
    public Hashtable<Connexion, Livreur> getLivreursAuthentifies() {
        return this.livreursAuthentifies;
    }

    /**
     * Retourne la liste des livraisons à effectuer.
     *
     * @return La liste des livraisons à effectuer.
     */
    public FilePrioriteLivraisons getLivraisonsAEffectuer() {
        return this.livraisonsAEffectuer;
    }

    /**
     * Retourne la liste des livraisons qui ont échoué.
     *
     * @return La liste des livraisons qui ont échoué.
     */
    public IListeLivraisons getLivraisonsEchouees() {
        return this.livraisonsEchouees;
    }

    /**
     * Retourne la trace courante à afficher dans la console, et l'annule.
     *
     * @return La chaîne de caractères constituant la trace.
     */
    public String consommerTrace() {
        String trace = this.trace;
        this.trace = "";
        return trace;
    }


    /**
     * Lit le fichier des livreurs enregistrés.
     */
    private void lireFichierLivreurs() {
        try {
            this.notifierEcouteursConsole("Lecture du fichier des livreurs...");

            List<String> lignes = Files.readAllLines(Path.of(Config.fichierLivreurs), StandardCharsets.UTF_8);

            for (String ligne : lignes) {
                ligne = ligne.trim();
                if (ligne.charAt(0) != '#') {   //  ignorer les commentaires.
                    Arguments args = new Arguments(new Evenement(null, null, ligne));
                    int idLivreur = Integer.parseInt(args.extraireArgumentSuivant());
                    String typeLivreur = args.extraireArgumentSuivant().toUpperCase();
                    String nomLivreur = args.lire();
                    Livreur livreur;

                    // Créer le livreur avec le constructeur approprié
                    switch (typeLivreur) {
                        case "VELO":
                            livreur = new LivreurVelo(idLivreur, nomLivreur);
                            break;
                        case "CAMION":
                            livreur = new LivreurCamion(idLivreur, nomLivreur);
                            break;
                        case "VOITURE":
                            livreur = new LivreurVoiture(idLivreur, nomLivreur);
                            break;
                        default:
                            throw new IOException();
                    }
                    this.livreursEnregistres.ajouter(livreur);
                    this.notifierObservateurs();
                    this.notifierEcouteursConsole(ligne);
                }
            }
        } catch (IOException | ListeChaineeException e) {
            System.err.println("ERREUR dans la lecture du fichier de livreurs.");
            System.exit(-1);
        }
    }

    /**
     * Écrit le fichier des livreurs enregistrés.
     */
    private void ecrireFichierLivreurs() {
        try {
            StringBuilder contenu = new StringBuilder();

            for (Livreur livreur : this.livreursEnregistres) {
                contenu.append(livreur).append("\n");
            }
            contenu.insert(0, "#  structure <id livreur> <type livreur> <nom livreur>\n");

            Files.writeString(Path.of(Config.fichierLivreurs), contenu.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("ERREUR dans l'écriture du fichier de livreurs.");
            System.exit(-1);
        }
    }

    /**
     * Lance la sauvegarde des livreurs enregistrés avant de quitter le serveur.
     *
     */
    public void quitter() {
        this.ecrireFichierLivreurs();
    }

    /**
     * Affiche l'ensemble des livraisons à effectuer.
     *
     */
    public void afficherLivraisonsAEffectuer() {
        System.out.println("Liste des livraisons à effectuer :");
        System.out.println("(id, lot, priorité, tentative)");
        this.livraisonsAEffectuer.afficher();
    }

    /**
     * Affiche des statistiques concernant les livraisons.
     *
     */
    public void afficherStatistiques() {
        System.out.println("Livreurs enregistrés : " + this.getLivreursEnregistres().taille());
        System.out.println("Livreurs authentifiés : " + this.getLivreursAuthentifies().size());
        System.out.println("Livraisons à effectuer : " + this.getLivraisonsAEffectuer().taille());
    }


    /**
     * Applique la commande EXIT envoyée par un client.
     *
     * @param evenement L'évènement reçu.
     * @return La chaîne à renvoyer au client.
     */
    private String traiterEXIT(Evenement evenement) {
        String reponse;
        Connexion cnx = (Connexion) evenement.getSource();

        //  Les livraisons en cours de ce livreur sont remises en attente.
        Livreur livreur = this.livreursAuthentifies.get(cnx);

        if (livreur != null) {
            IListeLivraisons livraisons = livreur.supprimerToutesLesLivraisonsEnCours();

            for (Livraison livraison : livraisons) {
                livraison.annulerTentative();
                livraison.setStatut(Statut.EN_ATTENTE);
                this.livraisonsAEffectuer.ajouter(livraison);
            }
            this.notifierObservateurs();

            //  Ce livreur n'est plus connecté
            this.livreursAuthentifies.remove(cnx);
            livreur.setAuthentifie(false);
            this.notifierObservateurs();
        }

        reponse = "END";

        cnx.envoyer(reponse);
        cnx.close();

        return "";
    }

    /**
     * Applique la commande ID envoyée par un client.
     *
     * @param evenement L'évènement reçu.
     * @return La chaîne à renvoyer au client.
     */
    private String traiterID(Evenement evenement) {
        String reponse;
        Connexion cnx = (Connexion) evenement.getSource();

        try {
            int idLivreur = Integer.parseInt(evenement.getArgument());
            String msg = String.format("SERVEUR : Le livreur #%d veut s'identifier...", idLivreur);
            this.notifierEcouteursConsole(msg);

            if (this.livreursAuthentifies.get(cnx) != null) {  // Le livreur est-il déjà identifié ?
                return "ALREADY_AUTHENTIFIED_ERROR";
            }

            Livreur livreur = this.livreursEnregistres.rechercher(idLivreur);
            if (livreur == null) {  // Le livreur est-il enregistré ?
                return "AUTHENTICATION_ERROR";
            }

            if (this.livreursAuthentifies.contains(livreur)) {  // Le livreur est-il identifié sur un autre client ?
                return "TOO_MANY_CONNECTIONS_ERROR";
            }

            // Le livreur est reconnu.
            this.livreursAuthentifies.put(cnx, livreur);
            livreur.setAuthentifie(true);
            this.notifierObservateurs();

            msg = String.format("Le client %s s'est authentifié avec succès.", cnx.getAdresse());
            this.notifierEcouteursConsole(msg);

            reponse = "AUTHORIZED " + idLivreur + " " + livreur.getNom();
        } catch (NumberFormatException nfe) {   //  L'argument n'est pas un nombre.
            reponse = "BAD_ARGUMENT_ERROR";
        }

        return reponse;
    }

    /**
     * Applique la commande REGISTER envoyée par le client.
     *
     * @param evenement L'évènement reçu.
     * @return La chaîne à renvoyer au client.
     */
    private String traiterREGISTER(Evenement evenement) {
        String reponse;
        Connexion cnx = (Connexion) evenement.getSource();
        Arguments args = new Arguments(evenement);

        try {
            int idLivreur = Integer.parseInt(args.extraireArgumentSuivant());
            String msg = String.format("SERVEUR : Le livreur #%d veut s'enregistrer...", idLivreur);
            this.notifierEcouteursConsole(msg);

            if (this.livreursAuthentifies.get(cnx) != null) {  // Le livreur est-il déjà identifié ?
                return "ALREADY_AUTHENTIFIED_ERROR";
            }

            // Le nouveau livreur est ajouté.
            String typeLivreur = args.extraireArgumentSuivant();
            if (typeLivreur == null) {
                throw new NumberFormatException();
            }
            typeLivreur = typeLivreur.toUpperCase();

            String nomLivreur = args.lire();
            if (nomLivreur == null || nomLivreur.isEmpty()) {
                throw new NumberFormatException();
            }

            Livreur livreur;
            switch (typeLivreur) {
                case "VELO":
                    livreur = new LivreurVelo(idLivreur, nomLivreur);
                    break;
                case "CAMION":
                    livreur = new LivreurCamion(idLivreur, nomLivreur);
                    break;
                case "VOITURE":
                    livreur = new LivreurVoiture(idLivreur, nomLivreur);
                    break;
                default:
                    throw new NumberFormatException();
            }

            this.livreursEnregistres.ajouter(livreur);
            this.notifierObservateurs();

            msg = String.format("Le client %s s'est enregistré avec succès.", cnx.getAdresse());
            this.notifierEcouteursConsole(msg);
            reponse = "REGISTERED " + idLivreur + " " + livreur.getNom();
        } catch (NumberFormatException nfe) {   //  L'argument n'est pas un nombre.
            reponse = "BAD_ARGUMENT_ERROR";
        } catch (ListeChaineeException e) {
            reponse = "ALREADY_REGISTERED_ERROR";
        }

        return reponse;
    }

    /**
     * Applique la commande GET envoyée par un client.
     *
     * @param evenement L'évènement reçu.
     * @return La chaîne à renvoyer au client.
     */
    private String traiterGET(Evenement evenement) {
        StringBuilder reponse;
        Connexion cnx = (Connexion) evenement.getSource();

        if (!evenement.getArgument().isEmpty()) {
            return "BAD_ARGUMENT_ERROR";
        }

        if (this.livraisonsAEffectuer.estVide()) {  // plus de livraisons à effectuer.
            return "EMPTY";
        }

        Livreur livreur = this.livreursAuthentifies.get(cnx);
        if (livreur == null) {    // livreur non identifié.
            return "AUTHENTICATION_ERROR";
        }

        //  envoi des livraisons au client.
        int nb = Math.min(this.livraisonsAEffectuer.taille(), livreur.capaciteLivraison() - livreur.nbLivraisonsEnCours());

        reponse = new StringBuilder("DELIVERIES " + nb);
        for (int i = 0; i < nb; i++) {
            Livraison livraison = this.livraisonsAEffectuer.retirer();
            livraison.nouvelleTentative();
            livreur.ajouterLivraisonEnCours(livraison);
            livraison.setStatut(Statut.EN_COURS);

            this.notifierObservateurs();

            int idLivraison = livraison.getId();
            int lotLivraidon = livraison.getLot();
            Priorite prioriteLivraison = livraison.getPriorite();
            int tentativeLivraison = livraison.getTentative();
            reponse.append(" ").append(idLivraison)
                    .append(" ").append(lotLivraidon)
                    .append(" ").append(prioriteLivraison)
                    .append(" ").append(tentativeLivraison);
        }

        return reponse.toString();
    }

    /**
     * Applique la commande DELIVERED envoyée par un client.
     *
     * @param evenement L'évènement reçu.
     * @return La chaîne à renvoyer au client.
     */
    private String traiterDELIVERED(Evenement evenement) {
        String reponse;
        Connexion cnx = (Connexion) evenement.getSource();

        try {
            Livreur livreur = this.livreursAuthentifies.get(cnx);
            if (livreur == null) {
                throw new AuthenticationException();
            }

            int idLivraison = Integer.parseInt(evenement.getArgument());

            if (livreur.estSansLivraisonsEnCours()) {    //  pas de livraison en cours pour ce livreur
                return "NO_DELIVERY_ERROR";
            }

            Livraison livraison = livreur.supprimerLivraisonEnCours(idLivraison);
            if (livraison == null) {    //  cette livraison ne fait pas partie de celles de ce livreur
                return "BAD_DELIVERY_ERROR";
            }

            //  Livraison acceptée.
            String msg = String.format("SERVEUR : Le livreur #%d a effectué la livraison #%d", livreur.getId(), idLivraison);
            this.notifierEcouteursConsole(msg);
            livreur.ajouterLivraisonEffectuee(livraison);
            livraison.setStatut(Statut.LIVREE);
            this.notifierObservateurs();
            reponse = "DELIVERED_OK " + idLivraison;
        } catch (NumberFormatException nfe) {   //  L'argument n'est pas un nombre.
            reponse = "BAD_ARGUMENT_ERROR";
        } catch (AuthenticationException e) {
            reponse = "AUTHENTICATION_ERROR";
        }

        return reponse;
    }

    /**
     * Applique la commande FAILED envoyée par un client.
     *
     * @param evenement L'évènement reçu.
     * @return La chaîne à renvoyer au client.
     */
    private String traiterFAILED(Evenement evenement) {
        String reponse;
        Connexion cnx = (Connexion) evenement.getSource();

        try {
            Livreur livreur = this.livreursAuthentifies.get(cnx);
            if (livreur == null) {  // Le livreur n'est pas authentifié.
                throw new AuthenticationException();
            }

            if (livreur.estSansLivraisonsEnCours()) {    //  pas de livraison en cours pour ce livreur
                return "NO_DELIVERY_ERROR";
            }

            int idLivraison = Integer.parseInt(evenement.getArgument());

            Livraison livraison = livreur.supprimerLivraisonEnCours(idLivraison);  //  supprimer cette livraison de celles du livreur
            if (livraison == null) {    //  cette livraison ne fait pas partie de celles de ce livreur
                return "BAD_DELIVERY_ERROR";
            }

            String msg = String.format("SERVEUR : Le livreur #%d n'a pas pu effectuer la livraison #%d", livreur.getId(), idLivraison);
            this.notifierEcouteursConsole(msg);

            if (livraison.resteTentatives()) {
                this.livraisonsAEffectuer.ajouter(livraison);
                livraison.setStatut(Statut.EN_ATTENTE);
                reponse = "FAILED_CONTINUE " + idLivraison;
            } else {
                this.livraisonsEchouees.ajouter(livraison);
                livraison.setStatut(Statut.ECHOUEE);
                reponse = "FAILED_ABORT " + idLivraison;
            }
            this.notifierObservateurs();
        } catch (NumberFormatException nfe) {   //  L'argument n'est pas un nombre.
            reponse = "BAD_ARGUMENT_ERROR";
        } catch (AuthenticationException e) {
            reponse = "AUTHENTICATION_ERROR";
        }

        return reponse;
    }

    /**
     * Calcule et retourne le revenu produit par un livreur.
     *
     * @param evenement L'évènement reçu.
     */
    private String traiterINCOME(Evenement evenement) {
        String reponse;
        Connexion cnx = (Connexion) evenement.getSource();

        try {
            Livreur livreur = this.livreursAuthentifies.get(cnx);
            if (livreur == null) {
                throw new AuthenticationException();
            }

            int nb = livreur.nbLivraisonsEffectuees();
            double revenu = livreur.calculerRevenu();

            reponse = "REVENU " + revenu + " " + nb;
        } catch (NumberFormatException nfe) {   //  L'argument n'est pas un nombre.
            reponse = "BAD_ARGUMENT_ERROR";
        } catch (AuthenticationException e) {
            reponse = "AUTHENTICATION_ERROR";
        }

        return reponse;
    }

    /**
     * Retourne toutes les livraisons en cours pour le livreur concerné.
     *
     * @param evenement Événement de type INFO à traiter.
     * @return La chaine constituant la réponse à retourner au client.
     */
    private String traiterINFO(Evenement evenement) {
        StringBuilder reponse;
        Connexion cnx = (Connexion) evenement.getSource();

        Livreur livreur = this.livreursAuthentifies.get(cnx);
        if (livreur == null) {    // livreur non identifié.
            return "AUTHENTICATION_ERROR";
        }

        if (livreur.estSansLivraisonsEnCours()) {
            return "NO_DELIVERY_ERROR";
        }

        //  Envoi de toutes les livraisons du livreur au client.
        if (evenement.getArgument().isEmpty()) { // Le livreur n'a pas donné de numéro de livraison
            reponse = new StringBuilder();
            Iterator<Livraison> iter = livreur.donneIterateurLivraisonsEnCours();
            int cpt = 0;
            while (iter.hasNext()) {
                Livraison livraison = iter.next();
                reponse.append(" ").append(livraison.getId());
                reponse.append(" ").append(livraison.getLot());
                reponse.append(" ").append(livraison.getPriorite());
                reponse.append(" ").append(livraison.getTentative());
                cpt++;
            }
            reponse = new StringBuilder("DELIVERIES_INFO " + cpt + reponse);

            return reponse.toString();
        }

        // Le livreur a donné un numéro de livraison
        try {
            int idLivraison = Integer.parseInt(evenement.getArgument());

            Livraison livraison = livreur.rechercherLivraisonEnCours(idLivraison);
            if (livraison == null) {
                reponse = new StringBuilder("BAD_DELIVERY_ERROR");
            } else {
                reponse = new StringBuilder("DELIVERIES_INFO " + 1);
                reponse.append(" ").append(livraison);
            }
        } catch (Exception e) {
            reponse = new StringBuilder("BAD_ARGUMENT_ERROR");
        }

        return reponse.toString();
    }

    /**
     * Le client envoie un message à un autre ou à d'autres livreurs.
     *
     * @param evenement Événement de type SEND à traiter.
     * @return La chaine constituant la réponse à retourner au client.
     */
    private String traiterSEND(Evenement evenement) {
        StringBuilder reponse;
        Connexion cnx = (Connexion) evenement.getSource();

        Livreur livreur = this.livreursAuthentifies.get(cnx);
        if (livreur == null) {    // livreur non identifié.
            return "AUTHENTICATION_ERROR";
        }

        Arguments args = new Arguments(evenement);

        String idMsg = args.extraireArgumentSuivant();

        if (this.messagesId.contains(idMsg)) {  // Le message a déjà été reçu
            return "";
        }

        // Le message n'avait pas été reçu
        // - on traite le message et
        // - on envoie un ACK
        this.messagesId.add(idMsg);

        String to = args.extraireArgumentSuivant();
        String msg = args.lire();

        String toSend = "MSG " + livreur.getId() + " " + msg;

        if (to.equals("*")) {   //  envoie à tout le monde sauf ce livreur
            for (var v : this.livreursAuthentifies.entrySet()) {
                if (v.getKey() != cnx) {
                    v.getKey().envoyer(toSend);
                }
            }
            return "ACK " + idMsg;  // envoie ACK au livreur
        }

        try {
            int toId = Integer.parseInt(to);
            Livreur toLivreur = this.livreursEnregistres.rechercher(toId);
            for (var v : this.livreursAuthentifies.entrySet()) {
                if (v.getValue().equals(toLivreur)) {
                    v.getKey().envoyer(toSend);
                }
            }
            reponse = new StringBuilder("ACK " + idMsg);
        } catch (Exception e) {
            reponse = new StringBuilder("BAD_ARGUMENT_ERROR");
        }

        return reponse.toString();
    }

    /**
     * Renvoie un message d'erreur au client pour cause d'évènement inconnu.
     *
     * @return La chaîne à renvoyer au client.
     */
    private String traiterCOMMAND_ERROR() {
        String reponse;

        reponse = "COMMAND_ERROR";

        return reponse;
    }


    /**
     * Gère un évènement reçu en paramètre.
     *
     * @param evenement L'événement a géré.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();

        if (source instanceof Connexion) {
            Connexion cnx = (Connexion) source;
            String msg = String.format("GEST. LIV. a reçu : " + evenement.getType() + " " + evenement.getArgument());
            this.notifierEcouteursConsole(msg);

            String reponse;
            switch (evenement.getType().toUpperCase()) {
                case "EXIT": // Le client se déconnecte.
                    reponse = this.traiterEXIT(evenement);
                    break;
                case "REGISTER": // Le client s'enregistre comme nouveau livreur.
                    reponse = this.traiterREGISTER(evenement);
                    break;
                case "ID": // Le client s'identifie.
                    reponse = this.traiterID(evenement);
                    break;
                case "GET": //  Le client a demandé des livraisons à effectuer.
                    reponse = this.traiterGET(evenement);
                    break;
                case "DELIVERED": // Le client informe qu'une livraison a été effectuée.
                    reponse = this.traiterDELIVERED(evenement);
                    break;
                case "FAILED": // Le client informe qu'une livraison n'a pas pu être effectuée.
                    reponse = this.traiterFAILED(evenement);
                    break;
                case "INCOME":
                    reponse = this.traiterINCOME(evenement);
                    break;
                case "INFO":
                    reponse = this.traiterINFO(evenement);
                    break;
                case "SEND":
                    reponse = this.traiterSEND(evenement);
                    break;
                default: // La commande envoyée par le client n'a pas été reconnue.
                    reponse = this.traiterCOMMAND_ERROR();
            }

            cnx.envoyer(reponse);
        }
    }

    /**
     * Notifier tous les écouteurs de console.
     *
     */
    public void notifierEcouteursConsole(String msg) {
        // Affichage sur la console de l'IDE
        System.out.println(msg);

       this.trace = msg;
       this.notifierObservateurs();
    }
}

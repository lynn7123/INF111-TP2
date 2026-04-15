package com.vue;

import com.gestionnaireLivraisons.GestionnaireLivraisons;
import com.gestionnaireLivraisons.Livraison;
import com.gestionnaireLivraisons.Priorite;

import javax.swing.*;
import java.awt.*;

/**
 * La classe pour les boites de dialogues d'ajout d'une livraison.
 */
public class AjoutLivraisonDialogue extends JDialog {

    private final MiniServerUI miniServerUI;
    private final GestionnaireLivraisons gestionnaireLivraisons;

    /**
     * Le constructeur pour la boite de dialogue d'ajout d'une livraison.
     *
     * @param miniServerUI           La fenêtre propriétaire de cette boite de dialogue.
     * @param gestionnaireLivraisons Le gestionnaire de livraisons associé.
     */
    public AjoutLivraisonDialogue(MiniServerUI miniServerUI, GestionnaireLivraisons gestionnaireLivraisons) {
        super(miniServerUI, "Ajout d'une livraison", true);
        this.miniServerUI = miniServerUI;
        this.gestionnaireLivraisons = gestionnaireLivraisons;
        this.initialiserComposants();
    }

    /**
     * Affichage et gestion de la boite de dialogue pour l'ajout d'une livraison.
     */
    public void initialiserComposants() {
        this.setLayout(new GridBagLayout());
        this.setSize(280, 140);
        this.setLocationRelativeTo(this.miniServerUI);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champ Lot
        JLabel labelLot = new JLabel("Lot :");
        JTextField champLot = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        this.add(labelLot, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        this.add(champLot, gbc);

        // Champ Priorité
        JLabel labelPriorite = new JLabel("Priorité :");
        JComboBox<Priorite> comboPriorite = new JComboBox<>(Priorite.values());
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        this.add(labelPriorite, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        this.add(comboPriorite, gbc);

        // Boutons — classes anonymes
        JButton boutonAjouter = new JButton("Ajouter");
        JButton boutonAnnuler = new JButton("Annuler");

        boutonAjouter.addActionListener(e -> {
            try {
                int lot = Integer.parseInt(champLot.getText().trim());
                Priorite priorite = (Priorite) comboPriorite.getSelectedItem();

                if (!Livraison.validerLotLivraison(lot)) {
                    int lotMax = Livraison.lotMaximal();
                    afficherErreur(String.format(
                            "Numéro de lot invalide.\nLes lots valides sont entre 1 et %d.", lotMax + 1));
                    return;
                }

                ajouterLivraison(lot, priorite);
                dispose();

            } catch (NumberFormatException ex) {
                afficherErreur("Le numéro de lot doit être un entier valide.");
            }
        });

        boutonAnnuler.addActionListener(e -> dispose());

        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panneauBoutons.add(boutonAjouter);
        panneauBoutons.add(boutonAnnuler);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 1;
        this.add(panneauBoutons, gbc);

        this.setVisible(true);
    }

    /**
     * Affiche une boite de dialogue pour les erreurs de saisies.
     *
     * @param msg Le message à afficher dans la boite.
     */
    private void afficherErreur(String msg) {
        String[] erreurOptions = {"Opps..."};
        JOptionPane.showOptionDialog(this.miniServerUI, msg,
                "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, erreurOptions, erreurOptions[0]);
    }

    /**
     * Ajoute une livraison à la liste des livraisons à effectuer.
     *
     * @param lot      Le lot de la livraison.
     * @param priorite La priorité de la livraison.
     */
    private void ajouterLivraison(int lot, Priorite priorite) {
        Livraison nouvelleLivraison = new Livraison(priorite, lot);
        this.gestionnaireLivraisons.getLivraisonsAEffectuer().ajouter(nouvelleLivraison);
        this.gestionnaireLivraisons.notifierObservateurs();

        JOptionPane.showMessageDialog(this.miniServerUI,
                String.format("Livraison #%d ajoutée avec succès (Lot %d, %s).",
                        nouvelleLivraison.getId(), lot, priorite),
                "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}

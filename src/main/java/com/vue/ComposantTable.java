package com.vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 * Un composant qui permet d'afficher des informations dans une table.
 * Ce composant peut être utilisé dans plusieurs panneaux.
 *
 */
public class ComposantTable extends JPanel {
    private final JTable table;
    private final String[] nomsColonnes;

    /**
     * Un tableau pour indiquer si les données de la colonne doivent être centrées (true) ou non (false)
     * Il doit être de la même taille que le tableau nomsColonnes
     *
     * L'utilisation de cet attribut est optionnel. Le but est seulement esthétique
     * Vous pouvez le laisser à null et ne pas l'utiliser dans votre code.
     *
     */
    private final boolean[] donneesCentrees;

    /**
     * Constructeur pour ComposantTable.
     *
     * @param titre   Le titre de la grille.
     * @param largeur La largeur de la grille.
     * @param hauteur La hauteur de la grille.
     * @param nomsColonnes Tableau des noms de colonnes
     * @param donneesCentrees La hauteur de la grille.
     */
    public ComposantTable(String titre, int largeur, int hauteur, String[] nomsColonnes, boolean[] donneesCentrees) {
        this.nomsColonnes = nomsColonnes;
        this.donneesCentrees = donneesCentrees;
        this.table = new JTable();
        this.table.setFont(Config.fonte); // considère la fonte définie dans Config

        // modele non-editable
        DefaultTableModel modele = new DefaultTableModel(nomsColonnes ,0){
            @Override
            public boolean isCellEditable( int row , int column){
                return false;
            }
        };
        this.table.setModel(modele);

        // selection 1 seul ligne a la fois
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //empecher reorganisation des colonnes
        this.table.getTableHeader().setReorderingAllowed(false);
        // centrer colonnes
        if (donneesCentrees !=null){
            DefaultTableCellRenderer renduCentre = new DefaultTableCellRenderer();
            renduCentre.setHorizontalAlignment(SwingConstants.CENTER);
            for ( int i=0; i< donneesCentrees.length ; i++){
                if (donneesCentrees[i]){
                    this.table.getColumnModel().getColumn(i).setCellRenderer(renduCentre);
                }
            }
        }
        // Panneau defilement vertical
        JScrollPane defilement = new JScrollPane(this.table);
        defilement.setPreferredSize(new Dimension(largeur, hauteur));

        // Mise en page du panneau avec titre
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder(titre));
        this.add(defilement, BorderLayout.CENTER);

    }

    /**
     * Constructeur sans centrage de colonnes
     *
     * @param titre
     * @param largeur
     * @param hauteur
     * @param nomsColonnes
     */
    public ComposantTable(String titre, int largeur, int hauteur, String[] nomsColonnes) {
        this(titre, largeur, hauteur, nomsColonnes , null) ;
    }

    /**
     * Enregistre un écouteur de souris sur la table
     *
     * @param ml L'écouteur de clic souris qui sera averti si un évènement de clic survient sur cette table.
     */
    public void enregistrerEcouteur(MouseListener ml) {
     this.table.addMouseListener(ml);
    }

    /**
     * Retourne l'index de la ligne qui est actuellement sélectionnée.
     * Les index commencent à 0.
     *
     * @return La ligne sélectionnée ou -1 síl n'y a aucune sélection.
     */
    public int ligneSelectionnee() {
        return this.table.getSelectedRow();
    }

    /**
     * Retourne la donnée se trouvant à une ligne/colonne données en paramètres.
     *
     * @param ligne La ligne où trouver la donnée.
     * @param colonne La colonne où trouver la donnée.
     * @return La donnée.
     */
    public String lireCase(int ligne, int colonne) {
        return (String) (((DefaultTableModel) this.table.getModel()).getDataVector().get(ligne).get(colonne));
    }

    /**
     * Met à jour les données de la table avec la matrice de données passée en paramètre.
     *
     * @param donnees La matrice de données.
     */
    public void mettreAJour(Vector<Vector<String>> donnees) {

        DefaultTableModel modele = (DefaultTableModel) this.table.getModel();

        // Vider la table
        modele.setRowCount(0);

        // Remplir avec les nouvelles données
        if (donnees != null) {
            for (Vector<String> ligne : donnees) {
                modele.addRow(ligne);
            }
        }

        // Reappliquer le centrage après la mise à jour
        if (this.donneesCentrees != null) {
            DefaultTableCellRenderer renduCentre = new DefaultTableCellRenderer();
            renduCentre.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < this.donneesCentrees.length && i < this.table.getColumnCount(); i++) {
                if (this.donneesCentrees[i]) {
                    this.table.getColumnModel().getColumn(i).setCellRenderer(renduCentre);
                }

            }
        }
    }

}

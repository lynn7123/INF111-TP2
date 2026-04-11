package com.atoudeft.commun.net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Cette classe représente un point de connexion d'un client vers un serveur ou d'un serveur vers un client.
 * Encapsule le socket utilisé pour la connexion ainsi que les flux de caractères pour envoyer et recevoir du texte.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Connexion {

    private Socket socket;
    private PrintWriter os;
    private BufferedInputStream is;

    private static final int BUFFER_MAX_SIZE = 2000;
    private final byte[] buf = new byte[BUFFER_MAX_SIZE];    // buffer de lecture
    private int size = 0;                    // place utilisée dans le buffer

    /**
     * Construit une connexion sur un socket, initialisant les flux de caractères utilisés par le socket.
     *
     * @param s Socket Le socket sur lequel la connexion est créée
     */
    public Connexion(Socket s) {
        try {
            socket = s;
            is = new BufferedInputStream(socket.getInputStream());
            os = new PrintWriter(socket.getOutputStream());
        } catch (IOException ignored) {
        }
    }

    /**
     * Vérifie si du texte est arrivé sur la connexion et le retourne. Retourne la chaine vide s'il n'y a pas de texte.
     *
     * @return String le texte reçu, ou la chaine vide, si aucun texte n'est arrivé ou null si la connexion est rompue.
     */
    public String getAvailableText() {
        String res = "";

        try {
            if (size == BUFFER_MAX_SIZE) {
                System.err.println("Buffer de lecture plein...");
                System.exit(-2);
            }

            if (is.available() > 0) {
                // Lire le flux d'entrée
                int added = is.read(buf, size, BUFFER_MAX_SIZE - size);
                this.size += added;
            }

            StringBuilder tmp = new StringBuilder(new String(buf, 0, this.size, StandardCharsets.UTF_8));
            int pos = tmp.indexOf("\n");

            if (pos == -1) {    // fin de paquet non trouvée
                return "";
            }

            res = tmp.delete(pos, tmp.length()).toString().trim();

            if (pos + 1 < BUFFER_MAX_SIZE) {
                System.arraycopy(buf, pos + 1, buf, 0, size - pos - 1);
            }
            this.size -= pos + 1;

        } catch (IOException ignored) {
        }

        return res;
    }

    /**
     * Envoie un texte sur la connexion
     *
     * @param texte String texte envoyé
     */
    public void envoyer(String texte) {
        os.print(texte);
        os.print('\n'); // marquer la fin du paquet
        os.flush();
    }

    /**
     * Ferme la connexion en fermant le socket et les flux utilisés.
     *
     * @return true si la connexion a été fermée correctement et false, sinon.
     */
    public boolean close() {
        try {
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public String getAdresse() {
        return this.socket.getLocalSocketAddress().toString();
    }
}
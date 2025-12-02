package it.unibo.uniboparty.view.minigames.typeracergame.api;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Interfaccia che rappresenta la parte grafica del gioco.
 * Permette al Controller di aggiornare testo, timer e input utente
 * senza conoscere l'implementazione concreta della UI.
 */
public interface View {

    /**
     * Aggiorna il testo principale visibile (parola da digitare o messaggi).
     *
     * @param text nuovo testo mostrato
     */
    void setLabel1(String text);

    /**
     * Aggiorna l'etichetta che mostra il tempo rimanente.
     *
     * @param t tempo rimanente in secondi
     */
    void updateTimeLabel(int t);

    /**
     * Restituisce l'etichetta principale, utile per operazioni grafiche o test.
     *
     * @return JLabel principale
     */
    JLabel getLabel1();

    /**
     * Restituisce il campo testo utilizzato per digitare le parole.
     *
     * @return textfield di input
     */
    JTextField getTextField();
}

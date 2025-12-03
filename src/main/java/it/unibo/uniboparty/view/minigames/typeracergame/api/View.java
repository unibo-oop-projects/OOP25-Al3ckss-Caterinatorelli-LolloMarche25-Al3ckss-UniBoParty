package it.unibo.uniboparty.view.minigames.typeracergame.api;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;

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

    /**
     * Registers an ActionListener on the internal text field.
     * This avoids external code directly mutating the UI component.
     *
     * @param listener the listener to register
     */
    void addTextFieldActionListener(ActionListener listener);

    /**
     * Returns the current text inside the input field.
     *
     * @return the field text
     */
    String getTextFieldText();

    /**
     * Clears the input field text on the Event Dispatch Thread.
     */
    void clearTextField();

    /**
     * Binds the view to the given model and registers the view as observer.
     *
     * @param model the model to bind to
     */
    void bindModel(Model model);

    /**
     * Displays the final score when the game ends.
     *
     * @param finalScore the player's final score
     */
    void showFinalScore(int finalScore);
}

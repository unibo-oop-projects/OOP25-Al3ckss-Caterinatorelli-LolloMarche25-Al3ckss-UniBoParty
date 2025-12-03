package it.unibo.uniboparty.model.minigames.typeracergame.impl;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

/**
 * Provides a collection of possible words for the TypeRacer game.
 * 
 * <p>
 * The list is immutable.
 */
public final class WordList {

    /**
     * Immutable list of words for TypeRacer.
     */
    public static final List<String> WORDS = Collections.unmodifiableList(Arrays.asList(
        "auto", "casa", "sole", "luna", "mare", "vento", "pioggia", "neve", "montagna", "fiume",
        "strada", "cielo", "tempo", "notte", "giorno", "amico", "amore", "cuore", "libro", "scuola",
        "computer", "telefono", "tastiera", "schermo", "mouse", "sedia", "tavolo", "porta", "finestra", "letto",
        "sogno", "vita", "gioia", "paura", "forza", "luce", "ombra", "risata", "pianto", "gioco",
        "corsa", "volo", "salto", "cammino", "scrivo", "leggo", "corro", "penso", "creo", "disegno",
        "gatto", "cane", "uccello", "pesce", "tigre", "lupo", "orso", "volpe", "rana", "ape",
        "rosso", "blu", "verde", "giallo", "nero", "bianco", "grigio", "marrone", "chiaro", "scuro",
        "veloce", "lento", "alto", "basso", "forte", "debole", "caldo", "freddo", "facile", "difficile",
        "oggi", "domani", "ieri", "sempre", "mai", "subito", "dopo", "prima", "spesso", "quasi",
        "andare", "venire", "stare", "vedere", "sapere", "volere", "potere", "dire", "parlare", "ascoltare"
    ));

    /**
     * Private constructor for checkstyle.
     */
    private WordList() { }
}

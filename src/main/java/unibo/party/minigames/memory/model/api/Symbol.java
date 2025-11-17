package unibo.party.minigames.memory.model.api;

/**
 * Elenco dei simboli possibili per le carte del Memory.
 * Ogni valore rappresenta un tipo distinto di simbolo da abbinare.
 */
public enum Symbol {
    STAR("star"), 
    HEART("heart"), 
    CAT("cat"), 
    APPLE("apple"), 
    MOON("moon"),
    SUN("sun"),
    CAR("car"),
    DOG("dog");

    private final String imageKey;

    Symbol(String imageKey) {
        this.imageKey = imageKey;
    }

    @Override
    public String toString() {
        return this.imageKey;
    }
}

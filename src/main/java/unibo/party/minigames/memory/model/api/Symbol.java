package unibo.party.minigames.memory.model.api;

/**
 * List of all possible symbols for Memory cards.
 * Each value represents a different type of symbol that can appear on a card.
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

    /**
     * String used to load the correct image for the symbol.
     */
    private final String imageKey;

    /**
     * Creates a symbol with its image key.
     * @param imageKey the name used to find the image file.
     */
    Symbol(String imageKey) {
        this.imageKey = imageKey;
    }

    /**
     * @return the image key as a string.
     * This is useful for debugging or showing the symbol name.
     */
    @Override
    public String toString() {
        return this.imageKey;
    }
}

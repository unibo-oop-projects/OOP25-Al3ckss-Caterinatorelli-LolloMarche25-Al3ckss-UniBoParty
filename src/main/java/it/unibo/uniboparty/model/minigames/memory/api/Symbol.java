package it.unibo.uniboparty.model.minigames.memory.api;

/**
 * List of all possible symbols for Memory cards.
 * 
 * <p>
 * Each symbol corresponds to an image file stored inside
 * {@code /images/memory/}, identified by the {@code imageKey}.
 * </p>
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
     * 
     * @param imageKey the name used to find the image file.
     */
    Symbol(String imageKey) {
        this.imageKey = imageKey;
    }

    /**
     * @return the image key as a string.
     */
    @Override
    public String toString() {
        return this.imageKey;
    }
}

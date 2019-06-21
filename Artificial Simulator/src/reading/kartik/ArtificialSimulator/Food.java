package reading.kartik.ArtificialSimulator;

/**
 * This class extends AnEntity, and is used to create and store food objects in
 * the world. Aside from its super parameters, it also has a tag variable, which
 * stores whether the food is vegetarian or not.
 *
 * @author Kartik
 * @version 1.0
 */

public class Food extends AnEntity {

    private char tag;

    /**
     * Writing the definitons of the default and overloaded constructors
     */

    // Default constructor
    public Food() {
        // super();
        super.setSpecies("Food");
        super.setEnergy(5);// Food has an energy of 5kJ
        // super.setSymbol('.');//That's the symbol!
    }

    // Overloaded Constructor
    public Food(String sp, int hp, int vp, int e, int id1) {
        super(sp, hp, vp, e, id1);
        super.setSpecies("Food");
        super.setEnergy(5);// Food has an energy of 5kJ
        // super.setSymbol('.');//That's the symbol!
    }

    /**
     * Method to return the tag value
     *
     * @return
     */
    protected char getTag() {
        return tag;
    }

    /**
     * Method to set the tag value
     *
     * @param tag
     */
    protected void setTag(char tag) {
        this.tag = tag;
    }
}

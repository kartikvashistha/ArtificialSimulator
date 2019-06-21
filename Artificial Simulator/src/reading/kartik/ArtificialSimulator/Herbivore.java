package reading.kartik.ArtificialSimulator;

/**
 * This classs extends AnEntity, and is used to create and store a food's
 * parameters.
 *
 * @author Kartik
 * @version 1.0
 */
public class Herbivore extends AnEntity {

    /**
     * Defining the default and overloaded functions
     */

    private Direction d;// Stores the current direction of the Herbivore

    // Default Constructor
    public Herbivore() {
        super();
        d = Direction.getDirection();
        // setImg(new Image(getClass().getResourceAsStream("bison.png")));
    }

    // Overloaded Constructor
    public Herbivore(String sp, int hp, int vp, int e, int id1) {
        super(sp, hp, vp, e, id1);
        d = Direction.getDirection();
        // setImg(new Image(getClass().getResourceAsStream("bison.png")));
    }

    /**
     * Method to get what direction the entity is moving in
     *
     * @return
     */
    protected Direction knowDirection() {
        return d;
    }

    /**
     * Method to set a random direction to the entity
     */
    protected void setDirection() {
        d = Direction.getDirection();
    }
}

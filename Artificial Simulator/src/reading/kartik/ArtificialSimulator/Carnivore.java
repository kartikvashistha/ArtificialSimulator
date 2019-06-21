package reading.kartik.ArtificialSimulator;

/**
 * This class inherits from AnEntity, and is used to create objects of type
 * Herbivores. It inherits attributes and functions from the super class, and
 * has it's own direction variable.
 *
 * @author Kartik Vashistha
 * @version 1.0
 */
public class Carnivore extends AnEntity {
    private Direction d;// Stores the direction of the entity

    /**
     * defining the default and overloaded constructors
     */
    // Default Constructor
    public Carnivore() {
        super();
        d = Direction.getDirection();
    }

    // Overloaded Constructor
    public Carnivore(String sp, int hp, int vp, int e, int id1) {
        super(sp, hp, vp, e, id1);
        d = Direction.getDirection();
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

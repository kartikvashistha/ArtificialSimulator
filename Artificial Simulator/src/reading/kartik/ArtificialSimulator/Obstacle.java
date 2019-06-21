package reading.kartik.ArtificialSimulator;

/**
 * This class extends AnEntity, and is used to define and store an obstacle's
 * parameters.
 *
 * @author Kartik
 * @version 1.0
 */
public class Obstacle extends AnEntity {

    /**
     * Defining the default and overloaded constructors.
     */

    // Default Constructor
    public Obstacle() {
        // super();
        super.setSpecies("Obstacle");// That's the name
        super.setEnergy(0);// The energy, since it doesn't move
        // setImg(new Image(getClass().getResourceAsStream("rock.png")));
        // super.setSymbol('#');//It's symbol!
    }

    // Overloaded Constructor
    public Obstacle(String sp, int hp, int vp, int e, int id1) {
        super(sp, hp, vp, e, id1);
        super.setSpecies("Obstacle");// That's the name
        super.setEnergy(0);// The energy, since it doesn't move
        // super.setSymbol('#');//It's symbol!
    }

}

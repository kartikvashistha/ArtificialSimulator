package reading.kartik.ArtificialSimulator;

import java.util.Random;

/**
 * This is an enum, used to create and return random direction values.
 *
 * @author Kartik Vashistha
 * @version 1.0
 */
public enum Direction {

    NORTH, SOUTH, EAST, WEST;

    /**
     * This method calculates and returns a random direction value.
     *
     * @return
     */
    public static Direction getDirection() {
        Random random = new Random();

        return values()[random.nextInt(values().length)];
    }
}

package reading.kartik.ArtificialSimulator;

import javafx.scene.image.Image;

/**
 * This class consists of attributes and methods to define an Entity. Classes
 * like Obstacles, Food, Herbivore ,Carnivore, etc inherit from it.
 *
 * @author Kartik Vashistha
 * @version 1.0
 * @since 17/1/17
 */

public abstract class AnEntity {

    private String species;
    private int x;
    private int y;
    private int energy;
    private int id;
    private Image image = null;// Stores the image

    /**
     * Defining the default and overloaded constructors respectively
     */

    // Default Constructor
    public AnEntity() {

        species = "";

        x = -1;
        y = -1;
        energy = 0;
        id = 0;
    }

    // Parametrised Constructor
    public AnEntity(String sp, int hp, int vp, int e, int id1) {

        species = sp;

        x = hp;
        y = vp;
        energy = e;
        id = id1;
    }

    /**
     * Method to return the x coordinates
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Method to return the y coordinates
     */
    public int getY() {
        return y;
    }

    /**
     * Method to get the species value
     *
     * @return
     */
    public String getSpecies() {
        return species;
    }

    // Method to get the id value
    public int getID() {
        return id;
    }

    // Method to get the energy
    public int getEnergy() {
        return energy;
    }

    /**
     * Method to set the species value
     *
     * @param s
     */
    public void setSpecies(String s) {
        species = "" + s;
    }

    /**
     * Method to set the x coordinates
     *
     * @param x1
     */
    public void setX(int x1) {
        x = x1;
    }

    /**
     * Method to set the y coordinates
     *
     * @param y1
     */
    public void setY(int y1) {
        y = y1;
    }

    /**
     * Method to set the energy value
     *
     * @param e
     */
    public void setEnergy(int e) {
        energy = e;
    }

    /**
     * Method to set the id value
     *
     * @param id1
     */
    public void setID(int id1) {
        id = id1;
    }

    /**
     * Method to return the entities name and and its position as a String
     *
     * @return
     */
    public String toString() {
        String temp = "Name: " + species + " X-Coordinates: " + x + " Y-Coordinates: " + y;
        return temp;
    }

    /**
     * Method to return the all the attributes of the entity as a String
     *
     * @return
     */
    public String toText() {
        String temp = toString() + " Energy:" + energy + " ID:" + id;
        return temp;
    }

    /**
     * Method to display the details of the Entity
     *
     * @return
     */
    public String display() {

        return toText();

    }

    /**
     * Method to return the image of the entity
     *
     * @return
     */
    protected Image getImg() {
        return image;
    }

    /**
     * Method to set the image of the entity
     *
     * @param img
     */
    protected void setImg(Image img) {
        image = img;
    }

    /**
     * Method to check if the entity is at the position sx,sy
     *
     * @param sx
     * @param sy
     * @return
     */
    protected boolean isHere(int sx, int sy) {
        return sx == x && sy == y;
    }

    protected void displayEntity(AnInterface inter) {
        inter.showEntity(getImg(), x, y);
    }

}

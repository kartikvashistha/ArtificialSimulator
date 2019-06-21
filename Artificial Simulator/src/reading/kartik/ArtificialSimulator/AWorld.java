package reading.kartik.ArtificialSimulator;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is the class where most of the crucial under the hood scenes happen. It
 * has methods to initialize and draw the world to the canvas. To store the
 * entities, and ArrayList of each type is created.
 *
 * @author Kartik
 * @version 1.0
 */
public class AWorld {

    private int x;
    private int y;
    protected int numberOfHerbivores;
    protected int numberOfCarnivores;
    final int RANGE = 4;
    protected int count;
    protected String config;

    protected boolean populate;

    protected ArrayList<String> txt_arraylist;
    protected ArrayList<Herbivore> herbivore;
    protected ArrayList<Carnivore> carnivore;
    protected ArrayList<Food> food;
    protected ArrayList<Obstacle> obstacles;

    protected static ArrayList<Image> allImages;

    protected String[] namesOfHerbivores = {"bee", "bison", "rabbit"};
    protected String[] namesOfCarnivores = {"hyena", "lion", "snake"};
    protected String[] foodArray = {"meat", "apple", "strawberry", "grass", "honey"};

    /**
     * Defining the default and overloaded constructors
     */

    // Default Constructor
    public AWorld() {

        x = 20;
        y = 20;

        herbivore = new ArrayList<Herbivore>();
        carnivore = new ArrayList<Carnivore>();
        obstacles = new ArrayList<Obstacle>();
        allImages = new ArrayList<Image>();
        food = new ArrayList<Food>();
        numberOfHerbivores = 0;
        numberOfCarnivores = 0;
        count = 0;
        populate = false;
        config += " ";

        initialiseImages();
    }

    public AWorld(int x1, int y1) {
        x = x1;
        y = y1;
        herbivore = new ArrayList<Herbivore>();
        carnivore = new ArrayList<Carnivore>();
        obstacles = new ArrayList<Obstacle>();
        food = new ArrayList<Food>();
        numberOfHerbivores = 0;
        numberOfCarnivores = 0;
        count = 0;
        populate = false;
        initialiseImages();
        config += " ";
    }

    /**
     * Returns the X size of the world
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X value of the world
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the Y value of the world
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y value of the world
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * This function loads all the images once in the memory, to save resources
     * during runtime
     */
    protected void initialiseImages() {

        allImages.add(new Image(getClass().getResourceAsStream("rock.png")));
        allImages.add(new Image(getClass().getResourceAsStream("meat.png")));
        allImages.add(new Image(getClass().getResourceAsStream("apple.png")));
        allImages.add(new Image(getClass().getResourceAsStream("strawberry.png")));
        allImages.add(new Image(getClass().getResourceAsStream("grass.png")));
        allImages.add(new Image(getClass().getResourceAsStream("honey.png")));
        allImages.add(new Image(getClass().getResourceAsStream("bee.png")));
        allImages.add(new Image(getClass().getResourceAsStream("bison.png")));
        allImages.add(new Image(getClass().getResourceAsStream("rabbit.png")));
        allImages.add(new Image(getClass().getResourceAsStream("hyena.png")));
        allImages.add(new Image(getClass().getResourceAsStream("lion.png")));
        allImages.add(new Image(getClass().getResourceAsStream("snake.png")));

    }

    /**
     * Method to add obstacles to the obstacles ArrayList
     *
     * @param num
     */
    protected void addObstacles(int num) {

        for (int i = 0; i < num; i++) {
            obstacles.add(new Obstacle());
            obstacles.get(i).setImg(allImages.get(0));

            // Assigning random coordinates to the entity
            int randX, randY;
            do {
                randX = getARandomNumber(0, getX());
                randY = getARandomNumber(0, getY());
            } while (!canMove(randX, randY));// checking if it can move to
            // randX,randY location
            obstacles.get(i).setX(randX);
            obstacles.get(i).setY(randY);
        }
    }

    /**
     * Adding food to the food ArrayList
     *
     * @param num
     */
    protected void addFood(int num) {

        for (int i = 0; i < num; i++) {
            food.add(new Food());

            String n = foodArray[getARandomNumber(0, foodArray.length - 1)];
            food.get(i).setSpecies(n);// Assign a species name randomly
            // Assigning the pictures to the food object
            switch (n) {
                case "meat":
                    food.get(i).setImg(allImages.get(1));
                    food.get(i).setTag('n');
                    break;
                case "apple":
                    food.get(i).setImg(allImages.get(2));
                    food.get(i).setTag('v');
                    break;
                case "strawberry":
                    food.get(i).setImg(allImages.get(3));
                    food.get(i).setTag('v');
                    break;
                case "grass":
                    food.get(i).setImg(allImages.get(4));
                    food.get(i).setTag('v');
                    break;
                case "honey":
                    food.get(i).setImg(allImages.get(5));
                    food.get(i).setTag('v');
            }

            // Assigning random coordinates to the entity
            int randX, randY;
            do {
                randX = getARandomNumber(0, getX());
                randY = getARandomNumber(0, getY());
            } while (!canMove(randX, randY));// checking if it can move to
            // randX,randY location
            food.get(i).setX(randX);
            food.get(i).setY(randY);
        }

    }

    /**
     * Adding herbivores to the herbivore ArrayList
     *
     * @param numOfHerbivore
     */
    protected void addHerbivore(int numOfHerbivore) {

        int j = 0;
        // Loop to add the Herbivores to the herbivore ArrayList
        for (int i = numberOfHerbivores; j < numOfHerbivore; i++, j++) {
            herbivore.add(new Herbivore());

            String n = namesOfHerbivores[getARandomNumber(0, namesOfHerbivores.length - 1)];
            herbivore.get(i).setSpecies(n);// Assign a species name randomly
            // Assigning the picture to the herbivore
            switch (n) {
                case "bee":
                    herbivore.get(i).setImg(allImages.get(6));
                    break;
                case "bison":
                    herbivore.get(i).setImg(allImages.get(7));
                    break;
                case "rabbit":
                    herbivore.get(i).setImg(allImages.get(8));
                    break;
            }

            // Assigning random coordinates to the entity
            int randX, randY;
            do {
                randX = getARandomNumber(0, getX());
                randY = getARandomNumber(0, getY());
            } while (!canMove(randX, randY));// checking if it can move to
            // randX,randY location
            herbivore.get(i).setX(randX);
            herbivore.get(i).setY(randY);

            // Assigning random ID's
            int a;
            do {
                // Checking if we're assigning ID's for the first time
                if (numberOfHerbivores != 0) {
                    a = getARandomNumber(0, numberOfHerbivores);
                } else {
                    a = getARandomNumber(0, numOfHerbivore);
                }
            } while (!checkIDHerbivore(a));
        }
        numberOfHerbivores += numOfHerbivore;// updating the total number of
        // Herbivores
    }

    /**
     * Method to check if the ID is unique
     *
     * @param m
     * @return
     */
    protected boolean checkIDHerbivore(int m) {
        for (Herbivore h : herbivore) {
            if (h.getID() == m)// checking if ID is available
                return false;// It's not available!
        }
        return true;
    }

    /**
     * Adding carnivores to the carnivore ArrayList
     *
     * @param numOfCarnivore
     */
    protected void addCarnivore(int numOfCarnivore) {

        int j = 0;
        // The check condition has numOfCarnivore==0 to add the entity one at a
        // time as well by parsing 0 if we need to add just one entity
        for (int i = numberOfCarnivores; j < numOfCarnivore; i++, j++) {
            carnivore.add(new Carnivore());

            String n = namesOfCarnivores[getARandomNumber(0, namesOfCarnivores.length - 1)];
            carnivore.get(i).setSpecies(n);// Assign a species name randomly
            // Assigning the picture to the carnivore
            switch (n) {
                case "hyena":
                    carnivore.get(i).setImg(allImages.get(9));
                    break;
                case "lion":
                    carnivore.get(i).setImg(allImages.get(10));
                    break;
                case "snake":
                    carnivore.get(i).setImg(allImages.get(11));
                    break;
            }

            // Assigning random coordinates to the entity
            int randX, randY;
            do {
                randX = getARandomNumber(0, getX());
                randY = getARandomNumber(0, getY());
            } while (!canMove(randX, randY));// checking if it can move to
            // randX,randY location
            carnivore.get(i).setX(randX);
            carnivore.get(i).setY(randY);

            // Assigning random ID's
            int a;
            do {
                // Checking if we're assigning ID's for the first time
                if (numberOfCarnivores != 0) {
                    a = getARandomNumber(0, numberOfCarnivores);

                } else {
                    a = getARandomNumber(0, numOfCarnivore);

                }
            } while (checkIDCarnivore(a));
        }

        numberOfCarnivores += numOfCarnivore;
    }

    /**
     * Method to check if the ID generated is unique
     *
     * @param m
     * @return
     */
    protected boolean checkIDCarnivore(int m) {
        for (Carnivore c : carnivore) {
            if (c.getID() == m)// checking if ID is available
                return false;// It's not available!
        }
        return true;
    }

    /**
     * Method to return a random number
     *
     * @param min
     * @param max
     * @return
     */
    protected int getARandomNumber(int min, int max) {
        Random rn = new Random();

        return (rn.nextInt(max - min + 1) + min);
    }

    /**
     * This method checks if the entity can move or spawn at sX,sY coordinates
     *
     * @param sX
     * @param sY
     * @return
     */
    protected boolean canMove(int sX, int sY) {

        boolean result = false;

        // Check the obstacle ArrayList first
        for (int i = 0; i < obstacles.size(); i++) {

            // Check if there is any obstacle present at sX,sY
            if (obstacles.get(i).isHere(sX, sY))
                return result;

        }

        // Check the obstacle ArrayList first
        for (int i = 0; i < food.size(); i++) {

            if (populate == true)
                break;

            // Check if there is any obstacle present at sX,sY
            if (food.get(i).isHere(sX, sY))

                return result;
        }

        // Check the obstacle ArrayList first
        for (int i = 0; i < herbivore.size(); i++) {

            // Check if there is any obstacle present at sX,sY
            if (herbivore.get(i).isHere(sX, sY))
                return result;

        }

        // Check the obstacle ArrayList first
        for (int i = 0; i < carnivore.size(); i++) {
            // Check if there is any obstacle present at sX,sY
            if (carnivore.get(i).isHere(sX, sY))
                return result;
        }
        return !result;
    }

    /**
     * shows the obstacle
     *
     * @param inter
     */
    protected void showObstacles(AnInterface inter) {
        for (Obstacle o : obstacles) {
            o.displayEntity(inter);
        }
    }

    /**
     * shows the food
     *
     * @param inter
     */
    protected void showFood(AnInterface inter) {
        for (Food f : food) {
            f.displayEntity(inter);
        }
    }

    /**
     * shows the herbivore
     *
     * @param inter
     */
    protected void showHerbivores(AnInterface inter) {
        for (Herbivore h : herbivore) {
            h.displayEntity(inter);
        }
    }

    /**
     * shows the carnivore
     *
     * @param inter
     */
    protected void showCarnivores(AnInterface inter) {
        for (Carnivore c : carnivore) {
            c.displayEntity(inter);
        }
    }

    /**
     * Method to report if food exists at a particular location or not
     *
     * @param a
     * @param b
     * @return
     */
    protected boolean reportFoodHerbivore(int a, int b) {
        for (Food f : food) {
            if (f.getX() == a && f.getY() == b && f.getTag() == 'v') {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to report if food exists at a particular location or not
     *
     * @param a
     * @param b
     * @return
     */
    protected boolean reportFoodCarnivore(int a, int b) {
        for (Food f : food) {
            if (f.getX() == a && f.getY() == b && f.getTag() == 'n') {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to see of there is food in the particular direction
     *
     * @param d1
     * @param d2
     * @param range
     * @param currentEntity
     */
    protected void smellFoodHerbivore(Direction d1, Direction d2, int range, Herbivore currentEntity) {

        // d2 stores the direction opposite to the current direction, d1

		/*
         * d3 stores a random direction which isn't a direction it's currently
		 * going into, or the direction opposite to it. d4 stores the direction
		 * opposite to d3.
		 */
        Direction d3, d4;
        boolean flag = false;
        int count = 0;

		/*
		 * Do while loop to check if the random direction assigned is not equal
		 * to the direction it's currently going in, or the direction opposite
		 * to its current direction.
		 */

        do {
            d3 = Direction.getDirection();
        } while (d3 == d1 || d3 == d2);

        // Do while loop to find the direction opposite of d2
        do {
            d4 = Direction.getDirection();
        } while (d4 == d1 || d4 == d2 || d4 == d3);

        do {
            // Switch case to smell
            switch (d3) {
                case NORTH:
                    for (int i = currentEntity.getX(); i >= currentEntity.getX() - range; i--) {

                        // Checking if it can move
                        if (i > 0 && canMove(i, currentEntity.getY())) {
                            // Checking if there is food
                            if (reportFoodHerbivore(i, currentEntity.getY())) {
                                // Change the d1 to d3
                                do {
                                    currentEntity.setDirection();
                                } while (currentEntity.knowDirection() != d3);
                                flag = true;
                                break;
                            }

                            // Seeing if it has reached the last iteration
                            if (i == currentEntity.getX() - range) {
                                // To check if the direction has been changed
                                // twice
                                if (count < 2) {
                                    d3 = d4;
                                    count++;
                                } else {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        // When it can't move, change d3=d4
                        else {
                            // To check if the direction has been changed twice
                            if (count < 2) {
                                d3 = d4;
                                count++;
                            } else {
                                flag = true;
                                break;
                            }
                        }
                    }

                    break;

                case SOUTH:
                    for (int i = currentEntity.getX() + 1; i <= currentEntity.getX() + range; i++) {
                        // Checking if it can move
                        if (i < x && canMove(i, currentEntity.getY())) {
                            // Checking if there is food
                            if (reportFoodHerbivore(i, currentEntity.getY())) {
                                // Change the d1 to d3
                                do {
                                    currentEntity.setDirection();
                                } while (currentEntity.knowDirection() != d3);
                                flag = true;
                                break;
                            }
                            // Seeing if it has reached the last iteration
                            if (i == currentEntity.getX() + range) {
                                // To check if the direction has been changed twice
                                if (count < 2) {
                                    d3 = d4;
                                    count++;
                                } else {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        // When it can't move, change d3=d4
                        else {
                            // To check if the direction has been changed twice
                            if (count < 2) {
                                d3 = d4;
                                count++;
                            } else {
                                flag = true;
                                break;
                            }
                        }
                    }

                    break;
                case EAST:
                    for (int i = currentEntity.getY() + 1; i <= currentEntity.getY() + range; i++) {
                        // Checking if it can move
                        if (i < y && canMove(currentEntity.getX(), i)) {
                            // Checking if there is food
                            if (reportFoodHerbivore(currentEntity.getX(), i)) {
                                // Change the d1 to d3
                                do {
                                    currentEntity.setDirection();
                                } while (currentEntity.knowDirection() != d3);
                                flag = true;
                                break;
                            }

                            // Seeing if it has reached the last iteration
                            if (i == currentEntity.getY() + range) {
                                // To check if the direction has been changed twice
                                if (count < 2) {
                                    d3 = d4;
                                    count++;
                                } else {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        // When it can't move, change d3=d4
                        else {
                            // To check if the direction has been changed twice
                            if (count < 2) {
                                d3 = d4;
                                count++;
                            } else {
                                flag = true;
                                break;
                            }
                        }
                    }

                    break;
                case WEST:
                    for (int i = currentEntity.getY() - 1; i >= currentEntity.getY() - range; i--) {
                        // Checking if it can move
                        if (i > 0 && canMove(currentEntity.getX(), i)) {
                            // Checking if there is food
                            if (reportFoodHerbivore(currentEntity.getX(), i)) {
                                // Change the d1 to d3
                                do {
                                    currentEntity.setDirection();
                                } while (currentEntity.knowDirection() != d3);
                                flag = true;
                                break;
                            }

                            // Seeing if it has reached the last iteration, if yes,
                            // do the following
                            if (i == currentEntity.getY() - range) {
                                // To check if the direction has been changed twice
                                if (count < 2) {
                                    d3 = d4;
                                    count++;
                                } else {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        // When it can't move, change d3=d4
                        else {
                            // To check if the direction has been changed twice
                            if (count < 2) {
                                d3 = d4;
                                count++;
                            } else {
                                flag = true;
                                break;
                            }

                        }
                    }

                    break;
            }
        } while (flag == false);

    }

    // Method to see of there is food in the particular direction
    protected void smellFoodCarnivore(Direction d1, Direction d2, int range, Carnivore currentEntity) {

        // d2 stores the direction opposite to the current direction, d1

		/*
		 * d3 stores a random direction which isn't a direction it's currently
		 * going into, or the direction opposite to it. d4 stores the direction
		 * opposite to d3.
		 */
        Direction d3, d4;
        boolean flag = false;
        int count = 0;

		/*
		 * Do while loop to check if the random direction assigned is not equal
		 * to the direction it's currently going in, or the direction opposite
		 * to its current direction.
		 */

        do {
            d3 = Direction.getDirection();
        } while (d3 == d1 || d3 == d2);

        // Do while loop to find the direction opposite of d2
        do {
            d4 = Direction.getDirection();
        } while (d4 == d1 || d4 == d2 || d4 == d3);

        do {
            // Switch case to smell
            switch (d3) {
                case NORTH:
                    for (int i = currentEntity.getX(); i >= currentEntity.getX() - range; i--) {

                        // Checking if it can move
                        if (i > 0 && canMove(i, currentEntity.getY())) {
                            // Checking if there is food
                            if (reportFoodCarnivore(i, currentEntity.getY())) {
                                // Change the d1 to d3
                                do {
                                    currentEntity.setDirection();
                                } while (currentEntity.knowDirection() != d3);
                                flag = true;
                                break;
                            }

                            // Seeing if it has reached the last iteration
                            if (i == currentEntity.getX() - range) {
                                // To check if the direction has been changed
                                // twice
                                if (count < 2) {
                                    d3 = d4;
                                    count++;
                                } else {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        // When it can't move, change d3=d4
                        else {
                            // To check if the direction has been changed twice
                            if (count < 2) {
                                d3 = d4;
                                count++;
                            } else {
                                flag = true;
                                break;
                            }
                        }
                    }

                    break;

                case SOUTH:
                    for (int i = currentEntity.getX() + 1; i <= currentEntity.getX() + range; i++) {
                        // Checking if it can move
                        if (i < x && canMove(i, currentEntity.getY())) {
                            // Checking if there is food
                            if (reportFoodCarnivore(i, currentEntity.getY())) {
                                // Change the d1 to d3
                                do {
                                    currentEntity.setDirection();
                                } while (currentEntity.knowDirection() != d3);
                                flag = true;
                                break;
                            }
                            // Seeing if it has reached the last iteration
                            if (i == currentEntity.getX() + range) {
                                // To check if the direction has been changed twice
                                if (count < 2) {
                                    d3 = d4;
                                    count++;
                                } else {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        // When it can't move, change d3=d4
                        else {
                            // To check if the direction has been changed twice
                            if (count < 2) {
                                d3 = d4;
                                count++;
                            } else {
                                flag = true;
                                break;
                            }
                        }
                    }

                    break;
                case EAST:
                    for (int i = currentEntity.getY() + 1; i <= currentEntity.getY() + range; i++) {
                        // Checking if it can move
                        if (i < y && canMove(currentEntity.getX(), i)) {
                            // Checking if there is food
                            if (reportFoodCarnivore(currentEntity.getX(), i)) {
                                // Change the d1 to d3
                                do {
                                    currentEntity.setDirection();
                                } while (currentEntity.knowDirection() != d3);
                                flag = true;
                                break;
                            }

                            // Seeing if it has reached the last iteration
                            if (i == currentEntity.getY() + range) {
                                // To check if the direction has been changed twice
                                if (count < 2) {
                                    d3 = d4;
                                    count++;
                                } else {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        // When it can't move, change d3=d4
                        else {
                            // To check if the direction has been changed twice
                            if (count < 2) {
                                d3 = d4;
                                count++;
                            } else {
                                flag = true;
                                break;
                            }
                        }
                    }

                    break;
                case WEST:
                    for (int i = currentEntity.getY() - 1; i >= currentEntity.getY() - range; i--) {
                        // Checking if it can move
                        if (i > 0 && canMove(currentEntity.getX(), i)) {
                            // Checking if there is food
                            if (reportFoodCarnivore(currentEntity.getX(), i)) {
                                // Change the d1 to d3
                                do {
                                    currentEntity.setDirection();
                                } while (currentEntity.knowDirection() != d3);
                                flag = true;
                                break;
                            }

                            // Seeing if it has reached the last iteration, if yes,
                            // do the following
                            if (i == currentEntity.getY() - range) {
                                // To check if the direction has been changed twice
                                if (count < 2) {
                                    d3 = d4;
                                    count++;
                                } else {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        // When it can't move, change d3=d4
                        else {
                            // To check if the direction has been changed twice
                            if (count < 2) {
                                d3 = d4;
                                count++;
                            } else {
                                flag = true;
                                break;
                            }

                        }
                    }

                    break;
            }
        } while (flag == false);

    }

    /**
     * Method which makes every entity move
     *
     * @param opposite
     */
    protected void moveCarnivore(Direction opposite) {

        // Loop where the magic happens

        for (int i = 0; i < carnivore.size(); i++) {
            int temp1 = 0, temp2 = 0;
            boolean temp3 = false;

            // Smelling food
            smellFoodCarnivore(carnivore.get(i).knowDirection(), opposite, RANGE, carnivore.get(i));

            switch (carnivore.get(i).knowDirection()) {

                case NORTH:
                    temp1 = -1;
                    temp2 = 0;
                    // Checking if the next coordinate is available
                    if (carnivore.get(i).getX() + temp1 >= 0) {
                        temp3 = true;
                        opposite = Direction.SOUTH;
                    }
                    // Otherwise change direction
                    else
                        carnivore.get(i).setDirection();

                    break;

                case SOUTH:
                    temp1 = 1;
                    temp2 = 0;
                    // Checking if the next coordinate is available
                    if (carnivore.get(i).getX() + temp1 < getX()) {
                        temp3 = true;
                        opposite = Direction.NORTH;
                    }
                    // Otherwise change direction
                    else
                        carnivore.get(i).setDirection();
                    break;

                case EAST:
                    temp1 = 0;
                    temp2 = 1;
                    // Checking if the next coordinate is available
                    if (carnivore.get(i).getY() + temp2 < getY()) {
                        temp3 = true;
                        opposite = Direction.WEST;
                    }
                    // Otherwise change direction
                    else
                        carnivore.get(i).setDirection();

                    break;

                case WEST:
                    temp1 = 0;
                    temp2 = -1;
                    // Checking if the next coordinate is available
                    if (carnivore.get(i).getY() + temp2 >= 0) {
                        temp3 = true;
                        opposite = Direction.EAST;
                    }
                    // Otherwise change direction
                    else
                        carnivore.get(i).setDirection();

                    break;

            }

            // To see if the entity can move to the next pos
            if (temp3) {
                // Checking if the entity can move to the next
                // coordinate
                if (canMove(carnivore.get(i).getX() + temp1, carnivore.get(i).getY() + temp2)) {

                    // Checking if there is food in the next position
                    if (reportFoodCarnivore(carnivore.get(i).getX() + temp1, carnivore.get(i).getY() + temp2)) {

                        for (int k = 0; i < food.size(); k++) {
                            if (food.get(k).getX() == carnivore.get(i).getX() + temp1
                                    && food.get(k).getY() == carnivore.get(i).getY() + temp2) {
                                food.remove(k);
                                break;
                            }
                        }
                        carnivore.get(i).setX(carnivore.get(i).getX() + temp1);
                        carnivore.get(i).setY(carnivore.get(i).getY() + temp2);

                        // Updating its energy
                        carnivore.get(i).setEnergy(carnivore.get(i).getEnergy() + 5);
                        // updating count
                        count++;
                    }
                    // else make it move to the required direction by 1
                    // unit
                    else {
						/*
						 * Moving the entity up/down one place and updating its
						 * position
						 */
                        carnivore.get(i).setX(carnivore.get(i).getX() + temp1);
                        carnivore.get(i).setY(carnivore.get(i).getY() + temp2);
                    }
                }

                // since it cannot move in that direction change its
                // direction
                else {
                    carnivore.get(i).setDirection();
                }
            }
        }

    }

    /**
     * Method which makes every entity move
     *
     * @param opposite
     */
    protected void moveHerbivore(Direction opposite) {

        // Loop where the magic happens

        for (int i = 0; i < herbivore.size(); i++) {
            int temp1 = 0, temp2 = 0;
            boolean temp3 = false;

            // Smelling food
            smellFoodHerbivore(herbivore.get(i).knowDirection(), opposite, RANGE, herbivore.get(i));

            switch (herbivore.get(i).knowDirection()) {

                case NORTH:
                    temp1 = -1;
                    temp2 = 0;
                    // Checking if the next coordinate is available
                    if (herbivore.get(i).getX() + temp1 >= 0) {
                        temp3 = true;
                        opposite = Direction.SOUTH;
                    }
                    // Otherwise change direction
                    else
                        herbivore.get(i).setDirection();

                    break;

                case SOUTH:
                    temp1 = 1;
                    temp2 = 0;
                    // Checking if the next coordinate is available
                    if (herbivore.get(i).getX() + temp1 < getX()) {
                        temp3 = true;
                        opposite = Direction.NORTH;
                    }
                    // Otherwise change direction
                    else
                        herbivore.get(i).setDirection();
                    break;

                case EAST:
                    temp1 = 0;
                    temp2 = 1;
                    // Checking if the next coordinate is available
                    if (herbivore.get(i).getY() + temp2 < getY()) {
                        temp3 = true;
                        opposite = Direction.WEST;
                    }
                    // Otherwise change direction
                    else
                        herbivore.get(i).setDirection();

                    break;

                case WEST:
                    temp1 = 0;
                    temp2 = -1;
                    // Checking if the next coordinate is available
                    if (herbivore.get(i).getY() + temp2 >= 0) {
                        temp3 = true;
                        opposite = Direction.EAST;
                    }
                    // Otherwise change direction
                    else
                        herbivore.get(i).setDirection();

                    break;

            }

            // To see if the entity can move to the next pos.
            if (temp3) {
                // Checking if the entity can move to the next
                // coordinate
                if (canMove(herbivore.get(i).getX() + temp1, herbivore.get(i).getY() + temp2)) {

                    // Checking if there is food in the next position
                    if (reportFoodHerbivore(herbivore.get(i).getX() + temp1, herbivore.get(i).getY() + temp2)) {
                        // Moving the entity up/down one place and
                        // updating
                        // its
                        // position
                        for (int k = 0; i < food.size(); k++) {
                            if (food.get(k).getX() == herbivore.get(i).getX() + temp1
                                    && food.get(k).getY() == herbivore.get(i).getY() + temp2) {
                                food.remove(k);
                                break;
                            }
                        }
                        herbivore.get(i).setX(herbivore.get(i).getX() + temp1);
                        herbivore.get(i).setY(herbivore.get(i).getY() + temp2);

                        // Updating its energy
                        herbivore.get(i).setEnergy(herbivore.get(i).getEnergy() + 5);
                        // updating count
                        count++;
                    }
                    // else make it move to the required direction by 1
                    // unit
                    else {
                        // Moving the entity up/down one place and
                        // updating
                        // its
                        // position
                        herbivore.get(i).setX(herbivore.get(i).getX() + temp1);
                        herbivore.get(i).setY(herbivore.get(i).getY() + temp2);
                    }
                }

                // since it cannot move in that direction change its
                // direction
                else {
                    herbivore.get(i).setDirection();
                }
            }
        }

    }

    /**
     * This method checks if any letters are entered while filling in the
     * world's size and the entity details
     *
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     * @param s5
     * @param s6
     * @return
     */
    protected boolean checkForLetters(String s1, String s2, String s3, String s4, String s5, String s6) {

        boolean f1 = checkIfItsANumber(s1), f2 = checkIfItsANumber(s2), f3 = checkIfItsANumber(s3),
                f4 = checkIfItsANumber(s4), f5 = checkIfItsANumber(s5), f6 = checkIfItsANumber(s6);

        return (f1 && f2 && f3 && f4 && f5 && f6);
    }

    /**
     * THis method checks if s is a number
     *
     * @param s
     * @return
     */
    protected boolean checkIfItsANumber(String s) {
        boolean result = false;
        for (int i = 0; i < s.length(); i++) {
            try {
                int digit = Integer.parseInt("" + s.charAt(i));
                if (digit >= 0 && digit <= 9)
                    result = true;
                else {
                    result = false;
                    break;
                }
            } catch (NumberFormatException nfe) {
                result = false;
                break;
            }
        }
        return result;

    }

}

package reading.kartik.ArtificialSimulator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

import java.io.*;

/**
 * This is the class where all the GUI goodness happens, It draws, and moves the
 * entities, when it is made to run. It sets the canvas, the menu and the
 * toolbar necessary to control the simulation.
 *
 * @author Kartik
 * @version 1.0
 */

public class AnInterface extends Application {

    GraphicsContext gc;
    AnimationTimer timer;
    protected Stage pStage;
    private VBox vBox;
    private boolean runState = false;

    protected AWorld world = new AWorld();

    protected int sf = 40;

    /**
     * This method is used to open explorer and save/load files with the
     * extension txt
     *
     * @return
     */
    private FileChooser fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".txt", "*.txt"));
        return fileChooser;
    }

    /**
     * This method sets the MenuBar
     */
    protected MenuBar setMenu() {

        MenuBar menubar = new MenuBar();

        Menu file = new Menu("File");
        MenuItem newConfig = new MenuItem("New Configuration");
        newConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage dialog = new Stage();
                dialog.setTitle("New Configuration");
                dialog.initModality(Modality.APPLICATION_MODAL);
                // Defines a modal window that blocks events from being
                // delivered to any other application window.
                dialog.initOwner(pStage);

                GridPane gridPane = new GridPane();
                Text scenetitle = new Text("Enter the details");
                scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                gridPane.add(scenetitle, 0, 0, 2, 1);
                gridPane.setAlignment(Pos.CENTER);
                gridPane.setHgap(15);
                gridPane.setVgap(10);

                Label xCordinate = new Label("X Coordinate");
                TextField xCord = new TextField();
                gridPane.add(xCordinate, 0, 1);
                gridPane.add(xCord, 0, 2);

                Label yCordinate = new Label("Y Coordinate");
                TextField yCord = new TextField();
                gridPane.add(yCordinate, 1, 1);
                gridPane.add(yCord, 1, 2);

                Label obstacle = new Label("Obstacle(%)");
                TextField tObst = new TextField();
                gridPane.add(obstacle, 0, 3);
                gridPane.add(tObst, 0, 4);

                Label food = new Label("Food(%)");
                TextField tFood = new TextField();
                gridPane.add(food, 1, 3);
                gridPane.add(tFood, 1, 4);

                Label herbivore = new Label("Herbivores");
                TextField tHerbivore = new TextField();
                gridPane.add(herbivore, 0, 5);
                gridPane.add(tHerbivore, 0, 6);

                Label carnivore = new Label("Carnivores");
                TextField tCarnivore = new TextField();
                gridPane.add(carnivore, 1, 5);
                gridPane.add(tCarnivore, 1, 6);

                Button button = new Button("Enter");
                button.setMinWidth(30);
                button.setAlignment(Pos.BASELINE_LEFT);

                BooleanBinding booleanBind = xCord.textProperty().isEmpty().or(yCord.textProperty().isEmpty())
                        .or(tObst.textProperty().isEmpty()).or(tFood.textProperty().isEmpty())
                        .or(tHerbivore.textProperty().isEmpty()).or(tCarnivore.textProperty().isEmpty());

                button.disableProperty().bind(booleanBind);

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        // Checking if any character entered is a letter
                        if (!world.checkForLetters("" + xCord.getCharacters(), "" + yCord.getCharacters(),
                                "" + tFood.getCharacters(), "" + tObst.getCharacters(), "" + tCarnivore.getCharacters(),
                                "" + tHerbivore.getCharacters())) {
                            // Show alert
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("Please enter only numbers!");

                            alert.showAndWait();
                        }

                        // Checking if the sum of food, obstacles and entities
                        // is greater than the world's area
                        else if ((Double.parseDouble("" + tFood.getCharacters())
                                + Double.parseDouble("" + tObst.getCharacters()))
                                * Double.parseDouble("" + xCord.getCharacters())
                                * Double.parseDouble("" + yCord.getCharacters()) / 100.0
                                + Double.parseDouble("" + tHerbivore.getCharacters())
                                + Double.parseDouble("" + tCarnivore.getCharacters()) > Double.parseDouble(
                                "" + xCord.getCharacters()) * Double.parseDouble("" + yCord.getCharacters())) {

                            // Show alert
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText(
                                    "Sum of entities, obstacles and food is greater than the world's area!");

                            alert.showAndWait();

                        }

                        // else start printing the world and close this stage
                        else {
                            // Setting the world coordinates

                            world.setX(Integer.parseInt("" + xCord.getCharacters()));
                            world.setY(Integer.parseInt("" + yCord.getCharacters()));
                            double area = Double.parseDouble("" + xCord.getCharacters())
                                    * Double.parseDouble("" + yCord.getCharacters());
                            // world.initialiseArray();

                            // Setting the number of obstacles and food
                            world.addObstacles(
                                    ((int) ((Double.parseDouble("" + tObst.getCharacters()) * area) / 100.0)));
                            world.addFood(((int) ((Double.parseDouble("" + tFood.getCharacters()) * area) / 100.0)));

                            // Setting the number of herbivores and carnivores
                            world.addCarnivore(Integer.parseInt("" + tCarnivore.getCharacters()));
                            world.addHerbivore(Integer.parseInt("" + tHerbivore.getCharacters()));

                            dialog.close();
                            pStage.close();

                            setStage(pStage);

                            drawWorld();
                            world.config = "";
                            world.config += writeToFile();// writeToFIle returns
                            // a string, and can
                            // be read by the
                            // readFromFile
                            // function for
                            // resetting the map
                            world.populate = true;
                        }
                    }
                });
                gridPane.add(button, 1, 7);

                Scene dialogScene = new Scene(gridPane, 400, 300);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
        MenuItem openConfig = new MenuItem("Open Configuration");
        openConfig.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                FileChooser fileChooser = null;
                File inFile = null;
                try {
                    fileChooser = fileChooser();
                    inFile = fileChooser.showOpenDialog(pStage);
                    String n = inFile.getName();
                    System.out.println(n);
                    readFromFile(inFile.getPath(), 1);// 1 is when we read from
                    // a file, 2 is when we
                    // read from a string
                    pStage.close();
                    setStage(pStage);
                    drawWorld();
                    world.populate = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem save = new MenuItem("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                save();
            }
        });
        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                save();
            }
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pStage.close();
            }
        });
        // adding all the MenuItems to menu
        file.getItems().addAll(newConfig, openConfig, save, saveAs, exit);

        Menu view = new Menu("View");
        MenuItem displayConfig = new MenuItem("Display Configuration");
        MenuItem editConfig = new MenuItem("Edit Configuration");
        MenuItem displayInfoAboutLifeForms = new MenuItem("Display info about all the Life Forms");
        displayInfoAboutLifeForms.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage dialog = new Stage();
                dialog.setTitle("Information about Life Forms");
                dialog.initModality(Modality.APPLICATION_MODAL);
                // Defines a modal window that blocks events from being
                // delivered to any other application window.
                dialog.initOwner(pStage);

                TableView<AnEntity> table;
                VBox vb = new VBox();

                // Setting the scene
                Scene dialogScene = new Scene(vb, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();

            }
        });
        MenuItem displayInfoAboutMap = new MenuItem("Display info about Map");
        displayInfoAboutMap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage dialog = new Stage();
                dialog.setTitle("Information about Map");
                dialog.initModality(Modality.APPLICATION_MODAL);
                // Defines a modal window that blocks events from being
                // delivered to any other application window.
                dialog.initOwner(pStage);

                // GridPane gridPane= new GridPane();
                VBox vb = new VBox();
                Label x = new Label("X-Size: " + world.getX());
                Label y = new Label("Y-Size: " + world.getY());
                Label ob = new Label("No. of Obstacles: " + world.obstacles.size());
                Label food = new Label("Amount of Food: " + world.food.size());
                Label herbi = new Label("No. of Herbivores: " + world.numberOfHerbivores);
                Label carni = new Label("No. of Carnivores: " + world.numberOfCarnivores);

                vb.getChildren().addAll(x, y, ob, food, herbi, carni);
                vb.setSpacing(25);
                vb.setPadding(new Insets(30, 65, 30, 65));

                // Setting the scene
                Scene dialogScene = new Scene(vb);
                dialog.setScene(dialogScene);
                dialog.show();

            }
        });
        // adding all the MenuItems to the menu
        view.getItems().addAll(displayConfig, editConfig, displayInfoAboutLifeForms, displayInfoAboutMap);

        Menu edit = new Menu("Edit");
        MenuItem modifyLifeFormParameters = new MenuItem("Modify current life form parameters");
        MenuItem removeCurrentLifeForm = new MenuItem("Remove Current Life Form");
        MenuItem addNewLifeForm = new MenuItem("Add a new life form");
        addNewLifeForm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (world.populate == true) { // Seeing if the world is
                    // populated, if not, do
                    // nothing.
                    Stage dialog = new Stage();
                    dialog.setTitle("New Configuration");
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    // Defines a modal window that blocks events from being
                    // delivered to any other application window.
                    dialog.initOwner(pStage);

                    GridPane gridPane = new GridPane();
                    Text scenetitle = new Text("Add Life Forms");
                    scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                    gridPane.add(scenetitle, 0, 0, 2, 1);
                    gridPane.setAlignment(Pos.CENTER);
                    gridPane.setHgap(15);
                    gridPane.setVgap(10);
                    Label herbivore = new Label("Herbivores");
                    TextField tHerbivore = new TextField();
                    gridPane.add(herbivore, 0, 1);
                    gridPane.add(tHerbivore, 0, 2);

                    Label carnivore = new Label("Carnivores");
                    TextField tCarnivore = new TextField();
                    gridPane.add(carnivore, 0, 3);
                    gridPane.add(tCarnivore, 0, 4);

                    Button button = new Button("Enter");
                    button.setMinWidth(30);
                    button.setAlignment(Pos.BASELINE_LEFT);

                    // This part disables the buttons till all the fields are
                    // filled
                    BooleanBinding booleanBind = (tHerbivore.textProperty().isEmpty())
                            .or(tCarnivore.textProperty().isEmpty());
                    button.disableProperty().bind(booleanBind);

                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            // Checking if the sum of food, obstacles and
                            // entities is greater than the world's area
                            if ((double) world.obstacles.size() + (double) world.food.size()
                                    + Double.parseDouble("" + tHerbivore.getCharacters())
                                    + Double.parseDouble("" + tCarnivore.getCharacters()) > world.getX()
                                    * world.getY()) {
                                // Show alert
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText(null);
                                alert.setContentText(
                                        "Sum of entities, obstacles and food is greater than the world's area!");
                                alert.showAndWait();
                            } else {
                                // Setting the number of herbivores and
                                // carnivores
                                world.addCarnivore(Integer.parseInt("" + tCarnivore.getCharacters()));
                                world.addHerbivore(Integer.parseInt("" + tHerbivore.getCharacters()));

                                dialog.close();
                                pStage.close();
                                setStage(pStage);
                                drawWorld();

                                world.config = "";
                                world.config += writeToFile();// writeToFIle
                                // returns a
                                // string, and
                                // can be read
                                // by the
                                // readFromFile
                                // function for
                                // resetting the
                                // map
                                world.populate = true;
                            }
                        }
                    });
                    gridPane.add(button, 1, 7);

                    Scene dialogScene = new Scene(gridPane, 400, 300);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
            }
        });

        edit.getItems().addAll(modifyLifeFormParameters, removeCurrentLifeForm, addNewLifeForm);

        Menu simulation = new Menu("Simulation");
        MenuItem run = new MenuItem("Run");
        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.start();
            }
        });
        MenuItem pause = new MenuItem("Pause");
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timer.stop();
            }
        });
        MenuItem reset = new MenuItem("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (world.populate == true) {
                    timer.stop();
                    pStage.close();
                    readFromFile(world.config, 2);
                    setStage(pStage);
                    drawWorld();
                }
            }
        });
        MenuItem displayMapAtEachIteration = new MenuItem("Display map at each iteration");
        displayMapAtEachIteration.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listWorld();
                drawWorld();
            }
        });
        // adding all the MenuItems to the menu
        simulation.getItems().addAll(run, pause, reset, displayMapAtEachIteration);

        Menu help = new Menu("Help");
        MenuItem displayInfoAboutApp = new MenuItem("Display info about application");
        displayInfoAboutApp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage dialog = new Stage();
                dialog.setTitle("About Application");
                dialog.initModality(Modality.APPLICATION_MODAL);
                // Defines a modal window that blocks events from being
                // delivered to any other application window.
                dialog.initOwner(pStage);
                VBox vb = new VBox();
                String message = "Welcome to the Artificial Simulator App!\nThis app is a simulation of entities moving towards food by smelling their environment!\nEnjoy your time with it!";
                vb.getChildren().addAll(new Label(message));
                vb.setSpacing(25);
                vb.setPadding(new Insets(30, 65, 20, 20));

                Scene dialogScene = new Scene(vb);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
        MenuItem displayInfoAboutAuthor = new MenuItem("Display info about author");
        displayInfoAboutAuthor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage dialog = new Stage();
                dialog.setTitle("About author");
                dialog.initModality(Modality.APPLICATION_MODAL);
                // Defines a modal window that blocks events from being
                // delivered to any other application window.
                dialog.initOwner(pStage);
                VBox vb = new VBox();
                String message = "Kartik Vashistha is a penultimate student at University of Reading, studying Computer Science.\nIn his free time, he like to code and play games.\nYou can reach out to him on Twitter @Geeky_Kartik";
                vb.getChildren().addAll(new Label(message));
                vb.setSpacing(25);
                vb.setPadding(new Insets(30, 20, 30, 20));

                Scene dialogScene = new Scene(vb);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });
        // adding all the MenuItems to the menu
        help.getItems().addAll(displayInfoAboutApp, displayInfoAboutAuthor);

        menubar.getMenus().addAll(file, view, edit, simulation, help);

        return menubar;
    }

    /**
     * Method to move the Herbivores and the Carnivores
     */
    protected void listWorld() {
        try {
            // calling the compute method which does all of the computation
            Direction opposite = Direction.getDirection();// Stores the opposite
            // direction

            // int num;
            world.moveHerbivore(opposite);
            world.moveCarnivore(opposite);
        } catch (Exception e) {
            // Notifying the user that an error has occurred
            System.out.println("\nSorry, an error occured, please restart the program!");
            e.printStackTrace();

            System.out.println("Terminating program.. Done.");
            System.exit(0);
        }
    }

    /**
     * Method to draw the entity onto the canvas.
     *
     * @param image
     * @param x
     * @param y
     */
    protected void showEntity(Image image, int x, int y) {
        gc.drawImage(image, x * sf, y * sf, sf, sf);
    }

    /**
     * Method which initiates drawing of all the entities in the world
     */
    protected void drawWorld() {
        gc.clearRect(0, 0, world.getX() * sf, world.getY() * sf);

        world.showObstacles(this);
        world.showFood(this);
        world.showHerbivores(this);
        world.showCarnivores(this);
    }

    /**
     * This method sets the stage for GUI interface.
     */
    protected void setStage(Stage stage) {

        stage.setTitle("Artificial Life Simulator");

        BorderPane bp = new BorderPane();
        Group root = new Group();
        vBox = new VBox();
        Canvas canvas = new Canvas(world.getX() * sf, world.getY() * sf);
        Button runOrPause = new Button("Run/Pause");
        runOrPause.setPrefWidth(80);
        runOrPause.setPrefHeight(35);
        Button reset = new Button("Reset");
        reset.setPrefWidth(80);
        reset.setPrefHeight(35);
        Button simulate = new Button("Simulate");
        simulate.setPrefWidth(80);
        simulate.setPrefHeight(35);

        root.getChildren().add(canvas);
        vBox.getChildren().addAll(runOrPause, reset, simulate);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(30, 25, 55, 25));

        bp.setTop(setMenu());
        bp.setRight(vBox);
        bp.setCenter(root);

        gc = canvas.getGraphicsContext2D();

        timer = new AnimationTimer() {
            int t = 0;

            public void handle(long currentNanoTime) {
                if (t % 15 == 0) {
                    // theArena.moveRobots();
                    listWorld();
                    drawWorld();
                }
                t++;
            }
        };

        runOrPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (world.populate == true && runState == false) {
                    timer.start();
                    runState = true;
                } else if (runState == true) {
                    timer.stop();
                    runState = false;
                }
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (world.populate == true) {
                    timer.stop();
                    pStage.close();
                    readFromFile(world.config, 2);
                    setStage(pStage);
                    drawWorld();
                }
            }
        });

        // Displays the map at each iteration, whenever it's pressed
        simulate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listWorld();
                drawWorld();
            }
        });

        Scene scene = new Scene(bp);

        stage.setScene(scene);
        stage.show();
        pStage = stage;
    }

    /**
     * Method that return a string of all the details of the world which then
     * writes to the file
     *
     * @return
     */
    protected String writeToFile() {
        String result = "";

        result += " ";
        // Adding the world coordinates first
        result += world.getX() + " " + world.getY() + " ";

        // Adding the obstacles details
        for (Obstacle o : world.obstacles) {
            result += o.getX() + " " + o.getY() + " ";
        }

        result += ": ";// This colon represents that another entity's details
        // are going to be written now

        // Writing the food details
        for (Food f : world.food) {
            result += f.getX() + " " + f.getY() + " " + f.getSpecies() + " " + f.getTag() + " ";
        }

        result += ": ";// This colon represents that another entity's details
        // are going to be written now

        // Writing the Herbivores details
        for (Herbivore h : world.herbivore) {
            result += h.getX() + " " + h.getY() + " " + h.getSpecies() + " ";
        }

        result += ": ";// This colon represents that another entity's details
        // are going to be written now

        // Writing the Carnivores details
        for (Carnivore c : world.carnivore) {
            result += c.getX() + " " + c.getY() + " " + c.getSpecies() + " ";
        }

        result += "; ";// This semi-colon represents that we have reached the
        // end of the file

        return result;
    }

    /**
     * Method to read the saved details from a file
     *
     * @param file
     * @param mode
     */
    protected void readFromFile(String file, int mode) {
        BufferedReader br = null;
        FileReader fr = null;
        String s = "";
        String[] array;
        int i = 0;
        int counter = 0;
        switch (mode) {
            case 1:
                try {

                    fr = new FileReader(file);
                    br = new BufferedReader(fr);

                    String sCurrentLine;

                    br = new BufferedReader(new FileReader(file));

                    while ((sCurrentLine = br.readLine()) != null) {
                        s += sCurrentLine;
                    }
                    world.config = "";
                    world.config += s;

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    try {
                        if (br != null)
                            br.close();

                        if (fr != null)
                            fr.close();

                    } catch (IOException ex) {

                        ex.printStackTrace();
                    }
                }
                break;
            case 2:
                world.config = "";
                world.config += file;
                counter++;
                break;

        }

        if (mode == 1)
            array = s.split(" ");
        else {
            array = file.split(" ");
        }
        // Now updating the world object's details

        // Setting the world's coordinates
        world.setX(Integer.parseInt(array[counter++]));
        world.setY(Integer.parseInt(array[counter++]));

        // Now writing to the obstacles ArraYlist
        world.obstacles.clear();// clears the ArrayList
        while (!array[counter].equals(":")) {
            world.obstacles.add(new Obstacle());
            world.obstacles.get(i).setX(Integer.parseInt(array[counter++]));
            world.obstacles.get(i).setY(Integer.parseInt(array[counter++]));
            assignImage("rock", i);
            i++;
        }
        counter++;
        // Now writing to the food arralist
        world.food.clear();// clears the ArrayList
        i = 0;
        while (!array[counter].equals(":")) {
            world.food.add(new Food());
            world.food.get(i).setX(Integer.parseInt(array[counter++]));
            world.food.get(i).setY(Integer.parseInt(array[counter++]));
            world.food.get(i).setSpecies(array[counter++]);
            world.food.get(i).setTag(array[counter++].charAt(0));
            assignImage(world.food.get(i).getSpecies(), i);
            i++;
        }
        counter++;
        // Now writing to the herbivore ArrayList
        world.herbivore.clear();// clears the ArrayList
        i = 0;
        while (!array[counter].equals(":")) {
            world.herbivore.add(new Herbivore());
            world.herbivore.get(i).setX(Integer.parseInt(array[counter++]));
            world.herbivore.get(i).setY(Integer.parseInt(array[counter++]));
            world.herbivore.get(i).setSpecies(array[counter++]);
            assignImage(world.herbivore.get(i).getSpecies(), i);
            i++;
        }
        counter++;
        // Now writing to the carnivore ArrayList
        world.carnivore.clear();// clears the ArrayList
        i = 0;
        while (!array[counter].equals(";")) {
            world.carnivore.add(new Carnivore());
            world.carnivore.get(i).setX(Integer.parseInt(array[counter++]));
            world.carnivore.get(i).setY(Integer.parseInt(array[counter++]));
            world.carnivore.get(i).setSpecies(array[counter++]);
            assignImage(world.carnivore.get(i).getSpecies(), i);
            i++;
        }

    }

    /**
     * Method to start the save process, by writing to a file
     */
    protected void save() {
        BufferedWriter bw = null;
        FileWriter fw = null;
        FileChooser fileChooser = null;
        File outFile = null;

        try {
            fileChooser = fileChooser();
            outFile = fileChooser.showSaveDialog(pStage);
            // outFile.createNewFile();
            fw = new FileWriter(outFile);
            bw = new BufferedWriter(fw);

            bw.write(writeToFile());
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }

    /**
     * Method to assign an image to the entities after they are read from a file
     *
     * @param s
     * @param index
     */

    protected void assignImage(String s, int index) {

        switch (s) {
            case "rock":
                world.obstacles.get(index).setImg(world.allImages.get(0));
                break;
            case "meat":
                world.food.get(index).setImg(world.allImages.get(1));
                break;
            case "apple":
                world.food.get(index).setImg(world.allImages.get(2));

                break;
            case "strawberry":
                world.food.get(index).setImg(world.allImages.get(3));

                break;
            case "grass":
                world.food.get(index).setImg(world.allImages.get(4));
                break;
            case "honey":
                world.food.get(index).setImg(world.allImages.get(5));
                break;
            case "bee":
                world.herbivore.get(index).setImg(world.allImages.get(6));
                break;
            case "bison":
                world.herbivore.get(index).setImg(world.allImages.get(7));
                break;
            case "rabbit":
                world.herbivore.get(index).setImg(world.allImages.get(8));
                break;
            case "hyena":
                world.carnivore.get(index).setImg(world.allImages.get(9));
                break;
            case "lion":
                world.carnivore.get(index).setImg(world.allImages.get(10));
                break;
            case "snake":
                world.carnivore.get(index).setImg(world.allImages.get(11));
                break;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        setStage(primaryStage);
    }

    /**
     * This is the main method, which starts the GUI
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

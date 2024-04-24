package drawshapes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DrawShapes extends Application
{
    private ShapeScene shapeScene = new ShapeScene();

    private BorderPane root;
    private Canvas canvas;
    private Color color = Color.RED;
    private Shape currentShape = Shape.RECTANGLE;
    private int width = 800;
    private int height = 600;

    private void clear()
    {
        // delete all of the drawings from the canvas
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void draw()
    {
        clear();
        shapeScene.draw(canvas.getGraphicsContext2D());
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        root = new BorderPane();
        root.setTop(createMenuBar(primaryStage));
        
        canvas = new Canvas(width, height);
        root.setCenter(canvas);

        // add a mouse click event handler
        canvas.setOnMousePressed(event -> {
            // add a rectangle to the scene
            switch (currentShape)
            {
                case RECTANGLE:
                    System.out.println("rectangle of color " +color);
                    shapeScene.addShape(new Rectangle(color, 50, 50, event.getX(), event.getY()));
                    break;
                case CIRCLE:
                    //shapeScene.addShape(new Circle(color, 25, event.getX(), event.getY()));
                    break;
                case SQUARE:
                    //shapeScene.addShape(new Triangle(color, 50, 50, event.getX(), event.getY()));
                    break;
            }
            draw();
        });

        // Set the scene
        Scene scene = new Scene(root, width, height);
        URL styleURL = getClass().getResource("/stylesheet.css");
		String stylesheet = styleURL.toExternalForm();
		scene.getStylesheets().add(stylesheet);

        primaryStage.setTitle("DrawShapes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage primaryStage)
    {
        MenuBar menuBar = new MenuBar();
    	menuBar.getStyleClass().add("menubar");

        //
        // File menu
        //
        Menu fileMenu = new Menu("File");
        addMenuItem(fileMenu, "Load Scene", () -> {
            System.out.println("Load Scene");
        });

        addMenuItem(fileMenu, "Save Scene", () -> {
            System.out.println("Save Scene");
        });

        // add a separator to the fileMenu
        fileMenu.getItems().add(new SeparatorMenuItem());

        addMenuItem(fileMenu, "Exit", () -> {
            primaryStage.close();
        });

        menuBar.getMenus().add(fileMenu);

        //
        // Edit Menu
        //
        Menu editMenu = new Menu("Edit");
        addMenuItem(editMenu, "Clear", () -> {
            shapeScene.clear();
        });

        menuBar.getMenus().add(editMenu);

        //
        // Color Menu
        //
        Menu colorMenu = new Menu("Color");
        addMenuItem(colorMenu, "Red", () -> {
            color = Color.RED;
        });
        addMenuItem(colorMenu, "Green", () -> {
            color = Color.GREEN;
        });
        addMenuItem(colorMenu, "Blue", () -> {
            color = Color.BLUE;
        });
        addMenuItem(colorMenu, "Black", () -> {
            color = Color.BLACK;
        });
        //TODO: add an option for a custom color that is set with RGB values

        menuBar.getMenus().add(colorMenu);


        return menuBar;
    }

    private static void writeToFile(File file, String content) throws IOException
    {
        Files.write(file.toPath(), content.getBytes());
    }

    private void addMenuItem(Menu menu, String name, Runnable action)
    {
        MenuItem menuItem = new MenuItem(name);
        menuItem.setOnAction(event -> action.run());
        menu.getItems().add(menuItem);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}

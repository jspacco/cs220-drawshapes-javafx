package drawshapes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
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
    private boolean shiftDown = false;
    private Point2D startDrag;
    private Rectangle highlight;
    private int width = 800;
    private int height = 600;

    // very light blue color that is mostly translucent
    private static Color HIGHLIGHT_COLOR = new Color(0.5, 0.5, 1.0, 0.2);

    private void clear()
    {
        // delete all of the drawings from the canvas
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void draw()
    {
        clear();
        shapeScene.draw(canvas.getGraphicsContext2D());
        if (highlight != null)
        {
            highlight.draw(canvas.getGraphicsContext2D());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        root = new BorderPane();
        root.setTop(createMenuBar(primaryStage));
        
        canvas = new Canvas(width, height);
        root.setCenter(canvas);

        // add a keyboard handler
        root.setOnKeyPressed(event -> {
            switch (event.getCode())
            {
                case R:
                    currentShape = Shape.RECTANGLE;
                    break;
                case C:
                    currentShape = Shape.CIRCLE;
                    break;
                case T:
                    currentShape = Shape.TRIANGLE;
                    break;
                case S:
                    currentShape = Shape.SQUARE;
                    break;
                case SHIFT:
                    shiftDown = true;
                    break;
            }
        });

        // add a keyboard release handler
        root.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SHIFT)
            {
                shiftDown = false;
            }
        });

        canvas.setOnMouseDragged(event -> {
            shapeScene.unselectAll();
            Point2D currentDrag = new Point2D(event.getX(), event.getY());

            // holy crap, it is ELEGEANT A.F. to use the dx and dy here
            double dx = currentDrag.getX() - startDrag.getX();
            double dy = currentDrag.getY() - startDrag.getY();
            // this line is gorgeous
            highlight = new Rectangle(HIGHLIGHT_COLOR, Math.abs(dx), Math.abs(dy), startDrag.getX() + dx/2, startDrag.getY() + dy/2);
            shapeScene.selectShapes(highlight);
            draw();
        });

        canvas.setOnMousePressed(event -> {
            startDrag = new Point2D(event.getX(), event.getY());
        });

        canvas.setOnMouseReleased(event -> {
            highlight = null;
            draw();
        });

        // add a mouse click event handler
        canvas.setOnMouseClicked(event -> {
            // only draw a shape if the mouse was not dragged
            if (event.getX() != startDrag.getX() || event.getY() != startDrag.getY()) return;
            System.out.println("CLICK");
            switch (currentShape)
            {
                case RECTANGLE:
                    System.out.println("rectangle of color " +color);
                    shapeScene.addShape(new Rectangle(color, 50, 50, event.getX(), event.getY()));
                    break;
                case CIRCLE:
                    System.out.println("circle of color " +color);
                    //shapeScene.addShape(new Circle(color, 25, event.getX(), event.getY()));
                    break;
                case TRIANGLE:
                    System.out.println("triangle of color " +color);
                    //shapeScene.addShape(new Triangle(color, 50, 50, event.getX(), event.getY()));
                    break;
                case SQUARE:
                    System.out.println("square of color " +color);
                    //shapeScene.addShape(new Square(color, 50, event.getX(), event.getY()));
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
            draw();
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

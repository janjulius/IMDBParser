package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load in the .fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        // Extract controller
        ViewController view = (ViewController) fxmlLoader.getController();

        primaryStage.setTitle("Parser");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Initializing classes
        Model model = new Model();
        Controller controller = new Controller(model, view);
        Parser parser = new Parser(controller);

        // Initialize some values
        controller.setup();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

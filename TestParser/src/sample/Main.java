package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Parser");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        Model model = new Model();
        Controller controller = new Controller(model);
        Parser parser = new Parser(controller);

        parser.parseMovie();
        model.printMovies();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

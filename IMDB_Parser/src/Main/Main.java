package Main;

import Data.ObjectStorage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load in the .fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Fxml/parser.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        // Extract controller
        ViewController view = (ViewController) fxmlLoader.getController();

        primaryStage.setTitle("Parser");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Initializing classes
        ObjectStorage objectStorage = new ObjectStorage();
        Controller controller = new Controller(objectStorage, view, primaryStage);
        Parser parser = new Parser(controller);

        // Initialize some values
        controller.setup();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

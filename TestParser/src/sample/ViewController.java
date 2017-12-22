package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;

public class ViewController {

    Controller controller;

    @FXML ProgressBar progressbarActor;

    public void setProgressBar(double value){
        progressbarActor.setProgress(value);
    }

    public void injectController(Controller controller){
        this.controller = controller;
    }

    @FXML
    public void actorParseButton(){
        controller.view.setProgressBar(0);
        ZonedDateTime dt = ZonedDateTime.now();
        try{
            this.controller.parser.parseActor();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ZonedDateTime dt2 = ZonedDateTime.now();
        System.out.println(String.format("Parsed list in %s seconds", Duration.between(dt, dt2).getSeconds()));
    }
}

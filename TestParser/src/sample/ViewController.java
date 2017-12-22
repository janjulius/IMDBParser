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
    public void actorParseButton() throws IOException{
        controller.view.setProgressBar(0);
        ZonedDateTime dt = ZonedDateTime.now();

        try {
            new Thread(() ->
            {
                try{
                    this.controller.parser.parseActor();
                    ZonedDateTime dt2 = ZonedDateTime.now();
                    System.out.println(String.format("Parsed list in %s seconds", Duration.between(dt, dt2).getSeconds()));
                }
                catch(IOException e){
                    throw new RuntimeException();
                }
            }).start();
        }
        catch(RuntimeException e){
            if(e.getCause() instanceof IOException){
//                throw e.getCause();
            }
            else
                throw e;
        }
    }
}

package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.ZonedDateTime;

import Constants.*;

/**
 * ViewController
 * @author Jos de Vries
 */
public class ViewController {

    Controller controller;

    public final int
            ACTORS_LIST = 0,
            ACTRESS_LIST = 1,
            BUSINESS_LIST = 2,
            COUNTRIES_LIST = 3,
            GENRES_LIST = 4,
            MOVIES_LIST = 5,
            RATINGS_LIST = 6,
            RUNNINGTIMES_LIST = 7;

    @FXML ProgressBar progressbarMovie;
    @FXML ProgressBar progressbarActors;
    @FXML ProgressBar progressbarBusiness;
    @FXML ProgressBar progressbarRunningTimes;
    @FXML ProgressBar progressbarRating;
    @FXML ProgressBar progressbarCountries;

    @FXML Button parseMovieButton;
    @FXML Button parseActorsButton;
    @FXML Button parseBusinessButton;
    @FXML Button parseRunningTimesButton;
    @FXML Button parseRatingsButton;
    @FXML Button parseCountriesButton;
    @FXML Button writeCsvButton;

    // Certain lists shouldn't be parsed if other lists haven't been parsed yet.
    public void setup(){
        parseBusinessButton.setDisable(true);
        parseRunningTimesButton.setDisable(true);
        parseRatingsButton.setDisable(true);
        parseCountriesButton.setDisable(true);
        parseActorsButton.setDisable(true);
//        writeCsvButton.setDisable(true);
    }

    public void setProgressBar(double value){
        progressbarActors.setProgress(value);
    }

    public void injectController(Controller controller){
        this.controller = controller;
    }

    @FXML
    public void movieParseButton() throws IOException{
        ZonedDateTime dt = ZonedDateTime.now();
        this.controller.parser.parseMovie();
        progressbarMovie.setProgress(100);

        int count = 0;

//        System.out.println(Arrays.asList(controller.model.returnMovieHash()));
        for (String key : controller.model.returnMovieHash().keySet()){
            if (count < 300){
                Movie m = controller.model.returnMovieHash().get(key);
                //System.out.println("Title of movie: " + m.getTitle() + " Key of movie: " + key);
                count++;
            }
            count++;
        }

        parseBusinessButton.setDisable(false);
        parseRunningTimesButton.setDisable(false);
        parseRatingsButton.setDisable(false);
        parseCountriesButton.setDisable(false);
        parseActorsButton.setDisable(false);
        parseMovieButton.setDisable(true);

        ZonedDateTime dt2 = ZonedDateTime.now();
        System.out.println(String.format("Parsed list in %s seconds", Duration.between(dt, dt2).getSeconds()));
    }

    @FXML
    public void actorParseButton() throws IOException{
        controller.view.setProgressBar(0);

        // Load in the data so we can pass it into the parser function later
        BufferedReader brMale = Files.newBufferedReader(Paths.get(Constants.dir + Constants.data[ACTORS_LIST]), StandardCharsets.ISO_8859_1);
        BufferedReader brFemale = Files.newBufferedReader(Paths.get(Constants.dir + Constants.data[ACTRESS_LIST]), StandardCharsets.ISO_8859_1);

        ZonedDateTime dt = ZonedDateTime.now();

        // Start the parseActors function twice, for each gender. On a different thread for potential javaFX updates (progressbar, etc)
        try {
            new Thread(() ->
            {
                try{
                    this.controller.parser.parseActors(brMale, "m");
                    this.controller.parser.parseActors(brFemale, "f");

                    parseActorsButton.setDisable(true);

                    progressbarActors.setProgress(100);
//                    controller.model.printActors();

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

    @FXML
    public void businessParseButton() throws IOException{
        ZonedDateTime dt = ZonedDateTime.now();
        this.controller.parser.parseBusiness();
        progressbarBusiness.setProgress(100);
        ZonedDateTime dt2 = ZonedDateTime.now();
        System.out.println(String.format("Parsed list in %s seconds", Duration.between(dt, dt2).getSeconds()));
        parseBusinessButton.setDisable(true);
    }

    @FXML
    public void runningtimeParseButton() throws IOException{
        ZonedDateTime dt = ZonedDateTime.now();
        this.controller.parser.parseRunningTimes();
        progressbarRunningTimes.setProgress(100);
        ZonedDateTime dt2 = ZonedDateTime.now();
        System.out.println(String.format("Parsed list in %s seconds", Duration.between(dt, dt2).getSeconds()));
        parseRunningTimesButton.setDisable(true);
    }

    @FXML
    public void ratingParseButton() throws IOException{
        ZonedDateTime dt = ZonedDateTime.now();
        this.controller.parser.parseMovieRatings();
        progressbarRating.setProgress(100);
        int count = 0;

//        for (String key : controller.model.returnMovieHash().keySet()){
//            if (count < 300){
//                Movie m = controller.model.returnMovieHash().get(key);
//                System.out.println("Title of movie: " + m.getTitle() + " Rating: " + m.getRating());
//                count++;
//            }
//            count++;
//        }

        ZonedDateTime dt2 = ZonedDateTime.now();
        System.out.println(String.format("Parsed list in %s seconds", Duration.between(dt, dt2).getSeconds()));
        parseRatingsButton.setDisable(true);
    }

    @FXML
    public void countryParseButton() throws IOException{
        ZonedDateTime dt = ZonedDateTime.now();
        this.controller.parser.parseCountries();
        progressbarCountries.setProgress(100);
        ZonedDateTime dt2 = ZonedDateTime.now();
        System.out.println(String.format("Parsed list in %s seconds", Duration.between(dt, dt2).getSeconds()));
        parseCountriesButton.setDisable(true);
    }

    @FXML
    public void writeCsvButton() throws IOException{
        controller.writeCsv(controller.model.returnMovieHash());
        controller.writeActorToCsv();
    }
}

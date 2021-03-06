package Main;

import Data.ObjectStorage;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import Constants.*;

public class Controller {

    ObjectStorage objectStorage;
    ViewController view;
    Parser parser;
    Stage stage;

    public Controller(ObjectStorage objectStorage, ViewController view, Stage stage){
        this.objectStorage = objectStorage;
        this.view = view;
        this.stage = stage;
        view.injectController(this);
    }

    // Add actor but also return it
    public Actor addActor(int id, String gender, String firstname, String lastname, int movieID) {
            Actor a = this.objectStorage.addActor(id, gender, firstname, lastname, movieID);
            return a;
    }

    public void addParser(Parser parser){
        this.parser = parser;
    }

    public void setup() {
        this.view.setup();
    }

    /**
     * Methods to write the parsed lists to csv data which can then be imported into a database.
     * Path: out/CSV
     */
    public void writeMovieToCsv(HashMap<String, Movie> movies) throws FileNotFoundException {
        PrintWriter movieWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Constants.csvDir + "Movies.csv"), StandardCharsets.UTF_8), false);
        PrintWriter genreWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Constants.csvDir + "Genres.csv"), StandardCharsets.UTF_8), false);
        PrintWriter countryWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Constants.csvDir + "Countries.csv"), StandardCharsets.UTF_8), false);

        StringBuilder sb = new StringBuilder();

        movies.forEach((String s, Movie m) -> {
            if (m.getTitle().length() > 0){
                sb.append(m.getId());
                sb.append(',');
                sb.append(m.getTitle());
                sb.append(',');

                if (m.getYear() != 0){
                    sb.append(Integer.toString(m.getYear()));
                }
                sb.append(',');

                if (m.getRunningTime() != 0){
                    sb.append(Integer.toString(m.getRunningTime()));
                }
                sb.append(',');

                if (m.getRating() != 0){
                    sb.append(Double.toString(m.getRating()));
                }
                sb.append(',');

                if (m.getBudget() != 0){
                    sb.append(Double.toString(m.getBudget()));
                }
                sb.append(',');

                if (m.getProfits() != 0){
                    sb.append(Double.toString(m.getProfits()));
                }

                for(String c  : m.getCountries()){
                    countryWriter.println(m.getId() + "," + c);
                }

                for(String g  : m.getGenres()) {
                    genreWriter.println(m.getId() + "," + g);
                }

                movieWriter.write(sb.toString());
                sb.setLength(0);
                movieWriter.println();
            }
        });

        countryWriter.close();
        genreWriter.close();
        movieWriter.close();
        System.out.println("Wrote Movies to csv at " + Constants.csvDir);
        System.out.println("Wrote Countries to csv at " + Constants.csvDir);
        System.out.println("Wrote Genres to csv at " + Constants.csvDir);
    }

    public void writeActorToCsv() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Constants.csvDir + "Actors.csv"), StandardCharsets.UTF_8), false);
        StringBuilder sb = new StringBuilder();

        objectStorage.returnActors().forEach((Actor a) -> {
                    sb.append(a.getId());
                    sb.append(',');
                    sb.append(a.getFirstName());
                    sb.append(',');
                    sb.append(a.getLastName());
                    sb.append(',');
                    sb.append(a.getGender());
                    pw.write(sb.toString());
                    sb.setLength(0);
                    pw.println();
                });

        pw.close();
        System.out.println("Wrote Actors to csv at " + Constants.csvDir);
    }

    public void writeActorMovieToCsv() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Constants.csvDir + "ActorsInMovies.csv"), StandardCharsets.UTF_8), false);
        StringBuilder sb = new StringBuilder();

        objectStorage.returnActors().forEach((Actor a) -> {
            for (int movieID : a.getMovies()){
                sb.append(a.getId());
                sb.append(',');
                sb.append(movieID);
                pw.write(sb.toString());
                sb.setLength(0);
                pw.println();
            }
        });

        pw.close();
        System.out.println("Wrote Actors in Movies to csv at " + Constants.csvDir);
    }
}

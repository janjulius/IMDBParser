package Main;

import Data.ObjectStorage;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

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
        StringBuilder sb = new StringBuilder();

        movies.forEach((String s, Movie m) -> {
            sb.append(m.getId());
            sb.append(',');
            sb.append(m.getTitle());
            sb.append(',');
            sb.append(Integer.toString(m.getYear()));
            sb.append(',');
            sb.append(Integer.toString(m.getRunningTime()));
            sb.append(',');
            sb.append(Double.toString(m.getRating()));
            sb.append(',');
            sb.append(Double.toString(m.getBudget()));
            sb.append(',');
            sb.append(Double.toString(m.getProfits()));
            pw.write(sb.toString());
            sb.setLength(0);
            pw.println();
        });

        pw.close();
        System.out.println("Wrote Movies to csv");
    }

    public void writeActorToCsv() throws FileNotFoundException {
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
        System.out.println("Wrote Actors to csv");
    }

    public void writeActorMovieToCsv() throws FileNotFoundException {
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
        System.out.println("Wrote Actors in Movies to csv");
    }

    public void writeToCsv(HashMap<Integer, String> hashmap, String file) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        hashmap.forEach((Integer x, String y) -> {
            sb.append(x);
            sb.append(',');
            sb.append(y);
            pw.write(sb.toString());
            sb.setLength(0);
            pw.println();
        });

        pw.close();
        System.out.println("Wrote " + file + " to csv");
    }
}

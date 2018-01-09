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
        PrintWriter pw = new PrintWriter(new File("out\\Movies.csv"));
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

                pw.write(sb.toString());
                sb.setLength(0);
                pw.println();
            }
        });

        pw.close();
        System.out.println("Wrote Movies to csv");
    }

    public void writeActorToCsv() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("out\\Actors.csv"));
        StringBuilder sb = new StringBuilder();

        objectStorage.returnActors().forEach((Actor a) -> {
            if (!a.getFirstName().contains("\"") && !a.getLastName().contains("\"")){
                sb.append(a.getId());
                sb.append(',');

                if (a.getFirstName().length() != 0 && !a.getFirstName().contains(",")){
                    sb.append(a.getFirstName());
                }
                sb.append(',');

                if (a.getLastName().length() != 0 && !a.getLastName().contains(",")){
                    sb.append(a.getLastName());
                }
                sb.append(',');

                sb.append(a.getGender());
                pw.write(sb.toString());
                sb.setLength(0);
                pw.println();
            }
        });

        pw.close();
        System.out.println("Wrote Actors to csv");
    }

    public void writeActorMovieToCsv() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("out\\ActorsInMovies.csv"));
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
        PrintWriter pw = new PrintWriter(new File("out\\" + file + ".csv"));
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

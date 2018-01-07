package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    Model model;
    ViewController view;
    Parser parser;

    public Controller(Model model, ViewController view){
        this.model = model;
        this.view = view;
        view.injectController(this);
    }

    public void addRunningTime(String title, int runningTime){
        model.setMovieRunningTime(title, runningTime);
    }

    // Add actor but also return it
    public Actor addActor(int id, String gender, String firstname, String lastname, int movieID) {
            Actor a = this.model.addActor(id, gender, firstname, lastname, movieID);
            return a;
    }

    public void addBusiness(String title,
            double budget,double profits,
            String sed){
        model.setBusinesses(title, budget, profits, sed);
    }

    public void addParser(Parser parser){
        this.parser = parser;
    }

    public void setup() {
        this.view.setup();
    }

    // Writes all the movie objects to csv
    public void writeCsv(HashMap<String, Movie> movies) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("testmovies.csv"));
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

//        pw.write(sb.toString());
        pw.close();
        System.out.println("Wrote movies to csv");
    }

    public void writeActorToCsv() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("testActors.csv"));
        StringBuilder sb = new StringBuilder();

        model.returnActors().forEach((Actor a) -> {
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
        System.out.println("Wrote actors to csv");
    }

    public void writeToCsv(HashMap<Integer, String> hashmap, String file) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("test" + file + ".csv"));
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

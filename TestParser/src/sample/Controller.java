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
    public Actor addActor(String gender, String firstname, String lastname, String movie) {
            Actor a = this.model.addActor(gender, firstname, lastname, movie);
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

    public void writeCsv(HashMap<String, Movie> movies) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("testmovies.csv"));
        StringBuilder sb = new StringBuilder();

        movies.forEach((String s, Movie m) -> {
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
            if (m.getBudget() > 10000){
                System.out.println(m.getBudget());
            }
            if (m.getProfits() > 10000){
                System.out.println(m.getProfits());
            }
            if (m.getRunningTime() > 30){
                System.out.println(m.getRunningTime());
            }
            pw.write(sb.toString());
            sb.setLength(0);
            pw.println();
        });

//        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }
}

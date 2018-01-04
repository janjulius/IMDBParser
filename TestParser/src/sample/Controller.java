package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Controller {

    Model model;
    ViewController view;
    Parser parser;

    public Controller(Model model, ViewController view){
        this.model = model;
        this.view = view;
        view.injectController(this);
    }

    public void addMovie(String title, int year){ this.model.addMovie(title, year);}

    public boolean addCountry(String title, String country, int count){
        return this.model.addCountry(title, country, count);
    }
    public void addRunningTime(String title, int runningTime){
        model.setMovieRunningTime(title, runningTime);
    }
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

    public void writeCsv(ArrayList<Movie> movies) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("test.csv"));
        StringBuilder sb = new StringBuilder();

        for(Movie m : movies)
        {
            sb = m.Append(sb);
        }
        
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }
}

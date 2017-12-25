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

    public void addRunningTime(String title, int runningTime){
        model.setMovieRunningTime(title, runningTime);
    }

    // Add actor but also return it
    public Actor addActor(String gender, String firstname, String lastname, String movie) {
            Actor a = this.model.addActor(gender, firstname, lastname, movie);
            return a;
    }

    public void addParser(Parser parser){
        this.parser = parser;
    }

    public void setup() {
        this.view.setup();
    }

//    public void writeCsv(ArrayList<Movie> movies) throws FileNotFoundException {
//        PrintWriter pw = new PrintWriter(new File("test.csv"));
//        StringBuilder sb = new StringBuilder();
//
//        for(Movie m : movies)
//        {
//            sb = m.Append(sb);
//        }
//
//        pw.write(sb.toString());
//        pw.close();
//        System.out.println("done!");
//    }
}

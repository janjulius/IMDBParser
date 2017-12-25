package sample;

public class Controller {

    Model model;
    ViewController view;
    Parser parser;

    public Controller(Model model, ViewController view){
        this.model = model;
        this.view = view;
        view.injectController(this);
    }

//    public void addMovie(String title, int year){ this.model.addMovie(title, year);}

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

    public void setup(){
        this.view.setup();
    }
}

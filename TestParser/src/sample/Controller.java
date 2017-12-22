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

    public void addParser(Parser parser){
        this.parser = parser;
    }
}

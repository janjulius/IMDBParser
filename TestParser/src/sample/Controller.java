package sample;

public class Controller {

    Model model;

    public Controller(Model model){
        this.model = model;
    }

    public void addMovie(String title, int year){
        this.model.addMovie(title, year);
    }
}

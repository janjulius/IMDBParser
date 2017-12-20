package sample;

public class Controller {

    Model model;

    public Controller(Model model){
        this.model = model;
    }

    public void addMovie(String title, int year){ this.model.addMovie(title, year);}

    public void addCountry(String title, String country, int year){this.model.addCountry(title, country, year);}
}

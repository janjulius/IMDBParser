package sample;

import java.util.ArrayList;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private ArrayList<Actor> actors = new ArrayList<Actor>();
    int count;

    public void addMovie(String title, int year){
        movies.add(new Movie(title, year));
    }
    public Actor addActor(String gender, String firstname, String lastname, String movie) {
        Actor a = new Actor(gender, firstname, lastname, movie);
        actors.add(a);
        return a;
    }

    public ArrayList<Movie> returnMovies(){
        return this.movies;
    }

    public void printMovies(){
        count = 0;
        for (Movie m : movies){
            if (count < 5000){
                count++;
                System.out.println(m.getTitle()+ " - " + m.getYear());
            }
        }
    }

    public void printActors(){
        count = 0;
        for (Actor a : actors){
//            if (count < 500) {
                for (String mName : a.getMovies()) {
                    System.out.println(a.getFirstName() + " " + a.getLastName() + " (" + a.getGender() + ")" + " - " + mName);
                }
//                count++;
//            }

        }
    }
}

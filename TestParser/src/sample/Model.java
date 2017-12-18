package sample;

import java.util.ArrayList;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public void addMovie(String title, int year){
        movies.add(new Movie(title, year));
    }

    public void printMovies(){
        for (Movie m : movies){
            System.out.println(m.getTitle()+ " - " + m.getYear());
        }
    }
}

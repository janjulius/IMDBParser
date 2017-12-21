package sample;

import java.util.ArrayList;
import java.util.List;

import static Constants.Constants.createSeperateTables;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private List<RunningTime> rTimes = new ArrayList<>();
    private List<Business> businesses = new ArrayList<>();

    public void addMovie(String title, int year){
        movies.add(new Movie(title, year));
    }
    public void setMovieRunningTime(String t, int runningTime){
        if(createSeperateTables) {
            rTimes.add(new RunningTime(t, runningTime)); //add new rnningtime as runningtim object to runningtime list
        } else {

            Movie m = movies.stream() //stream through movies list
                    .filter(c -> c.getTitle().equals(t)) //filter the list on wether the c object equals the title t
                    .findFirst() //find the first that equals the in the filter expression
                    .orElse(null); //return null if its not there.

            if(m != null)
                m.setRunningTime(runningTime);
        }
        System.out.println("new: " + t); //print new object
    }



    public void printMovies(){
        for (Movie m : movies){
            System.out.println(m.getTitle()+ " - " + m.getYear());
        }
    }
}

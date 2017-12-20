package sample;

import java.util.ArrayList;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public void addMovie(String title, int year){
        movies.add(new Movie(title, year));
    }
    public void setMovieRunningTime(int index, int runningTime){
        if(index > -1){
            System.out.println("set movie " + movies.get(index).getTitle() + " to " + runningTime);
            movies.get(index).setRunningtime(runningTime);
        }
    }

    public int findMovieIndex(String t, int y){
        System.out.println(movies.indexOf(new Movie(t, y)));
        System.out.println(t + " " + y);
        return movies.indexOf(new Movie(t, y));
    }

    public void printMovies(){
        for (Movie m : movies){
            System.out.println(m.getTitle()+ " - " + m.getYear());
        }
    }
}

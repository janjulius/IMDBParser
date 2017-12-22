package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Constants.Constants.createSeperateTables;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private ArrayList<Actor> actors = new ArrayList<Actor>();
    private List<RunningTime> rTimes = new ArrayList<>();
    private List<Business> businesses = new ArrayList<>();
    int count;

    public void addMovie(String title, int year){
        movies.add(new Movie(title, year, null));
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
    public Actor addActor(String gender, String firstname, String lastname, String movie) {
        Actor a = new Actor(gender, firstname, lastname, movie);
        actors.add(a);
        return a;
    }

    public ArrayList<Movie> returnMovies(){
        return this.movies;
    }

    public void printActors() {
        count = 0;
        for (Actor a : actors) {
//            if (count < 500) {
            for (String mName : a.getMovies()) {
                System.out.println(a.getFirstName() + " " + a.getLastName() + " (" + a.getGender() + ")" + " - " + mName);
            }
//                count++;
//            }
        }
    }

    public boolean addCountry(String title, String country, int count){
        for(int i = 0; i < movies.size(); i++){
            if(movies.get(i).getTitle().toLowerCase().equals(title.toLowerCase())){
                movies.get(i).addCountry(country);
                System.out.println(" Succes! " + movies.get(i).getTitle() + " - " + country);
                return true;
            }
        }
        System.out.println(" Failed");
        return false;

    }

    public void printMovies() throws IOException {

        PrintWriter writer = new PrintWriter("test.txt", "UTF-8");

        for (Movie m : movies){
            String line = m.getTitle() + " - " + m.getYear() + " - " + m.getCountry();
            writer.println(line);
            //System.out.println(line);
        }

        writer.close();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}

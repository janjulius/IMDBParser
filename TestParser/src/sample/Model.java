package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Constants.Constants.createSeperateTables;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private ArrayList<Actor> actors = new ArrayList<Actor>();
    private List<RunningTime> rTimes = new ArrayList<>();
    private List<Business> businesses = new ArrayList<>();

    private HashMap<Integer, String> countryHash = new HashMap<>();
    private HashMap<Integer, String> genreHash = new HashMap<>();
    private HashMap<String, Movie> movieHash = new HashMap<>();

    int count;

    //movie hash
    public HashMap<String, Movie> returnMovieHash() {
        return this.movieHash;
    }

    public void addToHashmap(String movieTitle, Movie m) {
        movieHash.put(movieTitle, m);
    }

    public Movie returnMovie(String movieTitle) {
        return movieHash.get(movieTitle);
    }

    //country hash
    public HashMap<Integer, String> getCountryHash() {
        return countryHash;
    }

    public void addToCountries(int id, String country) {
        countryHash.put(id, country);
    }

    //genre hash
    public HashMap<Integer, String> getGenreHash() {
        return genreHash;
    }

    public void addToGenres(int id, String genre) {
        genreHash.put(id, genre);
    }

    public ArrayList<Actor> returnActors() {
        return this.actors;
    }

    public void setMovieRunningTime(String t, int runningTime) {
        if (createSeperateTables) {
            rTimes.add(new RunningTime(t, runningTime)); //add new rnningtime as runningtim object to runningtime list
        } else {

            Movie m = movies.stream() //stream through movies list
                    .filter(c -> c.getTitle().equals(t)) //filter the list on wether the c object equals the title t
                    .findFirst() //find the first that equals the in the filter expression
                    .orElse(null); //return null if its not there.

            if (m != null)
                m.setRunningTime(runningTime);
        }
        //System.out.println("new: " + t); //print new object
    }

    public void setBusinesses(String title,
                              double budget, double profits,
                              String sed) {
        if (createSeperateTables) {
            businesses.add(new Business(title, budget, profits, sed));
        } else {

            Movie m = movies.stream() //stream through movies list
                    .filter(c -> c.getTitle().equals(title)) //filter the list on wether the c object equals the title t
                    .findFirst() //find the first that equals the in the filter expression
                    .orElse(null); //return null if its not there.

            if (m != null)
                m.setBusinessInfo(budget, profits, sed);
        }
        //System.out.println("new: " + title); //print new object
    }

    public Actor addActor(int id, String gender, String firstname, String lastname, int movieID) {
        Actor a = new Actor(id, gender, firstname, lastname, movieID);
        actors.add(a);
        return a;
    }
}

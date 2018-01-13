package Data;

import Main.Actor;
import Main.Movie;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectStorage {
    private ArrayList<Actor> actors = new ArrayList<>();
    private HashMap<String, Movie> movieHash = new HashMap<>();

    public HashMap<String, Movie> returnMovieHash() {
        return this.movieHash;
    }

    // Return a movie quickly by searching for similar hashes of the Movie Title
    public Movie returnMovie(String movieTitle) {
        return movieHash.get(movieTitle);
    }

    public ArrayList<Actor> returnActors() {
        return this.actors;
    }

    public void addToHashmap(String movieTitle, Movie m) {
        movieHash.put(movieTitle, m);
    }

    public Actor addActor(int id, String gender, String firstname, String lastname, int movieID) {
        Actor a = new Actor(id, gender, firstname, lastname, movieID);
        actors.add(a);
        return a;
    }
}

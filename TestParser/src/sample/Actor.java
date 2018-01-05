package sample;

import java.util.ArrayList;
import java.util.List;

public class Actor {

    private List<String> movies = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String gender;

    public Actor(String gender, String firstname, String lastname, String movie){
        this.gender = gender;
        this.firstName = firstname;
        this.lastName = lastname;
        movies.add(movie);
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setFirstName(String firstname){
        this.firstName = firstname;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public List<String> getMovies(){
        return this.movies;
    }

    public String getGender(){
        return this.gender;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void addMovie(String movieName){
        movies.add(movieName);
    }
}

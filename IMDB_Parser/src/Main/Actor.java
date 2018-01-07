package Main;

import java.util.ArrayList;
import java.util.List;

public class Actor {

    private int id;
    private List<Integer> movies = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String gender;

    public Actor(int id, String gender, String firstname, String lastname, int movieID){
        this.id = id;
        this.gender = gender;
        this.firstName = firstname;
        this.lastName = lastname;
        movies.add(movieID);
    }

    public int getId(){ return this.id; }

    public String getGender(){
        return this.gender;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public List<Integer> getMovies(){
        return this.movies;
    }

    public void setID(int id) { this.id = id; }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setFirstName(String firstname){
        this.firstName = firstname;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void addMovie(int movieID){
        movies.add(movieID);
    }
}

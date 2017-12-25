package sample;

import java.util.ArrayList;

public class Movie {

    private String title;
    private int year;
    private ArrayList<String> countries = new ArrayList<>();
    private int runningTime;
    private double rating;


    public Movie(String title, int year){
        this.title = title;
        this.year = year;
    }

    public String getTitle(){
        return title;
    }

    public ArrayList<String> getCountries() { return countries; }

    public void addCountry(String country) { countries.add(country); }

    public int getYear(){
        return this.year;
    }

    public double getRating(){
        return this.rating;
    }

    public void setRunningTime(int r){ runningTime = r;}

    public void setRating(double rating) { this.rating = rating;}

    // Hashmap
    @Override
    public int hashCode() {
        return title.hashCode();
    }

    // Hashmap
    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Movie)) return false;
        Movie otherMovie = (Movie)other;
        return this.hashCode() == otherMovie.hashCode();
    }
}

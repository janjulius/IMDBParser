package sample;

import java.util.ArrayList;

public class Movie {

    private String title;
    private int year;
    private String country;
    private int runningTime;
    private double rating;


    public Movie(String title, int year, String country){
        this.title = title;
        this.year = year;
        this.country = "unknown";
    }

    public String getTitle(){
        return title;
    }

    public String getCountry() { return country; }

    public void addCountry(String country) { this.country = country; }

    public int getYear(){
        return this.year;
    }

    public void setRunningTime(int r){ runningTime = r;}

    public void setRating(double rating) { this.rating = rating;}
}

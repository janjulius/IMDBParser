package sample;

import java.util.ArrayList;

public class Movie {

    private String title;
    private int year;
    private ArrayList<String> country;


    public Movie(String title, int year, String country){
        this.title = title;
        this.year = year;
        this.country = new ArrayList();
    }

    public String getTitle(){
        return this.title;
    }

    public int getYear(){
        return this.year;
    }

    public ArrayList<String> getCountry() { return country; }

    public void addCountry(String country) { this.country.add(country); }
}

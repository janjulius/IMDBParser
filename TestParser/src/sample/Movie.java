package sample;

import java.util.ArrayList;

public class Movie {

    private String title;
    private int year;


    public Movie(String title, int year){
        this.title = title;
        this.year = year;
    }

    public String getTitle(){
        return this.title;
    }

    public int getYear(){
        return this.year;
    }
}

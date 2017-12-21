package sample;

import java.util.ArrayList;

public class Movie {

    private String title;
    private int year;
    private int runningTime;


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

    public void setRunningTime(int r){ runningTime = r;}
}

package Main;

import java.util.ArrayList;

public class Movie {

    private int id;
    private String title;
    private int year;
    private int runningTime;
    private double rating;
    private double budget;
    private double profits;
    private String sed; //start end date
    private ArrayList<String> countries;
    private ArrayList<String> genres;


    public Movie(String title, int year, int id){
        this.title = title;
        this.year = year;
        this.id = id;
        countries = new ArrayList();
        genres = new ArrayList();

    }

    //country list
    public void addCountry(String country){ countries.add(country); }
    public ArrayList<String> getCountries() { return countries; }

    //genre list
    public void addGenre(String genre){ genres.add(genre); }
    public ArrayList<String> getGenres() { return genres; }

    public String getTitle(){
        return title;
    }

    public int getId() { return id; }

    public int getYear(){
        return this.year;
    }

    public double getRating(){
        return this.rating;
    }

    public int getRunningTime() { return this.runningTime; }

    public double getBudget() { return this.budget; }

    public double getProfits() { return this.profits; }

    public void setRunningTime(int r){ runningTime = r;}

    public void setRating(double rating) { this.rating = rating;}

    public void setBusinessInfo(double b, double p, String s) {
        budget = b;
        profits = p;
        sed = s;
    }

    // Hashmap
    @Override
    public int hashCode() {
        return title.hashCode();
    }

    // Hashmap
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Movie)) return false;
        Movie otherMovie = (Movie) other;
        return this.hashCode() == otherMovie.hashCode();
    }
/*
  public StringBuilder Append(StringBuilder sb)
    {
        sb.append(title);
        sb.append(',');
        sb.append(Integer.toString(year));
        sb.append(',');
        sb.append(country);
        sb.append(',');
        sb.append(Integer.toString(runningTime));
        sb.append(',');
        sb.append(Double.toString(rating));
        sb.append(',');
        return sb;

    } */
}

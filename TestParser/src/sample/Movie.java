package sample;

import java.util.ArrayList;

public class Movie {

    private String title;
    private int year;
    private String genre;
    private ArrayList<String> countries = new ArrayList<>();
    private int runningTime;
    private double rating;


    public Movie(String title, int year, String genre){
        this.title = title;
        this.year = year;
        this.genre = genre;
    }

    public String getTitle(){
        return title;
    }

    public ArrayList<String> getCountries() { return countries; }

    public void addCountry(String country) { countries.add(country); }

    public int getYear(){
        return this.year;
    }

    public String getGenre(){ return this.genre;}

    public double getRating(){
        return this.rating;
    }

    public int getRunningTime() { return this.runningTime; }

    public void setRunningTime(int r){ runningTime = r;}

    public void setRating(double rating) { this.rating = rating;}

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

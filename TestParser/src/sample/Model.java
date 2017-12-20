package sample;

import java.io.*;
import java.util.ArrayList;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public void addMovie(String title, int year){
        movies.add(new Movie(title, year, null));
    }

    public void addCountry(String title, String country, int year){

        for(Movie m:movies){
            if(m.getTitle().toLowerCase().equals(title.toLowerCase()) &&
                    m.getYear() == year){
                m.addCountry(country);
                System.out.println("Succes! " + m.getTitle() + " - " + title + " - " + country);
                break;
            }
        }

    }

    public void printMovies() throws IOException {

        PrintWriter writer = new PrintWriter("test.txt", "UTF-8");


        for (Movie m : movies){
            String line = "";
            line += m.getTitle() + " - " + m.getYear();
            for (String country : m.getCountry()){
                line += " - " + country;
            }
            writer.println(line);
            //System.out.println(line);
        }

        writer.close();
    }
}

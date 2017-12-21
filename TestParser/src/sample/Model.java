package sample;

import java.io.*;
import java.util.ArrayList;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public void addMovie(String title, int year){
        movies.add(new Movie(title, year, null));
    }

    public boolean addCountry(String title, String country, int count){
        for(int i = 0; i < movies.size(); i++){
            if(movies.get(i).getTitle().toLowerCase().equals(title.toLowerCase())){
                movies.get(i).addCountry(country);
                System.out.println(" Succes! " + movies.get(i).getTitle() + " - " + country);
                return true;
            }
        }
        System.out.println(" Failed");
        return false;

    }

    public void printMovies() throws IOException {

        PrintWriter writer = new PrintWriter("test.txt", "UTF-8");


        for (Movie m : movies){
            String line = m.getTitle() + " - " + m.getYear() + " - " + m.getCountry();
            writer.println(line);
            //System.out.println(line);
        }

        writer.close();
    }
}

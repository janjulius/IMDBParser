package sample;

import java.io.*;
import java.util.ArrayList;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private int count;

    public void addMovie(String title, int year){
        movies.add(new Movie(title, year, null));
    }

    public void addCountry(String title, String country){
        for(int i = count; i < movies.size(); i++){
            if(movies.get(i).getTitle().toLowerCase().equals(title.toLowerCase())){
                movies.get(i).addCountry(country);
                System.out.println("Succes! " + movies.get(i).getTitle() + " - " + country);
                count++;
                break;
            } else {
                System.out.println("failed");
            }
        }

    }

    public void printMovies() throws IOException {

        PrintWriter writer = new PrintWriter("test.txt", "UTF-8");


        for (Movie m : movies){
            String line = m.getTitle() + " - " + m.getYear() + " - " + m.getCountry();
            writer.println(line);
            System.out.println(line);
        }

        writer.close();
    }
}

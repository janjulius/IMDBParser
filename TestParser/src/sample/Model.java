package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Constants.Constants.createSeperateTables;

public class Model {
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private List<RunningTime> rTimes = new ArrayList<>();
    private List<Business> businesses = new ArrayList<>();

    public void addMovie(String title, int year){
        movies.add(new Movie(title, year, null));
    }
    public void setMovieRunningTime(String t, int runningTime){
        if(createSeperateTables) {
            rTimes.add(new RunningTime(t, runningTime)); //add new rnningtime as runningtim object to runningtime list
        } else {

            Movie m = movies.stream() //stream through movies list
                    .filter(c -> c.getTitle().equals(t)) //filter the list on wether the c object equals the title t
                    .findFirst() //find the first that equals the in the filter expression
                    .orElse(null); //return null if its not there.

            if(m != null)
                m.setRunningTime(runningTime);
        }
        System.out.println("new: " + t); //print new object
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

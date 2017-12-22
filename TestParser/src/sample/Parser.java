package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Parser {

    Controller controller;

    public Parser(Controller controller){
        this.controller = controller;
        controller.addParser(this);
    }

    public void parseMovie() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\JDV\\Desktop\\Jaar2P2imdbdata\\movies.list");
        BufferedReader reader = new BufferedReader(fr);

        int count = 0;
        int skipped = 0;

        try {
            String line = reader.readLine();
            while (line != null) {
                if(line.startsWith("\"")) {
//                    skipped++;
                    line = reader.readLine();
                }
                else{
//                    count++;
                    int yearSep = line.indexOf( '\t' );
                    if (yearSep > 0){
                        String title = line.substring( 0, yearSep ).trim();
                        String yearString = line.substring( yearSep ).trim();
                        title = title.substring(0, title.indexOf('('));
//                        if ( yearString.length() > 4 )
//                        {
//                            yearString = yearString.substring( 0, 4 );
//                        }
//                        final int year = Integer.parseInt( yearString );
                        if (yearString.equals("????")){
                            this.controller.addMovie(title, 0000);
                        }
                        else {
                            this.controller.addMovie(title, Integer.parseInt(yearString));
                        }
                    }

                    line = reader.readLine();
                }
            }
//            System.out.println("Count: " + count);
//            System.out.println("Skipped: " + skipped);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseActor() throws IOException{
        FileReader fr = new FileReader("C:\\Users\\JDV\\Desktop\\Jaar2P2imdbdata\\actors.list");
        BufferedReader reader = new BufferedReader(fr);
        String originalLine;
        int skipCounter = 0;
        String firstName;
        String lastName;
        String movieName;
        Actor a = new Actor("", "", "", "");
        int progresscount = 0;
        int totalCount = 22666969;

        try {
            String line = reader.readLine();
            while(line != null && !line.contains("SUBMITTING UPDATES")){
                if (skipCounter > 238){
                    if (line.contains(",") && !Character.isWhitespace(line.charAt(0))){
                        originalLine = line;
                        line = line.substring(0, line.indexOf('\t')).trim();

                        if (line.contains(",")){
                            lastName = line.substring(0, line.indexOf(','));
                            firstName = line.substring((line.indexOf(',') + 1)).trim();

                            int derp = originalLine.indexOf(firstName) + firstName.length();
                            String movieLine = originalLine.substring(derp);
                            movieName = movieLine.substring(0, (movieLine.indexOf(')') +1)).trim();

                            a = this.controller.addActor("m", firstName, lastName, movieName);
                        }

                        else {
                            firstName = line.substring(0);
                            this.controller.addActor("m", firstName, "", "");

                        }
                        progresscount++;
                        this.controller.view.setProgressBar(progresscount/totalCount);
                        line = reader.readLine();
                    }
                    else if(line.isEmpty() || line.contains("\"")){
                        progresscount++;
                        this.controller.view.setProgressBar(progresscount/totalCount);
                        line = reader.readLine();
                    }
                    else {
                        line = line.substring(0, line.indexOf(')') +1);
                        movieName = line.trim();
                        a.addMovie(movieName);
                        progresscount++;
                        this.controller.view.setProgressBar(progresscount/totalCount);
                        line = reader.readLine();
                    }
                }
                else {
                    skipCounter++;
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println(progresscount);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseActress() throws IOException{
        FileReader fr = new FileReader("C:\\Users\\JDV\\Desktop\\Jaar2P2imdbdata\\actresses.list");
        BufferedReader reader = new BufferedReader(fr);
        String originalLine;
        int skipCounter = 0;
        String firstName;
        String lastName;
        String movieName;
        Actor a = new Actor("", "", "", "");

        try {
            String line = reader.readLine();
            while(line != null && !line.contains("SUBMITTING UPDATES")){
                if (skipCounter > 238){
                    if (line.contains(",") && !Character.isWhitespace(line.charAt(0))){
                        originalLine = line;
                        line = line.substring(0, line.indexOf('\t')).trim();

                        if (line.contains(",")){
                            lastName = line.substring(0, line.indexOf(','));
                            firstName = line.substring((line.indexOf(',') + 1)).trim();

                            int derp = originalLine.indexOf(firstName) + firstName.length();
                            String movieLine = originalLine.substring(derp);
                            movieName = movieLine.substring(0, (movieLine.indexOf(')') +1)).trim();

                            a = this.controller.addActor("f", firstName, lastName, movieName);
                        }

                        else {
                            firstName = line.substring(0);
                            this.controller.addActor("f", firstName, "", "");

                        }
                        line = reader.readLine();
                    }
                    else if(line.isEmpty() || line.contains("\"")){
                        line = reader.readLine();
                    }
                    else {
                        line = line.substring(0, line.indexOf(')') +1);
                        movieName = line.trim();
                        a.addMovie(movieName);
                        line = reader.readLine();
                    }
                }
                else {
                    skipCounter++;
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

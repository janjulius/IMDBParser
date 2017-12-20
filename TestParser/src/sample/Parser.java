package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Constants.*;

public class Parser {

    Controller controller;

    public final int
            ACTORS_LIST = 0,
            ACTRESS_LIST = 1,
            BUSINESS_LIST = 2,
            COUNTRIES_LIST = 3,
            GENRES_LIST = 4,
            MOVIES_LIST = 5,
            RATINGS_LIST = 6,
            RUNNINGTIMES_LIST = 7;


    public Parser(Controller controller){
        this.controller = controller;
    }

    public void parseMovie() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[MOVIES_LIST]);
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
                        title = title.substring(0, title.indexOf(yearSep));
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

    public void parseBusiness() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[BUSINESS_LIST]);
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

    public void parseRunningTimes() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[RUNNINGTIMES_LIST]);
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
                    int rtSep = line.indexOf( '\t' );
                    if (rtSep > 0){
                        String title = line.substring( 0, rtSep ).trim();
                        String yearString = line.substring( line.indexOf('(') + 1, line.indexOf('(') + 5 ).trim();
                        if (!yearString.matches("[0-9]+")){
                            String remainder = line.substring(line.indexOf(')') + 1, line.length());
                            if(remainder.contains("(") && remainder.contains(")")){
                                System.out.println("remainder" + remainder);
                                yearString = remainder.substring(remainder.indexOf('(') + 1, remainder.indexOf(')'));
                            }
                        }
                        String timeRunning = line.substring(rtSep, line.length()).trim();

                        title = title.substring(0, title.indexOf('('));
                        timeRunning = timeRunning.replaceAll("[^\\d]", "").trim();

                        int bla = Integer.parseInt(timeRunning);
                        System.out.println("title " + title);
                        System.out.println("timerunning " + bla);
                        System.out.println("year " + yearString);
//                        if ( yearString.length() > 4 )
//                        {
//                            yearString = yearString.substring( 0, 4 );
//                        }
//                        final int year = Integer.parseInt( yearString );
                        if(timeRunning != null){
                            if(yearString.equals("????")) {
                                controller.addRunningTime(title, 0000, bla);
                            } else {
                                controller.addRunningTime(title, Integer.parseInt(yearString),bla );
                            }
                        }
                        else {
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
}

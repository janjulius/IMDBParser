package sample;

import java.io.BufferedReader;
import java.util.Date;
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
                        title = title.substring(0, yearSep);
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

    public void parseCountries() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\lars-\\Documents\\School\\Periode 2\\Big Movie\\data\\countries.list");
        BufferedReader reader = new BufferedReader(fr);

        int count = 0;

        int skipped = 0;

        try {
            String line = reader.readLine();
            while (line != null) {
                if(count >= 5000) {
                    break;
                }
                if(line.startsWith("\"")) {
                    line = reader.readLine();
                }
                else{
                    int country = line.indexOf( '\t' );

                    if (country > 0){
                        String title = line.substring( 0, country ).trim();
                        String countryString = line.substring( country ).trim();
                        title = title.substring(0, country);

                        System.out.print(count);
                        if(this.controller.addCountry(title, countryString, count)){
                            count++;
                        }
                    }

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

    public void parseBusiness() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[BUSINESS_LIST]);
        BufferedReader reader = new BufferedReader(fr);

        try {
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("\"")) { //ignore series
                    line = reader.readLine();
                } else {
                    if (line.length() > 0) {
                        String title;
                        String profits;
                        int budget;
                        String sed; //start end date
                        if (line.startsWith("MV:")) {
                            if (line.substring(line.indexOf("MV:") + 4, line.length()).startsWith("\"")) {
                                line = reader.readLine();
                            } else {
                                title = line.replace("MV:", "").trim();
                                System.out.println(title);
                            }
                        }
                    }
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

    public boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses the runningtimes.list
     * @throws IOException
     */
    public void parseRunningTimes() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[RUNNINGTIMES_LIST]);
        BufferedReader reader = new BufferedReader(fr);

        try {
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("\"")) { //ignore series
                    line = reader.readLine();
                } else {
                    int sepIndex = line.indexOf('\t');
                    if (sepIndex > 0) {
                        String title = line.substring(0, sepIndex).trim();
                        String timeRunning = line.substring(sepIndex, line.length()).trim();

                        title = title.substring(0, sepIndex).trim();

                        if(timeRunning.contains("(") || timeRunning.contains(")")){
                            timeRunning = timeRunning.replaceAll("\\(.*\\)", ""); //replace anything within () with nothing
                        }
                        timeRunning = timeRunning.replaceAll("[^\\d]", "").trim(); //remove any non number character and trim

                        int parsedrtInt = Integer.parseInt(timeRunning);

                        if (timeRunning != null) {
                            controller.addRunningTime(title, parsedrtInt);
                        }

                    }
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

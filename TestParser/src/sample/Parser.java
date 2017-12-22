package sample;

import java.io.BufferedReader;
import java.util.Date;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Constants.*;

public class Parser{

    Controller controller;
    int actorProgress = 0;
    int actorTotalCount = 22666969;

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
        controller.addParser(this);
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

    public void updateActorBar(){
        while (actorProgress >= 0){
            this.controller.view.setProgressBar(actorProgress/actorTotalCount);
        }
    }

    public void parseActor() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[ACTORS_LIST]);
        BufferedReader reader = new BufferedReader(fr);
        String originalLine;
        int skipCounter = 0;
        String firstName;
        String lastName;
        String movieName;
        Actor a = new Actor("", "", "", "");
        actorProgress = 0;

        try {
            String line = reader.readLine();
            while (line != null && !line.contains("SUBMITTING UPDATES")) {
                if (skipCounter > 238) {
                    if (line.contains(",") && !Character.isWhitespace(line.charAt(0))) {
                        originalLine = line;
                        line = line.substring(0, line.indexOf('\t')).trim();

                        if (line.contains(",")) {
                            lastName = line.substring(0, line.indexOf(','));
                            firstName = line.substring((line.indexOf(',') + 1)).trim();

                            int derp = originalLine.indexOf(firstName) + firstName.length();
                            String movieLine = originalLine.substring(derp);
                            movieName = movieLine.substring(0, (movieLine.indexOf(')') + 1)).trim();

                            a = this.controller.addActor("m", firstName, lastName, movieName);
                        } else {
                            firstName = line.substring(0);
                            this.controller.addActor("m", firstName, "", "");

                        }
                        actorProgress++;
//                        this.controller.view.setProgressBar(progresscount / totalCount);
                        line = reader.readLine();
                    } else if (line.isEmpty() || line.contains("\"")) {
                        actorProgress++;
//                        this.controller.view.setProgressBar(progresscount / totalCount);
                        line = reader.readLine();
                    } else {
                        line = line.substring(0, line.indexOf(')') + 1);
                        movieName = line.trim();
                        a.addMovie(movieName);

//                        this.controller.view.setProgressBar(progresscount / totalCount);
                        line = reader.readLine();
                    }
                } else {
                    skipCounter++;
                    line = reader.readLine();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                actorProgress = -1;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseActress() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[ACTRESS_LIST]);
        BufferedReader reader = new BufferedReader(fr);
        String originalLine;
        int skipCounter = 0;
        String firstName;
        String lastName;
        String movieName;
        Actor a = new Actor("", "", "", "");

        try {
            String line = reader.readLine();
            while (line != null && !line.contains("SUBMITTING UPDATES")) {
                if (skipCounter > 238) {
                    if (line.contains(",") && !Character.isWhitespace(line.charAt(0))) {
                        originalLine = line;
                        line = line.substring(0, line.indexOf('\t')).trim();

                        if (line.contains(",")) {
                            lastName = line.substring(0, line.indexOf(','));
                            firstName = line.substring((line.indexOf(',') + 1)).trim();

                            int derp = originalLine.indexOf(firstName) + firstName.length();
                            String movieLine = originalLine.substring(derp);
                            movieName = movieLine.substring(0, (movieLine.indexOf(')') + 1)).trim();

                            a = this.controller.addActor("f", firstName, lastName, movieName);
                        } else {
                            firstName = line.substring(0);
                            this.controller.addActor("f", firstName, "", "");

                        }
                        line = reader.readLine();
                    } else if (line.isEmpty() || line.contains("\"")) {
                        line = reader.readLine();
                    } else {
                        line = line.substring(0, line.indexOf(')') + 1);
                        movieName = line.trim();
                        a.addMovie(movieName);
                        line = reader.readLine();
                    }
                } else {
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

    public void parseCountries() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[COUNTRIES_LIST]);
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

    public void parseMovieRatings(ArrayList<Movie> movies) throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[RATINGS_LIST]);
        BufferedReader reader = new BufferedReader(fr);
        int count = 0;
        int skipped = 0;
        boolean dataReached = false;

        try {
            String line = reader.readLine();

            while(line != null) {
                while (!dataReached) {
                    if (line.startsWith("MOVIE RATINGS REPORT")) {
                        dataReached = true;
                    } else {
                        line = reader.readLine();
                    }
                }
                if (line.startsWith("---"))
                {
                    break;
                }
                if (!line.startsWith("     ")) {
//                    skipped++;
                    line = reader.readLine();
                }
                else {
//                    count++;
                    String[] splitter = line.split(" ");
                    int[] possibleIndex = new int[3];
                    int index = 0;
                    for (int i = 7; i < splitter.length; i++) {
                        if (index < 3) {
                            if (!splitter[i].equals("")) {
                                possibleIndex[index] = i;
                                index++;
                            }
                        }
                    }

                    if (splitter[possibleIndex[2]].startsWith("\"")) {
                        line = reader.readLine();
                        continue;
                    }

                    int votes = Integer.parseInt(splitter[possibleIndex[0]]);
                    double rating = Double.parseDouble(splitter[possibleIndex[1]]);

                    String title = "";
                    for (int i = possibleIndex[2]; i < splitter.length; i++) {
                        if (splitter[i].startsWith("(")) {
                            break;
                        }
                        if (i != splitter.length - 1) {
                            title += splitter[i] + " ";
                        }
                    }
                    if (!title.equals("")) {
                        title = title.substring(0, title.length() - 1);
                    }
                    int yearIndex = splitter.length - 1;

                    if (splitter[yearIndex].length() < 5) {
                        yearIndex -= 1;
                    }

                    String yearString = splitter[yearIndex].substring(1, 5);

                    if(yearString.equals("????"))
                    {
                        yearString = "0000";
                    }
                    title += " " + splitter[yearIndex];
                    int year = Integer.parseInt(yearString);
                    for(int i = 0; i < movies.size(); i++)
                    {
                        if(movies.get(i).getTitle().equals(title)) {
                            movies.get(i).setRating(rating);
                            break;
                        }
                    }
                    line = reader.readLine();
                }
//            System.out.println("Count: " + count);
//            System.out.println("Skipped: " + skipped);
            }
        }
        catch (IOException e) {
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
}

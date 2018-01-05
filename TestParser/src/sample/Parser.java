package sample;

import java.io.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Constants.*;

public class Parser {

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

    public Parser(Controller controller) {
        this.controller = controller;
        controller.addParser(this);
    }

    /**
     * Parses Movies
     *
     * @throws IOException
     * @author Joyce Rosenau/Jos de Vries
     */
    public void parseMovie() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[GENRES_LIST]);
        BufferedReader reader = new BufferedReader(fr);

        //count will be our ID
        int count = 0;
        int skipped = 0;
        int year = 0000;

        try {
            String line = reader.readLine();
            while (line != null) {
                if (skipped < 384) {
                    line = reader.readLine();
                    skipped++;
                    if (line.startsWith("\"")) {
                        line = reader.readLine();
                    }
                } else {
//
                    int yearSep = line.indexOf('(');
                    int genre = line.indexOf('\t');

                    String title = line.substring(0, yearSep).trim();
                    String title2 = line.substring(0, (line.indexOf(')') + 1));
                    String yearString = line.substring((yearSep + 1), (yearSep + 5)).trim();
                    String genreString = line.substring(genre).trim();
//                        if ( yearString.length() > 4 )
//                        {
//                            yearString = yearString.substring( 0, 4 );
//                        }
//                        final int year = Integer.parseInt( yearString );
                    try {
                        // This throws an exception because the string is invalid.
                        year = Integer.parseInt(yearString);
                    } catch (NumberFormatException n) {
                    }


                    Movie mov = controller.model.returnMovie(title2);

                    if (mov != null) {
                        mov.addGenre(genreString);
                    } else {
                        if (yearString.equals("????") || yearString.equals("0")) {
                            Movie m = new Movie(title, 0000, count);
                            m.addGenre(genreString);
                            controller.model.addToHashmap(title2, m);
                        } else {
                            Movie m = new Movie(title, year, count);
                            m.addGenre(genreString);
                            controller.model.addToHashmap(title2, m);
                        }

                        count++;
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

    /**
     * Parses Actors
     *
     * @throws IOException
     * @author Jos de Vries
     */
    public void parseActors(FileReader fr, String gender) throws IOException {
        BufferedReader reader = new BufferedReader(fr);
        String originalLine;
        int skipCounter = 0;
        String firstName;
        String lastName;
        String movieName;
        Actor a = new Actor("", "", "", "");
//        actorProgress = 0;

        // Try/catch since this is good practise with reading files.
        try {
            String line = reader.readLine();

            // Filter for empty lines and stop when it reaches the end of useful data.
            while (line != null && !line.contains("SUBMITTING UPDATES")) {
                // Skip intro text
                if (skipCounter > 238) {
                    // Filter for lines that have commas in them and which do not start with a whitespace at the first index.
                    if (line.contains(",") && !Character.isWhitespace(line.charAt(0))) {
                        originalLine = line;
                        line = line.substring(0, line.indexOf('\t')).trim();

                        // More filtering for commas - Else is for if people do not have last names listed.
                        if (line.contains(",")) {
                            lastName = line.substring(0, line.indexOf(','));
                            firstName = line.substring((line.indexOf(',') + 1)).trim();

                            int indexFilter = originalLine.indexOf(firstName) + firstName.length();
                            String movieLine = originalLine.substring(indexFilter);
                            movieName = movieLine.substring(0, (movieLine.indexOf(')') + 1)).trim();

                            // Add actor when all data is collected and return actor so we can add movies to it later
                            a = this.controller.addActor(gender, firstName, lastName, movieName);
                        } else {
                            firstName = line.substring(0);
                            this.controller.addActor(gender, firstName, "", "");
                        }
//                        actorProgress++;
//                        this.controller.view.setProgressBar(progresscount / totalCount);
                        line = reader.readLine();
                    } else if (line.isEmpty() || line.contains("\"")) {
//                        actorProgress++;
//                        this.controller.view.setProgressBar(progresscount / totalCount);
                        line = reader.readLine();
                    } else {
                        line = line.substring(0, line.indexOf(')') + 1);
                        movieName = line.trim();

                        // Since an actor was defined earlier, we can add additional movies to it now.
                        a.addMovie(movieName);

//                        this.controller.view.setProgressBar(progresscount / totalCount);
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
//                actorProgress = -1;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses Countries
     *
     * @throws IOException
     * @author Lars
     */
    public void parseCountries() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[COUNTRIES_LIST]);
        BufferedReader reader = new BufferedReader(fr);
        PrintWriter pw = new PrintWriter(new File("test.csv"));

        int count = 0;

        try {
            //start reading lines from data file
            String line = reader.readLine();
            while (line != null) {
                //skip series
                if (line.startsWith("\"")) {
                    line = reader.readLine();
                } else {
                    //get country
                    int country = line.indexOf('\t');

                    if (country > 0) {
                        //get title with country
                        String title = line.substring(0, country).trim();
                        String countryString = line.substring(country).trim();
                        title = title.substring(0, country);

                        //get movie with this title
                        Movie mov = controller.model.returnMovie(title);

                        if (mov != null) {
                            //add country to csv
                            pw.write(mov.getId()+ "," + countryString + ";");
                            System.out.print(mov.getId()+ "," + countryString);
                        }

                        //continue
                        line = reader.readLine();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parses business
     *
     * @throws IOException
     * @author Jan Julius
     */
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
                        String title = null;
                        double budget = 0;
                        double profits = 0;
                        String sed = null; //start end date

                        boolean foundEnd = false;

                        while (!foundEnd) {

                            line = reader.readLine();
                            if (line == null) {
                                foundEnd = true;
                                break;
                            }
                            if (line.startsWith("MV:")) {

                                if (line.substring(line.indexOf("MV:") + 4, line.length()).startsWith("\"")) {
                                    line = reader.readLine();
                                } else {
                                    title = line.replace("MV:", "").trim();
                                    //System.out.println(title);
                                }
                            }

                            if (line.startsWith("BT:")) {
                                if (line.substring(line.indexOf("BT:") + 4, line.length()).startsWith("\"")) {
                                    line = reader.readLine();
                                } else {
                                    budget = Double.parseDouble(line.replaceAll("[^\\d]", "").trim());
                                    //System.out.println(budget);
                                }
                            }

                            if (line.startsWith("GR:")) {

                                if (line.substring(line.indexOf("GR:") + 4, line.length()).startsWith("\"")) {
                                    line = reader.readLine();
                                } else {

                                    profits = Double.parseDouble(line.replaceAll("\\(.*\\)", "").replaceAll("[^\\d]", "").trim());

                                    //System.out.println(profits);
                                }
                            }

                            if (line.startsWith("SD:")) {
                                if (line.substring(line.indexOf("GR:") + 4, line.length()).startsWith("\"")) {
                                    line = reader.readLine();
                                } else {
                                    sed = line.substring(3, line.length());
                                    //System.out.println(sed);
                                }
                            } else if (line.startsWith("---")) {
                                controller.addBusiness(title, budget, profits, sed);
                                title = null;
                                budget = 0;
                                profits = 0;
                                sed = null;
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
     *
     * @throws IOException
     * @author Jan Julius
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

                        if (timeRunning.contains("(") || timeRunning.contains(")"))
                            timeRunning = timeRunning.replaceAll("\\(.*\\)", ""); //replace anything within () with nothing

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

    public void parseMovieRatings() throws IOException {
        FileReader fr = new FileReader(Constants.dir + Constants.data[RATINGS_LIST]);

        BufferedReader reader = new BufferedReader(fr);
        boolean dataReached = false;

        try {
            String line = reader.readLine();

            while (line != null) {
                while (!dataReached) {
                    if (line.startsWith("MOVIE RATINGS REPORT")) { //Hier in de ratings file begint de data die we nodig hebben.
                        dataReached = true;
                    } else {
                        line = reader.readLine();
                    }
                }
                if (line.startsWith("---")) {
                    break; //Het einde van de data
                }
                if (!line.startsWith("     ")) {
                    line = reader.readLine(); //controle of de line wel echt movie data bevat, alle lines die hiermee beginnen doen dat.
                } else {
                    String[] splitter = line.split(" "); //elke line word gesplit op spaties.
                    int[] possibleIndex = new int[3];
                    int index = 0;
                    for (int i = 7; i < splitter.length; i++) {
                        if (index < 3) {
                            if (!splitter[i].equals("")) {
                                possibleIndex[index] = i; //De juiste indices voor de data worden gezocht.
                                index++;
                            }
                        }
                    }

                    if (splitter[possibleIndex[2]].startsWith("\"")) {
                        line = reader.readLine(); //Als de titel met een '' begint, is dit een serie. Wij nemen series niet op in een tabel dus die skippen we.
                        continue;
                    }

                    double rating = Double.parseDouble(splitter[possibleIndex[1]]); //De tweede entry in de splitter bevat de rating.

                    String title = "";
                    for (int i = possibleIndex[2]; i < splitter.length; i++) {
                        if (splitter[i].startsWith("(")) {
                            break;
                        }
                        title += splitter[i] + " "; //Totdat er een "(" word gevonden word elk woord van de title aan de title-variable toegevoegd.
                    }
                    if (!title.equals("")) {
                        title = title.substring(0, title.length() - 1); //De laatste spatie wordt verwijderd.
                    }
                    int yearIndex = splitter.length - 1;

                    while (splitter[yearIndex].length() < 6) {

                        yearIndex -= 1; //splitter[yearIndex] moet grootte 6 zijn, aangezien het uit een viercijferig getal en twee haakjes bestaat, is deze grootte niet 6 word er gezocht naar de juiste index.
                    }

                    title += " " + splitter[yearIndex].substring(0, 6); //Het jaartal wordt aan de title toegevoegd

                    // Search the movie hashmap for the corresponding movie, do a nullcheck and add rating
                    Movie mov = controller.model.returnMovie(title);

                    if (mov != null) {
                        mov.setRating(rating);
                        System.out.println("Added rating for: " + title);
                    } else {
                        System.out.println("Skipped!");
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

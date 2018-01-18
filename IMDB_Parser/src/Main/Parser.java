package Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

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

    public Parser(Controller controller) {
        this.controller = controller;
        controller.addParser(this);
    }

    /**
     * Parses Movies & Genres
     *
     * @throws IOException
     * @author Joyce Rosenau/Jos de Vries/Lars
     */
    public void parseMovie() throws IOException {

        BufferedReader reader = Files.newBufferedReader(Paths.get(Constants.dir + Constants.data[GENRES_LIST]), StandardCharsets.ISO_8859_1);

        //count will be our ID
        int count = 0;
        int skipped = 0;
        int year = 0000;

        // Try/catch since this is good practise with reading files.
        try {
            String line = reader.readLine();
            while (line != null) {
                // Skip intro text
                if (skipped < 384) {
                    line = reader.readLine();
                    skipped++;
                } else {
                    if (line.startsWith("\"")) {
                        line = reader.readLine();
                        continue;
                    } else {
                        int yearSep = line.indexOf('(');
                        int genre = line.indexOf('\t');

                        if (line.startsWith("((")) {
                            line = reader.readLine();
                            continue;
                        }

                        if (line.startsWith(("("))) {
                            if (line.contains(",")) {
                                line = reader.readLine();
                                continue;
                            }
                            String yearline = line.substring(1, line.length() - 1);
                            yearSep = yearline.indexOf('(');
                        }

                        // Substring to get the values we want
                        String title = line.substring(0, yearSep).trim();
                        String title2 = line.substring(0, (line.indexOf(')') + 1));
                        String yearString = line.substring((yearSep + 1), (yearSep + 5)).trim();
                        String genreString = line.substring(genre).trim();

                        if (title2.contains(",")) {
                            line = reader.readLine();
                            continue;
                        } else if (title2.length() == 0) {
                            line = reader.readLine();
                            continue;
                        }

                        // Succesfully found movie
                        else {
                            try {
                                // This throws an exception because the string is invalid.
                                year = Integer.parseInt(yearString);
                            } catch (NumberFormatException n) {
                            }

                            Movie mov = controller.objectStorage.returnMovie(title2);

                            // Nullcheck for invalid movies
                            if (mov != null) {
                                // Add to genre list in movie object if it already exists
                                mov.addGenre(genreString);
                            } else {
                                if (yearString.equals("????") || yearString.equals("0")) {
                                    year = 0;
                                }

                                Movie m = new Movie(title, year, count);
                                m.addGenre(genreString);
                                controller.objectStorage.addToHashmap(title2, m);

                                count++;
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
     * Parses Actors
     *
     * @throws IOException
     * @author Jos de Vries
     */
    public void parseActors(BufferedReader reader, String gender, int id) throws IOException {

        String originalLine;
        int skipCounter = 0;
        int idCounter = id;
        String firstName;
        String lastName;
        String movieName;
        Actor a = new Actor(0,"", "", "", 0);

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

                            if (firstName.contains("\"") && lastName.contains("\"")) {
                                line = reader.readLine();
                                continue;
                            }

                            if (firstName.length() != 0 && firstName.contains(",")) {
                                line = reader.readLine();
                                continue;
                            }

                            if (lastName.length() != 0 && lastName.contains(",")) {
                                line = reader.readLine();
                                continue;
                            }

                            int indexFilter = originalLine.indexOf(firstName) + firstName.length();
                            String movieLine = originalLine.substring(indexFilter);
                            movieName = movieLine.substring(0, (movieLine.indexOf(')') + 1)).trim();

                            // Search up the movie so we can pass it's id in the actor constructor
                            Movie mov = controller.objectStorage.returnMovie(movieName);

                            if (mov != null){
                                // Add actor when all data is collected and return actor so we can add movies to it later
                                a = this.controller.addActor(idCounter, gender, firstName, lastName, mov.getId());
                                idCounter++;
                            }

                        } else {
                            firstName = line.substring(0);
                            a = this.controller.addActor(idCounter, gender, firstName, "", 0);
                            idCounter++;
                        }
                        // Tried getting the progressbar to work - Maybe later.
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
                        Movie mov = controller.objectStorage.returnMovie(movieName);

                        if (mov != null){
                            // Since an actor was defined earlier, we can add additional movies to it now.
                            a.addMovie(mov.getId());
                        }

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

        BufferedReader reader = Files.newBufferedReader(Paths.get(Constants.dir + Constants.data[COUNTRIES_LIST]), StandardCharsets.ISO_8859_1);

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
                        Movie mov = controller.objectStorage.returnMovie(title);

                        if (mov != null) {
                            //add country to csv
                            mov.addCountry(countryString);
                            //System.out.println(mov.getId()+ "," + countryString);
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
     * Parses business
     *
     * @throws IOException
     * @author Jan Julius
     */
    public void parseBusiness() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(Constants.dir + Constants.data[BUSINESS_LIST]), StandardCharsets.ISO_8859_1);

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
                                    if(line.contains("USD")) {
                                        //System.out.println("found line starting with usd here it is " + line);
                                        double a = Double.parseDouble(line.replaceAll("[^\\d]", "").trim());
                                        if(budget < a)
                                            budget = a;
                                    }
                                    //System.out.println(budget);
                                }
                            }

                            if (line.startsWith("GR:")) {

                                if (line.substring(line.indexOf("GR:") + 4, line.length()).startsWith("\"")) {
                                    line = reader.readLine();
                                } else {
                                    if(line.contains("USD")) {
                                        double a = Double.parseDouble(line.replaceAll("\\(.*\\)", "").replaceAll("[^\\d]", "").trim());
                                        if(profits < a)
                                            profits = a;
                                    }
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
                                Movie m = controller.objectStorage.returnMovie(title);
                                if(m != null) {
                                    m.setBusinessInfo(budget, profits, sed);
                                }
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
     * Parses the runningtimes
     *
     * @throws IOException
     * @author Jan Julius
     */
    public void parseRunningTimes() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(Constants.dir + Constants.data[RUNNINGTIMES_LIST]), StandardCharsets.ISO_8859_1);

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

                        Movie m = controller.objectStorage.returnMovie(title);

                        if (timeRunning != null && m != null) {
                            m.setRunningTime(parsedrtInt);
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
     * Parses the movie ratings
     *
     * @throws IOException
     * @author Hielke
     */
    public void parseMovieRatings() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(Constants.dir + Constants.data[RATINGS_LIST]), StandardCharsets.ISO_8859_1);
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
                    Movie mov = controller.objectStorage.returnMovie(title);

                    if (mov != null) {
                        mov.setRating(rating);
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

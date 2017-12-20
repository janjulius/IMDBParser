package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Parser {

    Controller controller;

    public Parser(Controller controller){
        this.controller = controller;
    }

    public void parseMovie() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\lars-\\Documents\\School\\Periode 2\\Big Movie\\data\\movies.list");
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

    public void parseCountries() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\lars-\\Documents\\School\\Periode 2\\Big Movie\\data\\countries.list");
        BufferedReader reader = new BufferedReader(fr);

        int count = 0;
        int skipped = 0;

        try {
            String line = reader.readLine();
            while (line != null) {
                if(line.startsWith("\"")) {
                    line = reader.readLine();
                }
                else{
                    count++;
                    int country = line.indexOf( '\t' );
                    int yearSep = line.indexOf( '(' )+1;

                    if (country > 0){

                        String yearString = line.substring(yearSep, yearSep+4);
                        System.out.print("first " + yearString);

                        System.out.println(yearString);

                        //check if year isnt part of title
                        if(!tryParseInt(yearString)){
                            yearSep = line.indexOf("(", yearSep);
                        }

                        yearString = line.substring(yearSep, yearSep+4);

                        System.out.print(yearString);
                        String title = line.substring( 0, country ).trim();
                        String countryString = line.substring( country ).trim();
                        title = title.substring(0, title.indexOf('('));
                        int year = Integer.parseInt(yearString);

                        this.controller.addCountry(title, countryString, year);

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

}

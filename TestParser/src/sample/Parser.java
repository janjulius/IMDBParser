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
}

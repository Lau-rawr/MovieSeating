package com.laura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SeatingMain {

    public static void main(String[] args) {
        if (args.length > 0) {
            GetFile fileProcessor = new GetFile(args[0]);
            Theater movieTheater = new Theater();

            try {
                File file = new File(args[0]);
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                /* Read and process the file */
                String newEntry = bufferedReader.readLine();

                while (newEntry != null) {
                    int output = movieTheater.fillReservation(newEntry);
                    if (output == 1) {
                        System.out
                                .println("Invalid number of Seats");
                    }
                    if (output == -1) {
                        System.out
                                .println("Sorry, cannot process request due to Insufficient seats");
                    }

                    newEntry = bufferedReader.readLine();
                }
                /* Writing to File */
                fileProcessor.writeToFile(movieTheater.getResults());


//                /* Calling the Test method */
//                TestTheaterSeating test = new TestTheaterSeating();
//                MovieTheater testObject = new MovieTheater();
//                test.testMe(testObject);

            } catch (FileNotFoundException ex) {
                System.err.println("Input file not Found.");
                ex.printStackTrace();
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

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

                /* Read first line of file*/
                String nextReservation = bufferedReader.readLine();
                /* Read the file, and see if reservation can be filled */
                while (nextReservation != null) {
                    int output = movieTheater.fillReservation(nextReservation);
                    if (output == -2) {
                        System.out.println("Invalid number of Seats "+ nextReservation);
                    }
                    if (output == -1) {
                        System.out .println("Sorry, cannot process request due to Insufficient seats" + nextReservation);
                    }
                    nextReservation = bufferedReader.readLine();
                }
                /* Writing to File , passing our seating chart with it */
                fileProcessor.writeToFile(movieTheater.getResults());

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

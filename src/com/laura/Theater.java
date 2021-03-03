package com.laura;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;



public class Theater {

    //set as themselves so changing config is faster
    int rows = 10, col = 20, totalSeats = rows * col, bufferSeats = 3, bufferRows = 1 ;
    String[][] seatMap = new String[rows][col];
    int[] seatsRemaining = {col, col, col, col, col, col, col, col, col, col};
    LinkedHashMap<String, ArrayList<String>> hm = new LinkedHashMap<>(); // act as our queue but maintaining order

    // constructor left empty on purpose due to no passed in values
    public Theater() {
    }

    public int fillReservation(String res ){
        String[] booking = res.split(" ");
        int seatsRequested = Integer.parseInt(booking[1]);
        String resNum = booking[0];
        // if there are people in the reservation
        if(seatsRequested > 0){
            // if the number of seats requested is less than or equal to the number of total seats
            if(totalSeats >= seatsRequested){
                if(seatsRequested > 20){
                    int val = 0;
                    while(seatsRequested > 20){
                        val = assignSeats(resNum, 20);
                        seatsRequested -= 20;
                    }
                    return val;
                }
                return assignSeats(resNum, seatsRequested);
            }
            else{
                return -1;
            }
        }
        return -2;
    }

    private int assignSeats(String resNum, int seatsRequested) {
        boolean resFilled = false;
        int totalSeatsNeeded = seatsRequested;
        ArrayList<String> bookSeats = new ArrayList<>();
        boolean canSitTogether = true;

        // looping through the rows
        while (!resFilled) {
            if(seatsRequested <= totalSeats) {
                for (int i = rows; i >= 0; i--) {
                    if (seatsRemaining[i] <= seatsRequested && canSitTogether) {
                        //lopping through the colm could optimize
                        for (int seatNum = 0; seatNum < col; seatNum++) {


                            //find first empty seat
                            if (seatMap[i][seatNum] == null) {
                                //want group to be able to sit together first if not then must break them up
                                for (int sitToget = seatNum + 1; sitToget < seatsRequested; sitToget++) {
                                    if (seatMap[i][sitToget] != null) {
                                        canSitTogether = false;
                                        break;
                                    }
                                }

                                if (canSitTogether) {
                                    int lastSeat = seatNum + seatsRequested;
                                    Arrays.fill(seatMap[i], seatNum, lastSeat, resNum);
                                    fillBuffer(i, seatNum, seatsRequested, lastSeat);
                                    seatsRemaining[i] -= seatsRequested;
                                    totalSeats -= seatsRequested;
                                    if (!hm.containsKey(resNum)) {
                                        hm.put(resNum, bookSeats);
                                    }
                                    int counter = 0;
                                    while (counter < totalSeatsNeeded) {
                                        //value of the alpha plus the seat num
                                        hm.get(resNum).add((char) (i + 65) + Integer.toString(counter + seatNum));
                                        counter++;
                                    }

                                }
                                resFilled = true;
                            }
                        }
                    }
                }
                if (!resFilled || !canSitTogether ) {//gotta break them up so place scattered
                    int k = 0;


                    while(totalSeatsNeeded != 0 && k < rows){
                        if(seatsRemaining[k] != 0){
                            for(int findSeat = 0 ; findSeat < col ; findSeat++){
                                if(seatMap[k][findSeat] != null){
                                    seatMap[k][findSeat] = resNum;
                                    //for future buffer
                                    bookSeats.add( k + " "+ findSeat);
                                    totalSeats--;
                                    seatsRemaining[k]--;
                                    totalSeatsNeeded--;
                                }
                            }
                            k--;
                        }
                    }

                    separateSeatsBuffer(resNum, bookSeats);

                    System.out.println(" ");
                }
            }
        }
        return -1;
    }

    private void separateSeatsBuffer(String resNum, ArrayList<String> bookSeats) {
        for (String seatBooked : bookSeats) {
            String[] seat = seatBooked.split(" ");
            int gotCol = Integer.parseInt(seat[1]);
            int gotRow = Integer.parseInt(seat[0]);

            int rowCounter = 1;
            while (rowCounter < bufferRows && gotRow - rowCounter > rows) {
                int rowUp = gotRow + rowCounter;
                int rowBelow = gotRow - rowCounter;
                if (rowUp < rows && seatMap[rowUp][gotCol] == null) {
                    seatMap[rowUp][gotCol] = "buffer";
                }
                if (rowBelow > 0 && seatMap[rowBelow][gotCol] == null) {
                    seatMap[rowBelow][gotCol] = "buffer";
                }
                rowCounter++;
                //else its a group member
            }

            int colCounter = 1;
            while (colCounter < bufferSeats && gotCol - colCounter > col) {
                int seatL = gotCol + colCounter;
                int seatR = gotCol - colCounter;
                if (seatR < col && seatMap[gotRow][seatR] == null) {
                    seatMap[gotRow][seatR] = "buffer";
                }
                if (seatL >= 0 && seatMap[gotRow][seatL] == null) {
                    seatMap[gotRow][seatL] = "buffer";
                }
                rowCounter++;
                //else its a group member
            }

        }
    }

    private void fillBuffer(int i, int seatNum, int seatsRequested, int lastSeat) {
        //row buffer
        for (int numrows = 1; numrows <= bufferRows; numrows++) {
            if(numrows-i > 0) {
                seatMap[i][i - numrows] = "buffer";
                seatsRemaining[i - numrows] -= 1;
                totalSeats -=1;
            }
            if(numrows+1 < rows){
                seatMap[i][i + numrows] = "buffer";
                seatsRemaining[i + numrows] -= 1;
                totalSeats -=1;
            }
        }

        for (int j = lastSeat + 1; i <= bufferSeats; i++) {
            if(j+seatNum<col) {
                seatMap[i][j+seatNum] = "buffer";
                seatsRemaining[i] -= 1;
                totalSeats -=1;
            }
            if(j-seatNum<col) {
                seatMap[i][j-seatNum] = "buffer";
                seatsRemaining[i] -= 1;
                totalSeats -=1;
            }
        }
    }

    public LinkedHashMap<String, ArrayList<String>> getResults() {
        return hm;
    }


}


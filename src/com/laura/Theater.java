package com.laura;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Theater {
    //global class variables
    int rows = 10, col = 20, totalSeats = rows * col, bufferSeats = 3, bufferRows = 1 ;
    Boolean[][] seatMap = new Boolean[rows][col];
    int[] seatsRemaining = {col, col, col, col, col, col, col, col, col, col};
    LinkedHashMap<String, ArrayList<String>> seatChart = new LinkedHashMap<>();


    // constructor
    public Theater() {
        for(int i = 0; i < rows;i++){
            for(int j = 0; j < col; j++){
                seatMap[i][j] = false;
            }
        }
    }

    /**
     * take reservation and check number of seats requsted if less than 20 call next fxn if not recursively call until
     * 0
     * @param res is a String passes in the reservation followed by a space then the number of seats wanted
     * @return int 0 if valid, -1 if not enough seats, -2 if reservation is less than 0
     */
    public int fillReservation(String res){
        String[] booking = res.split(" ");
        int seatsRequested = Integer.parseInt(booking[1]);
        String resNum = booking[0];
        // if there are people in the reservation
        if(seatsRequested > 0){ // if the number of seats requested is less than or equal to the number of total seats
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
            }else{
                return -1;
            }
        }
        return -2;
    }


    /**
     * sees if the reservation party can sit together or must be split up, then makes the reservation and calls for
     * buffer seats to be made
     * @param resNum the reservation number ex.R010
     * @param seatsRequested requested number of seats in reservatiob
     * @return int 0 if valid -1 if not enough seats(should have been caught already)
     */
    private int assignSeats(String resNum, int seatsRequested) {
        int totalSeatsNeeded = seatsRequested;
        ArrayList<String> bookSeats = new ArrayList<>();
        if(seatsRequested <= totalSeats) { // looping through the rows
            for (int rowIndex = rows - 1; rowIndex > 0; rowIndex--) {
                //find out if the row has enough seat using seatsRemaining array
                if (seatsRemaining[rowIndex] >= seatsRequested) { // see if they can sit together
                    int[] availSeats = findSeatsTogether(seatsRequested, rowIndex);
                    if (availSeats[0] != -1) {
                        int lastSeat;
                        lastSeat = availSeats[1] + seatsRequested - 1;
                        //must add one as arrays.fill toIndex is exclusive not inclusive
                        Arrays.fill(seatMap[availSeats[0]], availSeats[1], lastSeat+1, true);
                        fillBuffer(availSeats[0], availSeats[1], seatsRequested, lastSeat);
                        seatsRemaining[availSeats[0]] -= seatsRequested;
                        totalSeats -= seatsRequested;
                        if (!seatChart.containsKey(resNum)) {
                            seatChart.put(resNum, new ArrayList<>());
                        }
                        int counter = 0;
                        while (counter < totalSeatsNeeded) {
                            seatChart.get(resNum).add((char) (availSeats[0]+ 65) + Integer.toString(counter + availSeats[1] + 1));
                            counter++;
                        }
                        return 0;
                    } else { //they must split up
                        int splitRow = rows - 1;
                        while (totalSeatsNeeded > 0 && splitRow >= 0) {
                            if (seatsRemaining[splitRow] > 1) {
                                for (int findSeat = 0; findSeat < col - 1; findSeat++) {
                                    if (!seatMap[splitRow][findSeat]) {
                                        seatMap[splitRow][findSeat] = true;
                                        //for future buffer
                                        bookSeats.add(splitRow + " " + findSeat);
                                        if (!seatChart.containsKey(resNum)) {
                                            seatChart.put(resNum, new ArrayList<>());
                                        }else {
                                            seatChart.get(resNum).add((char) (splitRow + 65) + Integer.toString(findSeat +1));
                                        }
                                        totalSeats--;
                                        seatsRemaining[splitRow]--;
                                        totalSeatsNeeded--;
                                    }
                                }
                            }
                            splitRow--;
                        }
                        separateSeatsBuffer(bookSeats);
                    }
                }
            }
            return 0;
        }
        return -1;
    }

    /**
     * To maximize customer satisfaction we want to see if we can sit them together first
     * @param seatsRequested the number of seats from the reservation
     * @param rowIndex the first initial row that had enough seats in it
     * @return
     */
    private int[] findSeatsTogether(int seatsRequested, int rowIndex) {
        int[] result = {-1,-1};

        for(int rowOk = rowIndex; rowOk >=0 ; rowOk-- ){
            if(seatsRemaining[rowOk] > seatsRequested  ){ // enough seats in row
                int seatCountRemaining = seatsRequested;
                int seatCounter = 0;
                while(seatCountRemaining > 0 ){ // this row has the number of seats but can group sit together
                    if(seatMap[rowOk][seatCounter]){       //someones already there so reset counters
                        seatCountRemaining = seatsRequested;
                    }else{
                        seatCountRemaining--;
                    }
                    if(seatCounter!=col-1) { // not at the end of the row
                        seatCounter++;
                    }else{
                        break;
                    }
                }
                if(seatCountRemaining == 0){ // we found seats so pass back values
                    result[0]= rowOk;
                    result[1]= seatCounter-seatsRequested;
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * because the seperation of the group we can assume that the buffers can be set after they are sitting so one can
     * be in front of the other
     * @param bookSeats this is all the seats that were booked seperately
     */
    private void separateSeatsBuffer( ArrayList<String> bookSeats) {
        for (String seatBooked : bookSeats) {
            String[] seat = seatBooked.split(" ");
            int gotCol = Integer.parseInt(seat[1]);
            int gotRow = Integer.parseInt(seat[0]);

            int rowCounter = 1;
            while (rowCounter < bufferRows && gotRow - rowCounter > rows) {
                int rowUp = gotRow + rowCounter;
                int rowBelow = gotRow - rowCounter;
                if (rowUp < rows-1 && !seatMap[rowUp][gotCol] ) {
                    seatMap[rowUp][gotCol] = true;
                }
                if (rowBelow > 0 && !seatMap[rowBelow][gotCol] ) {
                    seatMap[rowBelow][gotCol] = true;
                }
                rowCounter++;
                //else its a group member
            }
            int colCounter = 1;
            while (colCounter < bufferSeats && gotCol - colCounter > col) {
                int seatL = gotCol + colCounter;
                int seatR = gotCol - colCounter;
                if (seatR < col && seatMap[gotRow][seatR] == null) {
                    seatMap[gotRow][seatR]= true;
                }
                if (seatL >= 0 && seatMap[gotRow][seatL] == null) {
                    seatMap[gotRow][seatL] = true;
                }
                rowCounter++;
                //else its a group member
            }
        }
    }

    /**
     * @param rowIdx  the row of the seats, goes by first seat
     * @param colIdx the first seat col in the booking
     * @param seatsRequested the total number of seats
     * @param lastSeat the last seat in the booking
     */
    private void fillBuffer(int rowIdx, int colIdx, int seatsRequested, int lastSeat) {
        //row buffer
        for (int numrows = 1; numrows <= bufferRows; numrows++) {
            if(rowIdx-numrows > 0) {
                for(int safety = 0; safety < seatsRequested; safety++) {
                    seatMap[rowIdx - numrows][colIdx+safety] = true;
                    seatsRemaining[rowIdx - numrows] -= 1;
                    totalSeats -= 1;
                }
            }
            if(numrows+rowIdx < rows-1){
                for(int safety = 0; safety < seatsRequested; safety++) {
                    seatMap[rowIdx + numrows][colIdx+safety] = true;
                    seatsRemaining[rowIdx + numrows] -= 1;
                    totalSeats -= 1;
                }
            }
        }

        for (int j =  1; j <= bufferSeats; j++) {
            if(j+lastSeat <= col-1 ) {
                seatMap[rowIdx][lastSeat+j] = true;
                seatsRemaining[rowIdx] -= 1;
                totalSeats -=1;
            }
            if(colIdx-j>=0 && !seatMap[rowIdx][colIdx-j] ) {
                seatMap[rowIdx][colIdx-j] = true;
                seatsRemaining[rowIdx] -= 1;
                totalSeats -=1;
            }
        }
    }


    /**
     * this is called when we are done reading the file and then want to write to the file
     * @return our seating chart arrangement of valid reservation
     */
    public LinkedHashMap<String, ArrayList<String>> getResults() {
        return seatChart;
    }

    public int getTotalSeats(){
        return totalSeats;
    }





}


package com.laura;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetFile {


    public GetFile(String file){

    }


    /**
     * writes our seating chart to the file
     * @param seatChart this is the generated seating chart we found
     */
    public void writeToFile(LinkedHashMap<String, ArrayList<String>> seatChart) {
        BufferedWriter writing;
        try {
            writing = new BufferedWriter(new FileWriter("output.txt"));
            for (Map.Entry<String, ArrayList<String>> pairs : seatChart.entrySet()) {
                String str = pairs.getKey() + " " + pairs.getValue();
               // System.out.print(str + "\n");
                writing.write(str + "\n");
            }
            writing.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
      //  System.out.println(seatChart);
        Path path = Paths.get("output.txt");
        System.out.println(path.toAbsolutePath() );
    }
}


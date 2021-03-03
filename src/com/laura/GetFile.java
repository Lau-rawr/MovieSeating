package com.laura;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetFile {

    private String fileName;
    private int lengthFile;

    public GetFile(String file){
        fileName = file;
        lengthFile =0;
    }

    public String readFile(String inputFile ){
        String content = null;
        try {

            File reservations = new File(inputFile);
            FileReader fReader = new FileReader(reservations);

            BufferedReader buffReader = new BufferedReader(fReader);
            StringBuilder sb = new StringBuilder();
            String line = buffReader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = buffReader.readLine();
                lengthFile++;
            }
            content = sb.toString();
        }catch(FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return content;
    }

    public void writeToFile(LinkedHashMap<String, ArrayList<String>> hm) {
        BufferedWriter wr = null;
        try {
            wr = new BufferedWriter(new FileWriter("output.txt"));
            Iterator<Map.Entry<String, ArrayList<String>>> itr = hm.entrySet()
                    .iterator();
            while (((Iterator<?>) itr).hasNext()) {
                Map.Entry<String, ArrayList<String>> pairs = itr.next();
                String str = pairs.getKey() + " " + pairs.getValue();
                System.out.print(str + "\n");
                wr.write(str + "\n");
            }
            wr.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println(hm);
    }
}


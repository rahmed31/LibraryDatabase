import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
Class for reading books_metadata.csv containing sample book metadata.
Unique publishers for a limit of 500 entries are saved into publishers.txt
 */

public class UniquePublishers {
    public static void main(String[] args) throws Exception, IOException {
        CSVReader reader = null;

        HashMap <String, Integer> publishers = new HashMap<String, Integer> ();

        //find unique publishers within the first 500 records
        try {
            reader = new CSVReader(new FileReader("lib/book_metadata.csv"));

            //remove column names
            String[] nextLine = reader.readNext();

            int count = 0;

            while((nextLine = reader.readNext()) != null && count < 500) {

                if (publishers.containsKey(nextLine[4])) {
                    publishers.put(nextLine[4], publishers.get(nextLine[4]) + 1);
                } else {publishers.put(nextLine[4], 1);}

                count++;
            }

        }
        catch (Exception e) {
            e.getMessage();
        }

        //create new txt file to save publisher names
        try {
            File myObj = new File("lib/publishers.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //save unique publishers in txt file
        try {
            FileWriter myWriter = new FileWriter("lib/publishers.txt");
            for (String i : publishers.keySet()) {
                myWriter.write(i + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

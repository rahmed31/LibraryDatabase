import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
Class for reading books_with_blurbs.csv containing sample book metadata.
Unique publishers for a limit of 500 entries are saved into publishers.txt
 */

public class ReadCSV {
    public static void main(String[] args) throws Exception, IOException {
        CSVReader reader = null;

        HashMap <String, Integer> publishers = new HashMap<String, Integer> ();

        try {
            reader = new CSVReader(new FileReader("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/book_metadata.csv"));

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

        try {
            File myObj = new File("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/publishers.txt");
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

        try {
            FileWriter myWriter = new FileWriter("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/publishers.txt");
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

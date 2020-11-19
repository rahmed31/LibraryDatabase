import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadCSV {
    public static void main(String[] args) throws Exception {
        CSVReader reader = null;

        try {
            reader = new CSVReader(new FileReader("/Users/raihanahmed/Desktop/Database Management/books_with_blurbs.csv"));
            String[] nextLine = reader.readNext();

            int count = 0;
//            (nextLine = reader.readNext()) != null
            while((nextLine = reader.readNext()) != null && count < 500) {
                System.out.println(nextLine.length);
                for (String token : nextLine) {
                    System.out.print(token + " ");
                }
                System.out.println();
                count++;
            }

        }
        catch (Exception e) {
            e.getMessage();
        }
    }
}

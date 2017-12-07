import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {

    public static Scanner userScanner = new Scanner(System.in);

    // opens a text file for input, returns a Scanner:
    public static Scanner openInputFile() {
        String filename;
        Scanner scanner = null;

        System.out.print("Enter the input filename: ");
        filename = userScanner.nextLine();
        File file = new File(filename);

        try {
            scanner = new Scanner(file);
        }// end try
        catch (FileNotFoundException fe) {
            System.out.println("Can't open input file\n");
            return null; // array of 0 elements
        } // end catch
        return scanner;
    }

    public static void addToGraph(hamilton<String> h) {

        Scanner scn = openInputFile();

        while(scn.hasNext()) {

            double cost = scn.nextDouble();

            String grr = scn.nextLine().trim();
            String[] split = grr.split("\\|");
            String first = split[0].trim();
            String second = split[1].trim();

            h.insert(first, second, cost);


        }


    }

    public static void main(String[] args) {
        hamilton<String> h = new hamilton<>();

        addToGraph(h);
        h.showAdjTable();
        h.displayAdj();


    }

}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map.Entry;

public class Driver
{
    public static Scanner userScanner = new Scanner(System.in);

    public static Scanner openInputFile()
    {
        String filename;
        Scanner scanner=null;

        System.out.print("Enter the input filename: ");
        filename = userScanner.nextLine();
        File file= new File(filename);

        try{
            scanner = new Scanner(file);
        }// end try
        catch(FileNotFoundException fe){
            System.out.println("Can't open input file\n");
            return null; // array of 0 elements
        } // end catch
        return scanner;
    }

    //add an edge from user
    public static int addEdge(hamilton<String> ParisSightseeing)
    {
        int edgesCounter = 0;

        Scanner scn = new Scanner(System.in);

        //while(scn.hasNextLine())
        //{

            System.out.println("Please enter the start vertex name to be added:");
            String first = scn.nextLine().trim();
            System.out.println("Please enter the destination vertex name to be added:");
            String second = scn.nextLine().trim();

            double cost = 0.0;
            try
            {
                cost = scn.nextDouble();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please type a NUMBER for distance");
            }

            ParisSightseeing.insert(first, second, cost);
            edgesCounter ++;
            System.out.println("This edge has been added to the graph\n");
        //}


        scn.close();
        return edgesCounter;

    }

    // add edges from file
    public static int allocateFile (hamilton<String> ParisSightseeing)
    {
        int edgeCounter = 0;

        Scanner scanner = openInputFile();
        if(scanner == null)
            return 0;

        double cost = 0.0;

        while(scanner.hasNextLine())
        {
            cost = scanner.nextDouble();
            String grr = scanner.nextLine().trim();
            String[] split = grr.split("\\|");
            String first = split[0].trim();
            String second = split[1].trim();

            // add an Edge to Graph
            ParisSightseeing.insert(first, second, cost);
            edgeCounter ++;
        }

        scanner.close();
        System.out.println("\n" + edgeCounter + " edges have been added to graph\n");
        return edgeCounter;
    }
    public static boolean removeEdge(hamilton<String> ParisSightseeing)
    {
        Scanner userChoice = new Scanner(System.in);

        System.out.println("Please enter the start vertex name to be deleted:");
        String src = userChoice.nextLine().trim();
        System.out.println("Please enter the destination vertex name to be deleted:");
        String dest = userChoice.nextLine().trim();

        userChoice.close();

        if(ParisSightseeing.removeEdge(src, dest)) {
            return true;
        }
        else {
            System.out.println("The specified edge doesn't exist in the graph.\n");
            return false;
        }
    }

    public static void displayGraph(hamilton<String> ParisSightseeing)
    {
        boolean askFlag = false;
        Visitor<String> graphVisitor = new Visit(ParisSightseeing);
        String startElement;

        System.out.println("Type your START POINT");
        Scanner strScanner = new Scanner(System.in);
        startElement = strScanner.nextLine().trim();

        while(!askFlag)
        {
            // prompt
            System.out.println("---Which style do you want to display?---");
            System.out.println("    1. Depth-First traversal");
            System.out.println("    2. Breadth-First traversal");
            System.out.println("    3. adjacency list of each vertex");
            System.out.println("Type 1, 2 or 3 for your choice");

            Scanner choiceScanner = new Scanner(System.in);
            int askResult = choiceScanner.nextInt();

            switch(askResult)
            {
                case 1:
                    ParisSightseeing.depthFirstTraversal(startElement, graphVisitor);
                    askFlag = true;
                    break;
                case 2:
                    ParisSightseeing.breadthFirstTraversal(startElement, graphVisitor);
                    askFlag = true;
                    break;
                case 3:
                    ParisSightseeing.showAdjTable();
                    askFlag = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again\n");
            }
        }
        strScanner.close();
    }


    public static void doHamiltonianCycle(hamilton<String> ParisSightseeing)
    {
        // HamiltonianCycleClass(result automatically displays on the screen

       ParisSightseeing.solve();

        // ask user if want to save to solution to text
        writeGraphToTextFile(ParisSightseeing);
    }

    public static void writeOriginalGraphToTextFile(hamilton<String> ParisSightseeing)
    {
        boolean askFlag = false;
        Scanner scanner = new Scanner(System.in);

        while(!askFlag)
        {
            System.out.println("Do you want to save the graph to a text file?");
            System.out.println("Type Y for Yes or N for No (case insensitive)");

            String askResult = scanner.next();

            if(askResult.compareToIgnoreCase("Y") == 0)
            {
                System.out.print("Enter the filename: ");
                File file= new File(userScanner.nextLine());
                PrintWriter wrt;

                try{
                    wrt = new PrintWriter(file);
                }// end try
                catch(FileNotFoundException fe){
                    System.out.println("Can't open file, please try again\n");
                    writeOriginalGraphToTextFile(ParisSightseeing);
                    return;
                } // end catch

                System.out.println("The graph was written to file\n");
                ParisSightseeing.writeGraph(wrt);
                askFlag = true;
            }
            else if(askResult.compareToIgnoreCase("N") == 0)
            {
                System.out.println("The graph will not be written to the file\n");
                askFlag = true;
            }
            else
            {
                System.out.println("Invalid choice, please try again\n");
            }
        }
        scanner.close();
    }

    public static void writeGraphToTextFile(hamilton<String> ParisSightseeing)
    {
        boolean askFlag = false;
        Scanner scanner = new Scanner(System.in);

        while(!askFlag)
        {
            System.out.println("Save the solution to a text file?");
            System.out.println("Type Y for Yes or N for No (case insensitive)");

            String askResult = scanner.next();

            if(askResult.compareToIgnoreCase("Y") == 0)
            {
                System.out.print("Enter the filename: ");
                File file= new File(userScanner.nextLine());
                PrintWriter wrt;

                try{
                    wrt = new PrintWriter(file);
                }// end try
                catch(FileNotFoundException fe){
                    System.out.println("Can't open file, please try again\n");
                    writeGraphToTextFile(ParisSightseeing);
                    return;
                } // end catch

                System.out.println("The solution was written to file\n");
                ParisSightseeing.writeSolution(wrt);
                askFlag = true;
            }
            else if(askResult.compareToIgnoreCase("N") == 0)
            {
                System.out.println("Solution will not be written to the file\n");
                askFlag = true;
            }
            else
            {
                System.out.println("Invalid choice, please type again\n");
            }
        }
        scanner.close();
    }

    public static void menuController(hamilton<String> ParisSightseeing)
    {
        boolean menuFlag = false;
        boolean graphFlag = false;
        int edgeCounter = 0;
        int removeCounter = 0;
        Scanner choiceScanner = new Scanner(System.in);

        System.out.println("Welcome to the Paris Sightseeing Service");

        while(!menuFlag)
        {
            System.out.println("---Your options are listed below---");
            System.out.println("    1. Allocate Edges from files");
            System.out.println("    2. Add an edge");
            System.out.println("    3. Remove one Edge");
            System.out.println("    4. Undo the last Remove");
            System.out.println("    5. Display the Graph");
            System.out.println("    6. Solve the Problem");
            System.out.println("    7. Write this Graph to a file");
            System.out.println("    8. Clear the current graph.");
            System.out.println("    9. Quit this Graph");
            System.out.println("Type 1, 2, 3, 4, 5, 6, 7, 8, or 9 for your choice");

            int askResult = choiceScanner.nextInt();

            switch(askResult)
            {
                case 1:// add from file
                {
                    edgeCounter +=  allocateFile(ParisSightseeing);
                    graphFlag = true;
                    break;
                }
                case 2: // add from user
                {
                    // prompt
                    System.out.println("Enter the START POINT, END POINT, and their DISTANCE");

                    edgeCounter += addEdge(ParisSightseeing);
                    graphFlag = true;
                    break;
                }
                case 3:// remove
                {
                    // graph is empty because no add happens
                    if(edgeCounter == 0)
                    {
                        System.out.println("Graph has no edges, remove rejected\n");
                    }
                    else
                    {
                        // prompt
                        System.out.println("Enter the START POINT and END POINT of the edge you want to remove");

                        removeEdge(ParisSightseeing);
                        removeCounter ++;
                        edgeCounter --;
                    }
                    break;
                }
                case 4: // undo remove
                {
                    // add happened once or remove happened once
                    if(graphFlag && !ParisSightseeing.isEmpty())
                    {
                        ParisSightseeing.undoLastRemove();
                        removeCounter -= 1;
                        System.out.println("The last removal has been undone.\n");
                    }
                    else
                    {
                        System.out.println("Undo failed, no removal has occurred, please try adding an edge to the graph\n");
                    }
                    break;
                }
                case 5: //display
                {
                    if(!ParisSightseeing.isEmpty()) {
                        displayGraph(ParisSightseeing);
                    } else {
                        System.out.println("The graph is empty, please add an edge.");
                    }
                    break;
                }
                case 6:
                {
                    doHamiltonianCycle(ParisSightseeing);
                    break;
                }
                case 7: // write graph to file
                {
                    writeOriginalGraphToTextFile(ParisSightseeing);
                    break;
                }
                case 8: // Clear the current graph
                {
                    ParisSightseeing.clear();
                    break;
                }
                case 9: // quit
                {
                    menuFlag = true;
                    break;
                }
                default:
                {
                    System.out.println("Invalid choice, please try again \n");
                }
            }
        }
        choiceScanner.close();
    }

    public static void main(String[] args)
    {
        hamilton<String> ParisSightseeing1;

        boolean runFlag = false;
        Scanner choiceScanner = new Scanner(System.in);

        while(!runFlag)
        {
            ParisSightseeing1 = new hamilton<>();

            menuController(ParisSightseeing1);

            System.out.println("Do you want to change to another graph or exit the program");
            System.out.println("Type Y for ANOTHER GRAPH or press any other key to exit");

            String askResult = choiceScanner.next();

            if(askResult.compareToIgnoreCase("Y") == 0)
            {
                runFlag = false;
                System.out.println("\n");
            }
            else
            {
                System.out.println("\nThank you for using our program.\n");
                System.out.println("\nProgram Ended\n");
                runFlag = true;
            }
        }
        choiceScanner.close();
    }
}

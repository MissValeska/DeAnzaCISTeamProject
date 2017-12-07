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

            double cost = 0.0;
            try
            {
                cost = scn.nextDouble();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Please type a NUMBER for distance");
            }

            String grr = scn.nextLine().trim();
            String[] split = grr.split("\\|");
            String first = split[0].trim();
            String second = split[1].trim();

            ParisSightseeing.insert(first, second, cost);
            edgesCounter ++;
            System.out.println("This edge has been added to the graph\n");
        //}


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
    public static boolean removeEdge(hamilton<String> ParisSightseeing,
                                     LinkedStack<Pair<Vertex<String>, Pair<Vertex<String>, Double>>> removedEdges)
    {
        Scanner userChoice = new Scanner(System.in);

        String grr = userChoice.nextLine().trim();
        String[] split = grr.split("\\|");
        String src = split[0].trim();
        String dest = split[1].trim();

        Vertex<String> startVertex = ParisSightseeing.getVertex(src);

        Iterator<Entry<String, Vertex<String>>> iter1 = ParisSightseeing.iterator();

        Iterator<Entry<String, Pair<Vertex<String>, Double>>> iter2;
        Entry<String, Pair<Vertex<String>, Double>> entry;
        Pair<Vertex<String>, Double> pair;

        while(iter1.hasNext())
        {
            Vertex<String> vertex = iter1.next().getValue();

            iter2 = vertex.adjList.entrySet().iterator();

            while(iter2.hasNext())
            {
                entry = iter2.next();
                pair = entry.getValue();

                if(vertex.data.equals(src) && pair.first.data.equals(dest))
                {
                    Vertex<String> firstSpot = new Vertex<>();
                    Vertex<String> lastSpot = new Vertex<>();
                    firstSpot.data = src;
                    lastSpot.data = dest;

                    Pair<Vertex<String>, Double> temp = new Pair<>(lastSpot, pair.second);
                    removedEdges.push(new Pair<Vertex<String>, Pair<Vertex<String>, Double>>(firstSpot, temp));
                    System.out.println("One edge has been removed from the graph\n");
                    return ParisSightseeing.remove(src, dest);
                    }
            }
        }

        System.out.println("Such edge doesn't exist in the graph\n");
        return false;
    }

    // Valeska Victoria wrote this
    public static void undoRemoves(hamilton<String> ParisSightseeing,
                                   LinkedStack<Pair<Vertex<String>, Pair<Vertex<String>, Double>>> removedEdges)
    {
        // undo the last remove
        Pair<Vertex<String>, Pair<Vertex<String>, Double>> temp = removedEdges.pop();
        String source = temp.first.getData();
        String dest = temp.second.first.getData();
        Double cost = temp.second.second;

        ParisSightseeing.insert(source,dest,cost);
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
                    System.out.println("Invalid choice, please type again\n");
            }
        }
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
            System.out.println("Save the graph to a text file?");
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

                System.out.println("graph was written to file\n");
                ParisSightseeing.writeGraph(wrt);
                askFlag = true;
            }
            else if(askResult.compareToIgnoreCase("N") == 0)
            {
                System.out.println("graph will not be written to the file\n");
                askFlag = true;
            }
            else
            {
                System.out.println("Invalid choice, please type again\n");
            }
        }
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

                System.out.println("Solution was written to file\n");
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
    }

    public static void menuController(hamilton<String> ParisSightseeing,
                                      LinkedStack<Pair<Vertex<String>, Pair<Vertex<String>, Double>>> removedEdges)
    {
        boolean menuFlag = false;
        boolean graphFlag = false;
        int edgeCounter = 0;
        int removeCounter = 0;
        Scanner choiceScanner = new Scanner(System.in);

        System.out.println("Good Afternoon, Welcome to Your High-end Paris Sightseeing Customization Service");

        while(!menuFlag)
        {
            System.out.println("---Your private options are listed below---");
            System.out.println("    1. Allocate Edges from files");
            System.out.println("    2. Add an edge");
            System.out.println("    3. Remove one Edge");
            System.out.println("    4. Undo the last Remove");
            System.out.println("    5. Display the Graph");
            System.out.println("    6. Solve the Problem");
            System.out.println("    7. Write this Graph to a file");
            System.out.println("    8. Quit this Graph");
            System.out.println("Type 1,2,3,4,5,6,7, or 8 for your choice");

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

                        removeEdge(ParisSightseeing, removedEdges);
                        removeCounter ++;
                        edgeCounter --;
                    }
                    break;
                }
                case 4: // undo remove
                {
                    // add happened once or remove happened once
                    if(graphFlag && (removeCounter > 0))
                    {
                        undoRemoves(ParisSightseeing, removedEdges);
                        removeCounter -= 1;
                        System.out.println("The last remove has been cancelled\n");
                    }
                    else
                    {
                        System.out.println("No remove happened, undo rejected, please type again\n");
                    }
                    break;
                }
                case 5: //display
                {
                    if(edgeCounter > 0) {
                        displayGraph(ParisSightseeing);
                    } else {
                        System.out.println("The graph is empty, please add some edges.");
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
                case 8: // quit
                {
                    //System.exit(0);
                    menuFlag = true;
                    break;
                }
                default:
                {
                    System.out.println("Invalid choice, please type again \n");
                }
            }
        }
    }

    public static void main(String[] args)
    {
        hamilton<String> ParisSightseeing1;
        LinkedStack<Pair<Vertex<String>, Pair<Vertex<String>, Double>>> removedEdges
                                                               = new LinkedStack<>();

        boolean runFlag = false;
        Scanner choiceScanner = new Scanner(System.in);

        while(!runFlag)
        {
            ParisSightseeing1 = new hamilton<>();

            menuController(ParisSightseeing1,removedEdges);

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
                System.out.println("\nProgram Ended\n");
                System.out.println("Thank you for choosing our service. " +
                                   "We are pleased to have the privilege to serve you.");
                runFlag = true;
            }
        }
    }
}

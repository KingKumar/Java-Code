import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BfsMarsTraveller {
    // Graph adjacency matrix (weighted graph)
    static protected int graphsize;
    static protected double[][] graph = null;

    // Map of all samples at all nodes
    static protected HashMap<String, List<String>> sampleMap = new HashMap<String, List<String>>();

    public static void main(String[] args) throws FileNotFoundException {
        inputToAdjM(args[2]);
        ArrayList<Integer> Start = new ArrayList();
        Start.add(alphaIndex(args[1])); // The start node
        ArrayList<ArrayList> Queue = new ArrayList();
        Queue.add(Start); // inserted in the initial queue of paths as [[Start]]

        graphsize = graph.length - 1;
        ArrayList Path = breadth_first(Queue, graphsize); // Breadth first
        // search

        System.out.println("Visited Nodes:");
        for (int x = Path.size() - 1; x > 0; x--) {
            System.out.println(IndexOfAlpha(Integer.parseInt(Path.get(x)
                    .toString()))
                    + "\tSample at location: "
                    + sampleMap.get(Path.get(x).toString()));
        }
        System.out.println("Path Cost: " + path_cost(Path));
        System.out.println(path_cost(Path));
    }

    // --------------------------------------------------------------
    // Adds to the queue paths extending the first path in the queue
    public static ArrayList<ArrayList> extend(ArrayList<Integer> Path) {
        ArrayList<ArrayList> NewPaths = new ArrayList();
        for (int i = 0; i < graph.length; i++) {
            if (graph[Path.get(0)][i] > 0 && !Path.contains(i)) {
                ArrayList Path1 = (ArrayList) Path.clone();
                Path1.add(0, i);
                NewPaths.add(Path1);
            }
        }
        return NewPaths;
    }

    // --------------------------------------------------------------
    // Appends y to x and returns the result in a new ArrayList z
    public static ArrayList append(ArrayList x, ArrayList y) {
        ArrayList z = (ArrayList) x.clone();
        ;
        for (int i = 0; i < y.size(); i++) {
            z.add(y.get(i));
        }
        return z;
    }

    // --------------------------------------------------------------
    // Breadth first search for a path from Start to Goal.
    // The Start node must be in the initial queue of paths as [[Start]]
    public static ArrayList<ArrayList> breadth_first(
            ArrayList<ArrayList> Queue, int Goal) {
        if (Queue.size() == 0) {
            return Queue; // path not found
        }
        if ((Integer) Queue.get(0).get(0) == Goal) {
            return Queue.get(0); // return path
        } else // replace the first path with all its extensions and put them at
               // the end of the queue
        {
            ArrayList<ArrayList> NewPaths = extend(Queue.get(0));
            Queue.remove(0);
            return breadth_first(append(Queue, NewPaths), Goal);
        }
    }

    // --------------------------------------------------------------
    public static double path_cost(ArrayList<Integer> Path) {
        double cost = 0;
        for (int i = 0; i < Path.size() - 1; i++) {
            cost = cost + graph[Path.get(i + 1)][Path.get(i)];
        }
        return cost;
    }

    // --------------------------------------------------------------
    public static void sort(ArrayList<ArrayList> Queue) {
        int out, in;
        for (out = Queue.size() - 1; out >= 1; out--) {
            for (in = 0; in < out; in++) {
                if (path_cost(Queue.get(in)) > path_cost(Queue.get(in + 1))) {
                    swap(Queue, in, in + 1);
                }
            }
        }
    }

    // --------------------------------------------------------------
    protected static void swap(ArrayList<ArrayList> a, int one, int two) {
        ArrayList<Integer> temp = a.get(one);
        a.set(one, a.get(two));
        a.set(two, temp);
    }

    // --------------------------------------------------------------
    protected static void inputToAdjM(String input)
            throws FileNotFoundException {
        Scanner file = new Scanner(new File(input));
        int count = 0;
        while (file.hasNextLine()) {
            count++;
            file.nextLine();
        }
        file.close();

        int baseCount = count - 1;
        double[][] graph1 = new double[count][count];

        BufferedReader bro = null;
        String line;
        String[] lineSplit;
        List<String> temp = new ArrayList<String>();
        int i;

        // Safe way to create a new file reader to iterate through input file
        try {
            bro = new BufferedReader(new FileReader(input));
        } catch (FileNotFoundException e) {
            System.out.println("Error declaring BufferedReader.");
            e.printStackTrace();
        }

        // Read the input file line by line, create Adj Matrices
        count = 0;
        try {
            while ((line = bro.readLine()) != null) {
                lineSplit = line.split(",");
                i = 0;
                temp.clear();
                String key = lineSplit[i];
                temp.add(lineSplit[i + 1]);
                temp.add(lineSplit[i + 2]);
                temp.add(lineSplit[i + 3]);
                sampleMap.put(key, temp);
                i += 4;
                while (i < lineSplit.length) {
                    if (lineSplit[i].equalsIgnoreCase("base")) {
                        graph1[count][baseCount] = Double
                                .parseDouble(lineSplit[i + 1]);
                    } else {
                        graph1[count][alphaIndex(lineSplit[i])] = Double
                                .parseDouble(lineSplit[i + 1]);
                    }
                    i += 2;
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error reading with BufferedReader.");
            e.printStackTrace();
        }

        graph = graph1.clone();
        // Close the buffered reader
        try {
            bro.close();
        } catch (IOException e) {
            System.out.println("Error closing BufferedReader.");
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------

    protected static int alphaIndex(String input) {
        switch (input) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
            case "G":
                return 6;
            case "H":
                return 7;
            case "I":
                return 8;
            case "J":
                return 9;
            case "K":
                return 10;
            case "L":
                return 11;
            case "M":
                return 12;
            case "N":
                return 13;
            case "O":
                return 14;
            case "P":
                return 15;
            case "Q":
                return 16;
            case "R":
                return 17;
            case "S":
                return 18;
            case "T":
                return 19;
            case "U":
                return 20;
            case "V":
                return 21;
            case "W":
                return 22;
            case "X":
                return 23;
            case "Y":
                return 24;
            case "Z":
                return 25;
            default:
                return -1;
        }
    }

    protected static String IndexOfAlpha(int input) {
        switch (input) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
            case 8:
                return "I";
            case 9:
                return "J";
            case 10:
                return "K";
            case 11:
                return "L";
            case 12:
                return "M";
            case 13:
                return "N";
            case 14:
                return "O";
            case 15:
                return "P";
            case 16:
                return "Q";
            case 17:
                return "R";
            case 18:
                return "S";
            case 19:
                return "T";
            case 20:
                return "U";
            case 21:
                return "V";
            case 22:
                return "W";
            case 23:
                return "X";
            case 24:
                return "Y";
            case 25:
                return "Z";
            default:
                return "whoa";
        }
    }
}
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StateRover {

    static Set<Integer> samples = new HashSet<Integer>();
    static int mapLength;

    public static void main(String args[]) {
        String file = args[0];
        BufferedReader bro = null;
        String line;
        String[] lineSplit;
        int percept;
        String atPoint;
        char action;
        Map<Point, String> mars = new HashMap<Point, String>();
        int index;
        Point point = new Point();
        int x = 1;
        int y = 2;
        int numSamps = 0;

        try {
            bro = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            while ((line = bro.readLine()) != null) {
                lineSplit = line.split(",");
                index = 1;
                x = 1;
                y = Integer.parseInt(lineSplit[0]);
                while (index < lineSplit.length) {
                    atPoint = lineSplit[index];
                    if (atPoint == "CLEAR") {
                        index++;
                        atPoint = lineSplit[index];
                        numSamps++;
                    } else {
                        index++;
                        atPoint = "BOULDER";
                    }
                    point.x = x;
                    point.y = y;
                    mars.put(point, atPoint);
                    index++;
                    x++;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            bro.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        boolean done = false;
        String samp = null;
        String samptwo = null;
        String looking = null;
        String act = null;
        point.x = 1;
        point.y = 1;
        while (!done) {
            samp = getSample(point, mars);
            samptwo = lookNorth(point, mars);
            looking = "NORTH";
            System.out.println("Position: <" + point.x + "," + point.y
                    + ">\tLooking: " + looking + "\tPerceived: <" + samp + ","
                    + samptwo + ">\tAction: "
                    + ReflexAgentWithState(samptwo, looking));

            act = ReflexAgentWithState(samptwo, looking);
            if (act == "GONORTH" || act == "GOWEST" || act == "GOSOUTH"
                    || act == "GOEAST") {
                if (act == "GONORTH") {
                    point.y++;
                } else if (act == "GOWEST") {
                    point.x--;
                } else if (act == "GOSOUTH") {
                    point.y--;
                } else if (act == "GOEAST") {
                    point.x++;
                }
            }

            samptwo = lookEast(point, mars);
            looking = "EAST";
            System.out.println("Position: <" + point.x + "," + point.y
                    + ">\tLooking: " + looking + "\tPerceived: <" + samp + ","
                    + samptwo + ">\tAction: "
                    + ReflexAgentWithState(samptwo, looking));

            act = ReflexAgentWithState(samptwo, looking);
            if (act == "GONORTH" || act == "GOWEST" || act == "GOSOUTH"
                    || act == "GOEAST") {
                if (act == "GONORTH") {
                    point.y++;
                } else if (act == "GOWEST") {
                    point.x--;
                } else if (act == "GOSOUTH") {
                    point.y--;
                } else if (act == "GOEAST") {
                    point.x++;
                }
            }

            samptwo = lookSouth(point, mars);
            looking = "SOUTH";
            System.out.println("Position: <" + point.x + "," + point.y
                    + ">\tLooking: " + looking + "\tPerceived: <" + samp + ","
                    + samptwo + ">\tAction: "
                    + ReflexAgentWithState(samptwo, looking));

            act = ReflexAgentWithState(samptwo, looking);
            if (act == "GONORTH" || act == "GOWEST" || act == "GOSOUTH"
                    || act == "GOEAST") {
                if (act == "GONORTH") {
                    point.y++;
                } else if (act == "GOWEST") {
                    point.x--;
                } else if (act == "GOSOUTH") {
                    point.y--;
                } else if (act == "GOEAST") {
                    point.x++;
                }
            }

            samptwo = lookWest(point, mars);
            looking = "WEST";
            System.out.println("Position: <" + point.x + "," + point.y
                    + ">\tLooking: " + looking + "\tPerceived: <" + samp + ","
                    + samptwo + ">\tAction: "
                    + ReflexAgentWithState(samptwo, looking));

            act = ReflexAgentWithState(samptwo, looking);
            if (act == "GONORTH" || act == "GOWEST" || act == "GOSOUTH"
                    || act == "GOEAST") {
                if (act == "GONORTH") {
                    point.y++;
                } else if (act == "GOWEST") {
                    point.x--;
                } else if (act == "GOSOUTH") {
                    point.y--;
                } else if (act == "GOEAST") {
                    point.x++;
                }
            }
        }
    }

    static String getSample(Point point, Map<Point, String> mars) {
        String value = mars.get(point);
        int sample;
        if (value != "BOULDER") {
            sample = Integer.parseInt(value);
            if (!samples.contains(sample)) {
                samples.add(sample);
            }
        }
        return value;
    }

    static String ReflexAgentWithState(String s2, String looking) {
        if (s2 == "NULL" || s2 == "BOULDER" && looking == "NORTH") {
            return "LOOKEAST";
        } else if (s2 == "NULL" || s2 == "BOULDER" && looking == "EAST") {
            return "LOOKSOUTH";
        } else if (s2 == "NULL" || s2 == "BOULDER" && looking == "SOUTH") {
            return "LOOKWEST";
        } else if (s2 == "NULL" || s2 == "BOULDER" && looking == "WEST") {
            return "LOOKNORTH";
        } else {
            return "GO" + looking;
        }
    }

    static String lookNorth(Point point, Map<Point, String> mars) {
        int why = point.y + 1;
        int ex = point.x;
        if (why > 2 || why < 1) {
            return "NULL";
        } else {
            Point newpoint = new Point();
            newpoint.x = ex;
            newpoint.y = why;
            String value = mars.get(newpoint);
            return value;
        }
    }

    static String lookEast(Point point, Map<Point, String> mars) {
        int why = point.y;
        int ex = point.x + 1;
        if (ex > mapLength || ex < 1) {
            return "NULL";
        } else {
            Point newpoint = new Point();
            newpoint.x = ex;
            newpoint.y = why;
            String value = mars.get(newpoint);
            return value;
        }
    }

    static String lookSouth(Point point, Map<Point, String> mars) {
        int why = point.y - 1;
        int ex = point.x;
        if (why > 2 || why < 1) {
            return "NULL";
        } else {
            Point newpoint = new Point();
            newpoint.x = ex;
            newpoint.y = why;
            String value = mars.get(newpoint);
            return value;
        }
    }

    static String lookWest(Point point, Map<Point, String> mars) {
        int why = point.y;
        int ex = point.x - 1;
        if (ex > mapLength || ex < 1) {
            return "NULL";
        } else {
            Point newpoint = new Point();
            newpoint.x = ex;
            newpoint.y = why;
            String value = mars.get(newpoint);
            return value;
        }
    }

    public enum Looking {
        NORTH, EAST, SOUTH, WEST
    }

    static void printOutput(int percept, char action) {
        System.out.println("Percieved: " + percept + "\tAction: " + action);
    }
}

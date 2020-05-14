import com.sun.security.jgss.GSSUtil;

import java.util.Locale;

public class Simulation {
    //Global Variables:
    public static final double G = 6.6743e-11; // gravitational constant
    public static final double AU = 150e9; // one astronomical unit (AU) is the average distance of earth to the sun.
    private static final int CanvasSize = 500; // all quantities are based on kilogram, seconds and meter.
    private static final int day = 60;
    private static final double spaceRange = 2 * AU; // half square length of simulation cutout in AU
    private static final int secondsPerFrame = 10 * 3600; // time gap between two drawn images in hours


    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //TODO: change implementation of this method according to 'Aufgabenblatt2.md'.
        Locale.setDefault(Locale.US); // use US formatted numbers (dot (.) as comma)

        //Read initial body positions and pass via linked list:
        CelestialSystem bodies = ReadDataUtil.initialize(day+1);
        ComplexCelestialSystem solarSystem = new ComplexCelestialSystem("Solar system");

        //Initialize jupiter system:
        CelestialSystem jupiterSystem = new CelestialSystem("Jupiter system");
        jupiterSystem.add(new CelestialBody("Jupiter", 1.898e27, 69911e3, StdDraw.ORANGE));
        jupiterSystem.add(new CelestialBody("Io", 8.9e22 ,1822e3, StdDraw.YELLOW));
        jupiterSystem.add(new CelestialBody("Europa", 4.8e22 ,1561e3, StdDraw.ORANGE));
        jupiterSystem.add(new CelestialBody("Ganymed", 1.5e23 ,2631e3, StdDraw.GRAY));
        jupiterSystem.add(new CelestialBody("Kallisto", 1.1e23 ,2411e3, StdDraw.GRAY));
        jupiterSystem.add(new CelestialBody("Amalthea", 1, 1, StdDraw.WHITE));
        jupiterSystem.add(new CelestialBody("Himalia", 1, 1, StdDraw.WHITE));
        jupiterSystem.add(new CelestialBody("Elara", 1, 1, StdDraw.WHITE));

        //Initialize earth system:
        CelestialSystem earthSystem = new CelestialSystem("Earth system");
        earthSystem.add(new CelestialBody("Earth", 5.97237e24, 6371.0084e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.BLUE));
        earthSystem.add(new CelestialBody("Moon", 7.349e22, 1738e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.GRAY));

        //initialize mars system:
        CelestialSystem marsSystem = new CelestialSystem("Mars system");
        marsSystem.add(new CelestialBody("Mars", 6.419e23, 3396e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.RED));
        marsSystem.add(new CelestialBody("Phobos", 1.072e16, 11e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.GRAY));
        marsSystem.add(new CelestialBody("Deimos", 1.8e15, 6e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.GRAY));

        //initialize saturn system:
        CelestialSystem saturnSystem = new CelestialSystem("Saturn system");

        //Test cases:
        CelestialBody metis1 = new CelestialBody("Metis", 1, 1, StdDraw.GRAY);
        CelestialBody metis2 = new CelestialBody("Metis", 1, 1, StdDraw.GRAY);
        CelestialBody metis3 = new CelestialBody("BlaBla", 11, 1, StdDraw.GRAY);
        jupiterSystem.add(1, metis1);
        System.out.println("Test CelestialBody:");
        System.out.println(metis1.equals(metis1)); // true
        System.out.println(metis1.equals(metis2)); // true
        System.out.println(metis2.equals(metis1)); // true
        System.out.println(metis1.equals(null)); // false
        System.out.println((metis1.equals(metis3))); // false
        System.out.println((metis1.hashCode() == metis2.hashCode())); // true

        System.out.println("Test CelestialSystem:");
        System.out.println(jupiterSystem);
        System.out.println(jupiterSystem.reverse());
        System.out.println(saturnSystem); // empty
        System.out.println(saturnSystem.reverse()); // empty
        System.out.println(jupiterSystem.equals(jupiterSystem)); // true
        System.out.println(jupiterSystem.equals(jupiterSystem.reverse())); // true
        System.out.println(jupiterSystem.reverse().equals(null)); // false
        System.out.println(jupiterSystem.hashCode()); // hashcode: 444260504
        System.out.println((jupiterSystem.reverse().hashCode())); // hashcode: 444260504
        System.out.println(saturnSystem.equals(saturnSystem)); // true

        System.out.println("\r\nTest Binary Search Tree:");
        CelestialSystemIndexTree index = new CelestialSystemIndexTree();
        index.add(jupiterSystem);
        index.add(marsSystem);
        index.add(earthSystem);
        System.out.println(index);
        System.out.println(index.get("Phobos").getName()); // Mars system
        System.out.println(index.get("Io").getName()); // Jupiter system
        System.out.println(index.get("Kallisto").getName()); // Jupiter system
        System.out.println(index.get("Earth").getName()); // Earth system
        System.out.println(index.numberOfBodies()); // 14
        System.out.println(index.numberOfSystems()); // 3

        System.out.println("\r\nTest Hash Table:");
        CelestialSystemIndex map1 = new CelestialSystemIndexMap();
        CelestialSystemIndex map2 = new CelestialSystemIndexMap();
        CelestialSystemIndex tree1 = new CelestialSystemIndexTreeVariant();
        CelestialSystemIndex tree2 = new CelestialSystemIndexTreeVariant();
        map1.add(jupiterSystem);
        tree1.add(jupiterSystem);
        map2.add((jupiterSystem.reverse()));
        tree2.add(jupiterSystem.reverse());
        System.out.println(map1.hashCode()); // hashcode -1788227640
        System.out.println(map2.hashCode()); // hashcode -1788227640
        System.out.println(map1.equals(map2)); // true
        System.out.println(tree1.equals(tree2)); // true
        System.out.println("--");
        System.out.println(map1.add(marsSystem)); // true;
        System.out.println(map1.add(marsSystem)); // false
        System.out.println(tree1.add(marsSystem)); // true
        System.out.println(tree1.add(marsSystem)); // false
        System.out.println(tree1.equals(tree2)); // false
        System.out.println(map1.add(saturnSystem)); // false
        System.out.println(map1);
        System.out.println(map2);
        System.out.println("--");

        System.out.println(map1.get(jupiterSystem.get("Io"))); // Jupiter system
        System.out.println(map1.get(marsSystem.get("Io"))); // null
        System.out.println(map1.get(earthSystem.get("Moon"))); // Earth system



        //Setup Canvas:
        /*
        StdDraw.setCanvasSize(CanvasSize, CanvasSize);
        StdDraw.setScale(-spaceRange, spaceRange);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        //Simulation loop:
        int seconds = 0;
        while (seconds < 3600*24*365) {
            seconds++; // each iteration computes within one second.

            //Compute the total force exerted on each body:
            Vector3[] forceOnBody = new Vector3[bodies.size()];
            for (int i = 0; i < bodies.size(); i++) {
                forceOnBody[i] = new Vector3(0,0,0); // begin with zero
                for (int j = 0; j < bodies.size(); j++) {
                    if (i == j) continue;
                    Vector3 forceToAdd = bodies.get(i).gravitationalForce(bodies.get(j));
                    forceOnBody[i] = forceOnBody[i].plus(forceToAdd);
                }
            }

            for (int i = 0; i < bodies.size(); i++) {
                bodies.get(i).move(forceOnBody[i]);
            }

            //Show all movements in StdDraw only every several hours (to speed up the simulation):
            if (seconds%secondsPerFrame == 0) {
                //StdDraw.clear(StdDraw.BLACK); // <--exclude if you want to draw orbits
                for (int i = 0; i < bodies.size(); i++) {
                    bodies.get(i).draw();
                }
                StdDraw.show();
            }
        }/**/
    }

    //TODO: Zusatzfragen:
    /*
    (1) Die equals()-Vergleiche von CelestialBody sind falsch, da auch andere Eigenschaften neben dem Namen
    zum Vergleich herangezogen werden. Die Vergleiche von CelestialSystem funktionieren weiterhin
    da hier nicht auf CelestialBody.equals zurückgegriffen wird sondern mittels contains(String) nach dem
    passenden Namen in der Liste gesucht wird.
    Die equals()-Vergleiche in CelestialSystemIndexMap sind ebenfalls unbeeinflusst da hier wieder über
    contains() nach passenden Keys gesucht wird (-> unbeeinflusst). Die Suche nach passenden Values ist durch
    CelestialSystem.equals() auch nicht beeinflusst.

    (2) Die hashCodes sind nicht mit equals übereinstimmend. Sprich die Bedingungen
    aus Frage 3 sind weiterhin nicht erfüllt.

    (3) Es gilt für x != null immer:
     x.equals(null) = false
     x.equals(x) = true
     x.equals(y) && y.equals(x) = true
     Bei wiederholten Aufrufen und unverändertem x musss equals() weiterhin die selben Ergebnisse
     liefern.
     Sind zwei Objekte nach der equals()-Methode gleich, so sind auch ihre hash codes gleich. Umgekehrt
     gilt diese Beziehung nicht zwingend.

     (4) Nein gilt nicht. Da beispielsweise zwei gleichnamige Körper mit unterschiedlichen Massen
     nach der equals()-Methode gleich sind aber einen unterschiedlichen String liefern (Im String wird
     die Masse eingebunden). Das kann problematisch werden da beim Debugging ebenfalls die
     Darstellung von toString() verwendet wird. Zwei Körper "schauen" dann scheinbar nicht gleich aus (nicht
     gleicher String) sind aber innerhalb des Programms per equals() gleich.

     (5) Es müssten die Methodenköpfe auf das Interface angepasst werden. Konkret muss die Methode
     get(String) auf get(CelestialBody) umgeschrieben werden, damit die übergebenen Parameter passen.
     */
}
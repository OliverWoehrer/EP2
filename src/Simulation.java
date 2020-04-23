import java.sql.SQLOutput;
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
        jupiterSystem.add(new CelestialBody("Jupiter", 1.898e27, 69911e3,new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.ORANGE));
        jupiterSystem.add(new CelestialBody("Io", 8.9e22 ,1822e3,new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.YELLOW));
        jupiterSystem.add(new CelestialBody("Europa", 4.8e22 ,1561e3,new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.ORANGE));
        jupiterSystem.add(new CelestialBody("Ganymed", 1.5e23 ,2631e3,new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.GRAY));
        jupiterSystem.add(new CelestialBody("Kallisto", 1.1e23 ,2411e3,new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.GRAY));

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

        //Initialize empty system:
        CelestialSystem emptySystem = new CelestialSystem("empty system");

        //Insert bodies into search tree:
        System.out.println("\nBinary Search Tree Test Cases:");
        CelestialSystemIndex searchTree = new CelestialSystemIndex();
        System.out.println(searchTree.add(bodies)); // true
        System.out.println(searchTree.add(bodies)); // false
        System.out.println(searchTree.add(jupiterSystem)); // true
        System.out.println(searchTree.add(earthSystem)); // false
        System.out.println(searchTree.add(marsSystem)); // false, duplicate body
        System.out.println(searchTree.add(emptySystem)); // false, tree not changed
        System.out.println(searchTree.get("Earth").equals(bodies)); // true

        //Setup Canvas:;
        StdDraw.setCanvasSize(CanvasSize, CanvasSize);
        StdDraw.setScale(-spaceRange, spaceRange);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        //Simulation loop:
        int seconds = 0;
        while (seconds < 3600*24*365) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

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
                StdDraw.show(); // show new positions
            }
        }
    }

    //TODO: Zusatzfragen:
    /*
    (1) Man kann den gesammten Baum traversieren und das zugehörige System jedes Körpers
    auf Übereinstimmung (->true) prüfen.
     */
}
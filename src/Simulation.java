import java.util.Locale;

public class Simulation {
    //Global Variables:
    public static final double G = 6.6743e-11; // gravitational constant
    public static final double AU = 150e9; // one astronomical unit (AU) is the average distance of earth to the sun.
    private static final int CanvasSize = 600; // all quantities are based on kilogram, seconds and meter.
    private static final int day = 60;
    private static final double spaceRange = 3 * AU; // half square length of simulation cutout in AU
    private static final int drawDelay = 3; // time gap between two drawn images in hours


    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //TODO: change implementation of this method according to 'Aufgabenblatt2.md'.
        Locale.setDefault(Locale.US); // use US formatted numbers (dot (.) as comma)

        //Read initial body positions and pass via linked list:
        CelestialSystem solarsystem = ReadDataUtil.initialize(day+1);
        int size = solarsystem.size();
        for (int i = 0; i < size; i++) {
            solarsystem.get(i).print();
        }

        //Initialize jupiter system:
        CelestialSystem jupiterSystem = new CelestialSystem("jupiterSystem");
        CelestialBody jupiter = new CelestialBody("Jupiter", 1.899e27, 142.984e9, StdDraw.ORANGE);
        CelestialBody europa = new CelestialBody("Europa", 4.8e22, 3.1216e6, StdDraw.GRAY);
        CelestialBody io = new CelestialBody("Io", 4.8e22, 3.1216e6, StdDraw.YELLOW);
        CelestialBody ganymed = new CelestialBody("Ganymed", 1.4819e23, 5.262e3, StdDraw.LIGHT_GRAY);
        CelestialBody kallisto = new CelestialBody("Kallisto", 1.076e23, 4.820e3, StdDraw.GREEN);
        CelestialBody[] jupitermoons = {jupiter, europa, io, ganymed, kallisto};
        for (CelestialBody moon : jupitermoons) {
            jupiterSystem.add(moon);
            moon.print();
        }

        //Initialize mars system:
        CelestialSystem marsSystem = new CelestialSystem("marsSystem");
        CelestialBody mars = new CelestialBody("Mars", 6.419e23, 26.8e3, StdDraw.DARK_GRAY);
        CelestialBody phobos = new CelestialBody("Phobos", 1.072e16, 26.8e3, StdDraw.DARK_GRAY);
        CelestialBody deimos  = new CelestialBody("Deimos", 4.8e22, 3.1216e6, StdDraw.LIGHT_GRAY);
        CelestialBody[] marsmoons = {phobos, deimos};
        for (CelestialBody moon: marsmoons) {
            marsSystem.add(moon);
            moon.print();
        }

        //Insert bodies into search tree:
        CelestialSystemIndex searchTree = new CelestialSystemIndex();
        searchTree.add(solarsystem);
        searchTree.add(jupiterSystem);
        searchTree.add(marsSystem);
        System.out.println(searchTree.get("Jupiter").toString());
        System.out.println(searchTree.get("Earth").toString());
        System.out.println(searchTree.get("Phobos").toString());

        //Setup Canvas:
        StdDraw.setCanvasSize(CanvasSize, CanvasSize);
        StdDraw.setScale(-spaceRange, spaceRange);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        //Simulation loop:
        int seconds = 0;
        while (true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            //Compute the total force exerted on each body:
            Vector3[] forceOnBody = new Vector3[size];
            for (int i = 0; i < size; i++) {
                forceOnBody[i] = new Vector3();
                for (int j = 0; j < size; j++) {
                    if (i == j) continue; // do not compare body with itself
                    Vector3 forceToAdd = solarsystem.get(i).gravitationalForce(solarsystem.get(j));
                    forceOnBody[i] = forceOnBody[i].plus(forceToAdd);
                }
            }  // --> forceOnBody[i] holds the force vector exerted on body with index i.

            //Move each body according to the total force exerted on it:
            for (int i = 0; i < size; i++) {
                solarsystem.get(i).move(forceOnBody[i]);
            }

            //Show all movements in StdDraw only every several hours (to speed up the simulation):
            if (seconds%(drawDelay*3600) == 0) {
                //StdDraw.clear(StdDraw.BLACK); // <--exclude if you want to draw orbits
                for (int i = 0; i < size; i++) {
                    solarsystem.get(i).draw();
                }
                StdDraw.show(); // show new positions
            }
        }
    }
}
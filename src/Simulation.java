import java.sql.SQLOutput;
import java.util.Locale;

public class Simulation {
    //Global Variables:
    public static final double G = 6.6743e-11; // gravitational constant
    public static final double AU = 150e9; // one astronomical unit (AU) is the average distance of earth to the sun.
    private static final int CanvasSize = 500; // all quantities are based on kilogram, seconds and meter.
    private static final int day = 60;
    private static final double spaceRange = 7 * AU; // half square length of simulation cutout in AU
    private static final int secondsPerFrame = 10 * 3600; // time gap between two drawn images in hours


    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //TODO: change implementation of this method according to 'Aufgabenblatt2.md'.
        Locale.setDefault(Locale.US); // use US formatted numbers (dot (.) as comma)

        //Read initial body positions and pass via linked list:
        CelestialSystem planetsSystem = ReadDataUtil.initialize(day+1);

        //Initialize mars system:
        CelestialSystem marsSystem = new CelestialSystem("Mars System");
        CelestialBody mars = new CelestialBody("Mars", 6.419e23, 26.8e3, StdDraw.DARK_GRAY);
        CelestialBody phobos = new CelestialBody("Phobos", 1.072e16, 26.8e3, StdDraw.DARK_GRAY);
        CelestialBody deimos  = new CelestialBody("Deimos", 4.8e22, 3.1216e6, StdDraw.LIGHT_GRAY);
        CelestialBody[] marsmoons = {mars, phobos, deimos};
        for (CelestialBody moon: marsmoons) {
            marsSystem.add(moon);
            //moon.print();
        }

        //Initialize jupiter system:
        CelestialSystem jupiterSystem = new CelestialSystem("Jupiter System");
        CelestialBody jupiter = new CelestialBody("Jupiter", 1.899e27, 142.984e9, StdDraw.ORANGE,
                0,-778e9,0,13.06e3,0,0);
        CelestialBody europa = new CelestialBody("Europa", 4.8e22, 1.5608e6, StdDraw.GRAY,
                0, -778e9-0.6709e9, 0, 13.06e3+13.74e3, 0, 0);
        CelestialBody io = new CelestialBody("Io", 4.8e22, 1.5608e6, StdDraw.YELLOW,
                0, -778e9-0.4216e9, 0, 13.06e3+17.3e3, 0, 0);
        CelestialBody[] jupitermoons = {jupiter, europa, io};
        for (CelestialBody moon : jupitermoons) {
            jupiterSystem.add(moon);
            //moon.print();
        }

        //Insert bodies into complex list of subsystems:
        System.out.println("\nComplex Linked List:");
        ComplexCelestialSystem solarSystem = new ComplexCelestialSystem("Solar System");
        solarSystem.add(planetsSystem);
        solarSystem.add(marsSystem);
        solarSystem.add(jupiterSystem);
        int totalNumberOfBodies = 0;
        for (int i = 0; i < solarSystem.size(); i++) {
            totalNumberOfBodies += solarSystem.get(i).size();
        }

        //Insert bodies into search tree:
        System.out.println("\nBinary Search Tree:");
        CelestialSystemIndex searchTree = new CelestialSystemIndex();
        searchTree.add(planetsSystem);
        searchTree.add(jupiterSystem);
        searchTree.add(marsSystem);

        //Setup Canvas:
        // CelestialBody focusedBody = solarSystem.get("Jupiter System").get("Jupiter");
        // CelestialBody focusedBody = solarSystem.get("Solarsystem").get("Earth");
        CelestialBody focusedBody = solarSystem.get("Solarsystem").get("Sol");
        StdDraw.setCanvasSize(CanvasSize, CanvasSize);
        StdDraw.setScale(-spaceRange, spaceRange);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        //Simulation loop:
        int seconds = 0;
        while (true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            //Copy every body into usable array:
            CelestialBody[] bodyList = new CelestialBody[totalNumberOfBodies];
            int index = 0;
            for (int i = 0; i < solarSystem.size(); i++) {
                for (int j = 0; j < solarSystem.get(i).size(); j++) {
                    bodyList[index++] = solarSystem.get(i).get(j);
                }
            }

            //Compute the total force exerted on each body:
            Vector3[] forceOnBody = new Vector3[totalNumberOfBodies];
            for (int i = 0; i < totalNumberOfBodies; i++) {
                forceOnBody[i] = new Vector3();
                for (int j = 0; j < totalNumberOfBodies; j++) {
                    if (i == j) continue; // skip body it self
                    Vector3 forceToAdd = bodyList[i].gravitationalForce(bodyList[j]);
                    forceOnBody[i] = forceOnBody[i].plus(forceToAdd);
                }
            }
            for (int i = 0; i < totalNumberOfBodies; i++) {
                bodyList[i].move(forceOnBody[i]);
            }

            //Show all movements in StdDraw only every several hours (to speed up the simulation):
            if (seconds%secondsPerFrame == 0) {
                //StdDraw.clear(StdDraw.BLACK); // <--exclude if you want to draw orbits
                //updateWindowFocus(focusedBody);
                for (int i = 0; i < solarSystem.size(); i++) {
                    for (int j = 0; j < solarSystem.get(i).size(); j++) {
                        solarSystem.get(i).get(j).draw();
                    }
                }
                StdDraw.show(); // show new positions
            }
        }
    }

    static private void updateWindowFocus(CelestialBody focusedBody) {
        double offsetX = focusedBody.getX();
        double offsetY = focusedBody.getY();
        double test1 = offsetX-spaceRange;
        double test2 = offsetX+spaceRange;
        StdDraw.setXscale(offsetX-spaceRange, offsetX+spaceRange);
        StdDraw.setYscale(offsetY-spaceRange, offsetY+spaceRange);
    }
}
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
        CelestialBody metis = new CelestialBody("Metis", 1, 1, StdDraw.GRAY);
        CelestialBody carme = new CelestialBody("Carme", 1, 1, StdDraw.GRAY);
        jupiterSystem.add(metis);

        //Initialize earth system:
        CelestialSystem earthSystem = new CelestialSystem("Earth system");
        earthSystem.add(new CelestialBody("Earth", 5.97237e24, 6371.0084e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.BLUE));
        earthSystem.add(new CelestialBody("Moon", 7.349e22, 1738e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.GRAY));

        //Initialize mars system:
        CelestialSystem marsSystem = new CelestialSystem("Mars system");
        marsSystem.add(new CelestialBody("Mars", 6.419e23, 3396e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.RED));
        marsSystem.add(new CelestialBody("Phobos", 1.072e16, 11e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.GRAY));
        marsSystem.add(new CelestialBody("Deimos", 1.8e15, 6e3, new Vector3(0,0,0),
                new Vector3(0,0,0), StdDraw.GRAY));

        //Initialize saturn system:
        CelestialSystem saturnSystem = new CelestialSystem("Saturn system");

        //Test cases:
        System.out.println("\r\nTest CelestialBodyIterator:");
        jupiterSystem.iterator();
        for (CelestialBody body : jupiterSystem) {
            System.out.println(body);
        }

        System.out.println("\r\nTest CelestialBodyComparator:");
        CelestialBodyComparator comparator = new CelestialBodyNameComparator();
        assert (comparator.compare(metis, carme) > 0);
        assert (comparator.compare(metis, null) == 0);

        System.out.println("\r\nTest CelestialSystemIndexTreeVariantC:");
        CelestialSystemIndexTreeVariantC treeVariant = new CelestialSystemIndexTreeVariantC(comparator);
        assert treeVariant.add(jupiterSystem);
        assert !treeVariant.add((jupiterSystem.reverse()));
        assert !treeVariant.add(saturnSystem);
        assert !treeVariant.contains(carme);
        assert treeVariant.get(metis).equals(jupiterSystem);
        for (CelestialBody celestialBody : treeVariant) {
            System.out.println(celestialBody);
        }

        System.out.println("\r\nTest Colletion View:");
        CelestialBodyCollection viewCollection = treeVariant.bodies();
        CelestialBodyCollection copyCollection = treeVariant.bodiesAsCelestialSystem();
        System.out.println("treeVariant:" + treeVariant);
        System.out.println("viewCollection:" + viewCollection);
        System.out.println("copyCollection: " + copyCollection);
        treeVariant.add(marsSystem);
        System.out.println("treeVariant after change:" + treeVariant);
        System.out.println("viewCollection after change:" + viewCollection);
        System.out.println("copyCollection after change: " + copyCollection);
        treeVariant = new CelestialSystemIndexTreeVariantC(comparator);
        viewCollection = treeVariant.bodies();
        System.out.println(viewCollection);

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
}
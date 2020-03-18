import java.sql.SQLOutput;
import java.util.Locale;

public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance of earth to the sun.
    public static final double AU = 150e9;

    // all quantities are based on units of kilogram respectively second and meter.

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //TODO: change implementation of this method according to 'Aufgabenblatt2.md'.
        Locale.setDefault(Locale.US);

        //Initialize Body Objects:
        CelestialBody sun = new CelestialBody("Sol", 1.989e30, 696340e3, StdDraw.YELLOW);
        CelestialBody earth = new CelestialBody("Earth", 5.972e24, 6371e3, StdDraw.BLUE,
                148e9, 0, 0, 0, 29.29e3, 0);
            // minimal distance to sun: 148e9 meters
            // orbital speed at minimal distance: 29.29e3 m/s
        CelestialBody mercury = new CelestialBody("Mercury", 3.301e23, 2.4397e3, StdDraw.RED,
                -46.0e9, 0, 0, 0, -47.87e3, 0);
            // arbitrary initialisation: position opposite to the earth with maximal distance.
            // viewing from z direction movement is counter-clockwise

        //Gather all bodies in Array:
        CelestialBody[] bodies = new CelestialBody[] {earth, sun, mercury};
        Vector3[] forceOnBody = new Vector3[bodies.length];
        System.out.println("Initial object values:");
        for (CelestialBody body: bodies) {
            body.print(); // print initial object values
        }

        //Setup Canvas:
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(-2*AU,2*AU);
        StdDraw.setYscale(-2*AU,2*AU);
        double pixelWidth = 4*AU/500;
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double seconds = 0;

        //Simulation loop:
        while(true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            //Compute the total force exerted on each body in bodies[]
            for (int i = 0; i < bodies.length; i++) {
                forceOnBody[i] = new Vector3(); // begin with zero
                for (int j = 0; j < bodies.length; j++) {
                    if (i == j) continue; // skip body itself for comparison
                    Vector3 forceToAdd = bodies[i].gravitationalForce(bodies[j]);
                    forceOnBody[i] = forceOnBody[i].plus(forceToAdd);
                }
            }
            // now forceOnBody[i] holds the force vector exerted on body with index i.

            //Move for each body in bodies[] according to the total force exerted on it
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].move(forceOnBody[i]);
            }

            //Show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                StdDraw.clear(StdDraw.BLACK); // exclude if you want to draw orbits
                for (CelestialBody body: bodies) { // draw new positions
                    body.draw();
                }
                StdDraw.show(); // show new positions
            }
        }
    }
}

//TODO: answer additional questions of 'Aufgabenblatt2'.



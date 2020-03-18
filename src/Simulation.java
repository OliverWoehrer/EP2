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
        sun.print();

        CelestialBody earth = new CelestialBody("Earth", 5.972e24, 6371e3, StdDraw.BLUE,
                148e9, 0, 0, 0, 29.29e3, 0);
        // minimal distance to sun: 148e9 meters
        // orbital speed at minimal distance: 29.29e3 m/s
        earth.print();

        CelestialBody mercury = new CelestialBody("Mercury", 3.301e23, 2.4397e3, StdDraw.RED,
                -46.0e9, 0, 0, 0, -47.87e3, 0);
        // arbitrary initialisation: position opposite to the earth with maximal distance.
        // viewing from z direction movement is counter-clockwise
        mercury.print();


        CelestialBody[] bodies = new CelestialBody[] {earth, sun, mercury};
        Vector3[] forceOnBody = new Vector3[bodies.length];

        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(-2*AU,2*AU);
        StdDraw.setYscale(-2*AU,2*AU);
        double pixelWidth = 4*AU/500;
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double seconds = 0;

        //simulation loop:
        while(true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            //compute the total force exerted on each body in bodies[]
            for (int i = 0; i < bodies.length; i++) {
                forceOnBody[i] = new Vector3(); // begin with zero
                for (int j = 0; j < bodies.length; j++) {
                    if (i == j) continue; // skip body itself for comparison
                    Vector3 forceToAdd = bodies[i].gravitationalForce(bodies[j]);
                    forceOnBody[i] = forceOnBody[i].plus(forceToAdd);
                }
            }
            // now forceOnBody[i] holds the force vector exerted on body with index i.

            //move for each body in bodies[] according to the total force exerted on it
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].move(forceOnBody[i]);
            }

            // show all movements in StdDraw canvas only every 3 hours (to speed up the simulation)
            if (seconds%(3*3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                StdDraw.clear(StdDraw.BLACK);

                // draw new positions
                for (int i = 0; i < bodies.length; i++) {
                    bodies[i].draw();
                }

                // show new positions
                StdDraw.show();
            }

        }

    }

    //TODO: remove static methods below.
    /*
    // Returns a vector representing the gravitational force exerted by 'b2' on 'b1'.
    public static Vector3 gravitationalForce(CelestialBody b1, CelestialBody b2) {
        Vector3 direction = minus(b2.position,b1.position);
        double r = length(direction);
        normalize(direction);
        double force = G*b1.mass*b2.mass/(r*r);
        return times(direction,force);
    }

    // Returns the norm of v1-v2.
    public static double distance(Vector3 v1, Vector3 v2) {
        double dX = v1.x-v2.x;
        double dY = v1.y-v2.y;
        double dZ = v1.z-v2.z;

        return Math.sqrt(dX*dX+dY*dY+dZ*dZ);
    }

    // Returns v1+v2.
    public static Vector3 plus(Vector3 v1, Vector3 v2) {

        Vector3 result = new Vector3();
        result.x = v1.x+v2.x;
        result.y = v1.y+v2.y;
        result.z = v1.z+v2.z;

        return result;
    }

    // Returns v1-v2.
    public static Vector3 minus(Vector3 v1, Vector3 v2) {

        Vector3 result = new Vector3();
        result.x = v1.x-v2.x;
        result.y = v1.y-v2.y;
        result.z = v1.z-v2.z;

        return result;
    }

    // Returns v*d.
    public static Vector3 times(Vector3 v, double d) {

        Vector3 result = new Vector3();
        result.x = v.x*d;
        result.y = v.y*d;
        result.z = v.z*d;

        return result;
    }

    // Returns the norm of 'v'.
    public static double length(Vector3 v) {

        return distance(v,new Vector3()); // distance to origin.
    }

    // Normalizes the specified vector 'v': changes the length of the vector such that its length
    // becomes one. The direction and orientation of the vector is not affected.
    public static void normalize(Vector3 v) {

        double length = length(v);
        v.x/=length;
        v.y/=length;
        v.z/=length;
    }

    */

}

//TODO: answer additional questions of 'Aufgabenblatt2'.



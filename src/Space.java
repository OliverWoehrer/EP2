import java.util.Arrays;

// Space is the actual program (executable class) using objects of class 'Body'.
public class Space {

    // Some constants helpful for the simulation (particularly the 'falling ball' example).
    public static final double G = 6.6743e-11; // gravitational constant
    public static final double MASS_OF_EARTH = 5.972e24; // kg
    public static final double MASS_OF_BALL = 1; // kg
    public static final double RADIUS_OF_EARTH = 6.371e6; // m (meters)

    // On the surface of earth the gravitational force on the ball weighing 1kg is
    // approximately as follows:
    public static final double GRAVITATIONAL_FORCE_ON_BALL =
            G*MASS_OF_EARTH*MASS_OF_BALL/(RADIUS_OF_EARTH*RADIUS_OF_EARTH); // kg*m/sec² ... F = mass * acc
    // This means each second its speed increases about 9.82 meters per second.

    //TODO: further variables, if needed.
    private static final int sec = 10; // duration of movements

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //TODO: implement method.

        //One test case is given:
        //Results for the falling ball on the surface of earth are as follows:
        //Height 10m: 2 (sec = number of move(fx,fy,fz) calls)
        //Height 100m: 5 (sec = number of move(fx,fy,fz) calls)

        Body ball1 = new Body();
        ball1.setMass(1); // 1 kg
        ball1.setPosition(0,0,100); // 100m height.
        ball1.setVelocity(0,0,0);
        System.out.println(fallToGround(ball1)); // 5

        ball1.setPosition(0,0,10); // 10m height.
        ball1.setVelocity(0,0,0);
        System.out.println(fallToGround(ball1)); // 2

        Body ball2 = new Body();
        ball2.setMass(15); // 15 kg
        ball2.setPosition(0,0,100); // 100m height.
        ball2.setVelocity(0,0,0);
        System.out.println(fallToGround(ball2)); // 5
        //Further examples are to be tested (body in empty space, rocket, feather).

        //TODO: implement test cases!
        //orbital object test case:
        Body orb = new Body();
        orb.setMass(2e3);
        orb.setPosition(0,0,0);
        orb.setVelocity(1,2,3);   // 3.7m/s initial velocity
        linearMovement(sec, orb);    // move for 10 seconds
        System.out.println("new orb position after " + sec + " sec.");

        //rocket test case:
        Body rocket = new Body();
        rocket.setMass(2e3);    // 2000kg
        rocket.setPosition(0,0,0);
        rocket.setVelocity(0,0,0);   // 0m/s initial velocity at take off
        acceleratedMovement(sec, rocket, 0, 0, 5e6);
        System.out.println("new rocket position after " + sec + " sec.");

        //feather test case:
        Body feather = new Body();
        feather.setMass(0.001);
        feather.setPosition(0, 0, 0);
        feather.setVelocity(0, 0, 0);
        randomMovement(sec, feather);
        System.out.println("new feather position after " + sec + " sec.");
    }

    // Returns the number of move(fx,fy,fz) calls needed for 'b' hitting the ground, i.e.,
    // the method reduces the z-coordinate of 'b' until it becomes 0 or negative.
    public static int fallToGround(Body b) {
        //TODO: implement recursive method.
        if (b.getHeight() > 0) {                                // check if body has hit the ground
            b.move(0, 0, -b.gravitationalForce()); // apply gravitational force to body
            return  1 + fallToGround(b);                        // body has fallen for one additional second
        } else {    // body has hit the ground (end recursion)
            return 0;
        }
    }

    //TODO: Define further methods as needed.
    /**
     * moves an object through space for a given number of seconds at the current velocity
     * @param seconds duration of movement in seconds
     * @param b pass object to be moved
     */
    public static void linearMovement(int seconds, Body b) {
        if (seconds > 0) {  // recursive step
            b.move(); // perform linear move
            linearMovement(seconds - 1, b); // enter recursion
        } // else: movement finished
    }

    /**
     * moves an object through space with a constant acceleration applied to it for the given number of seconds.
     * Therefor the objects is either speed up, slowing down or traveling at a constant speed if the acceleration
     * is zero.
     * @param seconds duration of movement in seconds
     * @param b body to be moved
     * @param fx acceleration force in x direction
     * @param fy acceleration force in y direction
     * @param fz acceleration force in z direction
     */
    public static void acceleratedMovement(int seconds, Body b, double fx, double fy, double fz) {
        if (seconds > 0) {  // recursive step
            b.move(fx, fy, fz); // perform accelerated move
            acceleratedMovement(seconds-1, b, fx, fy, fz);
        } // else: movement finished
    }

    /**
     * moves an object through space with a random acceleration force applied to it for the given number of seconds.
     * The force moving the body changes every second and varies between -0.5...+0.5 Newton in each direction (x,y,z)
     * @param seconds duration of movement in seconds
     * @param b body to be randomly moved
     */
    public static void randomMovement(int seconds, Body b) {
        if (seconds > 0) {
            double fx = (Math.random()) - 0.5;
            double fy = (Math.random()) - 0.5;
            double fz = (Math.random()) - 0.5;
            b.move(fx, fy, fz); // perform single move
            randomMovement(seconds-1, b); // recursive call
        } // else: movement finished
    }
}




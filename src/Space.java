
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

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //TODO: implement method.

        //One test case is given:
        //Results for the falling ball on the surface of earth are as follows:
        //Height 10m: 2 (sec = number of move(fx,fy,fz) calls)
        //Height 100m: 5 (sec = number of move(fx,fy,fz) calls)
        /*
        Body ball1 = new Body();
        ball1.setPosition(0,0,100); // 100m height.
        ball1.setVelocity(0,0,0);
        ball1.setMass(1); // 1 kg
        System.out.println(fallToGround(ball1)); // 5

        ball1.setPosition(0,0,10); // 10m height.
        ball1.setVelocity(0,0,0);
        System.out.println(fallToGround(ball1)); // 2

        Body ball2 = new Body();
        ball2.setPosition(0,0,100); // 100m height.
        ball2.setVelocity(0,0,0);
        ball2.setMass(15); // 15 kg
        System.out.println(fallToGround(ball1)); // 5
        */

        //Further examples are to be tested (body in empty space, rocket, feather).

    }

    // Returns the number of move(fx,fy,fz) calls needed for 'b' hitting the ground, i.e.,
    // the method reduces the z-coordinate of 'b' until it becomes 0 or negative.
    public static int fallToGround(Body b) {

        //TODO: implement recursive method.
        return 0;

    }

    //TODO: Define further methods as needed.

}




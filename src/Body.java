/**
 * Body represents a body or object in space with 3D-position, velocity and mass.
 * This class will be used by the executable class 'Space'. A body moves through space according to
 * its inertia and - if specified - an additional force exerted on it (for example the
 * gravitational force of another mass).
 */
public class Body {
    //TODO: class definition.
    private double mass;                                // mass of body in kg
    private float posX, posY, posZ;                     // position in space via XYZ coordinates in meters
    private float veloX, veloY, veloZ;                  // velocity vector via XYZ coordinates in m/s

    /**
     * Setter Method for setting the mass of an object
     * @param massArg mass of body in kg
     */
    public void setMass(double massArg) {
        this.mass = massArg;
    }

    /**
     * Setter Method for positioning the body in space
     * @param x coordinate in space in meters
     * @param y coordinate in space in meters
     * @param z coordinate in space in meters
     */
    public void setPosition(int x, int y, int z) {
        posX = x;
        posY = y;
        posZ = z;
    }

    /**
     * Setter Method for setting the current velocity of the body
     * @param vx velocity in x direction
     * @param vy velocity in y direction
     * @param vz velocity in z direction
     */
    public void setVelocity(float vx, float vy, float vz) {
        veloX = vx;
        veloY = vy;
        veloZ = vz;
    }

    /**
     * returns the distance of body to origin in z direction
     * @return absolute position in z direction
     */
    public float getHeight() {
        return this.posZ;
    }

    public double gravitationalForce() {
        // kg*m/sec² ... F = mass * acc
        return Space.G*Space.MASS_OF_EARTH*this.mass/(Space.RADIUS_OF_EARTH*Space.RADIUS_OF_EARTH);
    }

    /**
     * move() does a linear body movement in space in the direction of the current
     * velocity of the body. One movement takes exactly 1 sec to perform.
     */
    public void move() {
        posX += veloX;
        posY += veloY;
        posZ += veloZ;
    }

    /**
     * This method changes the current velocity according to the given acceleration forces before
     * performing a body movement in space in the direction of the new resulting velocity vector.
     * The resulting vector is the sum of the new force vector passed in the arguments and the current
     * velocity vector. To match the units of the vectors, the forces need to be divided by the mass
     * of the body: force = mass * acceleration: [N] = [kg] * [m/s^2]
     * @param fx acceleration force in x direction
     * @param fy acceleration force in y direction
     * @param fz acceleration force in z direction
     */
    public void move(double fx, double fy, double fz) {
        this.veloX += (float)(fx / this.mass);  // add current and new directions together
        this.veloY += (float)(fy / this.mass);
        this.veloZ += (float)(fz / this.mass);
        move();                     // perform linear movement
    }

}
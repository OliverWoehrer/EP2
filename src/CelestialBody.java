import java.awt.*;
import java.util.Objects;

// This class represents celestial bodies like stars, planets, asteroids, etc..
public class CelestialBody {
    //TODO: change modifiers.
    //Object Variables:
    private String name;
    private double mass;
    private double radius;
    private Vector3 position; // position of the center.
    private Vector3 velocity; // velocity vector of current body speed
    private Color color; // for drawing the body.

    //TODO: define constructor.
    //Constructors:
    public CelestialBody() {}

    public CelestialBody(String name, double mass, double radius, Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
        this.position = new Vector3();
        this.velocity = new Vector3();
    }

    public CelestialBody(String name, double mass, double radius, Color color, double posX, double posY, double posZ,
                         double veloX, double veloY, double veloZ) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
        this.position = new Vector3(posX, posY, posZ);
        this.velocity = new Vector3(veloX, veloY, veloZ);
    }

    public CelestialBody(String name, double mass, double radius, Vector3 position, Vector3 velocity, Color color) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position; // set position directly as vector
        this.velocity = velocity; // set velocity vector to given value
        this.color = color;
    }

    public CelestialBody(CelestialBody body, Vector3 position, Vector3 velocity) {
        this.name = body.name; // copy parameters from body
        this.mass = body.mass;
        this.radius = body.radius;
        this.position = position; // set position directly as vector
        this.velocity = velocity; // set velocity vector to given value
        this.color = body.color;
    }

    //Object Methods:

    /**
     * Returns the distance between this celestial body and the specified 'body'.
     * @param body body for distance to measured to
     * @return absolute positive distance between celestial bodies
     */
    public double distanceTo(CelestialBody body) {
        return this.position.distanceTo(body.position);
    }

    /**
     * Returns a vector representing the gravitational force exerted by 'body' on this celestial body
     * @param body celestial body which has gravitational force on this body
     * @return gravitational force between bodies
     */
    public Vector3 gravitationalForce(CelestialBody body) {
        Vector3 direction = body.position.minus(this.position);
        double r = this.distanceTo(body);  // get distance between celestial bodies
        if (r == 0) return new Vector3();
        double force = Simulation.G * this.mass * body.mass / (r * r); // Fg = (G*m1*m2) / r^2 [Newton]
        direction.normalize(); // get norm vector in direction of Fg
        return direction.times(force);
    }

    /**
     * Moves this body to a new position, according to the specified force vector 'force' exerted
     * on it, and updates the current movement accordingly.
     * (Movement depends on the mass of this body, its current movement and the exerted force.)
     * @param force is the acceleration vector exerted to the this body
     */
    public void move(Vector3 force) {
        Vector3 currentAcceleration = force.times(1/mass); // F = m*a --> a = F/m
        velocity = velocity.plus(currentAcceleration); // change velocity for given acceleration
        position = position.plus(velocity); // change position of body due to new velocity
    }

    /**
     * Draws the celestial body to the current StdDraw canvas as a dot using 'color' of this body.
     * The radius of the dot is in relation to the radius of the celestial body
     * (use a conversion based on the logarithm as in 'Simulation.java').
     */
    public void draw() {
        position.drawAsDot(1e9*Math.log10(this.radius), this.color);
        // use log10 because of large variation of radius.
    }

    /**
     * passes the name of the celestial body
     * @return name of body as string
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a string with the information about this celestial body including
     * name, mass, radius, position and current movement. Example:
     * "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
     * @return string in set format
     */
    @Override
    public String toString() {
        return "{"+name+"} "+mass+" kg, radius: "+radius+" m, at "+position+", (m/s): "+velocity;
    }

    /**
     * Two CelestialBodies are equals when they have the same name stored as a string.
     * @param o Object to compare with 'this' (CelestialBody)
     * @return true if they are equals as described as above, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != CelestialBody.class) return false;
        CelestialBody cb = (CelestialBody) o;
        return this.name.equals(cb.name);
    }/**/

    /**
     * Creates a hashcode based on the body name string
     * @return int hashcode
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
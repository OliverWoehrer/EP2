import java.awt.*;

// This class represents celestial bodies like stars, planets, asteroids, etc..
public class CelestialBody {

    //TODO: change modifiers.
    public String name;
    public double mass;
    public double radius;
    public Vector3 position; // position of the center.
    public Vector3 currentMovement;
    public Color color; // for drawing the body.

    //TODO: define constructor.

    // Returns the distance between this celestial body and the specified 'body'.
    public double distanceTo(CelestialBody body) {

        //TODO: implement method.
        return 0;
    }

    // Returns a vector representing the gravitational force exerted by 'body' on this celestial body.
    public Vector3 gravitationalForce(CelestialBody body) {

        //TODO: implement method.
        return null;
    }

    // Moves this body to a new position, according to the specified force vector 'force' exerted
    // on it, and updates the current movement accordingly.
    // (Movement depends on the mass of this body, its current movement and the exerted force.)
    public void move(Vector3 force) {

        //TODO: implement method.
    }

    // Returns a string with the information about this celestial body including
    // name, mass, radius, position and current movement. Example:
    // "Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s."
    public String toString() {

        //TODO: implement method.
        return "";
    }

    // Prints the information about this celestial body including
    // name, mass, radius, position and current movement, to the console (without newline).
    // Earth, 5.972E24 kg, radius: 6371000.0 m, position: [1.48E11,0.0,0.0] m, movement: [0.0,29290.0,0.0] m/s.
    public void print() {

        //TODO: implement method.
    }

    // Draws the celestial body to the current StdDraw canvas as a dot using 'color' of this body.
    // The radius of the dot is in relation to the radius of the celestial body
    // (use a conversion based on the logarithm as in 'Simulation.java').
    public void draw() {

        //TODO: implement method.
    }

}


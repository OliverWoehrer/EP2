// A collection of celestial bodies.
public interface CelestialBodyCollection extends CelestialBodyIterable {

    // Adds 'body' to the collection of bodies. The method returns 'true' if the collection was
    // changed as a result of the call. If adding has not changed the collection for some reason,
    // the method returns 'false'.
    boolean add(CelestialBody body);

    // Returns the number of celestial bodies of this collection.
    int size();
}

/**
 * Represents an index of celestial systems. The key of the index is a single celestial body and
 * the associated value is the celestial system to which the celestial body belongs.
 * For example, Io belongs to the system (Jupiter, Io, Europa, Ganymede, Kallisto).
 */
public interface CelestialSystemIndex {
    /**
     * Adds a system of bodies to the index. Adding a system adds multiple (key, value) pairs to the index,
     * one for each body of the system, with the same value, i.e., reference to the celestial system.
     * An attempt to add a system with a body that already exists in the index leaves the index unchanged and
     * the returned value would be 'false'. The method returns 'true' if the index was changed as a
     * result of the call and 'false' otherwise.
     */
    boolean add(CelestialSystem system);

    /**
     * Returns the celestial system with which a body is associated.
     * If body is not contained as a key, 'null' is returned
     */
    CelestialSystem get(CelestialBody body);

    /**
     * Returns 'true' if the specified 'body' is listed in the index.
     */
    boolean contains(CelestialBody body);

    /**
     * Returns 'true' if 'o' is of the same class as 'this' and 'this' and 'o' contain an equal set
     * of (key, value) pairs, i.e. each (key, value) pair of 'this' is contained (i.e. has an equal counterpart)
     * in 'o' and vice versa. Two (key, value) pairs are equal if they have equal keys and equal values.
     */
    boolean equals(Object o);
}

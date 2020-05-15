import java.util.Iterator;

public interface CelestialBodyIterator extends Iterator<CelestialBody> {

    // Returns 'true' if the iteration has more elements.
    boolean hasNext();

    // Returns the next element in the iteration.
    CelestialBody next();

}

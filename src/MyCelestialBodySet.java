import java.util.Collection;
import java.util.Iterator;

/**
 * This class implements a new view onto the index tree in CelestialSystemIndexTreeVariantC
 */
public class MyCelestialBodySet implements CelestialBodyCollection {
    private CelestialSystemIndexTreeVariantC tree;

    //Constructors:

    public MyCelestialBodySet(CelestialSystemIndexTreeVariantC tree) {
        this.tree = tree;
    }

    //Object Methods:

    /**
     * As 'this' is only a view onto a index tree, no elements are added
     * @return false, as the tree is not changed
     */
    public boolean add(CelestialBody body) {
        return false;
    }

    /**
     * This method uses the iterator of the original tree to count the number of bodies
     * @return Number of celestial bodies in index tree
     */
    public int size() {
        int count = 0;
        for (CelestialBody body : tree) {
            count++;
        }
        return count;
    }

    /**
     * As 'this' is only a view onto the index tree, the iterator of the tree itself is used
     * @return Iterator to iterate over the original index tree
     */
    @Override
    public CelestialBodyIterator iterator() {
        return tree.iterator();
    }

    /**
     * Returns a string representation of the index tree viewed as a celestial body set
     * @return String representing the original tree
     */
    @Override
    public String toString() {
        return tree.toString();
    }
}

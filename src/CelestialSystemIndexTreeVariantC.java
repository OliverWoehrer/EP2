//TODO: change class definition below according to specification in 'Aufgabenblatt6'.

import java.util.Iterator;

public class CelestialSystemIndexTreeVariantC implements CelestialSystemIndex, CelestialBodyIterable {
    private VariantCNode root;
    private CelestialBodyComparator comparator;

    // Initialises this index with a 'comparator' for sorting
    // the keys of this index.
    public CelestialSystemIndexTreeVariantC(CelestialBodyComparator comparator) {
        this.comparator = comparator;
    }

    /*
     * Adds a system of bodies to the index.
     * Adding a system adds multiple (key, value) pairs to the
     * index, one for each body of the system, with the same
     * value, i.e., reference to the celestial system.
     * An attempt to add a system with a body that already exists
     * in the index leaves the index unchanged and the returned
     * value would be 'false'.
     * The method returns 'true' if the index was changed as a
     * result of the call and 'false' otherwise.
     */
    public boolean add(CelestialSystem system) {
        if (system == null || system.size() == 0) return false;
        boolean changed = false;
        if (system == null || system.size() == 0) return false; // do not add empty system
        if (root == null) { // empty root, add first element
            root = new VariantCNode(system.get(0), system, null, null, comparator);
            changed = true;
        } else { // add first element
            changed = root.add(new VariantCNode(system.get(0), system, null, null, comparator));
        }
        /*for (int i = 0; i < system.size(); i++) {
            if (root.get(system.get(i)) != null) return false;  // duplicate found
        }/**/
        for (int i = 1; i < system.size(); i++) {
            changed |= root.add(new VariantCNode(system.get(i), system, null, null, comparator));
        }
        return changed;
    }

    /**
     * Returns the celestial system with which a body is
     * associated. If body is not contained as a key, 'null'
     * is returned.
     * @param body Body used as key to find system
     * @return Celestial system associated to the given key
     */
    public CelestialSystem get(CelestialBody body) {
        if (root == null) {
            return null;
        } else {
            return root.get(body);
        }
    }

    /**
     * Returns 'true' if the specified 'body' is listed
     * in the index.
     * @param body
     * @return
     */
    public boolean contains(CelestialBody body) {
        return get(body) != null;
    }



    // Returns a collection view of all entries of this index.
    public CelestialBodyCollection bodies() {
        //TODO: implement method.
        return null;

    }

    // Returns all entries of this as a new collection.
    public CelestialSystem bodiesAsCelestialSystem() {
        //TODO: implement method.
        //return root.createCollection(new CelestialSystem("collection"));
        return null;
    }

    /**
     * Returns the comparator used in this index.
     * @return Comparator
     */
    public CelestialBodyComparator getComparator() {
        return comparator;
    }

    /**
     * Creates a iterator, implemented by the helper class MyVariantCNodeIter
     * @return Iterator for index tree
     */
    public MyVariantCNodeIter iterator() {
        return new MyVariantCNodeIter(root);
    }

    /**
     * Returns a readable representation with (key, value) pairs sorted by the key.
     * @return Combined string
     */
    @Override
    public String toString() {
        if (root == null) {
            return "empty.";
        } else {
            return "{" + root.toString() + "}";
        }
    }

}

class VariantCNode {
    private VariantCNode left;
    private VariantCNode right;
    private CelestialBody key;
    private CelestialSystem cs;
    private CelestialBodyComparator comparator;

    public VariantCNode(CelestialBody key, CelestialSystem cs, VariantCNode left, VariantCNode right,
                        CelestialBodyComparator comparator) {
        this.key = key;
        this.cs = cs;
        this.left = left;
        this.right = right;
        this.comparator = comparator;

    }

    public boolean add(VariantCNode node) {
        if (node.key.equals(this.key)) return false; // duplicate found
        if (this.comparator.compare(this.key, node.key) > 0) {
            if (left == null) {
                left = node;
                return true;
            } else {
                return left.add(node);
            }
        } else {
            if (right == null) {
                right = node;
                return true;
            } else {
                return right.add(node);
            }
        }

    }

    public CelestialSystem get(CelestialBody body) {
        if (key.equals(body)) {
            return cs;
        }

        if (this.comparator.compare(this.key, body) > 0) {
            if (left == null) {
                return null;
            }
            return left.get(body);
        } else {
            if (right == null) {
                return null;
            }
            return right.get(body);
        }
    }

    /**
     * Gathers all celestial bodies of the given index tree into one new celestial system
     * as a collection.
     * @param collection
     * @return
     */
    //public CelestialSystem createCollection(CelestialSystem collection) {}

    /**
     * Getter method for the body key
     * @return Celestial body held as key in 'this'
     */
    public CelestialBody getBody() {
        return this.key;
    }

    /**
     * Creates a representation of the tree as a string
     * @return Combined string, representing the tree
     */
    @Override
    public String toString() {
        String ret = "";
        ret += left == null ? "" : left.toString();
        ret += "{"+key+":"+cs.getName()+"}\r\n";
        ret += right == null ? "" : right.toString();
        return ret;
    }

}

/**
 * This class implements the iterator for the index tree
 */
class MyVariantCNodeIter implements CelestialBodyIterator {
    VariantCNode root;

    //Constructors:

    public MyVariantCNodeIter(VariantCNode root) {
        this.root = root;
    }

    //Object Methods:

    public boolean hasNext() {
        return root != null;
    }

    public CelestialBody next() {
        //TODO: über baum iterieren, Skript S.97-99
        /*
        if (root == null) {
            return null;
        } else {
            CelestialBody ret = root.getBody();
            root = root.getNext();
            return ret;
        }/**/
        return null;
    }
}
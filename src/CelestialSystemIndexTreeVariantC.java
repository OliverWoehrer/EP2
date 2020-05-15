//TODO: change class definition below according to specification in 'Aufgabenblatt6'.

public class CelestialSystemIndexTreeVariantC implements CelestialSystemIndex {

    private VariantCNode root;
    private CelestialBodyComparator comparator;

    // Initialises this index with a 'comparator' for sorting
    // the keys of this index.
    public CelestialSystemIndexTreeVariantC(CelestialBodyComparator comparator) {
        this.comparator = comparator;
    }

    // Adds a system of bodies to the index.
    // Adding a system adds multiple (key, value) pairs to the
    // index, one for each body of the system, with the same
    // value, i.e., reference to the celestial system.
    // An attempt to add a system with a body that already exists
    // in the index leaves the index unchanged and the returned
    // value would be 'false'.
    // The method returns 'true' if the index was changed as a
    // result of the call and 'false' otherwise.
    public boolean add(CelestialSystem system) {

        if (system == null || system.size() == 0) {
            return false;
        }

        boolean changed = false;

        if (root == null) {
            root = new VariantCNode(system.get(0), system, null, null, comparator);
            changed = true;
        } else {
            changed = root.add(new VariantCNode(system.get(0), system, null, null, comparator));
        }

        for (int i = 1; i < system.size(); i++) {
            changed |= root.add(new VariantCNode(system.get(i), system, null, null, comparator));
        }

        return changed;

    }

    // Returns the celestial system with which a body is
    // associated. If body is not contained as a key, 'null'
    // is returned.
    public CelestialSystem get(CelestialBody body) {
        if (root == null) {
            return null;
        }

        return root.get(body);

    }

    // Returns 'true' if the specified 'body' is listed
    // in the index.
    public boolean contains(CelestialBody body) {
        return get(body) != null;
    }

    // Returns a readable representation with (key, value) pairs sorted by the key.
    public String toString() {
        if (root == null) {
            return "{}";
        }

        return "{" + root.toString() + "}";
    }

    // Returns a collection view of all entries of this index.
    public CelestialBodyCollection bodies() {
        //TODO: implement method.
        return null;

    }

    // Returns all entries of this as a new collection.
    public CelestialSystem bodiesAsCelestialSystem() {
        //TODO: implement method.
        return null;

    }

    // Returns the comparator used in this index.
    public CelestialBodyComparator getComparator() {
        return comparator;
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
        if (node.key.equals(this.key)) {
            return false;
        }

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

    public String toString() {
        String result;
        result = left == null ? "" : left.toString();
        result += result.isEmpty() ? "" : ",\n";
        result += this.key + " belongs to " + this.cs;
        result += right == null ? "" : ",\n" + right.toString();
        return result;

    }

}



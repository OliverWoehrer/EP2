//TODO: change class definition below according to specification in 'Aufgabenblatt6'.

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
     * @param body Celestial body to check for
     * @return true, if body was found in 'this' index tree
     */
    public boolean contains(CelestialBody body) {
        return get(body) != null;
    }

    /**
     * Returns a collection view of all entries of this index.
     * @return Collection as a view onto 'this' index tree
     */
    public CelestialBodyCollection bodies() {
        return new MyCelestialBodySet(this);
    }

    /**
     * Returns all entries of this as a new collection.
     * @return Celestial system holding all bodies from index tree
     */
    public CelestialSystem bodiesAsCelestialSystem() {
        CelestialSystem collection = new CelestialSystem("Colletion");
        for (CelestialBody body : this) {
            collection.add(body);
        }
        return collection;
    }

    /**
     * Returns the comparator used in this index.
     * @return Comparator
     */
    public CelestialBodyComparator getComparator() {
        return comparator;
    }

    /**
     * Creates an iterator, implemented by the helper class MyVariantCNodeIter
     * @return Iterator for index tree
     */
    public MyITIter iterator() {
        MyITIter iter = new MyITIter();
        if (root != null) {
            root.iter(iter, false);
        }
        return iter;

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
            return "=== Keys =====>\r\n" + root.toString() + "==============>";
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
     * Getter method for the body key
     * @return Celestial body held as key in 'this'
     */
    public CelestialBody getBody() {
        return this.key;
    }

    /**
     * Method works with iterator, transforms the tree into a ordered linear list
     * @param iter iterator
     * @param isNotRoot false, in case to root is used
     * @return Current celestial body
     */
    public CelestialBody iter(MyITIter iter, boolean isNotRoot) {
            VariantCNode node = isNotRoot ? right : this;
            while (node != null) {
                new MyITIter(node, iter);
                node = node.left;
            }
            return key; // return reference of celestial body held by this node
    }

    /**
     * Creates a representation of the tree as a string
     * @return Combined string, representing the tree
     */
    public String toString() {
        String ret = "";
        ret += left == null ? "" : left.toString();
        ret += "{"+key+":"+cs.getName()+"}\r\n";
        ret += right == null ? "" : right.toString();
        return ret;
    }

}

/**
 * This class implements the iterator for the index tree: Index Tree Iterator
 */
class MyITIter implements CelestialBodyIterator {
    private VariantCNode node;
    private MyITIter parent;

    //Constructors:

    public MyITIter() {}

    public MyITIter(VariantCNode node, MyITIter parent) {
        this.node = parent.node;
        parent.node = node;
        this.parent = parent.parent;
        parent.parent = this;
    }

    //Object Methods:

    public boolean hasNext() {
        return node != null;
    }

    public CelestialBody next() {
        if (node == null) { return null; }
        VariantCNode todo = node;
        node = parent.node;
        parent = parent.parent;
        return todo.iter(this , true);
    }
}

//TODO: Zusatzfragen:
/*
(1) Die Objekte aus der View erzeugt durch bodies() werden bewegt, da es sich hier nur um eine andere
Sichtweise auf die Körper aus dem index-tree handelt. Sprich die Reference zeigen weiterhin auf die
Originale und bewegen somit auch die urspürunglichen Körper

(2) Der Iterator durchmustert immer den gesamten Baum in In-Order, das heißt wenn Objekte im Baum
geändert werden, iteriert der Iterator auch über die geänderten Objekte
 */
import java.util.Objects;

/**
 * This is a variant of the original CelestialSystemIndexTree class, which is adapted to
 * implement the CelestialSystemIndex interface alongside with the CelestialSystemIndexMap class
 */
public class CelestialSystemIndexTreeVariant implements CelestialSystemIndex {
    //Object Variables:
    private MyVTreeNode root = new MyVTreeNode();
    private int numberOfSystems = 0;

    /**
     * Adds a system of bodies to the tree. Since the keys of the tree are the names of bodies,
     * adding a system adds multiple (key, value) pairs to the tree, one for each body of the
     * system, with the same value, i.e., reference to the celestial system.
     * An attempt to add a system with a body that already exists in the tree
     * leaves the tree unchanged and the returned value would be 'false'.
     * For example, it is not allowed to have a system ("Earth", "Moon") and a system ("Jupiter",
     * "Moon") indexed by the tree, since "Moon" is not unique.
     * The method returns 'true' if the tree was changed as a result of the call and
     * 'false' otherwise.
     * Adds all the names of the celestial bodies of the given celestial system into the binary search tree.
     * @param system system holding the bodies
     * @return true if all bodies were added successfully, false if a body is a duplicate
     */
    public boolean add(CelestialSystem system) {
        int systemSize = system.size();
        if (systemSize == 0) return false; // do not add empty system
        for (int i = 0; i < systemSize; i++) {
            if (root.contains(system.get(i).getName())) return false;  // duplicate found
        }

        numberOfSystems++; // add into binary search tree
        for (int i = 0; i < systemSize; i++) {
            root.add(system.get(i), system); // key=body name, value=celestial system
        }
        return true;
    }

    /**
     * Returns the celestial system with which a body is associated. Is a wrapper for
     * the recursive get(String) method which iterates the tree recursively till a matching key is
     * found (matching body name). If body is not contained as a key, 'null' is returned.
     * @param body Body to check for
     * @return CelestialSystem matching to the body-name
     */
    public CelestialSystem get(CelestialBody body) {
        if (root == null) {
            return null;
        }
        return root.getSystem(body.getName());
    }

    /**
     * Returns 'true' if the specified 'body' is listed in the index. Checks the
     * return value of get(String) to see if a matching system was found
     * @param body Body with name to be checked for
     * @return true, if a matching body-name was found, false otherwise
     */
    public boolean contains(CelestialBody body) {
        return get(body) != null;
    }

    /**
     * Returns the overall number of bodies indexed by the tree.
     * @return total number of bodies indexed by tree
     */
    private int numberOfBodies() {
        if (root.getBodyName() != null) {
            return root.numberOfBodies();
        } else {
            return 0;
        }
    }

    /**
     * Creates a representation of the tree as a string
     * @return String with all keys and matching values
     */
    @Override
    public String toString() {
        return root.getBodyName() == null ? "empty." : root.toString();
    }

    /**
     * Two trees are the same when they hold the same bodies with the matching values
     * @param o Object to be compared to
     * @return true if both objects are the same according to the description above
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CelestialSystemIndexTreeVariant tree = (CelestialSystemIndexTreeVariant) o;
        if (this.numberOfBodies() != tree.numberOfBodies()) return false;
        return root.equals(tree.root);
    }

    @Override
    public int hashCode() {
        int bias = 17 * numberOfBodies();
        return bias * root.hashCode();
    }
}

/**
 * This helper class implements internal tree nodes used by CelestialSystemIndex class.
 */
class MyVTreeNode {
    //Object Variables:
    private String bodyName; // name of celestial body
    private CelestialSystem system; // reference of the celestial system holding the body
    private MyVTreeNode left, right; // references of the children tree nodes

    //Constructors:

    public MyVTreeNode() {}

    private MyVTreeNode(CelestialBody body, CelestialSystem system) {
        this.bodyName = body.getName();
        this.system = system;
    }

    //Object Methods:

    /**
     * Inserts the celestial system according to the key body name
     * @param body body name
     * @param system celestial system containing the given body
     */
    public void add(CelestialBody body, CelestialSystem system) {
        if (this.bodyName != null) {
            if (body.getName().compareTo(this.bodyName) < 0) {
                if (left != null) {
                    left.add(body, system);
                } else left = new MyVTreeNode(body, system);
            } else {
                if (right != null) {
                    right.add(body, system);
                } else right = new MyVTreeNode(body, system);
            }
        } else {
            this.bodyName = body.getName();
            this.system = system;
        }
    }

    /**
     * Returns the celestial system holding the body with the given name.
     * Assert that root is a non-empty tree.
     * @param bodyName Name of body to look for
     * @return Reference of celestial system containing the body, null otherwise
     */
    public CelestialSystem getSystem(String bodyName) {
        int cmp = bodyName.compareTo(this.bodyName);
        if (cmp == 0) {
            return this.system;
        } else if (cmp < 0 && left != null) {
            return left.getSystem(bodyName);
        } else if (cmp > 0 && right != null) {
            return right.getSystem(bodyName);
        } else { // no matching body-key found
            return null;
        }
    }

    /**
     * Checks if a body with the same as the given one is already in binary search tree
     * @param bodyName name of body checked for
     * @return true if tree already contains body with same name
     */
    public boolean contains(String bodyName) {
        if (this.bodyName != null) { // internal tree node
            int cmp = bodyName.compareTo(this.bodyName);
            if (cmp == 0) {
                return true; // found duplicate in 'this' internal node
            } else if (cmp < 0 && left != null) {
                return left.contains(bodyName);
            } else if (cmp > 0 && right != null) {
                return right.contains(bodyName);
            } else {
                return false;
            }
        } else { // empty tree
            return false;
        }
    }

    /**
     * Returns the number of bodies held by the node itself and both children subtrees combined
     * @return number of bodies
     */
    public int numberOfBodies() {
        int totalNumberOfBodies = 1;
        if (left != null) { totalNumberOfBodies += left.numberOfBodies(); }
        if (right != null) { totalNumberOfBodies += right.numberOfBodies(); }
        return totalNumberOfBodies;
    }

    /**
     * Getter Method for search key body name
     * @return name of the celestial body
     */
    public String getBodyName() {
        return this.bodyName;
    }

    /**
     * Prints the root and the whole following subtree in order
     * @return String representation
     */
    @Override
    public String toString() {
        String ret = "";
        if (left != null) ret += left.toString();
        ret += "{"+bodyName+":"+system.getName()+"}\r\n";
        if (right != null) ret += right.toString();
        return ret;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == null || getClass() != o.getClass()) return false;
        MyVTreeNode tn = (MyVTreeNode) o;
        if (!tn.contains(bodyName)) return false;
        boolean ret = true;
        if (left != null) ret &= left.equals(tn);
        if (right != null) ret &= right.equals(tn);
        return ret;
    }

    @Override
    public int hashCode() {
        int ret = (bodyName.hashCode() * system.hashCode());
        if (left != null) ret += left.hashCode();
        if (right != null) ret += right.hashCode();
        return ret;
    }
}

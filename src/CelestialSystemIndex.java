/**
 * Implements a binary search tree for celestial bodies.
 * An internal tree node having at maximum two children which are references to two subtrees and does
 * not hold a key body reference. The left child always holds the lexicographically smaller key (body name)
 * A leaf node holds a body reference and has all it's child references set to null
 */
public class CelestialSystemIndex {
    //TODO: Define variables and constructor.
    //Object Variables:
    private MyBinaryTreeNode binaryTree = new MyBinaryTreeNode();
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
        //TODO: implement method.
        //Check for duplicate body names:
        boolean ret = false;
        for (int i = 0; i < system.size(); i++) {
            ret |= binaryTree.contains(system.get(i).getName());
        }
        if (ret) { return false; } // --> duplicate found, do not add system into search tree

        //Add into binary search tree:
        numberOfSystems++;
        for (int i = 0; i < system.size(); i++) {
            binaryTree.add(system.get(i), system); // key=body name, value=celestial system
        }
        return true;
    }

    /**
     * Returns the celestial system in which a body with the specified name exists.
     * For example, if the specified name is "Europa", the system of Jupiter (Jupiter, Io,
     * Europa, Ganymed, Kallisto) will be returned.
     * If no such system is found, 'null' is returned.
     * @param name name of the body
     * @return reference of the celestial system containing the body, otherwise null
     */
    public CelestialSystem get(String name) {
        //TODO: implement method.
        if (binaryTree.contains(name)) {
            return binaryTree.getSystem(name);
        } else return null; // no matching body found
    }

    /**
     * Returns the overall number of bodies indexed by the tree.
     * @return total number of bodies indexed by tree
     */
    public int numberOfBodies() {
        //TODO: implement method.
        if (binaryTree.getBodyName() != null) {
            return binaryTree.numberOfBodies();
        } else {
            return 0;
        }
    }

    /**
     * Returns the overall number of systems indexed by the tree.
     * @return total number of celestial systems indexed by tree
     */
    public int numberOfSystems() {
        //TODO: implement method.
        return this.numberOfSystems;
    }

    //TODO: Define additional class(es) implementing a binary tree (either here or outside class).


}

class MyBinaryTreeNode {
    //Object Variables:
    private String bodyName; // name of celestial body
    private CelestialSystem system; // reference of the celestial system holding the body
    private MyBinaryTreeNode left, right; // references of the children tree nodes

    //Constructors:

    public MyBinaryTreeNode() {}

    private MyBinaryTreeNode(CelestialBody body, CelestialSystem system) {
        this.bodyName = body.getName();
        this.system = system;
    }

    //Object Methods:

    /**
     * Checks if a body with the same as the given one is already in binary search tree
     * @param bodyName name of body checked for
     * @return true if tree already contains body with same name
     */
    public boolean contains(String bodyName) {
        boolean ret;
        if (this.bodyName != null) {
            int cmp = bodyName.compareTo(this.bodyName);
            if (cmp == 0) {
                return true;
            } else if (cmp < 0 && left != null) {
                ret = left.contains(bodyName);
                return ret;
            } else if (cmp > 0 && right != null) {
                ret = right.contains(bodyName);
                return ret;
            } else {
                return false;
            }
        } else { // empty tree
            return false;
        }
    }

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
                } else left = new MyBinaryTreeNode(body, system);
            } else {
                if (right != null) {
                    right.add(body, system);
                } else right = new MyBinaryTreeNode(body, system);
            }
        } else {
            this.bodyName = body.getName();
            this.system = system;
        }
    }

    /**
     * Returns the celestial system holding the body with the given name
     * @param bodyName name of body to look for
     * @return reference of celestial system containing the body, null otherwise
     */
    public CelestialSystem getSystem(String bodyName) {
        int cmp = bodyName.compareTo(this.bodyName);
        if (cmp < 0) {
            return left.getSystem(bodyName);
        } else if (cmp > 0) {
            return right.getSystem(bodyName);
        } else { // body name found
            return this.system;
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
}
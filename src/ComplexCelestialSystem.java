public class ComplexCelestialSystem {
    //Object Variables:
    private String name;
    private MyCCSListNode head, tail;

    //Constructors:

    /**
     * Initializes this system as an empty system with a name
     * @param name name of the complex list
     */
    public ComplexCelestialSystem(String name) {
        this.name = name; // set name of complex list
        head = tail = null;
    }

    //Object Methods:

    /**
     * Adds a subsystem of bodies to this system if there are no bodies in the subsystem
     * with the same name as a body or subsystem of this system.
     * The method returns 'true' if the collection was changed as a result of the call and
     * 'false' otherwise.
     * @param subsystem linked list (=subsystem) to be added
     * @return 'true' if the list was changed as a result of the call and 'false' otherwise
     */
    public boolean add(CelestialSystem subsystem) {
        if (subsystem == null) return false;
        if (head == null) { // empty list
            head = tail = new MyCCSListNode(subsystem, null, null);
            return true;
        }
        return (tail = head.add(subsystem)) != null;
    }

    /**
     * Checks if any of the subsystems contains a body with the given name or has the same name
     * as the given body itself.
     * @param bodyName name of the given body
     * @return true if any subsystem contains a name duplicate, false otherwise
     */
    public boolean contains(String bodyName) {
        if (head == null) {
            return false;
        }
        return head.get(bodyName) != null;
    }

    /**
     * Returns the single body or subsystem with 'name' or 'null' if no such body or subsystem
     * exists in this system. In case of a single body, the body is returned as a subsystem of
     * one body, with the same name as the body.
     * @param name name of subsystem or body to search for
     * @return reference of celestial system searched for, otherwise null-reference
     */
    public CelestialSystem get(String name) {
        if (head == null) {
            return null;
        } else {
            return head.get(name);
        }
    }

    /**
     * Returns the subsystem with the index 'i'. The subsystem that was first added to the list has the
     * index 0, the subsystem that was most recently added to the list has the index size()-1.
     * @param i index of subsystem to be returned
     * @return subsystem at index i, otherwise null-reference
     */
    public CelestialSystem get(int i) {
        if (head == null) {
            return null;
        } else {
            return head.get(i);
        }
    }

    /**
     * Returns the number of subsystem in this complex list.
     * @return length of linked list as integer
     */
    public int size() {
        if (head == null) {
            return 0;
        } else {
            return head.size();
        }
    }

    /**
     * Creates a readable representation of the list of complex systems
     * @return String representation
     */
    @Override
    public String toString() {
        String ret = "<=== {"+name+"} ===>\r\n";
        if (head == null) {
            return ret + "empty.\r\n";
        } else {
            return ret + head.toString() + "\r\n";
        }
    }
}

/**
 * This is a helper class for the ComplexCelestialSystem list. It implements a single list node
 */
class MyCCSListNode {
    //Object Variables:
    private CelestialSystem system;
    private MyCCSListNode next, prev;

    //Constructors:

    /**
     * initializes an item with the given celestial subsystem as an value entry
     * @param system celestial system object to be entry of list item
     */
    public MyCCSListNode(CelestialSystem system, MyCCSListNode next, MyCCSListNode prev) {
        this.system = system;
        this.next = next;
        this.prev = prev;
    }

    //Object Methods:

    /**
     * Adds a new CelestialSystem at the end of the linked-list.
     * Assert that is is a non-empty list.
     * @param system System to be added
     * @return Returns the reference of the newly added node
     */
    public MyCCSListNode add(CelestialSystem system) {
        for (int i = 0; i < system.size(); i++) { // check for duplicates in 'this' system
            if (this.system.contains(system.get(i).getName())) {
                return null;
            }
        }
        if (next == null) { // base case
            next = new MyCCSListNode(system, null, this);
            return next;
        } else { // recursive step
            return next.add(system);
        }
    }

    /**
     * Returns the single body or subsystem with 'name' or 'null' if no such body or subsystem
     * exists in this system. In case of a single body, the body is returned as a subsystem of
     * one body, with the same name as the body.
     * Assert that this is a npn-empty list.
     * @param name name of subsystem or body to search for
     * @return reference of celestial system searched for, otherwise null-reference
     */
    public CelestialSystem get(String name) {
        if (this.system.getName().equals(name)) { // check name of system itself
            return this.system;
        } else if (this.system.get(name) != null) { // check if system holds the name
            return new CelestialSystem(this.system.get(name).getName());
        } else if (next != null) { // recursive step
            return next.get(name);
        } else { // base case, no match found
            return null;
        }
    }

    /**
     * Returns the subsystem with the index 'i'. The subsystem that was first added to the list has the
     * index 0, the subsystem that was most recently added to the list has the index size()-1.
     * @param i index of subsystem to be returned
     * @return subsystem at index i, otherwise null-reference
     */
    public CelestialSystem get(int i) {
        if (i == 0) { // return this index
            return this.system;
        } else if (next == null) { // end of list
            return null;
        } else { // continue to search
            return next.get(i-1);
        }
    }

    /**
     * Returns the number of subsystem in this complex list.
     * @return length of linked list as integer
     */
    public int size() {
        if (next == null) { // end of list
            return 1;
        } else { // continue to count
            return 1 + next.size();
        }
    }

    @Override
    public String toString() {
        if (next == null) { // last list item
            return system.toString();
        } else { // continue list
            return system.toString()+"\r\n"+next.toString();
        }
    }
}
import java.util.Objects;

/**
 * Implements a linked list of CelestialBody objects. The object reference itself is held by a list node
 * besides a reference to the next list item. Celestial bodies with duplicate names inside this list,
 * are not allowed. Duplicates are prevented on adding new bodies.
 */
public class CelestialSystem {
    private String nameOfSystem;
    private MyCSListNode head, tail;

    //Constructors:

    /**
     * Initializes this system as an empty system with a name.
     * @param name name of the linked list
     */
    public CelestialSystem(String name) {
        //TODO: implement constructor.
        this.nameOfSystem = name; // set name of whole system
        head = tail = null;
    }

    //Object Methods:

    /**
     * Adds 'body' to the list of bodies of the system if the list does not already contain a
     * body with the same name as 'body', otherwise does not change the object state.
     * @param body body object to be added
     * @return 'true' if the list was changed as a result of the call and 'false' otherwise
     */
    public boolean add(CelestialBody body) {
        if (head == null) { // empty list
            head = tail = new MyCSListNode(body, null, null);
            return true;
        } else {
            return (tail = head.add(body)) != null;
        }
    }

    /**
     * Inserts the specified 'body' at the specified position in this list if 'i' is a valid index and
     * there is no body in the list with the same name as that of 'body'.
     * Shifts the element currently at that position (if any) and any subsequent elements to
     * the right (adds one to their indices). The first element of the list has the index 0.
     * @param i index the body should be added at. The first valid index is 0, the maximum index is size()-1
     * @param body body to be added to list
     * @return 'true' if the list was changed, 'false' otherwise.
     */
    public boolean add(int i, CelestialBody body) {
        if (this.get(body.getName()) != null) {
            return false; // duplicate found
        }
        if (i == 0) { // add at beginning of list
            head = new MyCSListNode(body, head, null);
            return true;
        }
        if (head == null) { // invalid index, empty list
            return false;
        }
        return head.add(i, body);
    }

    /**
     * Checks if a body with the same name as the given one is already in this celestial system
     * @param bodyName name of body to check for
     * @return true if celestial system contains body with same name, false otherwise
     */
    public boolean contains(String bodyName) {
        if (head == null) {
            return false;
        } else {
            return get(bodyName) != null;
        }
    }

    /**
     * Returns a new list that contains the same elements as 'this' list in reverse order.
     * The list 'this' is not changed and bodies are not duplicated (shallow copy).
     * @return reference of new reversed list
     */
    public CelestialSystem reverse() {
        CelestialSystem reversedSystem = new CelestialSystem("reversed "+this.nameOfSystem);
        if (tail !=  null) { // non-empty list
            reversedSystem.head = this.tail.reverse();
        }
        return reversedSystem;
    }

    /**
     * Returns the 'body' with the index 'i'. The body that was first added to the list has the
     * index 0, the body that was most recently added to the list has the index size()-1.
     * @param i index of body to be returned
     * @return body at index i, otherwise null-reference
     */
    public CelestialBody get(int i) {
        if (head == null) {
            return null;
        }
        return head.get(i);
    }

    /**
     * Returns the body with the specified name or 'null' if no such body exits in the list.
     * @param name name of body to be returned
     * @return reference of body with given name, otherwise null-reference
     */
    public CelestialBody get(String name) {
        if (head == null) {
            return null;
        }
        return head.get(name);
    }

    /**
     * Returns the number of bodies in this list.
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
     * Getter Method for name of celestial system
     * @return name of celestial system as string
     */
    public String getName() {
        return nameOfSystem;
    }

    /**
     * Returns a readable representation with the name of the system and all bodies in respective order of the list.
     * @return formatted string with names of all bodies
     */
    @Override
    public String toString() {
        String ret = "["+this.nameOfSystem+"]: ";
        if (head == null) {
            return ret + "empty.";
        } else {
            return ret + head.toString() + ".";
        }
    }

    /**
     * Two CelestialSystems are equals, when they contain the same bodies
     * @param o Object to be check with 'this' (CelestialSystem)
     * @return true, if both systems are the same according to the description
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CelestialSystem cs = (CelestialSystem) o;
        for (int i = 0; i < this.size(); i++) {
            CelestialBody temp = this.get(i);
            CelestialBody temp2 = cs.get(temp.getName());
            if (!temp.equals(temp2)) {
                return false;
            }



            /*if(!cs.contains(this.get(i).getName())) {
                return false;
            }/**/
        }
        return true;
    }

    @Override
    public int hashCode() {
        int ret = 0, bias = 27 * this.size(); // create bias without considering order of list
        for (int i = 0; i < this.size(); i++) {
            ret += bias * this.get(i).hashCode();
        }
        return ret;
    }
}

/**
 * This helper class implements internal list nodes used by CelestialSystem class
 */
class MyCSListNode {
    //Object Variables:
    private CelestialBody body;
    private MyCSListNode next, prev;

    //Constructors:

    public MyCSListNode(CelestialBody body, MyCSListNode next, MyCSListNode prev) {
        this.body = body;
        this.next = next;
        this.prev = prev;
    }

    //Object Methods:

    /**
     * Adds a new CelestialBody at the end of list.
     * Assert that this is a non-empty list.
     * @param body body to be added
     * @return Returns the reference of the newly added node
     */
    public MyCSListNode add(CelestialBody body) {
        if (body.getName().equals(this.body.getName())) {
            return null; // duplicate found
        } else if (next == null) {
            next = new MyCSListNode(body, null, this);
            return next; // add at end of list
        } else {
            return next.add(body);
        }
    }

    /**
     * Adds a new body into list at index i. Pre-condition is a valid index.
     * @param i index the body should be added at
     * @param body body to at to list
     * @return true is added successfully
     */
    public boolean add(int i, CelestialBody body) {
        if(i == 0) { // add new body here
            this.prev = this.prev.next = new MyCSListNode(body, this, this.prev);
            return true;
        }
        if (next != null) { // traverse recursively till index
            return next.add(i-1, body);
        }
        if (i == 1) { // add as next list node
            return add(body) != null;
        }
        return false;
    }

    /**
     * Creates a new CelestialSystem but in reversed order of 'this' list. The original list
     * is unchanged. Assert that 'this' is a non-empty list.
     * @return Reference for the reversed CelestialSystem
     */
    public MyCSListNode reverse() {
        MyCSListNode prev = this.prev == null ? null : this.prev.reverse();
        return new MyCSListNode(this.body, prev, null);
    }

    /**
     * Checks if a body with the same name is already in this non-empty celestial system.
     * In case the body is found the reference gets returned, else 'null'
     * @param bodyName name of body to check for
     * @return true if celestial system contains body with same name, false otherwise
     */
    public CelestialBody get(String bodyName) {
        if (this.body.getName().equals(bodyName)) {
            return this.body;
        }
        if (next != null) {
            return next.get(bodyName);
        }
        return null; // base case
    }

    /**
     * Returns the reference of the celestial body at index i, Pre-Condition is a non-empty list
     * @param i index of body to be returned
     * @return body reference for valid index i, 'null' otherwise
     */
    public CelestialBody get(int i) {
        if (i == 0) { // return this body at index
            return body;
        } else if (next == null) { // base case
            return null;
        } else { // continue to search
            return next.get(i-1);
        }
    }

    /**
     * Looks for the length of the list by traversing through
     * @return Number of CelestialBodies in list
     */
    public int size() {
        if (next == null) return 1;
        return 1 + next.size();
    }

    /**
     * Returns a string listing all bodies in respective order of the list.
     * Assert that the list is non-empty!
     * @return formatted string with names of all bodies
     */
    @Override
    public String toString(){
        if (next != null) { // recursive step
            return this.body.getName() + ", " + next.toString();
        } else { // base case
            return this.body.getName();
        }
    }
}
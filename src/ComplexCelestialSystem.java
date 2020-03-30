public class ComplexCelestialSystem {
    //Object Variables:
    //TODO: Define variables.
    private String name;
    private ComplexListItem head, last;

    /**
     * Initializes this system as an empty system with a name
     * @param name name of the complex list
     */
    public ComplexCelestialSystem(String name) {
        //TODO: implement constructor.
        this.name = name; // set name of complex list
        head = last = null;
    }

    /**
     * Adds a subsystem of bodies to this system if there are no bodies in the subsystem
     * with the same name as a body or subsystem of this system.
     * The method returns 'true' if the collection was changed as a result of the call and
     * 'false' otherwise.
     * @param subsystem linked list (=subsystem) to be added
     * @return 'true' if the list was changed as a result of the call and 'false' otherwise
     */
    public boolean add(CelestialSystem subsystem) {
        //TODO: implement method.
        ComplexListItem item = head;
        //search for list entries with same name to prevent duplicates:
        while (item != null && !item.system().getName().equals(subsystem.getName())) {
            item = item.next();
        }
        //check search result:
        if (item == null) { // no duplicate found --> add new subsystem
            ComplexListItem newItem = new ComplexListItem(subsystem);
            if (head == null) { // empty list -> add first item
                head = last = newItem;
            } else { // add item at end of list
                last.setNext(newItem);
                last = newItem;
            }
            return true; // <-- entry added successfully
        } else {
            return false; // <-- duplicate body found
        }
    }

    /**
     * Returns the single body or subsystem with 'name' or 'null' if no such body or subsystem
     * exists in this system. In case of a single body, the body is returned as a subsystem of
     * one body, with the same name as the body.
     * @param name name of subsystem or body to search for
     * @return reference of celestial system searched for, otherwise null-reference
     */
    public CelestialSystem get(String name) {
        //TODO: implement method.
        ComplexListItem item = head;
        while (item != null && !item.system().getName().equals(name)) {
            CelestialBody tempBody = item.system().get(name); // search for body in subsystem
            if (tempBody != null) { // celestial body found!
                CelestialSystem tempSystem = new CelestialSystem(tempBody.getName());
                tempSystem.add(tempBody);
                return tempSystem; // return found celestial body as only item in tempSystem
            }
            item = item.next();
        }
        return item == null ? null : item.system(); // return found system in case
    }

    /**
     * returns the number of entries of the list.
     * @return length of linked list as integer
     */
    public int size() {
        //TODO: implement method.
        int size = 0;
        ComplexListItem item = head;
        while (item != null) { // traverse through list once
            size++; // <-- count number of links
            item = item.next();
        }
        return size;
    }

    //TODO: Define additional class(es) implementing a linked list (either here or outside class).
}

class ComplexListItem {
    //Object Variables:
    private CelestialSystem system;
    private ComplexListItem next;

    //Constructors:

    /**
     * default constructor
     */
    public ComplexListItem() {
        this.next = null;
    }

    /**
     * initializes an item with the given celestial subsystem as an value entry
     * @param system celestial system object to be entry of list item
     */
    public ComplexListItem(CelestialSystem system) {
        this.system = system;
        this.next = null;
    }

    //Object Methods:

    /**
     * Setter Method for list entry
     * @param system set entry for this item (a CelestialSystem object)
     */
    public void setSystem(CelestialSystem system) {
        this.system = system;
    }

    /**
     * Setter Method for updating the next reference of linked list
     * @param next reference of following list item in list
     */
    public void setNext(ComplexListItem next) {
        this.next = next;
    }

    /**
     * Getter Method for entry of list item
     * @return celestial body; entry in list
     */
    public CelestialSystem system() {
        return this.system;
    }

    /**
     * Getter Method for next reference of linked list
     * @return reference of next item
     */
    public ComplexListItem next() {
        return this.next;
    }
}
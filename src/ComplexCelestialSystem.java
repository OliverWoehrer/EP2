public class ComplexCelestialSystem {
    //Object Variables:
    //TODO: Define variables.
    private String name;
    private MyListNode head, last;

    //Constructors:

    /**
     * Initializes this system as an empty system with a name
     * @param name name of the complex list
     */
    public ComplexCelestialSystem(String name) {
        //TODO: implement constructor.
        this.name = name; // set name of complex list
        head = last = null;
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
        //TODO: implement method.
        if (head != null ) { // at least one subsystem in list
            //Check for duplicate names:
            MyListNode item = head;
            while (item != null) {
                if (item.system.getName().equals(subsystem.getName())) { // check duplicate subsystem names
                    System.out.println("Duplicate subsystem name found [" +
                            subsystem.toString()+"]");
                    return false; // duplicate subsystem found
                }
                for (int i = 0; i < subsystem.size(); i++) { // iterate every body
                    if (item.system.contains(subsystem.get(i).getName())) {
                        System.out.println("Duplicate body name found: "+subsystem.get(i).getName());
                        return false; // duplicate body found
                    }
                    else continue;
                }
                item = item.next;
            }

            //If not already returned, no duplicate names where found: add subsystem to list
            MyListNode newItem = new MyListNode(subsystem);
            last.setNext(newItem);
            last = newItem;
            System.out.println("Added to Complex Linked List ["+subsystem.toString()+"]");
            return true; // entry added successfully
        } else { // empty list
            head = last = new MyListNode(subsystem);
            System.out.println("Added to Complex Linked List ["+subsystem.toString()+"]");
            return true;
        }
    }

    /**
     * Checks if any of the subsystems contains a body with the given name or has the same name
     * as the given body itself.
     * @param BodyName name of the given body
     * @return true if any subsystem contains a name duplicate, false otherwise
     */
    public boolean contains(String BodyName) {
        if (head != null) { // non-empty list
            MyListNode item = head;
            while (item != null && !item.system.contains(BodyName)) {
                if (item.system.getName().equals(BodyName)) return true;
                item = item.next;
            }
            return item == null ? false : true; // traversed entire list if null: no duplicates found
        } else { // empty list
            return false;
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
        MyListNode item = head;
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
     * Returns the subsystem with the index 'i'. The subsystem that was first added to the list has the
     * index 0, the subsystem that was most recently added to the list has the index size()-1.
     * @param i index of subsystem to be returned
     * @return subsystem at index i, otherwise null-reference
     */
    public CelestialSystem get(int i) {
        MyListNode item = head;
        while (item != null && i > 0) { // traverse list until index i
            i--;
            item = item.next();
        }
        return item == null ? null : item.system;
    }

    /**
     * Returns the number of subsystem in this complex list.
     * @return length of linked list as integer
     */
    public int size() {
        //TODO: implement method.
        int size = 0;
        MyListNode item = head;
        while (item != null) { // traverse through list once
            size++; // <-- count number of links
            item = item.next;
        }
        return size;
    }

    //TODO: Define additional class(es) implementing a linked list (either here or outside class).

    private class MyListNode {
        //Object Variables:
        private CelestialSystem system;
        private MyListNode next;

        //Constructors:

        /**
         * default constructor
         */
        public MyListNode() {
            this.next = null;
        }

        /**
         * initializes an item with the given celestial subsystem as an value entry
         * @param system celestial system object to be entry of list item
         */
        public MyListNode(CelestialSystem system) {
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
        public void setNext(MyListNode next) {
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
        public MyListNode next() {
            return this.next;
        }
    }
}


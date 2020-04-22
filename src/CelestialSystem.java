/**
 * Implements a linked list of CelestialBody objects. The object reference itself is held by a list node
 * besides a reference to the next list item. Celestial bodies with duplicate names inside this list,
 * are not allowed. Duplicates are prevented on adding new bodies.
 */
public class CelestialSystem {

    //TODO: Define variables.
    private String nameOfSystem;
    private MyListNode head, last;

    //Constructors:

    /**
     * Initializes this system as an empty system with a name.
     * @param name name of the linked list
     */
    public CelestialSystem(String name) {
        //TODO: implement constructor.
        this.nameOfSystem = name; // set name of whole system
        head = last = null;
    }

    //Object Methods:

    /**
     * Adds 'body' to the list of bodies of the system if the list does not already contain a
     * body with the same name as 'body', otherwise does not change the object state.
     * @param body body object to be added
     * @return 'true' if the list was changed as a result of the call and 'false' otherwise
     */
    public boolean add(CelestialBody body) {
        //TODO: implement method.
        CelestialBody entry = this.get(body.getName());
        if (entry == null) { // no duplicate found
            MyListNode newItem = new MyListNode(body);
            if (head == null) { // empty list -> add first item
                head = last = newItem;
            } else { // add item at end of list
                last.setNext(newItem);
                newItem.setPrev(last);
                last = newItem;
            }
            return true; // <-- entry added successfully
        } else {
            return false; // <-- duplicate body found
        }
    }

    /**
     * Inserts the specified 'body' at the specified position in this list if 'i' is a valid index and
     * there is no body in the list with the same name as that of 'body'.
     * Shifts the element currently at that position (if any) and any subsequent elements to
     * the right (adds one to their indices). The first element of the list has the index 0.
     * @param i index the body should be added at
     * @param body body to be added to list
     * @return 'true' if the list was changed, 'false' otherwise.
     */
    public boolean add(int i, CelestialBody body) {
        //TODO: implement method.
        if (this.get(body.getName()) == null && 0 < i && i < this.size()) { // no duplicate found, valid index
            MyListNode item = head;
            while (item != null && i > 0) { // traverse till index i:
                item = item.next();
                i--;
            }

            //Check if first element or empty list:
            MyListNode newItem = new MyListNode(body);
            if (item == null) { // empty list
                head = last = newItem;
            } else if (item == head) { // insert at start of list
                newItem.setNext(item.next());
                newItem.setPrev(item.prev());
                newItem.setNext(head);
                head = newItem;
            } else { // insert body anywhere
                newItem.setNext(item);
                newItem.setPrev(item.prev());
                item.setPrev(newItem);
                item.prev().setNext(newItem);
            }
            return true; // body added successfully
        } else { // body already in list, invalid index
            return false; // body not added
        }
    }

    /**
     * Checks if a body with the same name as the given one is already in this celestial system
     * @param bodyName name of body to check for
     * @return true if celestial system contains body with same name, false otherwise
     */
    public boolean contains(String bodyName) {
        if (head != null) { // non-empty list
            MyListNode item = head;
            while (item != null && !item.body.getName().equals(bodyName)) {
                item = item.next;
            }
            return item == null ? false : true; // traversed entire list if null: no duplicates found
        } else { // empty list
            return false;
        }
    }

    /**
     * Removes the body with the index 'i'. The body that was first added to the list has the
     * index 0, the body that was most recently added to the list has the index size()-1.
     * @param i index of body to be removed from list
     */
    public void remove(int i) {
        MyListNode item = head;
        while (item != null && i > 0) {
            item = item.next();
            i--;
        }
        if (item == head) { // remove first element
            head = item.next();
            head.setPrev(null);
        } else if (item == last) { // remove last element
            last = item.prev();
            last.setNext(null);
        } else { // remove any element
            item.next().setPrev(item.prev());
            item.prev().setNext(item.next());
        }
    }

    /**
     * Returns the 'body' with the index 'i'. The body that was first added to the list has the
     * index 0, the body that was most recently added to the list has the index size()-1.
     * @param i index of body to be returned
     * @return body at index i, otherwise null-reference
     */
    public CelestialBody get(int i) {
        //TODO: implement method.
        MyListNode item = head;
        while (item != null && i > 0) { // traverse through list until index i
            item = item.next();
            i--;
        }
        return item == null? null : item.body();
    }

    /**
     * Returns the body with the specified name or 'null' if no such body exits in the list.
     * @param name name of body to be returned
     * @return reference of body with given name, otherwise null-reference
     */
    public CelestialBody get(String name) {
        //TODO: implement method.
        MyListNode item = head;
        while (item != null && !item.body().getName().equals(name)) {
            item = item.next();
        }
        return item == null ? null : item.body();
    }

    /**
     * Returns the number of bodies in this list.
     * @return length of linked list as integer
     */
    public int size() {
        //TODO: implement method.
        int size = 0;
        MyListNode item = head;
        while (item != null) { // traverse through list once
            size++; // <-- count number of links
            item = item.next();
        }
        return size;
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
    public String toString() {
        //TODO: implement method.
        String ret = this.nameOfSystem + ": ";
        MyListNode item = head;
        while (item != null) {
            ret = ret + item.body().getName() + ", ";
            item = item.next();
        }
        return ret;
    }

    /**
     * Returns a new list that contains the same elements as this list in reverse order.
     * The list 'this' is not changed and bodies are not duplicated (shallow copy).
     * @return reference of new reversed list
     */
    public CelestialSystem reverse() {
        //TODO: implement method.
        CelestialSystem reversedSystem = new CelestialSystem("reversed"+this.nameOfSystem);
        for (int i = this.size()-1; i > 0; i--) {
            reversedSystem.add(this.get(i));
        }
        return reversedSystem;
    }

    private class MyListNode {
        //Object Variables:
        private CelestialBody body;
        private MyListNode next, prev;

        //Constructors:

        public MyListNode() {
            next = null;
        }

        public MyListNode(CelestialBody body) {
            this.body = body;
            this.next = null;
            this.prev = null;
        }

        //Object Methods:

        /**
         * Setter Method for updating the next reference of linked list
         * @param next reference of following list item in list
         */
        public void setNext(MyListNode next) {
            this.next = next;
        }

        /**
         * Setter Method for updating the prev reference of linked list
         * @param prev reference of the previous list item in list
         */
        public void setPrev(MyListNode prev) {
            this.prev = prev;
        }

        /**
         * Getter Method for entry of list item
         * @return celestial body; entry in list
         */
        public CelestialBody body() {
            return this.body;
        }

        /**
         * Getter Method for next reference of linked list
         * @return reference of next item
         */
        public MyListNode next() {
            return this.next;
        }

        /**
         * Getter Method for prev reference of linked list
         * @return reference of previous item
         */
        public MyListNode prev() {
            return this.prev;
        }
    }
}
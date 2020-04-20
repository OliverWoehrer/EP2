public class CelestialSystem {

    //TODO: Define variables.
    private String nameOfSystem;
    private MyListItem head, last;

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
            MyListItem newItem = new MyListItem(body);
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
            MyListItem item = head;
            while (item != null && i > 0) { // traverse till index i:
                item = item.next();
                i--;
            }

            //Check if first element or empty list:
            MyListItem newItem = new MyListItem(body);
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
     * Removes the body with the index 'i'. The body that was first added to the list has the
     * index 0, the body that was most recently added to the list has the index size()-1.
     * @param i index of body to be removed from list
     */
    public void remove(int i) {
        MyListItem item = head;
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
        MyListItem item = head;
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
        MyListItem item = head;
        while (item != null && !item.body().getName().equals(name)) {
            item = item.next();
        }
        return item == null ? null : item.body();
    }

    /**
     * returns the number of entries of the list.
     * @return length of linked list as integer
     */
    public int size() {
        //TODO: implement method.
        int size = 0;
        MyListItem item = head;
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
        MyListItem item = head;
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

}

class MyListItem {
    //Object Variables:
    private CelestialBody body;
    private MyListItem next, prev;

    //Constructors:

    public MyListItem() {
        next = null;
    }

    public MyListItem(CelestialBody body) {
        this.body = body;
        this.next = null;
        this.prev = null;
    }

    //Object Methods:

    /**
     * Setter Method for updating the next reference of linked list
     * @param next reference of following list item in list
     */
    public void setNext(MyListItem next) {
        this.next = next;
    }

    /**
     * Setter Method for updating the prev reference of linked list
     * @param prev reference of the previous list item in list
     */
    public void setPrev(MyListItem prev) {
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
    public MyListItem next() {
        return this.next;
    }

    /**
     * Getter Method for prev reference of linked list
     * @return reference of previous item
     */
    public MyListItem prev() {
        return this.prev;
    }
}

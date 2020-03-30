public class CelestialSystem {

    //TODO: Define variables.
    private String nameOfSystem;
    private ListItem head, last;
    //private int size = 0; // number of list items

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
            ListItem newItem = new ListItem(body);
            if (head == null) { // empty list -> add first item
                head = last = newItem;
            } else { // add item at end of list
                last.setNext(newItem);
                last = newItem;
            }
            //size++; only needed for global size
            return true; // <-- entry added successfully
        } else {
            return false; // <-- duplicate body found
        }
    }

    /**
     * Returns the 'body' with the index 'i'. The body that was first added to the list has the
     * index 0, the body that was most recently added to the list has the largest index (size()-1).
     * @param i index of body to be returned
     * @return body at index i, otherwise null-reference
     */
    public CelestialBody get(int i) {
        //TODO: implement method.
        ListItem item = head;
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
        ListItem item = head;
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
        ListItem item = head;
        while (item != null) { // traverse through list once
            size++; // <-- count number of links
            item = item.next();
        }
        return size;
    }

    //TODO: Define additional class(es) implementing the linked list (either here or outside class).

    /**
     * Getter Method for name of celestial system
     * @return name of celestial system as string
     */
    public String getName() {
        return nameOfSystem;
    }
}

class ListItem {
    //Object Variables:
    private CelestialBody body;
    private ListItem next;

    //Constructors:

    /**
     * default constructor
     */
    public ListItem() {
        next = null;
    }

    /**
     * initializes an item with the given celestial body as an value entry
     * @param body celestial body object to be entry of list item
     */
    public ListItem(CelestialBody body) {
        this.body = body;
        this.next = null;
    }

    //Object Methods:

    /**
     * Setter Method for list entry
     * @param body set entry for this item (a CelestialBody object)
     */
    public void setBody(CelestialBody body) {
        this.body = body;
    }

    /**
     * Setter Method for updating the next reference of linked list
     * @param next reference of following list item in list
     */
    public void setNext(ListItem next) {
        this.next = next;
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
    public ListItem next() {
        return this.next;
    }
}

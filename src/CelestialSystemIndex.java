public class CelestialSystemIndex {

    //TODO: Define variables and constructor.

    // Adds a system of bodies to the tree. Since the keys of the tree are the names of bodies,
    // adding a system adds multiple (key, value) pairs to the tree, one for each body of the
    // system, with the same value, i.e., reference to the celestial system.
    // An attempt to add a system with a body that already exists in the tree
    // leaves the tree unchanged and the returned value would be 'false'.
    // For example, it is not allowed to have a system ("Earth", "Moon") and a system ("Jupiter",
    // "Moon") indexed by the tree, since "Moon" is not unique.
    // The method returns 'true' if the tree was changed as a result of the call and
    // 'false' otherwise.
    public boolean add(CelestialSystem system) {
        //TODO: implement method.
        return false;

    }

    // Returns the celestial system in which a body with the specified name exists.
    // For example, if the specified name is "Europa", the system of Jupiter (Jupiter, Io,
    // Europa, Ganymed, Kallisto) will be returned.
    // If no such system is found, 'null' is returned.
    public CelestialSystem get(String name) {
        //TODO: implement method.
        return null;

    }

    // Returns the overall number of bodies indexed by the tree.
    public int numberOfBodies() {
        //TODO: implement method.
        return -1;

    }

    // Returns the overall number of systems indexed by the tree.
    public int numberOfSystems() {
        //TODO: implement method.
        return -1;

    }

    //TODO: Define additional class(es) implementing a binary tree (either here or outside class).

}



public interface CelestialBodyComparator {

    // A comparison function, which imposes a total ordering on objects of class 'CelestialBody'.
    // The ordering imposed by this function needs to be consistent with 'equals' of class
    // 'CelestialBody', i.e. c.compare(e1, e2)==0 has the same boolean value as b1.equals(b2).
    int compare(CelestialBody b1, CelestialBody b2);

}

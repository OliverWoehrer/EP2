public interface CelestialBodyComparator {

    /**
     * A comparison function, which imposes a total ordering on objects of class 'CelestialBody'.
     * The ordering imposed by this function needs to be consistent with 'equals' of class
     * 'CelestialBody', i.e. c.compare(e1, e2)==0 has the same boolean value as b1.equals(b2).
     * @param b1 Body to compare to b2
     * @param b2 Body used as a reference in comparison
     * @return Result of the comparison
     */
    int compare(CelestialBody b1, CelestialBody b2);

}

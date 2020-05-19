public class CelestialBodyNameComparator implements CelestialBodyComparator {

    //Constructors:
    public CelestialBodyNameComparator () {}

    //Object Methods:

    /**
     * Compares two bodies by their names. In case b1 has the name that comes lexicographically
     * first the return value is positive. If the name of b1 comes  lexicographically after the
     * name of b2, the returned value is negative.
     * If they are lexicographically identical, zero is returned.
     * @param b1 Body to compare to b2
     * @param b2 Body used as a reference in comparison
     * @return Result of the comparison.
     */
    @Override
    public int compare(CelestialBody b1, CelestialBody b2) {
        return b1.getName().compareTo(b2.getName());
    }
}

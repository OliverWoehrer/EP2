import java.util.Arrays;
import java.util.Objects;

public class CelestialSystemIndexMap implements CelestialSystemIndex{
    private CelestialBody[] keys = new CelestialBody[9];
    private CelestialSystem[] values = new CelestialSystem[9];
    private int count = 0;

    /**
     * Adds a system of bodies to the index. Adding a system adds multiple (key, value) pairs to the index,
     * one for each body of the system, with the same value, i.e., reference to the celestial system.
     * An attempt to add a system with a body that already exists in the index leaves the index unchanged and
     * the returned value would be 'false'. The method returns 'true' if the index was changed as a
     * result of the call and 'false' otherwise.
     *
     * @param system CelestialSystem to be added into the hash table
     */
    public boolean add(CelestialSystem system) {
        if (system == null || system.size() == 0) {
            return false;
        }
        for (int i = 0; i < system.size(); i++) { // check for duplicates in hash table
            if (get(system.get(i)) != null) return false;
        }
        for (int i = 0; i < system.size(); i++) {
            CelestialBody temp = system.get(i);
            int index = findIndex(temp);
            keys[index] = temp;
            values[index] = system;
            if (++count >= 0.75*keys.length) {
                CelestialBody[] currKeys = keys; // copy current array
                CelestialSystem[] currValue = values;
                keys = new CelestialBody[(currKeys.length << 1) - 1]; // double original array length
                values = new CelestialSystem[(currKeys.length << 1) - 1];
                for (int j = 0; j < currKeys.length; j++) { // copy current array into extended original one
                    if (currKeys[j] == null) continue;
                    keys[index = findIndex(currKeys[j])] = currKeys[j];
                    values[index] = currValue[j];
                }
            }
        }
        return true;
    }

    /**
     * Checks for the index in the hash table the given body (key is the body name) would go. It performs
     * linear search for the next free index in case the given index is already used.
     * @param body CelestialBody to check for
     * @return Index the given body would go in. Linear collision handling
     */
    private int findIndex(CelestialBody body) {
        if (body == null) return keys.length-1; // return null-entry
        int index = body.hashCode() & (keys.length-2);
        while (keys[index] != null && !keys[index].equals(body)) {
            index = (index + 1) & (keys.length-2); // find entry with matching key
        }
        return index;
    }

    /**
     * Returns the celestial system with which a body is associated.
     * If body is not contained as a key, 'null' is returned
     * @param body CelestialBody name is used as key
     */
    public CelestialSystem get(CelestialBody body) {
        if (body == null) return null;
        int index = body.hashCode() & (keys.length-2);
        while (keys[index] != null && !keys[index].equals(body)) {
            index = (index + 1) & (keys.length-2); // find entry with matching key
        }
        return values[index];
    }

    /**
     * Returns 'true' if the specified 'body' is listed in the index. Therefore checks the return value
     * of the get() method, which is null if no matching body (key) was found.
     * @param body CelestialBody to look for
     */
    public boolean contains(CelestialBody body) {
        return get(body) != null;
    }

    /**
     * Returns a readable representation with the name of the system and all bodies in random order of the list.
     * @return String representation
     */
    @Override
    public String toString() {
        ComplexCelestialSystem temp = new ComplexCelestialSystem("CelestialSystemIndexMap");
        for (CelestialSystem value : values) {
            temp.add(value);
        }
        return temp.toString();
    }

    /**
     * Two CelestialSystem hash tables are equal when they contain the same bodies (keys)
     * associated to the same CelestialSystem (values)
     * @param o Object to compare with
     * @return true if the systems are the same according to the description above
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CelestialSystemIndexMap hashTable = (CelestialSystemIndexMap) o;
        if (this.count != hashTable.count) return false; // lengths dont match
        for (CelestialBody key : keys) { // check for matching (key, value)-pairs
            if (key == null) continue;
            if (!hashTable.contains(key)) return false; // check for matching keys
            if (!this.get(key).equals(hashTable.get(key))) return false; // check for matching values
        }
        return true;
    }

    @Override
    public int hashCode() {
        int ret = 0, bias = 17 * count; // create bias without considering order of list
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) continue;
            ret += bias * (keys[i].hashCode() * values[i].hashCode());
        }
        return ret;
    }
}

//TODO: Zusatzfragen:
/*
(1) Die equals()-Vergleiche von CelestialBody sind falsch, da auch andere Eigenschaften neben dem Namen
zum Vergleich herangezogen werden. Die Vergleiche von CelestialSystem funktionieren weiterhin
da hier auf Standart Object.equals() zur??ckgegriffen wird. Und nach dem Ein Body immer nur einmal in ein
Systeme eingef??gt werden kann, sind es tats??chlich immer die gleichen bodies die verglichen werden.
Die equals()-Vergleiche in CelestialSystemIndexMap sind ebenfalls unbeeinflusst da hier wieder ??ber
contains() nach passenden Keys gesucht wird (-> unbeeinflusst). Die Suche nach passenden Values ist durch
CelestialSystem.equals() auch nicht beeinflusst.

(2) Die hashCodes sind nicht mit equals ??bereinstimmend. Sprich die Bedingungen
aus Frage 3 sind weiterhin nicht erf??llt.

(3) Es gilt f??r x != null immer:
 x.equals(null) = false
 x.equals(x) = true
 x.equals(y) && y.equals(x) = true
 Bei wiederholten Aufrufen und unver??ndertem x musss equals() weiterhin die selben Ergebnisse
 liefern.
 Sind zwei Objekte nach der equals()-Methode gleich, so sind auch ihre hash codes gleich. Umgekehrt
 gilt diese Beziehung nicht zwingend.

 (4) Nein gilt nicht. Da beispielsweise zwei gleichnamige K??rper mit unterschiedlichen Massen
 nach der equals()-Methode gleich sind aber einen unterschiedlichen String liefern (Im String wird
 die Masse eingebunden). Das kann problematisch werden da beim Debugging ebenfalls die
 Darstellung von toString() verwendet wird. Zwei K??rper "schauen" dann scheinbar nicht gleich aus (nicht
 gleicher String) sind aber innerhalb des Programms per equals() gleich.

 (5) Es m??ssten die Methodenk??pfe auf das Interface angepasst werden. Konkret muss die Methode
 get(String) auf get(CelestialBody) umgeschrieben werden, damit die ??bergebenen Parameter passen.
 */

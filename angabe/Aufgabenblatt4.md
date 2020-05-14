# Aufgabenblatt 4

## Allgemeine Anmerkungen
Ihre Lösung für dieses Aufgabenblatt ist bis Freitag, 24.4. 13h durch `git commit` und `push` 
abzugeben. Mit der Angabe wird die Datei `CelestialSystemIndexTree.java` mitgeliefert.
Vorgegebene Programmteile dürfen nur an den Stellen verändert werden, die mit TODO markiert sind.
Zusätzliche Klassen, Interfaces, Methoden und Variablen dürfen aber eingefügt werden.
Wenn Sie zusätzlich zu den gefragten Klassen, weitere Klassen definieren, achten Sie darauf, 
dass die Klassennamen mit `My` beginnen, um Konflikte mit späteren Aufgabenblättern zu vermeiden.

## Ziel
Ziel der Aufgabe ist die Anwendung der Konzepte: Listen und Bäume (siehe Skriptum Seiten 
46-70).

## Aufgaben
1. Ändern Sie die Klasse `CelestialSystem` so, dass sie als doppelt verkettete Liste 
implementiert wird. Ändern die Klasse, ohne dass sich das Verhalten ihrer Objekte ändert.

2. Fügen Sie folgende Methoden zur Klasse `CelestialSystem` hinzu:
    
````
    // Inserts the specified 'body' at the specified position 
    // in this list if 'i' is a valid index and there is no body 
    // in the list with the same name as that of 'body'.
    // Shifts the element currently at that position (if any) and 
    // any subsequent elements to the right (adds one to their 
    // indices). The first element of the list has the index 0. 
    // Returns 'true' if the list was changed as a result of 
    // the call, 'false' otherwise.
    public boolean add(int i, CelestialBody body) {
        //TODO: implement method.
        
    }
    
    // Returns a readable representation with the name of the 
    // system and all bodies in respective order of the list.
    public String toString() {
        //TODO: implement method.
        
    }



    // Returns a new list that contains the same elements as this
    // list in reverse order. The list 'this' is not changed and
    // bodies are not duplicated (shallow copy).
    public CelestialSystem reverse() {
        //TODO: implement method.
    
    }
````
    
3. Implementieren Sie die Klasse `CelestialSystemIndexTree`, die einen binären Suchbaum für Objekte der
Klasse `CelestialSystem` implementiert. Der Schlüssel für die Suche ist der Name eines 
Himmelskörpers (nicht der eines Systems). Die Klasse bietet Methoden zur Suche nach Systemen, 
in denen bestimmte Himmelskörper vorkommen. Zum Beispiel liefert die Suche mit dem Namen "Io" das
Jupiter-System mit den Himmelskörpern Jupiter, Io, Europa, Ganymed, Kallisto. 
Sie dürfen auch weitere sinnvolle Methoden der Klasse implementieren (z.B. eine Methode `toString`).

Die Positionen und Geschwindigkeitsvektoren spielen in diesem Aufgabenblatt keine Rolle. Sie 
können beim Testen (0,0,0) gesetzt werden.

## Zusatzfragen
Wie müsste man die Methoden von `CelestialSystemIndexTree` ändern, wenn man im Baum nicht nur mit 
Namen von Himmelskörpern, sondern auch mit Namen von Systemen suchen will?


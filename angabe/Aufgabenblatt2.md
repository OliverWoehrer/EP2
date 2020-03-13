# Aufgabenblatt 2

## Allgemeine Anmerkungen
Ihre Lösung für dieses Aufgabenblatt ist bis Freitag, 20.3. 13h durch `git commit` und `push` 
abzugeben. Mit der Angabe werden folgende Dateien mitgeliefert: `Simulation.java`, `Vector3.java` 
und `CelestialBody.java`. 
Vorgegebene Programmteile dürfen nur an den Stellen verändert werden, die mit TODO markiert sind.
Zusätzliche Klassen, Interfaces, Methoden und Variablen dürfen aber eingefügt werden.
Wenn Sie zusätzlich zu den gefragten Klassen, weitere Klassen definieren, achten Sie darauf, 
dass die Klassennamen mit `My` beginnen, um Konflikte mit späteren Aufgabenblättern zu vermeiden.

## Ziel
Ziel der Aufgabe ist die Anwendung der Konzepte: Datensatz, Data-Hiding, Konstruktor, 
Pseudovariable `this` (siehe Skriptum Seiten 39-50).

## Aufgaben
1. Lesen Sie sich die Kommentare in den Dateien durch und führen Sie die Klasse `Simulation` aus. 
2. Data hiding: 
    1. Machen Sie in den Klassen `Vector3` und `CelestialBody` alle Objektvariablen `private`.
    2. Definieren Sie entsprechende Konstruktoren um die Objektvariablen zu initialisieren. 
    `Simulation` soll nur noch diese nutzen und nicht mehr direkt auf die Objektvariablen 
    zugreifen dürfen.
3. Datenkapselung: Anstelle der gegebenen statischen Methoden in der Datei `Simulation.java` sollen 
nur noch entsprechende Objektmethoden der Klassen `CelestialBody` und `Vector3` benutzt werden. 
Implementieren Sie dazu die spezifizierten Methoden und bauen Sie `Simulation` so um, dass 
anstelle der Aufrufe statischer Methoden, Objektmethoden genutzt werden. Sie sollen alle 
in `CelestialBody` und `Vector3` spezifizierten Methoden implementieren, auch wenn nicht alle 
von `Simulation` genutzt werden. Die in `Simulation.java` gegebenen statischen Methoden 
können dann entfernt werden (natürlich bis auf `main`).
4. Freiwillige Zusatzaufgabe: Fügen Sie weitere Himmelskörper (z.B. Planeten, Monde oder Asteroiden) 
in die Simulation ein. Recherchieren Sie dazu die benötigten Daten. Verwenden Sie die ungefähren 
Angaben zur mittleren Orbitalgeschwindigkeit oder der Orbitalgeschwindigkeit bei Perihel oder Aphel 
der Umlaufbahn. Der Einfachheit halber kann - wie bei den in der Simulation bereits enthaltenen 
Planeten - Perihel oder Aphel auf eine Achse des Koordinatensystems gelegt werden. Die Daten müssen 
nicht exakt sein.

## Zusatzfragen
Beantworten Sie folgende Zusatzfragen als Kommentar in `Simulation.java`:

1. Was versteht man unter Datenkapselung?
2. Was versteht man unter _data hiding_?


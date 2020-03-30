import java.sql.SQLOutput;
import java.util.Locale;

public class Simulation {
    //Global Variables:
    public static final double G = 6.6743e-11; // gravitational constant
    public static final double AU = 150e9; // one astronomical unit (AU) is the average distance of earth to the sun.
    private static final int CanvasSize = 600; // all quantities are based on kilogram, seconds and meter.
    private static final int drawDelay = 20;
    private static int day = 60;

    // The main simulation method using instances of other classes.
    public static void main(String[] args) {

        //TODO: change implementation of this method according to 'Aufgabenblatt2.md'.
        Locale.setDefault(Locale.US); // use US formatted numbers (dot (.) as comma)

        //Read body positions of all bodies and pass via linked list:
        CelestialSystem solarsytem = ReadDataUtil.initialize(++day);
        int size = solarsytem.size();
        for (int i = 0; i < size; i++) {
            solarsytem.get(i).print();
        }

        //Setup Canvas:
        StdDraw.setCanvasSize(CanvasSize, CanvasSize);
        StdDraw.setScale(-2*AU,2*AU);
        double pixelWidth = 60*AU/CanvasSize;
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        //Simulation loop:
        long oldTime = System.currentTimeMillis();
        while(day <= 366) {
            long currTime = System.currentTimeMillis();
            if (currTime >= oldTime + drawDelay) {
                oldTime = currTime;
                solarsytem = ReadDataUtil.initialize(day++);
                StdDraw.clear(StdDraw.BLACK); // <--exclude if you want to draw orbits
                for (int i = 0; i < size; i++) {
                    solarsytem.get(i).draw();
                }
                StdDraw.show();
            } //else: wait for delay
        }
    }
}

//TODO: answer additional questions of 'Aufgabenblatt2'.



package masteringthreads.ch4_applied_threading_techniques.exercise_4_4;

// DO NOT CHANGE
public class HouseDrawingTest {
    public static void main(String... args) {
        new Thread(() -> {while(true) new HouseDrawing("Home1", "beige");}).start();

        new Thread(() -> {while(true) new HouseDrawing("Home2", "pink");}).start();
    }
}

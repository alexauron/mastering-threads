package masteringthreads.ch4_applied_threading_techniques.exercise_4_4;

import java.util.*;
import java.util.concurrent.*;

public class HouseDrawing extends StupidFramework {
    private final String colour;
    private static final ThreadLocal<String> tempColour =
            new ThreadLocal<>();
    private static String saveColour(String title, String colour) {
        tempColour.set(colour);
        return title;
    }
    public HouseDrawing(String title, String colour) {
        super(saveColour(title, colour));
        this.colour = colour;
        tempColour.remove();
    }
    private String getColour() {
        return Objects.requireNonNullElse(colour, tempColour.get());
    }
    public void draw() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Drawing house " + getTitle() + " with colour " + getColour());
    }
}

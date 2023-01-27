package playground;

import java.time.*;
import java.util.*;

public class TimerDemo {
    private static String name;
    public static void main(String... args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println(name.toUpperCase());
            }
        }, 5500);
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println(LocalDateTime.now());
            }
        }, 1000, 1000);
    }
}

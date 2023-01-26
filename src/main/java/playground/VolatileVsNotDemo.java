package playground;

public class VolatileVsNotDemo {
    private int value;

    public void foo() {
        for (int i = 0; i < 1_000_000_000; i++) {
            value++;
        }
    }

    public static void main(String... args) {
        for (int i = 0; i < 100; i++) {
            test();
        }
    }

    private static void test() {
        VolatileVsNotDemo demo = new VolatileVsNotDemo();
        long time = System.nanoTime();
        try {
            demo.foo();
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}

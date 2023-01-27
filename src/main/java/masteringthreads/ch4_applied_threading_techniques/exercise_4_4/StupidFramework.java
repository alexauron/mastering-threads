package masteringthreads.ch4_applied_threading_techniques.exercise_4_4;

// DO NOT CHANGE
public abstract class StupidFramework {
    private final String title;

    public StupidFramework(String title) {
        this.title = title;
        draw();
        System.out.println("You are using StupidFramework: " + title);
    }

    protected String getTitle() {
        return title;
    }

    public abstract void draw();
}

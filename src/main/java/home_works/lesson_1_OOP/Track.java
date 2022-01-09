package home_works.lesson_1_OOP;

public class Track {
    private int distance;

    public Track(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Трек длиной " + distance + " м.";
    }
}

package home_works.lesson_1_OOP;

public class Wall {
    private float height;

    public Wall(float height) {
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Стена высотой " + height + " м.";
    }
}

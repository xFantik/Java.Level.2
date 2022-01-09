package home_works.lesson_1_OOP;

public class Cat {
    private String name;
    private float maxHeightJump;
    private int maxDistanceRun;

    public Cat(String name, float maxHeightJump, int maxDistanceRun) {
        this.name = name;
        this.maxHeightJump = maxHeightJump;
        this.maxDistanceRun = maxDistanceRun;
    }

    public boolean run(Track track) {
        if (track.getDistance() <= maxDistanceRun) {
            // System.out.printf("Кот %s пробежал %s м.\n", name, track.getDistance());
            return true;
        }
        System.out.printf("Кот %s не смог пробежать такое расстояние\n", name);
        return false;
    }

    public boolean jump(Wall wall) {
        if (wall.getHeight() <= maxHeightJump) {
            //System.out.printf("Кот %s перепрыгнул стену высотой %s м.\n", name, wall.getHeight());
            return true;
        }
        System.out.printf("Кот %s не смог перепрыгнуть\n", name);
        return false;
    }

    @Override
    public String toString() {
        return "Кот " + name;
    }
}

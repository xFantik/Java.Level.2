package home_works.lesson_1_OOP;

public class Robot implements Competable {
    private String name;
    private float maxHeightJump;
    private int maxDistanceRun;

    public Robot(String name, float maxHeightJump, int maxDistanceRun) {
        this.name = name;
        this.maxHeightJump = maxHeightJump;
        this.maxDistanceRun = maxDistanceRun;
    }

    public boolean run(Track track) {
        if (track.getDistance() <= maxDistanceRun) {
            //System.out.printf("Робот %s пробежал %s м.\n", name, track.getDistance());
            return true;
        }
        System.out.printf("Робот %s не смог пробежать такое расстояние\n", name);
        return false;
    }

    public boolean jump(Wall wall) {
        if (wall.getHeight() <= maxHeightJump) {
            // System.out.printf("Робот %s перепрыгнул стену высотой %s м.\n", name, wall.getHeight());
            return true;
        }
        System.out.printf("Робот %s не смог перепрыгнуть\n", name);
        return false;
    }

    @Override
    public String toString() {
        return "Робот " + name;
    }
}

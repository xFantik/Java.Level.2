package home_works.lesson_1_OOP;

public class HomeWork_lesson1 {
    public static void main(String[] arg) {
        Competable[] members = {
                new Human("Василий", 1.1f, 600),
                new Human("Екатерина", 1.3f, 900),
                new Robot("Bob", 0.5f, 50),
                new Robot("Gog", 0, 120),
                new Cat("Барсик", 2.3f, 400),
                new Cat("Милка", 1.9f, 600),
        };
        Object[] stages = {
                new Track(100),
                new Wall(0.5f),
                new Track(200),
                new Wall(0.9f),
                new Track(600)
        };

        for (Competable member : members) {
            boolean winFlag = true;
            System.out.printf("\n%s вышел на дистанцию\n", member);
            for (Object stage : stages) {
                if (!goToStage(member, stage)) {
                    System.out.printf("%s не справился с испытанием и сошёл с дистанции!\n", member);
                    winFlag = false;
                    break;
                }
            }
            if (winFlag)
                System.out.println("Дистанция пройдена!!");
        }
    }

    private static boolean goToStage(Competable member, Object stage) {
        System.out.printf("%s видит: %s\n", member, stage);
        if (stage instanceof Wall) {
            return member.jump((Wall) stage);
        }
        if (stage instanceof Track) {
            return member.run((Track) stage);
        }
        return false;
    }


}

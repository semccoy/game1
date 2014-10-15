package game1;

import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.*;
import java.util.*;

class Testeez {

    public static void check(String label, Object x, Object y) throws Exception {
        if (x != y) {
            throw new Exception("\n" + label + ": " + x + " should equal " + y + " but it don't :(");
        }
    }

    public static void check_ints(String label, int x, int y) throws Exception {
        if (x != y) {
            throw new Exception("\n" + label + ": " + x + " should equal " + y + " but it don't :(");
        }
    }
}

public class Game1 extends World {

    static int goalx, goaly;
    static int endx, endy;
    static int WIDTH = 1440;
    static int HEIGHT = 800;
    static int CELLSIZE = 40;
    static int CELLSWIDE = 27;
    static int CELLSHIGH = 15;
    static int movements = 0;
    static int minMovements = 0;
    static boolean thisIsTheEnd;
    static Random rand = new Random();
    static Posn upperleft = new Posn(200, 120);
    static Posn base = new Posn(WIDTH / 2, HEIGHT / 2);
    static Mover pather = new Mover(Char.charPos().x, Char.charPos().y);
    public static WorldImage universe = new RectangleImage(base, WIDTH, HEIGHT, Color.black);
    public static WorldImage background = new RectangleImage(base, 1080, 600, Color.lightGray);
    public static RectangleImage start = new RectangleImage(new Posn(240, 160), CELLSIZE, CELLSIZE, Color.green);
    public static ArrayList<RectangleImage> worldArray = new ArrayList<RectangleImage>();
    public static ArrayList<RectangleImage> pathArray = new ArrayList<RectangleImage>();
    public static ArrayList<RectangleImage> tempPaths = new ArrayList<RectangleImage>();

    public static class Char {

        static int charx = 240;
        static int chary = 160;

        Char(int charx, int chary) {
            this.charx = charx;
            this.chary = chary;
        }

        public static Posn charPos() {
            return new Posn(charx, chary);
        }

        public static void move(String key) {
            if ((key.equals("up") || key.equals("w")) && !upCheck()) {
                while (!upCheck()) {
                    chary = chary - CELLSIZE;
                }
                movements++;
            } else if ((key.equals("left") || key.equals("a")) && !leftCheck()) {
                while (!leftCheck()) {
                    charx = charx - CELLSIZE;
                }
                movements++;
            } else if ((key.equals("down") || key.equals("s")) && !downCheck()) {
                while (!downCheck()) {
                    chary = chary + CELLSIZE;
                }
                movements++;
            } else if ((key.equals("right") || key.equals("d")) && !rightCheck()) {
                while (!rightCheck()) {
                    charx = charx + CELLSIZE;
                }
                movements++;
            } else if (key.equals("x")) {
                while (!upCheck()) {
                    charx = 240;
                    chary = 160;
                }
                movements = movements + 5;
            }
        }

        public static boolean onGoal() {
            return (Char.charPos().x - upperleft.x) / CELLSIZE - 1 == endx && (Char.charPos().y - upperleft.y) / CELLSIZE - 1 == endy;
        }

        // checks to see if blocks around char are solid
        public static boolean upCheck() {
            int checkx = Char.charPos().x;
            int checky = Char.charPos().y - CELLSIZE;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            return (worldArray.contains(gray) || worldArray.contains(darkGray));
        }

        public static boolean leftCheck() {
            int checkx = Char.charPos().x - CELLSIZE;
            int checky = Char.charPos().y;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            return (worldArray.contains(gray) || worldArray.contains(darkGray));
        }

        public static boolean downCheck() {
            int checkx = Char.charPos().x;
            int checky = Char.charPos().y + CELLSIZE;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            return (worldArray.contains(gray) || worldArray.contains(darkGray));
        }

        public static boolean rightCheck() {
            int checkx = Char.charPos().x + CELLSIZE;
            int checky = Char.charPos().y;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            return (worldArray.contains(gray) || worldArray.contains(darkGray));
        }

        public static WorldImage charImage() {
            return new RectangleImage(charPos(), CELLSIZE, CELLSIZE, Color.green);
        }
    }

    public static class Mover {

        static int moverx = 240;
        static int movery = 160;

        Mover(int moverx, int movery) {
            this.moverx = moverx;
            this.movery = movery;
        }

        public static Posn moverPos() {
            return new Posn(moverx, movery);
        }

        public static WorldImage moverImage() {
            return new RectangleImage(moverPos(), CELLSIZE, CELLSIZE, Color.yellow);
        }

        // checks to see if blocks around mover (block of interest) solid or just-moved-from
        public static boolean upCheck() {
            int checkx = Mover.moverPos().x;
            int checky = Mover.moverPos().y - CELLSIZE;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            RectangleImage yellow = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.yellow);
            return (worldArray.contains(gray) || worldArray.contains(darkGray) || pathArray.contains(yellow));
        }

        public static boolean leftCheck() {
            int checkx = Mover.moverPos().x - CELLSIZE;
            int checky = Mover.moverPos().y;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            RectangleImage yellow = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.yellow);
            return (worldArray.contains(gray) || worldArray.contains(darkGray) || pathArray.contains(yellow));
        }

        public static boolean downCheck() {
            int checkx = Mover.moverPos().x;
            int checky = Mover.moverPos().y + CELLSIZE;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            RectangleImage yellow = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.yellow);
            return (worldArray.contains(gray) || worldArray.contains(darkGray) || pathArray.contains(yellow));
        }

        public static boolean rightCheck() {
            int checkx = Mover.moverPos().x + CELLSIZE;
            int checky = Mover.moverPos().y;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            RectangleImage yellow = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.yellow);
            return (worldArray.contains(gray) || worldArray.contains(darkGray) || pathArray.contains(yellow));
        }

        public static RectangleImage upNeighbor() {
            return new RectangleImage(new Posn(moverPos().x, moverPos().y - CELLSIZE), CELLSIZE, CELLSIZE, Color.yellow);
        }

        public static RectangleImage leftNeighbor() {
            return new RectangleImage(new Posn(moverPos().x - CELLSIZE, moverPos().y), CELLSIZE, CELLSIZE, Color.yellow);
        }

        public static RectangleImage downNeighbor() {
            return new RectangleImage(new Posn(moverPos().x, moverPos().y + CELLSIZE), CELLSIZE, CELLSIZE, Color.yellow);
        }

        public static RectangleImage rightNeighbor() {
            return new RectangleImage(new Posn(moverPos().x + CELLSIZE, moverPos().y), CELLSIZE, CELLSIZE, Color.yellow);
        }
    }

    public static class TestWorld {

        TestWorld() {
        }

        int endx, endy, goalx, goaly = 400;
        Char testChar = new Char(240, 160);
        Mover testMover = new Mover(240, 160);

        public static ArrayList<RectangleImage> testWorldArray = new ArrayList<RectangleImage>();
        public static ArrayList<RectangleImage> testPathArray = new ArrayList<RectangleImage>();
        public static ArrayList<RectangleImage> testTempPaths = new ArrayList<RectangleImage>();

        public static void test() throws Exception {
            TestWorld tw = new TestWorld();
            // test for char position methods (mover works the same way)
            Testeez.check_ints("char x", tw.testChar.charPos().x, 240);
            Testeez.check_ints("char y", tw.testChar.charPos().y, 160);
            
            // test for char movement methods
            int charY = tw.testChar.chary;
            tw.testChar.move("up");
            Testeez.check_ints("char up",charY-40, charY);
            
        }
    }

    /// Pathing functions ///
    public static void pathGen() {
        while (canMove()) {
            patherDirectionalChecker();
            int nextDirection = randomInt(0, tempPaths.size() - 1);
            pathArray.add(tempPaths.get(nextDirection));
            RectangleImage ultimate = pathArray.get(pathArray.size() - 1);
            RectangleImage penultimate = pathArray.get(pathArray.size() - 2);
            int dx = (ultimate.pinhole.x - penultimate.pinhole.x) / CELLSIZE;
            int dy = (ultimate.pinhole.y - penultimate.pinhole.y) / CELLSIZE;
            int distToUpWall = (ultimate.pinhole.y - 160) / CELLSIZE;
            int distToLeftWall = (ultimate.pinhole.x - 240) / CELLSIZE;
            int distToDownWall = (640 - ultimate.pinhole.y) / CELLSIZE;
            int distToRightWall = (1200 - ultimate.pinhole.x) / CELLSIZE;

            if (dy < 0) {
                int reps = Math.min(randomInt(0, distToUpWall), 4);
                RectangleImage rockToBe = new RectangleImage(new Posn(ultimate.pinhole.x, ultimate.pinhole.y + dy * CELLSIZE * (reps + 1)), CELLSIZE, CELLSIZE, Color.yellow);
                while (pathArray.contains(rockToBe)) {
                    reps = Math.min(randomInt(0, distToUpWall), 4);
                    rockToBe = new RectangleImage(new Posn(ultimate.pinhole.x, ultimate.pinhole.y + dy * CELLSIZE * (reps + 1)), CELLSIZE, CELLSIZE, Color.yellow);
                }
                int counter = 0;
                while (counter < reps) {
                    RectangleImage nextPath = new RectangleImage(new Posn(ultimate.pinhole.x, ultimate.pinhole.y + dy * CELLSIZE), CELLSIZE, CELLSIZE, Color.yellow);
                    RectangleImage nextIsRock = new RectangleImage(new Posn(ultimate.pinhole.x, ultimate.pinhole.y + dy * CELLSIZE), CELLSIZE, CELLSIZE, Color.darkGray);
                    if (worldArray.contains(nextIsRock)) {
                        addBlock(nextIsRock.pinhole.x, nextIsRock.pinhole.y, Color.darkGray);
                    } else {
                        pathArray.add(nextPath);
                        ultimate = pathArray.get(pathArray.size() - 1);
                        counter++;
                    }
                }
                if (ultimate.pinhole.y > 160) {
                    int distToWallX = (ultimate.pinhole.x - upperleft.x) / CELLSIZE;
                    int distToWallY = (ultimate.pinhole.y - upperleft.y) / CELLSIZE;
                    addBlock(distToWallX - 1, distToWallY - 2, Color.darkGray);
                }
            }

            if (dx < 0) {
                int reps = Math.min(randomInt(0, distToLeftWall), 4);
                RectangleImage rockToBe = new RectangleImage(new Posn(ultimate.pinhole.x + dx * CELLSIZE * (reps + 1), ultimate.pinhole.y), CELLSIZE, CELLSIZE, Color.yellow);
                while (pathArray.contains(rockToBe)) {
                    reps = Math.min(randomInt(0, distToLeftWall), 4);
                    rockToBe = new RectangleImage(new Posn(ultimate.pinhole.x + dx * CELLSIZE * (reps + 1), ultimate.pinhole.y), CELLSIZE, CELLSIZE, Color.yellow);
                }
                int counter = 0;
                while (counter < reps) {
                    RectangleImage nextPath = new RectangleImage(new Posn(ultimate.pinhole.x + dx * CELLSIZE, ultimate.pinhole.y), CELLSIZE, CELLSIZE, Color.yellow);
                    RectangleImage nextIsRock = new RectangleImage(new Posn(ultimate.pinhole.x + dx * CELLSIZE, ultimate.pinhole.y), CELLSIZE, CELLSIZE, Color.darkGray);
                    if (worldArray.contains(nextIsRock)) {
                        addBlock(nextIsRock.pinhole.x, nextIsRock.pinhole.y, Color.darkGray);
                    } else {
                        pathArray.add(nextPath);
                        ultimate = pathArray.get(pathArray.size() - 1);
                        counter++;
                    }
                }
                if (ultimate.pinhole.x > 240) {
                    int distToWallX = (ultimate.pinhole.x - upperleft.x) / CELLSIZE;
                    int distToWallY = (ultimate.pinhole.y - upperleft.y) / CELLSIZE;
                    addBlock(distToWallX - 2, distToWallY - 1, Color.darkGray);
                }
            }

            if (dy > 0) {
                int reps = Math.min(randomInt(0, distToDownWall), 4);
                RectangleImage rockToBe = new RectangleImage(new Posn(ultimate.pinhole.x, ultimate.pinhole.y + dy * CELLSIZE * (reps + 1)), CELLSIZE, CELLSIZE, Color.yellow);
                while (pathArray.contains(rockToBe)) {
                    reps = Math.min(randomInt(0, distToDownWall), 4);
                    rockToBe = new RectangleImage(new Posn(ultimate.pinhole.x, ultimate.pinhole.y + dy * CELLSIZE * (reps + 1)), CELLSIZE, CELLSIZE, Color.yellow);
                }
                int counter = 0;
                while (counter < reps) {
                    RectangleImage nextPath = new RectangleImage(new Posn(ultimate.pinhole.x, ultimate.pinhole.y + dy * CELLSIZE), CELLSIZE, CELLSIZE, Color.yellow);
                    RectangleImage nextIsRock = new RectangleImage(new Posn(ultimate.pinhole.x, ultimate.pinhole.y + dy * CELLSIZE), CELLSIZE, CELLSIZE, Color.darkGray);
                    if (worldArray.contains(nextIsRock)) {
                        addBlock(nextIsRock.pinhole.x, nextIsRock.pinhole.y, Color.darkGray);
                    } else {
                        pathArray.add(nextPath);
                        ultimate = pathArray.get(pathArray.size() - 1);
                        counter++;
                    }
                }
                if (ultimate.pinhole.y < 640) {
                    int distToWallX = (ultimate.pinhole.x - upperleft.x) / CELLSIZE;
                    int distToWallY = (ultimate.pinhole.y - upperleft.y) / CELLSIZE;
                    addBlock(distToWallX - 1, distToWallY, Color.darkGray);
                }
            }

            if (dx > 0) {
                int reps = Math.min(randomInt(0, distToRightWall), 4);
                RectangleImage rockToBe = new RectangleImage(new Posn(ultimate.pinhole.x + dx * CELLSIZE * (reps + 1), ultimate.pinhole.y), CELLSIZE, CELLSIZE, Color.yellow);
                while (pathArray.contains(rockToBe)) {
                    reps = Math.min(randomInt(0, distToRightWall), 4);
                    rockToBe = new RectangleImage(new Posn(ultimate.pinhole.x + dx * CELLSIZE * (reps + 1), ultimate.pinhole.y), CELLSIZE, CELLSIZE, Color.yellow);
                }
                int counter = 0;
                while (counter < reps) {
                    RectangleImage nextPath = new RectangleImage(new Posn(ultimate.pinhole.x + dx * CELLSIZE, ultimate.pinhole.y), CELLSIZE, CELLSIZE, Color.yellow);
                    RectangleImage nextIsRock = new RectangleImage(new Posn(ultimate.pinhole.x + dx * CELLSIZE, ultimate.pinhole.y), CELLSIZE, CELLSIZE, Color.darkGray);
                    if (worldArray.contains(nextIsRock)) {
                        addBlock(nextIsRock.pinhole.x, nextIsRock.pinhole.y, Color.darkGray);
                    } else {
                        pathArray.add(nextPath);
                        ultimate = pathArray.get(pathArray.size() - 1);
                        counter++;
                    }
                }
                if (ultimate.pinhole.x < 1200) {
                    int distToWallX = (ultimate.pinhole.x - upperleft.x) / CELLSIZE;
                    int distToWallY = (ultimate.pinhole.y - upperleft.y) / CELLSIZE;
                    addBlock(distToWallX, distToWallY - 1, Color.darkGray);
                }
            }

            pather = new Mover(ultimate.pinhole.x, ultimate.pinhole.y);
            minMovements++;
            goalx = (ultimate.pinhole.x - upperleft.x) / CELLSIZE - 1;
            goaly = (ultimate.pinhole.y - upperleft.y) / CELLSIZE - 1;
        }
    }

    public static void patherDirectionalChecker() {
        tempPaths.clear();
        if (!pather.upCheck()) {
            tempPaths.add(Mover.upNeighbor());
        }
        if (!pather.leftCheck()) {
            tempPaths.add(Mover.leftNeighbor());
        }
        if (!pather.downCheck()) {
            tempPaths.add(Mover.downNeighbor());
        }
        if (!pather.rightCheck()) {
            tempPaths.add(Mover.rightNeighbor());
        }
    }

    public static void pathStart() {
        pathArray.add(new RectangleImage(new Posn(240, 160), CELLSIZE, CELLSIZE, Color.yellow)); // first segment of optimal path is where user starts
    }

    public static boolean canMove() {
        return (!Mover.upCheck() || !Mover.leftCheck() || !Mover.downCheck() || !Mover.rightCheck());
    }

    /// World building functions ///
    public static void addBackground() {
        Color color;
        for (int x = 0; x < CELLSWIDE; x++) {
            for (int y = 0; y < CELLSHIGH; y++) {
                int XSTART = upperleft.x + x * CELLSIZE;
                int YSTART = upperleft.y + y * CELLSIZE;
                if (x == 0 || y == 0 || x == CELLSWIDE - 1 || y == CELLSHIGH - 1) {
                    color = Color.gray;
                } else {
                    color = Color.lightGray;
                }
                worldArray.add(new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
            }
        }
    }

    public static void addBlock(int x, int y, Color color) {
        int XSTART = upperleft.x + (x % (CELLSWIDE - 2) + 1) * CELLSIZE;
        int YSTART = upperleft.y + (y % (CELLSHIGH - 2) + 1) * CELLSIZE;
        worldArray.add(new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
    }

    public static void addConfusion() {
        for (int x = 0; x < CELLSWIDE; x++) {
            for (int y = 0; y < CELLSHIGH; y++) {
                int XSTART = 240 + x * CELLSIZE;
                int YSTART = 160 + y * CELLSIZE;
                if (!(pathArray.contains(new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, Color.yellow)))
                        && !(x == 0 && y == 0) && !(x == endx && y == endy)) { // can't spawn rocks in optimal path, start, or end
                    int probOfRock = 20;
                    boolean rockHuh = new Random().nextInt(probOfRock) == 0;
                    if (rockHuh) {
                        addBlock(x, y, Color.darkGray);
                    }
                }
            }
        }
    }

    public static void setGoal() {
        endx = (pathArray.get(pathArray.size() - 1).pinhole.x - upperleft.x) / CELLSIZE - 1;
        endy = (pathArray.get(pathArray.size() - 1).pinhole.y - upperleft.y) / CELLSIZE - 1;
    }

    public static ArrayList<RectangleImage> allTheSmallThings() {
        addBackground();
        pathStart();
        pathGen();
        addBlock(goalx, goaly, Color.cyan);
        setGoal();
//        addConfusion();
        worldArray.trimToSize();
        return worldArray;
    }

    public WorldImage buildWorld() {
        WorldImage newscene = universe;
        for (int i = 0; i < worldArray.size(); i++) {
            RectangleImage temp = worldArray.get(i);
            newscene = new OverlayImages(newscene, temp);
        }
//        // uncomment this block to see optimal path
//        for (int i = 0; i < pathArray.size(); i++) {
//            RectangleImage path = pathArray.get(i);
//            newscene = new OverlayImages(newscene, path);
//        }
        return new OverlayImages(newscene,
                new OverlayImages(showScore(), (Char.charImage())));
    }

    /// Auxiliary functions ///
    public static int randomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public WorldImage showScore() {
        return new OverlayImages(new TextImage(new Posn(WIDTH / 2, 40), "Can you beat this level in fewer than " + (minMovements + 1) + " moves?", 20, Color.white),
                new TextImage(new Posn(WIDTH / 2, 65), "You have moved: " + movements + " times!", 20, Color.white));
    }

    /// Game worlds functions ///
    public Game1(WorldImage uni) {
        super();
        this.universe = uni;
    }

    public WorldImage makeImage() {
        return buildWorld(); // returns everything in worldArray
    }

    public void onTick() {
        if (Char.onGoal()) {
            thisIsTheEnd = true;
        } else {
            buildWorld();
        }
    }

    public void onKeyEvent(String key) {
        Char.move(key);
    }

    public WorldEnd worldEnds() {
        String winText;
        if (thisIsTheEnd) {
            if (movements <= minMovements) {
                winText = "Great job! That only took you " + movements + " moves!";
            } else if (movements <= (minMovements + 10)) {
                winText = "Not bad! That was " + movements + " moves.";
            } else {
                winText = "What are you doing?? What took you " + movements + " moves?";
            }
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(WIDTH / 2, HEIGHT / 2), winText, 40, Color.white)));
        } else {
            return new WorldEnd(false, this.makeImage());
        }
    }

    public static void main(String[] args) throws Exception {
        allTheSmallThings();
        Game1.TestWorld.test();
        System.out.println("all test passed :)");
        Game1 game = new Game1(universe);
        game.bigBang(WIDTH, HEIGHT, .1);
    }
}

/*
 You should write a short game manual that describes the rules of your game. You should run this past me so we can agree that the game is complex and interesting enough. You should use the invariants of your game to design testable components. 

 You should be able to build a completely automated version of your game for testing. For example, in my Tetris game, I might parameterize the game over a Tetrimino generator and a Input stream, so that I can test explicit sequences of inputs on Block sequences and ensure that the rules of the game are enforced.
 */

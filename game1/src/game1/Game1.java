package game1;

import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.*;
import java.util.*;

public class Game1 extends World {

    // clean these up later
    static int WIDTH = 1440;
    static int HEIGHT = 800;
    static int OFFSET = 8;
    static int BACKWIDTH = (OFFSET - 2) * WIDTH / OFFSET;
    static int BACKHEIGHT = (OFFSET - 2) * HEIGHT / OFFSET;
    static int CELLSIZE = 40;
    static int CELLSWIDE = BACKWIDTH / CELLSIZE;
    static int CELLSHIGH = BACKHEIGHT / CELLSIZE;
    static int movements = 0;
    static int minMovements = 0;
    static Posn base = new Posn(WIDTH / 2, HEIGHT / 2);
    static Posn upperleft = new Posn(200, 120);
    static Random rand = new Random();
    public static WorldImage universe = new RectangleImage(base, WIDTH, HEIGHT, Color.black);
    public static WorldImage background = new RectangleImage(base, BACKWIDTH, BACKHEIGHT, Color.lightGray);
    public static RectangleImage start = new RectangleImage(new Posn(240, 160), CELLSIZE, CELLSIZE, Color.green);
    public static ArrayList<RectangleImage> worldArray = new ArrayList<RectangleImage>();

    public static class Char {

        static int charx = 240; // + randomInt(0,24) * CELLSIZE;
        static int chary = 160; // + randomInt(0,12) * CELLSIZE;

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
            }
        }

        /// Checker functions ///
        // checks to see if block above Char is a solid block
        public static boolean upCheck() {
            int checkx = Char.charPos().x;
            int checky = Char.charPos().y - CELLSIZE;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            return (worldArray.contains(gray) || worldArray.contains(darkGray));
        }

        // checks to see if block left of Char is a solid block
        public static boolean leftCheck() {
            int checkx = Char.charPos().x - CELLSIZE;
            int checky = Char.charPos().y;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            return (worldArray.contains(gray) || worldArray.contains(darkGray));
        }

        // checks to see if block below Char is a solid block
        public static boolean downCheck() {
            int checkx = Char.charPos().x;
            int checky = Char.charPos().y + CELLSIZE;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            return (worldArray.contains(gray) || worldArray.contains(darkGray));
        }

        // checks to see if block right of Char is a solid block
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

        /// Checker functions ///
        // checks to see if block above Char is a solid block or was just moved to ("is yellow")
        public static boolean upCheck() {
            int checkx = Mover.moverPos().x;
            int checky = Mover.moverPos().y - CELLSIZE;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            RectangleImage yellow = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.yellow);
            return (worldArray.contains(gray) || worldArray.contains(darkGray) || pathArray.contains(yellow));
        }

        // checks to see if block left of Mover is a solid block or was just moved to ("is yellow")
        public static boolean leftCheck() {
            int checkx = Mover.moverPos().x - CELLSIZE;
            int checky = Mover.moverPos().y;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            RectangleImage yellow = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.yellow);
            return (worldArray.contains(gray) || worldArray.contains(darkGray) || pathArray.contains(yellow));
        }

        // checks to see if block below Mover is a solid block or was just moved to ("is yellow")
        public static boolean downCheck() {
            int checkx = Mover.moverPos().x;
            int checky = Mover.moverPos().y + CELLSIZE;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            RectangleImage yellow = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.yellow);
            return (worldArray.contains(gray) || worldArray.contains(darkGray) || pathArray.contains(yellow));
        }

        // checks to see if block right of Mover is a solid block or was just moved to ("is yellow")
        public static boolean rightCheck() {
            int checkx = Mover.moverPos().x + CELLSIZE;
            int checky = Mover.moverPos().y;
            RectangleImage gray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.gray);
            RectangleImage darkGray = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.darkGray);
            RectangleImage yellow = new RectangleImage(new Posn(checkx, checky), CELLSIZE, CELLSIZE, Color.yellow);
            return (worldArray.contains(gray) || worldArray.contains(darkGray) || pathArray.contains(yellow));
        }

        public static WorldImage moverImage() {
            return new RectangleImage(moverPos(), CELLSIZE, CELLSIZE, Color.yellow);
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

    /// Path solving functions ///
    // starts in same place as Char does to solve optimal path from your pov
    static Mover pather = new Mover(Char.charPos().x, Char.charPos().y);

    private static ArrayList<RectangleImage> pathArray = new ArrayList<RectangleImage>();
    private static ArrayList<RectangleImage> tempPaths = new ArrayList<RectangleImage>();

    public static void pathGen() {
//        int goalx = 0, goaly = 0;
        while (canMove()) {
            patherDirectionalChecker(); // tempPaths now holds all valid next-squares (non-solids and non-just-moved-froms)
            int direction = randomInt(0, tempPaths.size() - 1); // picks an integer that corresponds with the direction of next movement
            pathArray.add(tempPaths.get(direction)); // add that square to pathArray, that's where we're going next
            RectangleImage ultimate = pathArray.get(pathArray.size() - 1); // last thing in pathArray
            RectangleImage penultimate = pathArray.get(pathArray.size() - 2); // second last thing in pathArray
            int dx = (ultimate.pinhole.x - penultimate.pinhole.x) / CELLSIZE; // how we figure out which direction we just went
            int dy = (ultimate.pinhole.y - penultimate.pinhole.y) / CELLSIZE; // how we figure out which direction we just went
            int distToUpWall = (ultimate.pinhole.y - 160) / CELLSIZE;
            int distToLeftWall = (ultimate.pinhole.x - 240) / CELLSIZE;
            int distToDownWall = (640 - ultimate.pinhole.y) / CELLSIZE;
            int distToRightWall = (1200 - ultimate.pinhole.x) / CELLSIZE;

            if (dy < 0) {
                int reps = Math.min(randomInt(0, distToUpWall), 4); // prevents game from being impossibly hard
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
                        addRock(nextIsRock.pinhole.x, nextIsRock.pinhole.y);
                    } else {
                        pathArray.add(nextPath);
                        ultimate = pathArray.get(pathArray.size() - 1);
                        counter++;
                    }
                }
                if (ultimate.pinhole.y > 160) {
                    int distToWallX = (ultimate.pinhole.x - upperleft.x) / CELLSIZE;
                    int distToWallY = (ultimate.pinhole.y - upperleft.y) / CELLSIZE;
                    addRock(distToWallX - 1, distToWallY - 2);
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
                        addRock(nextIsRock.pinhole.x, nextIsRock.pinhole.y);
                    } else {
                        pathArray.add(nextPath);
                        ultimate = pathArray.get(pathArray.size() - 1);
                        counter++;
                    }
                }
                if (ultimate.pinhole.x > 240) {
                    int distToWallX = (ultimate.pinhole.x - upperleft.x) / CELLSIZE;
                    int distToWallY = (ultimate.pinhole.y - upperleft.y) / CELLSIZE;
                    addRock(distToWallX - 2, distToWallY - 1);
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
                        addRock(nextIsRock.pinhole.x, nextIsRock.pinhole.y);
                    } else {
                        pathArray.add(nextPath);
                        ultimate = pathArray.get(pathArray.size() - 1);
                        counter++;
                    }
                }
                if (ultimate.pinhole.y < 640) {
                    int distToWallX = (ultimate.pinhole.x - upperleft.x) / CELLSIZE;
                    int distToWallY = (ultimate.pinhole.y - upperleft.y) / CELLSIZE;
                    addRock(distToWallX - 1, distToWallY);
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
                        addRock(nextIsRock.pinhole.x, nextIsRock.pinhole.y);
                    } else {
                        pathArray.add(nextPath);
                        ultimate = pathArray.get(pathArray.size() - 1);
                        counter++;
                    }
                }
                if (ultimate.pinhole.x < 1200) {
                    int distToWallX = (ultimate.pinhole.x - upperleft.x) / CELLSIZE;
                    int distToWallY = (ultimate.pinhole.y - upperleft.y) / CELLSIZE;
                    addRock(distToWallX, distToWallY - 1);
                }
            }

            pather = new Mover(ultimate.pinhole.x, ultimate.pinhole.y);
            minMovements++;
//            goalx = (ultimate.pinhole.x - upperleft.x) / CELLSIZE - 1;
//            goaly = (ultimate.pinhole.y - upperleft.y) / CELLSIZE;
            System.out.println("pathGen is ok");
        }

        //  addGoal(goalx, goaly);
    }

    public static void patherDirectionalChecker() {
        tempPaths.clear();
        // if these paths are legal (i.e., not solid and not just moved from), add them to tempPaths
        if (!pather.upCheck()) {
            tempPaths.add(pather.upNeighbor());
        }
        if (!pather.leftCheck()) {
            tempPaths.add(pather.leftNeighbor());
        }
        if (!pather.downCheck()) {
            tempPaths.add(pather.downNeighbor());
        }
        if (!pather.rightCheck()) {
            tempPaths.add(pather.rightNeighbor());
        }
    }

    public static void pathStart() {
        pathArray.add(new RectangleImage(new Posn(240, 160), CELLSIZE, CELLSIZE, Color.yellow)); // first segment of optimal path is where user starts
    }

    public static boolean canMove() {
        return (!pather.upCheck() || !pather.leftCheck() || !pather.downCheck() || !pather.rightCheck());
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

    public static void addRock(int x, int y) {
        Color color = Color.darkGray;
        int XSTART = upperleft.x + (x % (CELLSWIDE - 2) + 1) * CELLSIZE;
        int YSTART = upperleft.y + (y % (CELLSHIGH - 2) + 1) * CELLSIZE;
        worldArray.add(new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
    }

    public static void addGoal(int x, int y) {
        Color color = Color.cyan;
        int XSTART = upperleft.x + (x % (CELLSWIDE - 2) + 1) * CELLSIZE;
        int YSTART = upperleft.y + (y % (CELLSHIGH - 2) + 1) * CELLSIZE;
        worldArray.add(new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
    }

    public static ArrayList<RectangleImage> allTheSmallThings() {
        pathStart();
        addBackground();
        pathGen();
        worldArray.trimToSize();
        return worldArray;
    }

    public WorldImage buildWorld() {
        WorldImage newscene = universe;
        for (int i = 0; i < worldArray.size(); i++) {
            RectangleImage temp = worldArray.get(i);
            newscene = new OverlayImages(newscene, temp);
        }

        // will update this to do "if (mode = SOLVERMODE) { show this part} else {don't}"
        // so user doesn't ever see the yellow
        for (int i = 0; i < pathArray.size(); i++) {
            RectangleImage path = pathArray.get(i);
            newscene = new OverlayImages(newscene, path);
        }

        return new OverlayImages(newscene,
                new OverlayImages(showScore(), Char.charImage()));
    }

    /// Auxiliary functions ///
    public static int randomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static Color randomColor() {
        return new Color(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255));
    }

    public WorldImage winScreen(WorldImage background) {
        WorldImage newscene = background;
        for (int x = 0; x < CELLSWIDE; x++) {
            for (int y = 0; y < CELLSHIGH; y++) {
                int XSTART = upperleft.x + x * CELLSIZE;
                int YSTART = upperleft.y + y * CELLSIZE;
                newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, randomColor()));
            }
        }
        return newscene;
    }

    public WorldImage showScore() {
        return new OverlayImages(new TextImage(new Posn(WIDTH / 2, 40), "Can you beat this level in " + minMovements + " moves?", 20, Color.white),
                new TextImage(new Posn(WIDTH / 2, 65), "You have moved: " + movements + " times!", 20, Color.white));
    }

    /// Tester functions ///

    /*
     more to come!
     */
    /// Game worlds functions ///
    public Game1(WorldImage uni) {
        super();
        this.universe = uni;
    }

    public void onTick() {
        buildWorld();
    }

    public void onKeyEvent(String key) {
        Char.move(key);
    }

    public WorldImage makeImage() {
        return buildWorld(); // returns everything in worldArray
    }

    public static void main(String[] args) {
        allTheSmallThings(); // populates worldArray
        System.out.println("ok ");
        Game1 game = new Game1(universe);
        game.bigBang(WIDTH, HEIGHT);
    }
}

/*
 A win or fail state.

 You should write a short game manual that describes the rules of your game. You should run this past me so we can agree that the game is complex and interesting enough. You should use the invariants of your game to design testable components. 

 You should be able to build a completely automated version of your game for testing. For example, in my Tetris game, I might parameterize the game over a Tetrimino generator and a Input stream, so that I can test explicit sequences of inputs on Block sequences and ensure that the rules of the game are enforced.
 */

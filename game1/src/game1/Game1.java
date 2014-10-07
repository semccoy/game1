package game1;

import tester.*;
/*
 // IDEA:
 // basic idea: that sliding thing from pokemon - http://cdn.bulbagarden.net/upload/2/2c/Slip_ice_demo.gif
 // fuller idea: http://bulbapedia.bulbagarden.net/wiki/Ice_tile
 // how to solve these things: http://bulbanews.bulbagarden.net/wiki/Crunching_the_numbers:_Graph_theory
 // problems to face: http://stackoverflow.com/questions/8876415/randomly-generate-directed-graph-on-a-grid

 // TODO:
 // - make moving functions
 // - scoring functions
 // - levels
 // - make solver
 // - make random start point
 // - make random paths
 // - make random rocks to accomodate random path
 // - make random rocks off of solve path to confuse players
 // - grading things
 // - buttons in the universe to click to restart level / whole game?
 // - "this is impossible" button? no guarantee that game is playable
 //     so let "unsolvable" be a solution?
 */
import javalib.impworld.*;   //funworld or impworld?
import javalib.worldimages.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    static Posn base = new Posn(WIDTH / 2, HEIGHT / 2);
    static Posn upperleft = new Posn(200, 120);
    static Random rand = new Random();
    public static WorldImage universe = new RectangleImage(base, WIDTH, HEIGHT, Color.black);
    public static WorldImage background = new RectangleImage(base, BACKWIDTH, BACKHEIGHT, Color.lightGray);
    public static WorldImage character = new RectangleImage(new Posn(240, 160), CELLSIZE, CELLSIZE, Color.green);
    private static ArrayList<RectangleImage> worldArray = new ArrayList<RectangleImage>();

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

        // works fine but doesn't move continuously/smoothly across screen, minor detail
        public static void move(String key) {
            if ((key.equals("up") || key.equals("w")) && !upCheck()) {
                while (!upCheck()) {
                    chary = chary - CELLSIZE;
                }
            } else if ((key.equals("left") || key.equals("a")) && !leftCheck()) {
                while (!leftCheck()) {
                    charx = charx - CELLSIZE;
                }
            } else if ((key.equals("down") || key.equals("s")) && !downCheck()) {
                while (!downCheck()) {
                    chary = chary + CELLSIZE;
                }
            } else if ((key.equals("right") || key.equals("d")) && !rightCheck()) {
                while (!rightCheck()) {
                    charx = charx + CELLSIZE;
                }
            }
        }

        public static WorldImage charImage() {
            return new RectangleImage(charPos(), CELLSIZE, CELLSIZE, Color.green);
        }

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
        addBackground();
        addRock(5, 7);
        addRock(15, 7);
        addGoal(10, 7);
        worldArray.trimToSize();
        return worldArray;
    }

    public WorldImage buildWorld() {
        WorldImage newscene = universe;
        for (int i = 0; i < worldArray.size(); i++) {
            RectangleImage temp = worldArray.get(i);
            newscene = new OverlayImages(newscene, temp);
        }
        return new OverlayImages(newscene, Char.charImage());
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
 A field of play where the blocks move.

 A set of "live" blocks that are player controlled. (This "set" may contain one block.)

 A set of "dead" blocks that are no longer player controlled.

 A scoring system.

 A win or fail state.

 A control mechanism.

 You should write a short game manual that describes the rules of your game. You should run this past me so we can agree that the game is complex and interesting enough. You should use the invariants of your game to design testable components. 

 You should be able to build a completely automated version of your game for testing. For example, in my Tetris game, I might parameterize the game over a Tetrimino generator and a Input stream, so that I can test explicit sequences of inputs on Block sequences and ensure that the rules of the game are enforced.
 */

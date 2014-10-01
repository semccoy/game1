package game1;

import tester.*;
/*
 // IDEA:
 // basic idea: that sliding thing from pokemon - http://cdn.bulbagarden.net/upload/2/2c/Slip_ice_demo.gif
 // fuller idea: http://bulbapedia.bulbagarden.net/wiki/Ice_tile
 // how to solve these things: http://bulbanews.bulbagarden.net/wiki/Crunching_the_numbers:_Graph_theory
 // problems to face: http://stackoverflow.com/questions/8876415/randomly-generate-directed-graph-on-a-grid

 // TODO:
 // - make objects (border rocks, character, internal node rocks)
 // - make "victory space"
 // - make non-random rock function and start functions for the time being
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
import javalib.funworld.*;
import javalib.worldimages.*;
import javalib.worldcanvas.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Game1 extends World {

    static Random rand = new Random();
    static int WIDTH = 1440;
    static int HEIGHT = 800;
    static int OFFSET = 8;
    static int BACKWIDTH = (OFFSET - 2) * WIDTH / OFFSET;
    static int BACKHEIGHT = (OFFSET - 2) * HEIGHT / OFFSET;
    static Posn base = new Posn(WIDTH / 2, HEIGHT / 2);
    static Posn upperleft = new Posn(200, 120);
    static int CELLSIZE = 40;
    static int CELLSWIDE = BACKWIDTH / CELLSIZE;
    static int CELLSHIGH = BACKHEIGHT / CELLSIZE;
    int randomCharXStart = randomInt(1, CELLSWIDE - 2);
    int randomCharYStart = randomInt(1, CELLSHIGH - 2);
    static int numRocks = 3; // internal rocks

    public static WorldImage universe = new RectangleImage(base, WIDTH, HEIGHT, Color.black);
    public static WorldImage background = new RectangleImage(base, BACKWIDTH, BACKHEIGHT, Color.lightGray);

    public WorldImage drawField(WorldImage background) {
        Color color;
        WorldImage newscene = background;
        for (int x = 0; x < CELLSWIDE; x++) {
            for (int y = 0; y < CELLSHIGH; y++) {
                int XSTART = upperleft.x + x * CELLSIZE;
                int YSTART = upperleft.y + y * CELLSIZE;
                if (x == 0 || y == 0 || x == CELLSWIDE - 1 || y == CELLSHIGH - 1) {
                    color = Color.red;
                } else {
                    color = Color.white;
                }
                newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
            }
        }
        return newscene;
    }

    // use this when someone beats a level
    // also increase game speed for a few seconds for maximum celebratory effect
    public WorldImage drawFieldWin(WorldImage background) {
        Color color;
        WorldImage newscene = background;
        for (int x = 0; x < CELLSWIDE; x++) {
            for (int y = 0; y < CELLSHIGH; y++) {
                int XSTART = upperleft.x + x * CELLSIZE;
                int YSTART = upperleft.y + y * CELLSIZE;
                if (x == 0 || y == 0 || x == CELLSWIDE - 1 || y == CELLSHIGH - 1) {
                    color = randomColor();
                } else {
                    color = randomColor();
                }
                newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
            }
        }
        return newscene;
    }

    // the issue with this atm is that everytime the world is drawn (like every game tick),
    // drawRock is called again. since drawRock uses random numbers for positions of rocks,
    // every time it's called, the rocks move around. i want the rocks to stay static over time
    // ideally i could create the random numbers elsewhere and use another function
    // to get them into drawRock, but haven't been able to do that yet. probably overkill too

    // a rock appears in any given space with probability until numRocks rocks exist
    public WorldImage drawRock(WorldImage background) {
        Color color = Color.gray;
        WorldImage newscene = background;
        int rockCounter = 0; // number of rocks present
        int chanceOfRock = 1000; // higher = lower chance of rock
        while (rockCounter < numRocks) {
            for (int x = 1; x < CELLSWIDE - 1; x++) {
                for (int y = 1; y < CELLSHIGH - 1; y++) {
                    boolean rockOrNot = rand.nextInt(chanceOfRock) == 0; // small enough that it's not distributed too heavily on the left
                    if (rockOrNot & (rockCounter < numRocks)) {
                        rockCounter++;
                        int XSTART = upperleft.x + x * CELLSIZE;
                        int YSTART = upperleft.y + y * CELLSIZE;
                        newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
                    }
                }
            }
        }
        return newscene;
    } // check if game is not over, if so, then return a static random rock display

    public WorldImage drawCharacter(WorldImage background) {
        Color color = Color.green;
        WorldImage newscene = background;
        int XSTART = upperleft.x + CELLSIZE;
        int YSTART = upperleft.y + CELLSIZE;
        newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
        return newscene;
    }

    public WorldImage drawRandomCharacter(WorldImage background) {
        Color color = Color.green;
        WorldImage newscene = background;
        int XSTART = upperleft.x + randomCharXStart * CELLSIZE;
        int YSTART = upperleft.y + randomCharYStart * CELLSIZE;
        newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
        return newscene;
    }

//    public WorldImage drawGoal(WorldImage background) {
//        Color color = Color.orange;
//        WorldImage newscene = background;
//        int XSTART = upperleft.x + 1000;
//        int YSTART = upperleft.y + 1000;
//        newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
//        return newscene;
//    }
//
//    // should be on outside rim somewhere
//    public WorldImage drawRandomGoal(WorldImage background) {
//        Color color = Color.orange;
//        WorldImage newscene = background;
//        int XSTART = upperleft.x + randomCharXStart * CELLSIZE;
//        int YSTART = upperleft.y + randomCharYStart * CELLSIZE;
//        newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
//        return newscene;
//    }
//    static int[] xarray = new int[CELLSWIDE - 2];
//    static int[] yarray = new int[CELLSHIGH - 2];
//
//    public static void test() {
//
//        for (int i = 0; i < CELLSWIDE - 2; i++) {
//            int x1 = randomInt(1, CELLSWIDE - 1);
//            xarray[i] = x1;
//        }
//        for (int j = 0; j < CELLSHIGH - 2; j++) {
//            int y1 = randomInt(1, CELLSHIGH - 1);
//            yarray[j] = y1;
//        }
//    }
    public static int randomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static Color randomColor() {
        return new Color(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255));
    }

    public Game1(WorldImage uni) {
        super();
        this.universe = uni;
    }

    public World onTick() {
        return this;
    }

    public WorldImage makeImage() {
        return new OverlayImages(this.universe, // just stack everything in one OverlayImage
                new OverlayImages(drawField(background), drawCharacter(drawRock(drawField(background))))); // this can probably be a lot shorter
        //new OverlayImages(drawIntRocks(drawField(this.background)), drawCharacter(drawIntRocks(drawField(this.background))))));
    }

    public static void main(String[] args) {
        System.out.println("ok ");
        Game1 game = new Game1(universe);
        game.bigBang(WIDTH, HEIGHT);
    }
}

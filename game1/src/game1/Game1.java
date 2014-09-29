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
import java.util.Random;

public class Game1 extends World {

    static Random rand = new Random();
    static int WIDTH = 1440;
    static int HEIGHT = 800;
    static int OFFSET = 8;
    static final int BACKWIDTH = (OFFSET - 2) * WIDTH / OFFSET;
    static int BACKHEIGHT = (OFFSET - 2) * HEIGHT / OFFSET;
    static Posn base = new Posn(WIDTH / 2, HEIGHT / 2); // centers things
    static Posn upperleft = new Posn(200, 120);
    static int CELLSIZE = 40;
    static int CELLSWIDE = BACKWIDTH / CELLSIZE;
    static int CELLSHIGH = BACKHEIGHT / CELLSIZE;

    public static RectangleImage[][] field;

    public static WorldImage universe = new RectangleImage(base, WIDTH, HEIGHT, Color.black);
    public static WorldImage background = new RectangleImage(base, BACKWIDTH, BACKHEIGHT, Color.lightGray);

    // so this method currently returns a multidimensional array ("field") of rectangle images
    // i want to print out every rectangle image stored in that array, maintaining thier
     // original positions, sizes, and colors whats the easiest way to do this?
    public RectangleImage setUpWorld() {
        field = new RectangleImage[CELLSWIDE][CELLSHIGH];
        for (int x = 0; x < CELLSWIDE; x++) {
            for (int y = 0; y < CELLSHIGH; y++) {
                int XSTART = upperleft.x + x * CELLSIZE;
                int YSTART = upperleft.y + y * CELLSIZE;
                if (x == 0 || y == 0 || x == CELLSWIDE - 1 || y == CELLSHIGH - 1) {
                    field[x][y] = new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, Color.red);
                } else {
                    field[x][y] = new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, Color.white);
                }
            }
        }
      return field[18][5];
    }

    // add to a list?
//    public static RectangleImage arrayPrinter(RectangleImage[][] field) {
//
//        for (x = CELLSWIDE; x > 0; x--) {
//            if (x == 0 || y == 0 || x == CELLSWIDE - 1 || y == CELLSHIGH - 1) {
//                return new RectangleImage(new Posn(200 + x * CELLSIZE, 120 + y * CELLSIZE), CELLSIZE, CELLSIZE, Color.red);
//            } else {
//                return new OverlayImages(field[0][0], arrayGrabber(field, x, y - 1));
//            }
//        }
//        return new OverlayImages(field[0][0], arrayGrabber(field, x - 1, y - 1));
//    }
    public static int randomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static Color randomColor() {
        return new Color(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255));
    }

    public Game1(WorldImage uni, RectangleImage[][] field) {
        super();
        this.universe = uni;
        this.field = field;
    }

    public World onTick() {
        return this;
    }

    public WorldImage makeImage() {
        return new OverlayImages(this.universe,
                new OverlayImages(this.background, setUpWorld()));
    }

    public static void main(String[] args) {
        System.out.println("ok ");
        Game1 game = new Game1(universe, field);
        game.bigBang(WIDTH, HEIGHT, 5);
    }
}

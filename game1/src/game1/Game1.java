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
import java.awt.*;
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
    static Posn lowerright = new Posn(1240, 680);
    static int CELLSIZE = 40;
    static int CELLSWIDE = BACKWIDTH / CELLSIZE;
    static int CELLSHIGH = BACKHEIGHT / CELLSIZE;

    public static WorldImage universe = new RectangleImage(base, WIDTH, HEIGHT, Color.black);
    public static WorldImage background = new RectangleImage(base, BACKWIDTH, BACKHEIGHT, Color.lightGray);
    
    
    public Square[] worldArray;
    // all drawing functions should just add new Squares to worldArray
    // makeImage should just iterate through and return everything to draw the world
    
    

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

    public WorldImage drawRock(int x, int y, WorldImage background) {
        Color color = Color.gray;
        WorldImage newscene = background;
        int XSTART = upperleft.x + (x % (CELLSWIDE - 2) + 1) * CELLSIZE;
        int YSTART = upperleft.y + (y % (CELLSHIGH - 2) + 1) * CELLSIZE;
        newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
        return newscene;
    }

    public WorldImage drawCharacter(WorldImage background) {
        Color color = Color.green;
        WorldImage newscene = background;
        int XSTART = upperleft.x + CELLSIZE;
        int YSTART = upperleft.y + CELLSIZE;
        newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
        return newscene;
    }

    public WorldImage drawGoal(WorldImage background) {
        Color color = Color.cyan;
        WorldImage newscene = background;
        int XSTART = lowerright.x - CELLSIZE;
        int YSTART = lowerright.y - CELLSIZE;
        newscene = new OverlayImages(newscene, new RectangleImage(new Posn(XSTART, YSTART), CELLSIZE, CELLSIZE, color));
        return newscene;
    }

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
        return new OverlayImages(this.universe, drawCharacter(drawGoal(drawRock(6, 0, drawField(background)))));
    }

    public static void main(String[] args) {
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
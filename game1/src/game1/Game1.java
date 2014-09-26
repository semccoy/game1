package game1;

//probably dont need most of these
import tester.*;
/*
 // IDEA:
 // basic idea: that slipping and sliding thing from pokemon - http://cdn.bulbagarden.net/upload/2/2c/Slip_ice_demo.gif
 // fuller idea: http://bulbapedia.bulbagarden.net/wiki/Ice_tile
 // how to solve these things: http://bulbanews.bulbagarden.net/wiki/Crunching_the_numbers:_Graph_theory
 // problems to face: http://stackoverflow.com/questions/8876415/randomly-generate-directed-graph-on-a-grid

 // TODO:
 // - create universe - ok 
 // - make game world background - ok 
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
 // - random world size?
 // - attractive images instead of circles and squares?
 // - buttons in the universe to click to restart level / whole game?
 // - "this is impossible" button? no guarantee that game is playable
 //     so let "unsolvable" be a solution?
 */
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import javalib.worldcanvas.*;
import javalib.tunes.*;
import java.util.*;
import java.awt.*;

public class Game1 extends World {

    static int WIDTH = 1440;
    static int HEIGHT = 780;
    static int BASEX = WIDTH / 2;
    static int BASEY = HEIGHT / 2;
    static int OFFSET = 8;
    static Posn base = new Posn(BASEX, BASEY);

    public static WorldImage universe = new RectangleImage(base, WIDTH, HEIGHT, Color.black);
    // leaves a border of (1 / OFFSET * universe size) around all sides of playing field (for buttons or something in the future)
    public static WorldImage background = new RectangleImage(base,(OFFSET-2)*WIDTH/OFFSET,(OFFSET-2)*HEIGHT/OFFSET,Color.red);
    static Random rand = new Random();

    public static int randInt(int min, int max) {
        return rand.nextInt(min + (max - min) + 1);
    }

    public Game1(WorldImage uni) {
        super();
        this.universe = uni;
    }

    public World onTick() {
        return this;
    }

    public WorldImage makeImage() {
        return new OverlayImages(this.universe, this.background);
    }

    // maybe return a Rock[]
//    public Rock buildRockBoundary() {
//        int rockDiameter = 40;
//        for (int i = 0; i < WIDTH % rockDiameter; i++) {
//            for (int j = 0; j < HEIGHT % rockDiameter; j++) {
//                // still have to edit these rock placements
//
//                // need an "if rock is actually on the boundry of the background, return it" statement here
//                return new Rock(new Posn(this.WIDTH, this.HEIGHT), rockDiameter / 2, Color.GRAY);
//
//            }
//        }
//        return new Rock(new Posn(0, 0), 2, Color.gray);
//    }
    // java cant find main function - probably has to do with things being static or not
    public static void main(String[] args) {
        System.out.println("ok");
        Game1 game = new Game1(universe);
        game.bigBang(WIDTH, HEIGHT, 0.3);
    }

}

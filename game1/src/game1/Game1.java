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
 // - create universe
 // - make game world background
 // - make objects (border rocks, character, internal node rocks)
 // - make moving functions
 // - scoring functions
 // - levels
 // - make solver
 // - make random start point
 // - make random paths
 // - grading things
 // - random world size?
 // - attractive images instead of circles and squares?
 // - buttons in the universe to click to restart level / whole game?
 */
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import javalib.worldcanvas.*;
import javalib.tunes.*;
import java.util.*;
import java.awt.*;

public class Game1 {

    // could make this cooler and use the following to accomodate any screen size
    // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int WIDTH = 1200;
    int HEIGHT = 1200;
    int OFFSET = 8; //larger = bigger gap between playing field and edge of universe

    public Universe buildUniverse() {
        return new Universe(new Posn(0, 0), WIDTH, HEIGHT, Color.black);
    }

    // leaves a border of (1 / OFFSET * universe size) around all sides of playing field
    // (for buttons or something in the future)
    public Background buildBackground() {
        return new Background(new Posn(WIDTH / OFFSET, HEIGHT / OFFSET), (1 - 2 / OFFSET) * WIDTH, (1 - 2 / OFFSET) * HEIGHT, Color.white);
    }

    // maybe return a Rock[]
    public Rock buildRockBoundary() {
        int rockDiameter = 40;
        for (int i = 0; i < WIDTH % rockDiameter; i++) {
            for (int j = 0; j < HEIGHT % rockDiameter; j++) {
                // still have to edit these rock placements

                // need an "if rock is actually on the boundry of the background, return it" statement here
                return new Rock(new Posn(this.WIDTH, this.HEIGHT), rockDiameter / 2, Color.GRAY);

            }
        }
        return new Rock(new Posn(0, 0), 2, Color.gray);
    }

    public void bigBang(int w, int h, double speed) {
        System.out.println("bang!");
    }

    // java cant find main function - probably has to do with things being static or not
    public void main(String[] args) {

        buildUniverse();
        buildBackground();

        System.out.println("ok");

    }

}

package game1;

//probably dont need half of these
import tester.*;
import javalib.impworld.*;
import javalib.funworld.*;
import javalib.appletworld.*;
import javalib.soundworld.*;
import javalib.appletsoundworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import javalib.worldcanvas.*;
import javalib.tunes.*;
import java.util.*;
import java.awt.Color;

public class Game1 {

    // IDEA:
    // basic idea: that slipping and sliding thing from pokemon - http://cdn.bulbagarden.net/upload/2/2c/Slip_ice_demo.gif
    // fuller idea: http://bulbapedia.bulbagarden.net/wiki/Ice_tile
    // how to solve these things: http://bulbanews.bulbagarden.net/wiki/Crunching_the_numbers:_Graph_theory
    // problems to face: http://stackoverflow.com/questions/8876415/randomly-generate-directed-graph-on-a-grid
    //
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
    //
    //
    class Universe {

        int width;
        int height;
        Posn location;
        IColor color;

        // constructor
        Universe(int width, int height, IColor color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        WorldImage universe() {
            //return new universe to house everything
            return new RectangleImage(location, width, height, new Red());
        }
    }

    class Background {

        int width;
        int height;
        Posn location;
        IColor color;

        // constructor
        Background(int width, int height, IColor color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        WorldImage background() {
            //return new background
            return new RectangleImage(location, width, height, new White());
        }
    }

    class Character {

        int width;
        int height;
        Posn location;
        IColor color;

        // constructor
        Character(int width, int height, IColor color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        WorldImage character() {
            //return new background
            return new RectangleImage(location, width, height, new Yellow());
        }
    }

    class Rock {

        int width;
        int height;
        Posn location;
        IColor color;

        // constructor
        Rock(int width, int height, IColor color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        WorldImage rock() {
            //return new background
            return new RectangleImage(location, width, height, new Black());
        }
    }

    // public WorldCanvas theCanvas;
    // public WorldImage grid = new RectangleImage(new Posn(0, 0), 2000, 2000, new Black());
    public void bigBang(int w, int h, double speed) {
        System.out.println("bang!");

    }

    public void main(String[] args) {
        System.out.println("ok");
        //bigBang(1200, 800, 5);

    }

}

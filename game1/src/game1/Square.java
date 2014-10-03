package game1;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.Random;

public class Square {

    public Posn location;
    Color color;
    static int CELLSWIDE;
    static int CELLSHIGH;
    int speed = 100;

    public Square(Posn location, Color color) {
        this.location = location;
    }

//    public Square move() {
//        switch (ek) {
//        // moves in direction of keypressed until hits block square in worldarray
//        }
//    }
}

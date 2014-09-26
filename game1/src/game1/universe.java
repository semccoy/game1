package game1;

import java.awt.Color;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

public class Universe {

    int width;
    int height;
    Posn position;
    Color color;
    Background background;
    Character character;
    Rock rock;

    // constructor
    Universe(Posn position, int width, int height, Color color) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.color = color;
        //this.background/character/rock = ...
    }

    WorldImage universeImage() {
        //return new universe to house everything
        return new RectangleImage(this.position, this.width, this.height, this.color);
    }
}
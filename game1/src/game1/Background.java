package game1;

import java.awt.Color;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

public class Background {

    int width;
    int height;
    Posn position;
    Color color;

    // constructor
    Background(Posn position, int width, int height, Color color) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    WorldImage backgroundImage() {
        //return new background
        return new RectangleImage(this.position, this.width, this.height, this.color);
    }
}

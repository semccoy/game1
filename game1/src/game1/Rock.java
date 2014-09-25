package game1;

import java.awt.Color;
import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

public class Rock {

    int radius;
    Posn position;
    Color color;

    // constructor
    Rock(Posn position, int radius, Color color) {
        this.position = position;
        this.radius = radius;
        this.color = color;
    }

    WorldImage rockImage() {
        //return new rock
        return new CircleImage(this.position, this.radius, this.color);
    }
}

package game1;

import java.awt.Color;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// havent started this yet

public class Character {

    int width;
    int height;
    Posn position;
    Color color;

    // constructor
    Character(Posn position, int width, int height, Color color) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    //MOVING FUNCTIONS HERE
    WorldImage characterImage() {
        //return new character
        return new RectangleImage(this.position, this.width, this.height, this.color);
    }
}

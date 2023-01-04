package agh.ics.oop.mapElements;

import agh.ics.oop.auxiliary.Vector2d;

public class Grass implements IMapElement{
    private Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2d newPosition) {
        this.position=newPosition;
    }

    public static String getImageResource() {
        return "src/main/resources/grass.png";
    }
}
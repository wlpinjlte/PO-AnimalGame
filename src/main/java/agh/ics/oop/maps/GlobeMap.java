package agh.ics.oop.maps;
import agh.ics.oop.CONSTANT;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.mapElements.Animal;
import agh.ics.oop.mapElements.MapStats;

public class GlobeMap extends AbstractWorldMap{
    public GlobeMap(int height, int width, CONSTANT constant, MapStats mapStats){
        super(height,width,constant,mapStats);
    }

    @Override
    public void positionChange(Animal element, Vector2d oldPosition, Vector2d newPosition){
        if(newPosition.precedes(endOfMap)&& newPosition.follows(startOfMap)){
            super.positionChange(element,oldPosition,newPosition);
        }else {
            element.setDirection(element.getDirection().opposite());
        }
    }
}

package agh.ics.oop.maps;

import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.mapElements.Animal;

public class GlobeMap extends AbstractWorldMap{

    public GlobeMap(int height,int width){
        super(height,width);
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

package agh.ics.oop.maps;

import agh.ics.oop.CONSTANT;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.mapElements.Animal;
import agh.ics.oop.mapElements.MapStats;

public class HellPortal extends AbstractWorldMap {
    public HellPortal(int height, int width, CONSTANT constant, MapStats mapStats){
        super(height,width,constant,mapStats);
    }

    @Override
    public void positionChange(Animal element, Vector2d oldPosition, Vector2d newPosition){
        if(newPosition.precedes(endOfMap)&& newPosition.follows(startOfMap)){
            super.positionChange(element,oldPosition,newPosition);
        }else{
            int positionX=(int)(Math.random()*endOfMap.x());
            int positionY=(int)(Math.random()*endOfMap.y());
            element.changeEnergy(-CONSTANT.COSTTOCONCIEVECHILDREN);
            super.positionChange(element,oldPosition,new Vector2d(positionX,positionY));
        }
    }
}

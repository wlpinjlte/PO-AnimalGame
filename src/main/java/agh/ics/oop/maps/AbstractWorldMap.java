package agh.ics.oop.maps;

import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.mapElements.Animal;
import agh.ics.oop.mapElements.IMapElement;
import agh.ics.oop.mapElements.IPositionChangeObserver;

import java.util.HashMap;
import java.util.LinkedList;

public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final HashMap<Vector2d,LinkedList<IMapElement>> map =new HashMap<>();
    protected final Vector2d endOfMap;
    protected final Vector2d startOfMap=new Vector2d(0,0);
    public AbstractWorldMap(int height, int width){
        endOfMap=new Vector2d(width-1,height-1);
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                LinkedList<IMapElement> listToPut=new LinkedList<>();
                map.put(new Vector2d(i,j),listToPut);
            }
        }
    }
    //dodalem setter pozycji zwierzecia
    @Override
    public void positionChange(Animal elementToAdd, Vector2d oldPosition, Vector2d newPosition) {
        map.remove(oldPosition);
        map.get(newPosition).add(elementToAdd);
        elementToAdd.setPosition(newPosition);
    }

    @Override
    public void place(IMapElement element) {
        map.get(element.getPosition()).add(element);
    }

    @Override
    public void removeDeadAnimals(Vector2d positionToUpdate) {

    }

    @Override
    public LinkedList<IMapElement> objectsAt(Vector2d position) {
        return map.get(position);
    }

    @Override
    public void growingGrass(Vector2d positionToUpdate) {

    }

    @Override
    public void eatingGrass(Vector2d positionToUpdate) {

    }

    @Override
    public void multiplication(Vector2d positionToUpdate) {

    }

    @Override
    public void upadteMap() {

    }
}

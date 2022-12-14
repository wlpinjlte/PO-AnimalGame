package agh.ics.oop.maps;

import agh.ics.oop.CONSTANT;
import agh.ics.oop.auxiliary.GrassFieldVariants;
import agh.ics.oop.auxiliary.Vector2d;
import agh.ics.oop.mapElements.MapStats;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
public class GrassFactory {
    private final CONSTANT CONSTANT;
    private final GrassFieldVariants grassFieldVariant;
    private final int NumberOfFiledToGrass;
    IWorldMap map;
    LinkedList<Vector2d> fertileFields=new LinkedList<>();
    LinkedList<Vector2d> remainingFields=new LinkedList<>();
    public GrassFactory(IWorldMap map, CONSTANT constant){
        this.map=map;
        this.CONSTANT=constant;
        this.grassFieldVariant = CONSTANT.grassFieldVariant;
        this.NumberOfFiledToGrass= CONSTANT.NUMBEROFGRASSTOGROW;
    }
    public void generateGrass(){
        setFieldsStatus();
        System.out.println(NumberOfFiledToGrass);
        Vector2d fieldToGrowGrass;
        int i=0;
        Collections.shuffle(fertileFields);
        Collections.shuffle(remainingFields);
        while(i<NumberOfFiledToGrass){
            if(!fertileFields.isEmpty()&&!remainingFields.isEmpty()){
                int randomInt=(int)(Math.random()*5);
                if(randomInt==0){
                    fieldToGrowGrass=remainingFields.pop();
                }else{
                    fieldToGrowGrass=fertileFields.pop();
                }
            }else if(fertileFields.isEmpty()&&remainingFields.isEmpty()){
                break;
            }else{
                if(fertileFields.isEmpty()){
                    fieldToGrowGrass=remainingFields.pop();
                }else{
                    fieldToGrowGrass=fertileFields.pop();
                }
            }
            i++;
            System.out.println(fieldToGrowGrass.toString()+' '+map.grassStatus((fieldToGrowGrass)));
            map.updateGrassField(fieldToGrowGrass);

        }
    }

    private void setFieldsStatus(){
        fertileFields.clear();
        remainingFields.clear();
        if(grassFieldVariant==GrassFieldVariants.EquatorBiased){
            int middleOfTheMap=(map.getEndOfMap().y()+1)/2;
            int range=(map.getEndOfMap().y()+1)/10;
            int start=middleOfTheMap-range-1;
            int end=middleOfTheMap+range-1;
            for(int i=0;i<=map.getEndOfMap().x();i++){
                for(int j=0;j<=map.getEndOfMap().y();j++){
                    if(map.grassStatus(new Vector2d(i,j))){
                        continue;
                    }
                    if(j>=start&&j<=end){
                        fertileFields.add(new Vector2d(i,j));
                    }else{
                        remainingFields.add(new Vector2d(i,j));
                    }
                }
            }
        }else if(grassFieldVariant==GrassFieldVariants.DeathBiased){
            ArrayList<Vector2d> allFields = new ArrayList<>();
            for(int i=0;i<=map.getEndOfMap().x();i++){
                for(int j=0;j<=map.getEndOfMap().y();j++){
                    if(map.grassStatus(new Vector2d(i,j))){
                        continue;
                    }
                    allFields.add(new Vector2d(i,j));
                }
            }
            if(allFields.size()==0){
                return;
            }
            allFields.sort(new Comparator<Vector2d>(){
                @Override
                public int compare(Vector2d cordinates1, Vector2d cordinates2) {
                    if(map.getFieldStatsPosition(cordinates1).getDeathAnimals()!=map.getFieldStatsPosition(cordinates2).getDeathAnimals()){
                        return map.getFieldStatsPosition(cordinates1).getDeathAnimals()-map.getFieldStatsPosition(cordinates2).getDeathAnimals();
                    }
                    if (cordinates1.x() != cordinates2.x()) {
                        return cordinates1.x() - cordinates2.x();
                    }

                    if (cordinates1.y() != cordinates2.y()) {
                        return cordinates1.y() - cordinates2.y();
                    }
                    return 0;
                }
            });
            int realNumberGrassToGrow=Math.min(NumberOfFiledToGrass, allFields.size());
            fertileFields= new LinkedList<>(allFields.subList(0, realNumberGrassToGrow));
            remainingFields=new LinkedList<>(allFields.subList(realNumberGrassToGrow, allFields.size()));
        }
    }
}

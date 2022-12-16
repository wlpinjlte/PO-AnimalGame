package agh.ics.oop.auxiliary;

public enum MapDirection {
    NORTH,
    NORTHWEST,
    NORTHEAST,
    SOUTHEAST,
    SOUTHWEST,
    SOUTH,
    WEST,
    EAST;

    public Vector2d toUnitVector(){
        return switch(this){
            case NORTH -> new Vector2d(0,1);
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case SOUTH -> new Vector2d(0,-1);
            case NORTHEAST -> new Vector2d(1,1);
            case NORTHWEST -> new Vector2d(-1,1);
            case SOUTHEAST -> new Vector2d(1,-1);
            case SOUTHWEST -> new Vector2d(-1,-1);
        };
    }
}

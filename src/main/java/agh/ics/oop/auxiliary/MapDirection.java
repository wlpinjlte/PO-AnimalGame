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

    public MapDirection next(MapDirection direction) {
        return switch (direction){
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }
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

    public MapDirection opposite(){
        return switch(this){
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case SOUTHWEST -> NORTHEAST;
            case NORTHEAST -> SOUTHWEST;
            case EAST -> WEST;
            case WEST -> EAST;
            case NORTHWEST -> SOUTHEAST;
            case SOUTHEAST -> NORTHWEST;
        };
    }
}

package rdg;

/**
 * Tiles exist as each point in a floor's map
 * Tiles always contain an environment, //and may contain one entity and multiple items
 * @author Jake
 */
public class Tile 
{
    private char type;
    private Pather mob;
    public int x, y, pathVal = 500;
    /**
     * Tile constructor
     * creates a new tile with a default type //and NULL entity
     * Tiles can also store position data in the form of coordinates
     */
    Tile()
    {
        type = '.';
        mob = null;
    }
    Tile(char type)
    {
        this.type = type;
        mob = null;
    }
    Tile(int x, int y)
    {
        this.x = x;
        this.y = y;
        mob = null;
    }
    Tile(int x, int y, int pathval)
    {
        this.x = x;
        this.y = y;
        this.pathVal = pathval;                
    }
    
    char getType()
    {
        if(mob == null)
            return type;
        else
            return mob.symbol;
    }
    
    void setType(char t)
    {
        type = t;
    }
    
    void setCoords(int x, int y)
    {
        this.x = x; 
        this.y = y;
    }
    
    void setMob(Pather p)
    {
        mob = p;
    }
}

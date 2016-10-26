package rdg;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Jake
 */
public class Room 
{
    public ArrayList<ArrayList<Tile>> roomMap = new ArrayList<ArrayList<Tile>>();
    public int size;
    public int x, y;
    //x and y are the coords of the lowwest x,y tile in the room
    /**
     * Room Constructor
     * @param size - room will be of dimensions sizeXsize
     */
    Room(int size, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.size = size;
        for(int i = 0; i < size; i++)
        {
            roomMap.add(new ArrayList<Tile>());
            for(int j = 0; j < size; j ++)
            {
                roomMap.get(i).add(new Tile(' '));
            }
        }
        for(int i = 0; i < size; i++)
        {
            roomMap.get(i).get(0).setType('#');
            roomMap.get(i).get(size - 1).setType('#');
            roomMap.get(0).get(i).setType('#');
            roomMap.get(size - 1).get(i).setType('#');
        }
    }
    
    void setCoords(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

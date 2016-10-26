package rdg;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Jake
 */
public class Floor 
{
    public ArrayList<ArrayList<Tile>> map = new ArrayList();
    private ArrayList<Room> rooms = new ArrayList();
    public int xMax, yMax;
    private boolean debug = false;
    public Tile stairUp, stairDown;
    
    Floor(int xMax, int yMax)
    {
        this.xMax = xMax;
        this.yMax = yMax;
        Random rand = new Random();
        
        //basic map construction
        for(int x = 0; x < xMax; x++)
        {
            map.add(new ArrayList<Tile>());
            for(int y = 0; y < yMax; y++)
            {
                map.get(x).add(new Tile());
            }
        }
        
        
        //first room creation
        int size = rand.nextInt(17) + 3;
        Tile p = new Tile(rand.nextInt(xMax - 2 * size) + size + 1, rand.nextInt(yMax - 2 * size) + size + 1);
        
        genRoom(size, p, rand.nextInt(4) + 1);
        clean();
    }
    
    /**
     * creates a room on the floor's map
     * recursive
     * @param size
     * @param p
     * @param side -- 1 is bottom, 2 is top, 3 is left, 4 is right
     */
    void genRoom(int size, Tile t, int side)
    {
        Random rand = new Random();        
        //test to see if potential room fits
        
        //if T is on the bottom of the room
        Room room = testRoom(side, size, t);
        if(room == null)
            return;
        
        //room placement
        rooms.add(room);
        placeRoom(room);
        
        //Hall placement
        int jk = 1;
        if(rooms.size() < 10)
            jk = 5;
        int j = rand.nextInt(10) + jk;
        for(int i = 0; i < j; i++)
        {
            //setting up genhall parameters
            int newSide = rand.nextInt(4) + 1;
            Tile newT = new Tile();
            
            if(debug == true)
            {
                newSide = 1;
            }
            
            //side-specific operations
            if(newSide == 1)      //hall extends from the top
            {
                newT.setCoords(room.x + rand.nextInt(size - 2) + 1, room.y + size);
            }
            else if(newSide == 2) //hall extends from bottom
            {
                newT.setCoords(room.x + rand.nextInt(size - 2) + 1, room.y - 1);
            }
            else if(newSide == 3) // hall extends from right
            {
                newT.setCoords(room.x + size, room.y + rand.nextInt(size - 2) + 1);
            }
            else                  // hall extends from left
            {
                newT.setCoords(room.x - 1, room.y + rand.nextInt(size - 2) + 1);
            }
            placeHall(rand.nextInt(2 * size)+ 3, newT, newSide);
        }
    }
    
    /**
     * tests the floor's map to see if a Room is placable at the given location
     * @param side
     * @param size
     * @param t
     * @return 
     */
    Room testRoom(int side, int size, Tile t)
    {
        int x = 0, y = 0, xx = 0, yy = 0;
        if(side == 1) /////// t on bottom                            for odd numbers: t will be offset towards x/y. That is, xx = t.x + (int)((size + 1) / 2)
        {                                                        //  this is so both sides expand from t unequally to fulfill odd size dimensions
            x = t.x - (int)(size / 2);                           //  this is compatable with both even and odd numbers since (int)(even + 1)/2 = even / 2
            xx = t.x + (int)((size + 1) / 2);
            y = t.y;
            yy = t.y + size;
        }
        else if(side == 2) // t on top
        {
            x = t.x - (int)(size / 2);
            xx = t.x + (int)((size + 1) / 2);
            y = t.y - size;
            yy = t.y;
        }
        else if(side == 3) // t on left
        {
            x = t.x;
            xx = t.x + size;
            y = t.y - (int)(size / 2);
            yy = t.y + (int)((size + 1) / 2);
        }
        else //////////////// t on right
        {
            x = t.x - size;
            xx = t.x;
            y = t.y - (int)(size / 2);
            yy = t.y + (int)((size + 1) / 2);
        }
        for(int i = x; i < xx; i++)
        {
            for(int j = y; j < yy; j++)
            {
                if(i >= xMax || j >= yMax || j <= 0 || i <= 0 || map.get(i).get(j).getType() != '.')
                    return null;
            }
        }        
        return new Room(size, x, y);
    }
    
    /**
     * places a room onto the floor's map
     * @param room 
     */
    void placeRoom(Room room)
    {
        int x = room.x;
        int y = room.y;
        for(int i = x; i < x + room.size; i++)
        {
            for(int j = y; j < y + room.size; j++)
            {
                map.get(i).get(j).setType(room.roomMap.get(i - x).get(j - y).getType());
            }
        }
    }
    
    /**
     * generates a hall coming off the side of a room
     * recursively generates another room
     * @param length
     * @param t (tile)
     * @param side 
     */
    void placeHall(int length, Tile t, int side)
    {
        Random rand = new Random();
        Tile newT = new Tile();
        int i;
        
        // hall placement
        if(side == 1 && t.y + length + 1 < yMax)      // extending up
        {
            for(i = t.y; i < t.y + length; i++)
            {
                if(map.get(t.x).get(i).getType() == '.' && map.get(t.x - 1).get(i).getType() == '.' && map.get(t.x + 1).get(i).getType() == '.')
                {
                    map.get(t.x).get(i).setType(' ');
                    map.get(t.x - 1).get(i).setType('#');
                    map.get(t.x + 1).get(i).setType('#');
                }
                else
                    return;
            }
            newT.setCoords(t.x, i);
        }
        else if(side == 2 && t.y - length > 0)    // extending down
        {
            for(i = t.y; i > t.y - length; i--)
            {
                if(map.get(t.x).get(i).getType() == '.' && map.get(t.x - 1).get(i).getType() == '.' && map.get(t.x + 1).get(i).getType() == '.')
                {
                    map.get(t.x).get(i).setType(' ');
                    map.get(t.x - 1).get(i).setType('#');
                    map.get(t.x + 1).get(i).setType('#');
                }
                else
                    return;
            }
            newT.setCoords(t.x, i + 1);
        }
        else if(side == 3 && t.x + length < xMax) // extending right
        {
            for(i = t.x; i < t.x + length; i++)
            {
                if(map.get(i).get(t.y).getType() == '.' && map.get(i).get(t.y - 1).getType() == '.'&& map.get(i).get(t.y + 1).getType() == '.')
                {
                    map.get(i).get(t.y).setType(' ');
                    map.get(i).get(t.y + 1).setType('#');
                    map.get(i).get(t.y - 1).setType('#');
                }
                else return;
            }
            newT.setCoords(i, t.y);
        }
        else if(side == 4 && t.x - length > 0)                 // extending left
        {
            for(i = t.x; i > t.x - length; i--)
            {
                if(map.get(i).get(t.y).getType() == '.' && map.get(i).get(t.y - 1).getType() == '.'&& map.get(i).get(t.y + 1).getType() == '.')
                {
                    map.get(i).get(t.y).setType(' ');
                    map.get(i).get(t.y + 1).setType('#');
                    map.get(i).get(t.y - 1).setType('#');
                }
                else return;
            }
            newT.setCoords(i + 1, t.y);
        }
        
        //recursive call to genroom
        genRoom(rand.nextInt(17) + 3, newT, side);
    }
    
    void printFloor()
    {
        for(int x = 0; x < xMax; x++)
        {
            for(int y = 0; y < yMax; y++)
            {
                System.out.print(map.get(x).get(y).getType());
            }
            System.out.println();
        }
    }
    
    /**
     * deletes halls that end at blackspace and places doors
     */
    void clean()
    {
        int i, j;
        for(i = 1; i < xMax - 1; i++)
        {
            for(j = 1; j < yMax - 1; j++)
            {
                //clearing whitespace
                int x = i, y = j;
                while(map.get(x).get(y).getType() == '.' && (map.get(x+1).get(y).getType() == ' ' || map.get(x-1).get(y).getType() == ' ' || map.get(x).get(y+1).getType() == ' ' || map.get(x).get(y-1).getType() == ' ' || map.get(x+1).get(y+1).getType() == ' ' || map.get(x+1).get(y-1).getType() == ' ' || map.get(x-1).get(y+1).getType() == ' ' || map.get(x-1).get(y-1).getType() == ' '))
                {
                    if(map.get(x+1).get(y).getType() == ' ')
                    {
                        x++;
                    }
                    else if(map.get(x-1).get(y).getType() == ' ')
                    {
                        x--;
                    }
                    else if(map.get(x).get(y+1).getType() == ' ')
                    {
                        y++;
                    }
                    else if(map.get(x).get(y-1).getType() == ' ')
                    {
                        y--;
                    }
                    else if(map.get(x+1).get(y+1).getType() == ' ')
                    {
                        x++;
                        y++;
                    }
                    else if(map.get(x+1).get(y-1).getType() == ' ')
                    {
                        x++;
                        y--;
                    }
                    else if(map.get(x-1).get(y+1).getType() == ' ')
                    {
                        x--;
                        y++;
                    }
                    else if(map.get(x-1).get(y-1).getType() == ' ')
                    {
                        x--;
                        y--;
                    }
                    map.get(x).get(y).setType('.');
                }
            }
        }
        for(i = 1; i < xMax - 1; i++)
        {
            for(j = 1; j < yMax - 1; j++)
            {
                //clearing empty walls
                if(map.get(i).get(j).getType() == '#' && ((map.get(i+1).get(j).getType() == '.' && map.get(i-1).get(j).getType() == '.') || (map.get(i).get(j+1).getType() == '.' && map.get(i).get(j-1).getType() == '.')))
                    map.get(i).get(j).setType('.');
            }
        }
        for(i = 1; i < xMax - 1; i++)
        {
            for(j = 1; j < yMax - 1; j++)
            {
                //placing doors
                if(map.get(i).get(j).getType() == '#' && ((map.get(i+1).get(j).getType() == ' ' && map.get(i-1).get(j).getType() == ' ') || (map.get(i).get(j+1).getType() == ' ' && map.get(i).get(j-1).getType() == ' ')))
                    map.get(i).get(j).setType('[');
            }
        }
    }
    
    void stair()
    {
        Random rand = new Random();
        int totRooms = rooms.size();
        int x1, x2, y1, y2;
        //placing up stairs
        // calculating possible positions
        Room r = rooms.get(rand.nextInt(totRooms / 2) + totRooms / 2);
        x1 = r.x + rand.nextInt(r.size - 1) + 1;
        y1 = r.y + rand.nextInt(r.size - 1) + 1;
        r = rooms.get(rand.nextInt(totRooms / 2));
        x2 = r.x + rand.nextInt(r.size - 1) + 1;
        y2 = r.y + rand.nextInt(r.size - 1) + 1;
        //testing distance
        if(Math.abs(.5 * (x1 - x2) * (y1 - y2)) < xMax * yMax / 6)
            stair();
        else
        {
            map.get(x1).get(y1).setType('^');
            stairUp.setCoords(x1, y1);
            map.get(x2).get(y2).setType('v');
            stairDown.setCoords(x2, y2);
        }
    }
}

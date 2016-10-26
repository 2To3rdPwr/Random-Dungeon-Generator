/*
 * TODO
 *      more floor types(boss)
 */
package rgd;
import java.util.ArrayList;
import java.util.Random;
/**
 * Floors.
 * floors are different levels of the dungeon. They contain the most data about the dungeon's structure.
 * @author Jake
 */
public class Floor 
{
    /**
     * Variables.
     * map ------------ 2D ArrayList of points. map contains all data about the shape of the floor.
     *                  map is referenced with Y first, which may be confusing.
     *                  EX: map.get(Y value).get(X value).get()
     * rooms ---------- Contains each room. used mostly for decorating purposes
     * length/width --- dimensions of the size of the floor.
     */
    private ArrayList<ArrayList<Point>> map = new ArrayList();
    private ArrayList<Room> rooms = new ArrayList();
    private int length, width;
    
    /**
     * Constructor.
     * creates a floor that is length X width and places a room at a random place in it.
     * begins the roomgen process.
     * @param length
     * @param width 
     */
    Floor(int length, int width)
    {
        this.length = length;
        this.width = width;
        for(int y = 0; y < length; y++)
        {
            map.add(new ArrayList<Point>());
            for(int x = 0; x < width; x++)
            {
                map.get(y).add(new Point(x,y,"."));
            }
        }
        Random rand = new Random();
        int size = 10;
        //placing the point in a random location
        Point p = new Point(rand.nextInt(width-(2*size))+size, rand.nextInt(length-(2*size))+size);
        genRoom(size, p);
    }
    
    /**
     * genRoom. (recursive)
     * genRoom is a recursive function that 
     * 1) creates a Room with size and point P as parameters (see room)
     * 2) checks to make sure that the room can, in fact, fit on the map.
     * 3) tests to see if the location the room will be in is empty using room.test()
     * 4) Places the room
     * 5) chooses a random direction
     * 6) attempts to places a hallway in that direction
     * 7) calls genRoom() with a random size and a point located at the end of the hallway.
     * 8) repeats 5-7 four more times
     * @param size
     * @param p (point located at the soon-to-be room
     */
    void genRoom(int size, Point p)
    {
        //1)
        Random rand = new Random();
        Room room;
        //int o = rand.nextInt(3);
        //o=0;/////////Untill round and diagonal rooms can be fixed, o shall remain 0.
        //if(o==0)
        //    room = new RoomRound(size, p);
        //else
            room = new Room(size,p);
        //else//(o==2)
        //    room = new RoomDimond(size, p);
        
        //2)
        if(p.getX()-(size/2)-1>0&&p.getX()+(size/2)+1<map.get(1).size()&&p.getY()-(size/2)-1>0&&p.getY()+(size/2)+1<map.size())
        {
            //3)
            if(room.test(map) == true)    
            {
                //4)
                map = room.place(map);
                rooms.add(room);
                //Room has been placed
                //preparing to place next room
                size = rand.nextInt(25)+10;
                if(size%2!=0)//rooms can only have even sizes
                    size = 10;
                int a = rand.nextInt(5)+1;         //a to be adjustable in settings
                if(rooms.size()<10)//helps prevent small maps by forcing more checks while there are fewer than 10 rooms
                    a=100;
                for(int i = 0; i < a; i++)
                {
                    //original room has been placed.
                    //now placing hallways
                    //5)
                    int j = rand.nextInt(4);
                    //j=2;/////////////////////////////HALL TEST PLZ REMOVE
                    if(j==0)//6)
                    {
                        Point top = room.getTop();
                        size = 10;
                        Point r = genHallUp(top, size);
                        if(r!=top)
                            genRoom(size, r);//7)
                    }
                    if(j==1)
                    {
                        Point left = room.getLeft();
                        Point r = genHallLeft(left, size);
                        if(r!=left)
                            genRoom(size, r);
                    }
                    if(j==2)
                    {
                        Point right = room.getRight();
                        Point r = genHallRight(right, size);
                        if(r!=right)
                            genRoom(size, r);
                    }
                    if(j==3)
                    {
                        Point bottom = room.getBottom();
                        Point r = genHallDown(bottom, size);
                        if(r!=bottom)
                            genRoom(size, r);
                    }
                }
            }
        }
    }
    
    /**
     * Print.
     * prints out this floor.
     * this function will only be used for troubleshooting/debugging in the app, if it is there at all
     * Prints out an asci representation of the current floor.
     */
    void printFloor()
    {
        for(int y = length-1; y > 0; y--)
        {
            for(int x = 0; x < width-1; x++)
            {
                System.out.print(map.get(y).get(x).getType());
            }
            System.out.println();
        }
    }
    
    /**
     * Getters/setters 
     */
    ArrayList<ArrayList<Point>>getMap()
    {
        return map;
    }
    void setMap(ArrayList<ArrayList<Point>> m)
    {
        map = m;
    }
    ArrayList<Room>getRooms()
    {
        return rooms;
    }
    void addRoom(Room r)
    {
        rooms.add(r);
    }
    
    /**
     * GenHall methods.
     * generates a hall in the method's direction starting at point p and
     * continuing for a random length
     * halls may connect to pre-existing rooms/halls.
     * they calculate the center point for the next room to be placed.
     * @param p starting point
     * @param size
     * @return Point p, centered at the position of the next room.
     */
    Point genHallUp(Point p, int size)
    {
        Random rand = new Random();
        int w = rand.nextInt((int)(2))+2;
        int l = rand.nextInt((int)(size*1.5))+3;
        boolean a = false, b = false;
        if(p.getY()+l+size+1>map.size())
            return p;
        Point q = new Point(p.getX(),(int)(p.getY()+l-1+(size/2)));
        for(int y = 1; y <= l; y++)
        {
            for(int x = -w+1; x < w; x++)
            {
            if(!map.get(p.getY()+y).get(p.getX()+w-1).getType().equals("."))//collision test
                a=true;
            if(!map.get(p.getY()+y).get(p.getX()-w+1).getType().equals("."))
                b=true;
            if(a&b)
            {
                return p;
            }
            }
            map.get(p.getY()+y).get(p.getX()+w-1).setType("#");
            map.get(p.getY()+y).get(p.getX()-w+1).setType("#");
            for(int x = 2-w; x < w-1; x++)//////////doors?
            {
                map.get(p.getY()+y).get(p.getX()+x).setType(" ");
            }
        }
        return q;
    }
    Point genHallRight(Point p, int size)/////////////////////
    {
        Random rand = new Random();
        int w = rand.nextInt((int)(1))+2;
        int l = rand.nextInt((int)(size*1.5))+3;
        boolean a = false, b = false;
        if(p.getX()+l+size+1>map.get(1).size())
            return p;
        Point q = new Point((int)p.getX()+l+(size/2),(int)(p.getY()));
        for(int x = 1; x <= l-1; x++)
        {
            for(int y = 1-w; y < w; y++)
            {
            if(!map.get(p.getY()+w).get(p.getX()+x).getType().equals("."))
                a=true;
            if(!map.get(p.getY()+1-w).get(p.getX()+x).getType().equals("."))
                b=true;
            if(a&b)
                return p;
            }
            map.get(p.getY()+w).get(p.getX()+x).setType("#");
            map.get(p.getY()-w+2).get(p.getX()+x).setType("#");
            for(int y = 2-w; y < w-1; y++)
            {
                map.get(p.getY()+y+1).get(p.getX()+x).setType(" ");
            }
        }
        return q;
    }
    Point genHallLeft(Point p, int size)
    {
        Random rand = new Random();
        int w = rand.nextInt((int)(1))+2;
        int l = rand.nextInt((int)(size*1.5))+3;
        boolean a = false, b = false;
        if(p.getX()-l-size-1<0)
            return p;
        Point q = new Point((int)p.getX()-(l-1)-(size/2)-1,(int)(p.getY()));
        for(int x = -1; x >= -l; x--)
        {
            for(int y = -w+2; y < w; y++)
            {
            if(!map.get(p.getY()+w).get(p.getX()+x).getType().equals("."))
                a=true;
            if(!map.get(p.getY()-w+2).get(p.getX()+x).getType().equals("."))
                b=true;
            if(a&b)
                return p;
            }
            map.get(p.getY()+w).get(p.getX()+x).setType("#");
            map.get(p.getY()-w+2).get(p.getX()+x).setType("#");
            for(int y = 2-w; y < w-1; y++)
            {
                map.get(p.getY()+y+1).get(p.getX()+x).setType(" ");
            }
        }
        return q;
    }
    Point genHallDown(Point p, int size)
    {
        Random rand = new Random();
        int w = rand.nextInt((int)(2))+2;
        int l = rand.nextInt((int)(size*1.5))+3;
        boolean a = false, b = false;
        if(p.getY()-1-size-1<0)//
            return p;
        Point q = new Point(p.getX(),(int)(p.getY()-l-(size/2)));
        for(int y = -1; y >= -l+1; y--)
        {
            for(int x = -w+1; x < w-1; x++)
            {
            if(p.getX()+x < 0 || p.getY() + y < 0)
                return p;
            else if(!map.get(p.getY()+y).get(p.getX()+w-1).getType().equals("."))
                a=true;
            if(!map.get(p.getY()+y).get(p.getX()-w+1).getType().equals("."))
                b=true;
            if(a&b)
                return p;
            }
            if(p.getY()+y <= 1)
                break;
            map.get(p.getY()+y).get(p.getX()+w-1).setType("#");
            map.get(p.getY()+y).get(p.getX()-w+1).setType("#");
            for(int x = 2-w; x < w-1; x++)
            {
                map.get(p.getY()+y).get(p.getX()+x).setType(" ");
            }
        }
        return q;
    }
    
    /**
     * Clean.
     * cleans up halls that do not create rooms or connect to anything, walls that jut into empty space,
     * and areas where floor type points touch empty type points.
     */
    void clean()
    {
        for(int y = 1; y < map.size()-1; y++)
        {
            for(int x = 1; x < map.get(0).size()-1; x++)
            {
                if(map.get(y).get(x).getType().equals("#"))//||map.get(y).get(x).getType().equals("#"))
                {
                    if(map.get(y-1).get(x).getType().equals(".")&&map.get(y+1).get(x).getType().equals("."))
                    {
                        map.get(y).get(x).setType(".");
                    }
                    if(map.get(y).get(x-1).getType().equals(".")&&map.get(y).get(x+1).getType().equals("."))
                    {
                        map.get(y).get(x).setType(".");
                    }
                }
                
            }
        }
        for(int y = 1; y < map.size()-1; y++)
        {
            for(int x = 1; x < map.get(0).size()-1; x++)
            {
                if(map.get(y).get(x).getType().equals(".")&&map.get(y-1).get(x).getType().equals(" "))
                {
                    map.get(y-1).get(x).setType(".");
                    clean();
                }
                if(map.get(y).get(x).getType().equals(".")&&map.get(y+1).get(x).getType().equals(" "))
                {
                    map.get(y+1).get(x).setType(".");
                    clean();
                }
                if(map.get(y).get(x).getType().equals(".")&&map.get(y).get(x-1).getType().equals(" "))
                {
                    map.get(y).get(x-1).setType(".");
                    clean();
                }
                if(map.get(y).get(x).getType().equals(".")&&map.get(y).get(x+1).getType().equals(" "))
                {
                    map.get(y).get(x+1).setType(".");
                    clean();
                }
            }
        }
        for(int y = 1; y < map.size()-1; y++)
        {
            for(int x = 1; x < map.get(0).size()-1; x++)
            {
                if(map.get(y).get(x).getType().equals("#")||map.get(y).get(x).getType().equals("#"))
                {
                    if(map.get(y-1).get(x).getType().equals(" ")&&map.get(y+1).get(x).getType().equals(" "))
                    {
                        map.get(y).get(x).setType(" ");
                    }
                    if(map.get(y).get(x-1).getType().equals(" ")&&map.get(y).get(x+1).getType().equals(" "))
                    {
                        map.get(y).get(x).setType(" ");
                    }
                }  
            }
        }
    }
    /**
     * decorates the rooms of this floor
     * see room.decorate() for more
     */
    void decorate()
    {
        map = rooms.get(0).decorate(map, 1);
        for(int i = 1; i < rooms.size()/5; i++)
        {
            map = rooms.get(i).decorate(map);
        }
        for(int i = rooms.size()/5+1; i < 3*rooms.size()/4; i++)
        {
            map = rooms.get(i).decorate(map);
        }
        for(int i = 3*rooms.size()/4+1; i < rooms.size(); i++)
        {
            map = rooms.get(i).decorate(map);
        }
        map = rooms.get(3*rooms.size()/4).decorate(map, -1);
    }//add more decoration types-chasm, pool, treasure, requests?
}

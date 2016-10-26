/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgd;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Rooms.
 * Rooms are rooms. not much else to say here.
 * @author Jake
 */
public class Room 
{
    /**
     * Variables.
     * roomMap ------------- a 2D arrayList of points which makes up the room.
     * size ---------------- length of one side of the room
     * first/last points --- used to help calculate the dimensions of the room and
     *                       give perspective. First is top left(?) and last is bottom right(?)
     */
    public ArrayList<ArrayList<Point>> roomMap = new ArrayList();
    public int size;
    public Point first, last;
    /**
     * Constructor.
     * creates a room that is size X size centered at point p.
     * this room is stored in the Room's roomMap.
     * @param size
     * @param p 
     */
    Room(int size, Point p)
    {
        this.size = size;
        int i = -1;
        for(int y = p.getY() + size/2; y > p.getY() - size/2; y--)
        {
            roomMap.add(new ArrayList<Point>());
            i++;
            for(int x = p.getX() - (size/2); x < p.getX() + size/2; x++)
            {
                roomMap.get(i).add(new Point(x,y,"P"));
            }
        }
        first = roomMap.get(size-1).get(0);
        last = roomMap.get(0).get(size-1);
    }
    
    /**
     * test.
     * Tests to see if this room would fit into the floor's map.
     * This is done by making sure that every point on the map between
     * the room's first and last points are of type "." (empty)
     * @param map
     * @return whether room is placable.
     */
    boolean test(ArrayList<ArrayList<Point>> map)
    {
        boolean ret = true;
        for(int l = last.getY(); l > first.getY(); l--)///////
        {
            for(int w = first.getX(); w < last.getX(); w++)
            {
                if(!map.get(l).get(w).getType().equals("."))
                    ret = false;
            }
        }
        return ret;
    }

    /**
     * place.
     * Used to place rooms on the floor's map.
     * returns the updated map containing the room.
     * @param map (floor)
     * @return map
     */
    ArrayList<ArrayList<Point>> place(ArrayList<ArrayList<Point>> map)
    {
        for(int l = first.getY(); l < last.getY(); l++)
        {
            for(int w = first.getX(); w < last.getX(); w++)
            {
                map.get(l).get(w).setType(" ");
            }
        }
        for(int l = last.getY(); l > first.getY(); l--)
        {
            map.get(l).get(first.getX()).setType("#");
            map.get(l).get(last.getX()).setType("#");
        }
        for(int w = first.getX(); w <= last.getX(); w++)
        {
            map.get(first.getY()).get(w).setType("#");
            map.get(last.getY()).get(w).setType("#");
        }
        return map;
    }
    
    /**
     * get methods.
     * @return point centered on the corresponding side
     */
    Point getTop()
    {
        return roomMap.get(0).get(size/2);
    }
    Point getLeft()
    {
        return roomMap.get(size/2).get(0);
    }
    Point getRight()
    {
        return roomMap.get(size/2).get(size-1);
    }
    Point getBottom()
    {
        return roomMap.get(size-1).get(size/2);
    }
    
    /**
     * decorate.
     * adds "decorations" to the room.
     * 
     * type is a random value from 0 to 100
     * each "if(# > type)" statement represents the % chance for a specific decoration.
     * the % chance can be found by subtracting that if statement's # from the # of the if statement before it.
     * EX: "CAVE" has a 20% chance of appearing because it's # is 40, and "CARPET"'s # is 20.
     * 
     * @param map
     * @return updated map
     */
    ArrayList<ArrayList<Point>> decorate(ArrayList<ArrayList<Point>> map)
    {
        Random rand = new Random();
        int type = rand.nextInt(100)+1;
        int ll = last.getY()-first.getY();//ll represents room dimentions. It can be used for scaling.
        //CARPET (may be removed)
        if(type <= 20)
        {
            for(int l = first.getY()+1; l < last.getY(); l++)
            {
                for(int ww = first.getX()+1; ww < last.getX(); ww++)
                {
                    map.get(l).get(ww).setType(",");
                }
            }
        }
        //CAVE
        else if(type <= 40)
        {
            for(int l = last.getY()-1; l > first.getY()+1; l--)
        {
            if(rand.nextInt(2)==1&&map.get(l).get(first.getX()).getType().equals("#"))
            {
            map.get(l).get(first.getX()+1).setType("#");
            }
            if(rand.nextInt(2)==1&&map.get(l).get(last.getX()).getType().equals("#"))
            {
            map.get(l).get(last.getX()-1).setType("#");
            }
        }
        for(int w = first.getX()+1; w <= last.getX()-1; w++)
        {
            if(rand.nextInt(2)==1&&map.get(first.getY()).get(w).getType().equals("#"))
            {
            map.get(first.getY()+1).get(w).setType("#");
            }
            if(rand.nextInt(2)==1&&map.get(last.getY()).get(w).getType().equals("#"))
            {
            map.get(last.getY()-1).get(w).setType("#");
            }
        }
        }
        //PILLARS
        else if(type <= 60)
        {
            for(int l = ll/6; l < ll/2; l+=2)
            {
                map.get(first.getY()+l).get(first.getX()+l).setType("+");
                map.get(last.getY()-l).get(last.getX()-l).setType("+");
                map.get(last.getY()-l).get(first.getX()+l).setType("+");
                map.get(first.getY()+l).get(last.getX()-l).setType("+");
            }
        }
        else if(type <= 80)//holes
        {
            
        }
        return map;
    }
    /**
     * decorate(specific).
     * used for the placement of unique decorations, such as stairs.
     * the "special" parameter refers to which decoration should be placed.
     * @param map
     * @param special
     * @return updated map
     */
    ArrayList<ArrayList<Point>> decorate(ArrayList<ArrayList<Point>> map, int special)
    {
        int s = last.getY()-first.getY();//s represents room dimentions. It can be used for scaling.
        if(special == 1)//STAIR UP
        {
            map.get(first.getY()+(s/2)).get(first.getX()+(s/2)).setType("^");
        }
        if(special == -1)//STAIR DOWN
        {
            map.get(first.getY()+(s/2)).get(first.getX()+(s/2)).setType("v");
        }
        return map;
    }
}
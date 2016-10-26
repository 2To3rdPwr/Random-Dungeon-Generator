/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgd;

import java.util.ArrayList;

/**
 * Subclass of Room. Makes a round room.
 * TODO: fix edges of rooms
 * make more hallway-compatible
 * @author Jake
 */
public class RoomRound extends Room
{
    RoomRound(int size, Point p)
    {
    super(size, p);
    }
    
    boolean test(ArrayList<ArrayList<Point>> map)
    {
        boolean ret = true;
        for(double l = -size/2+1; l < (size/2)-1; l+=0.5)///////
        {
            int w = (int)(Math.sqrt(Math.pow(size/2-1,2)-Math.pow(l,2)));
            if(!map.get((int)l+first.getY()+size/2).get(first.getX()+(size/2)+w).getType().equals(".")||!map.get((int)l+first.getY()+size/2).get(first.getX()+(size/2)+w).getType().equals("."))
                ret = false;
        }
        return ret;
    }
    
        ArrayList<ArrayList<Point>> place(ArrayList<ArrayList<Point>> map)
    {
        for(double r = 0; r < size/2; r+=0.01)
        {
        for(double l = -(r); l <= (r); l+=0.01)///////
        {
            int w = (int)(Math.sqrt(Math.pow(r,2)-Math.pow(l,2)));
            map.get((int)l+first.getY()+(size/2)).get(first.getX()+(int)(size/2)+w).setType(" ");
            map.get((int)l+first.getY()+(size/2)).get(first.getX()+(size/2)-w).setType(" ");
        }
        }
        for(double l = -(size/2); l <= (size/2); l+=0.01)///////
        {
            int w = (int)(Math.sqrt(Math.pow(size/2,2)-Math.pow(l,2)));
            map.get((int)l+first.getY()+(size/2)).get(first.getX()+(size/2)+w).setType("#");
            map.get((int)l+first.getY()+(size/2)).get(first.getX()+(size/2)-w).setType("#");
        }
        //map.get(first.getY()).get(first.getX()+size/2).setType(map.get(first.getY()-1).get(first.getX()+size/2).getType());
        return map;
    }
    Point getBottom()
    {
        return roomMap.get(size-2).get(size/2);
    }
    Point getLeft()
    {
        return roomMap.get(size/2).get(1);
    }
    Point getRight()
    {
        return roomMap.get(size/2).get(size-1);
    }
    ArrayList<ArrayList<Point>> decorate(ArrayList<ArrayList<Point>> map)//round decorations yet to be added.
    {
        return map;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgd;

import java.util.ArrayList;

/**
 * Diamond shaped room
 * @author Jake
 */
public class RoomDimond extends Room
{
    RoomDimond(int size, Point p)
    {
        super(size, p);
    }
     
    boolean test(ArrayList<ArrayList<Point>> map)
    {
        boolean ret = true;
        int w = 0;
        for(int l = first.getY()+1; l < last.getY()-(size/2); l++)///////
        {
            if(!map.get(l).get(first.getX()+(size/2)+w).getType().equals(".")||!map.get(l).get(first.getX()+(size/2)-w).getType().equals("."))
                ret = false;
            w++;
        }
        for(int l = first.getY()+(size/2); l < last.getY(); l++)///////
        {
            if(!map.get(l).get(first.getX()+(size/2)+w).getType().equals(".")||!map.get(l).get(first.getX()+(size/2)-w).getType().equals("."))
                ret = false;
            w--;
        }
        return ret;
    }
    
    ArrayList<ArrayList<Point>> place(ArrayList<ArrayList<Point>> map)/////fix diagonal room sides!
    {
        int w = 3;
        for(int l = first.getY(); l <= last.getY()-(size/2)-1; l++)
        {
            for(int ww = -w; ww<w; ww++)
            {
                map.get(l).get(first.getX()+(size/2)+ww).setType(" ");
            }
            map.get(l).get(first.getX()+(size/2)+w).setType("#");
            map.get(l).get(first.getX()+(size/2)-w).setType("#");
            w++;
        }
        for(int ll = first.getY()+(size/2)-1; ll < first.getY()+size/2+2; ll++)
        {
        for(int ww = -w+2; ww<w-2; ww++)
            {
                map.get(first.getY()+(size/2)).get(first.getX()+(size/2)+ww).setType(" ");
            }
        map.get(ll).get(first.getX()+(size/2)+w-1).setType("#");
        map.get(ll).get(first.getX()+(size/2)-w+1).setType("#");
        }
        w--;
        for(int l = first.getY()+(size/2)+1; l <= last.getY()+1; l++)
        {
            for(int ww = -w; ww<w; ww++)
            {
                map.get(l).get(first.getX()+(size/2)+ww).setType(" ");
            }
            map.get(l).get(first.getX()+(size/2)+w).setType("#");
            map.get(l).get(first.getX()+(size/2)-w).setType("#");
            w--;
        }
        for(int www = (first.getX()+(size/2)-1); www <= first.getX()+(size/2)+1; www++)
        {
            map.get(first.getY()).get(www).setType("#");
            map.get(last.getY()+1).get(www).setType("#");            
        }
        return map;
    }
    ArrayList<ArrayList<Point>> decorate(ArrayList<ArrayList<Point>> map)
    {
        return map;
    }
    Point getTop()
    {
        Point ret = roomMap.get(0).get(size/2);
        ret.setY(ret.getY()+1);
        return ret;
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
}

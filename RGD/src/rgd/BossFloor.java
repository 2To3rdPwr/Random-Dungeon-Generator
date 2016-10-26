/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgd;

import java.util.Random;
import java.util.ArrayList;
/**
 *
 * @author Jake
 */
public class BossFloor extends Floor
{
    BossFloor()
    {
        super(50,50);
    }
    @Override
    void genRoom(int size,Point p)
    {
        p.setX(25);
        p.setY(5);
        Room room = new Room(size, p);
        Random rand = new Random();
        setMap(room.place(getMap()));
        addRoom(room);
        Point q = genHallUp(room.getTop(),3);
        genBossRoom(q);
    }
    void genBossRoom(Point p)
    {
        ArrayList<ArrayList<Point>> m = getMap();
        for(int i = 15; i < 35; i++)
        {
            m.get(p.getY()).get(i).setType("#");
        }
        for(int i = 1; i < 10; i++)
        {
            for(int j = p.getX()-i-9; j < p.getX()+i+9; j++)
            {
                m.get(p.getY()+i).get(j).setType(" ");
            }
            m.get(p.getY()+i).get(p.getX()+i+9).setType("#");
            m.get(p.getY()+i).get(p.getX()-i-10).setType("#");
        }
        for(int i = p.getY()+10; i < p.getY()+20; i ++)
        {
            m.get(i).get(p.getX()+19).setType("#");
            m.get(i).get(p.getX()-20).setType("#");
            for(int j = p.getX()-19; j < p.getX()+19; j++)
            {
                m.get(i).get(j).setType(" ");
            }
        }
        for(int i = 1; i < 10; i++)
        {
            for(int j = p.getX()-(10-i)-9; j < p.getX()+(10-i)+10; j++)
            {
                m.get(p.getY()+i+19).get(j).setType(" ");
            }
            m.get(p.getY()+i+18).get(p.getX()+20-i).setType("#");
            m.get(p.getY()+i+19).get(p.getX()+i-20).setType("#");
        }
        for(int i = 15; i < 36; i++)
        {
            m.get(p.getY()+28).get(i).setType("#");
        }
    }
}

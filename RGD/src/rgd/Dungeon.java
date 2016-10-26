/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgd;
import java.util.ArrayList;
/**
 * Dungeon.
 * contains all the floors.
 * used to navigate up and down floors.
 * @author Jake
 */
public class Dungeon 
{
    private ArrayList<Floor> floor = new ArrayList();
    /**
     * Constructor.
     * creates a dungeon that is length X width (on average) and depth floors deep.
     * @param depth
     * @param length
     * @param width 
     */
    Dungeon(int depth, int length, int width)
    {
        for(int i = 0; i < depth; i++)
        {
            floor.add(new Floor(length, width));
            System.out.println("FLOOR "+ (i+1));
            floor.get(i).clean();
            floor.get(i).decorate();//theme param?)
            floor.get(i).printFloor();
        }
        floor.add(new BossFloor());
        System.out.println("FLOOR "+ (4));
        floor.get(3).clean();
        //floor.get(3).decorate();//theme param?)
        floor.get(3).printFloor();
    }
}

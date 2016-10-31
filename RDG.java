package rdg;
import java.util.Scanner;
import java.util.Random;
/**
 *  main() class. 
 * Currently set up to test my pathfinding algorithm
 * Illegal argument crashes sometimes occur because the dungeon generation algorithm is meant for larger dungeons,
 * but my pathfinding algorithm has terrible runtime (not sure if it incurs an infinite loop), meaning it only works on small dungeons.
 * 
 * Feel free to toggle pathfinding off to get the basic generator on a larger scale with better runtime.
 * @author Jake                                     |
 */                                            //   |
public class RDG {                             //   |
                                               //   |
    /**                                             |
     * @param args the command line arguments       |
     */                                         //  |
    public static void main(String[] args)      //  |
    {                                           //  |
        Scanner next = new Scanner(System.in);  //  |
                                                //  |
        boolean pathfinding = true;//  <-------------
        
        int a;
        if(pathfinding)
            a = 40; 
        else
            a = 100;
        Floor f = new Floor(a, a);
        f.printFloor();
        
        
        //pathfinding tester
        if(pathfinding)
        {
            Random rand = new Random();
            Tile start = new Tile();
            Tile end = new Tile();
            while(start.getType() != ' ')
            {
             start = f.map.get(rand.nextInt(f.xMax)).get(rand.nextInt(f.yMax));
            }
            while(end.getType() != ' ')
            {
             end = f.map.get(rand.nextInt(f.xMax)).get(rand.nextInt(f.yMax));
            }
            
            //Destination is $
            //the pather is @
            //Start is %
            f.map.get(end.x).get(end.y).setType('$');   
            end.setType('$');
            f.map.get(start.x).get(start.y).setType('%');
            f.printFloor();
            //creating a pather and showing its position on the map
            Pather p = new Pather(f, start, end); 
            
            f.map.get(start.x).get(start.y).setMob(p);
            
            while(f.map.get(end.x).get(end.y).mob == null)
            {
                //String i = next.nextLine();
                f = p.move();
                f.printFloor();
            }
        }
    }
    
}

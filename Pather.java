package rdg;
/**
 * My first attempt at a pathing algorithm.
 * I used a tree approach here, where each node has four children.
 * 
 * The root of the tree is the destination, and from there it searches the board until it finds the starting place
 * Once the starting place is found, the node that reached it, and thus the entire branch leading back to the root, is saved in a private variable.
 * If another node from a different branch reaches the starting space, the heights of the nodes are compared, and the shortest branch is kept.
 * New branches are pruned based on their height, their content(is the associated tile walkable?), and whether or not another node with the same data exists in it's branch.
 * This algorithm has a horrible runtime, and I suspect that an infinite loop is hiding somewhere, but for a first attempt, I am satisfied.
 * 
 * @author Jake
 */
public class Pather 
{
    public char symbol = '@';
    private Floor f;
    private Node path = new Node(new Tile());
    private Tile start, end;
    Pather(Floor f, Tile start, Tile end)
    {
        this.f = f;
        this.start = start;
        this.end = end;
        path.height = Integer.MAX_VALUE;
        
        findPath(new Node(end));
    }
    Pather(Floor f, Tile start, Tile end, Node path)
    {
        this.f = f;
        this.start = start;
        this.end = end;
        this.path = path;
    }
    
    Floor move()
    {
        f.map.get(path.data.x).get(path.data.y).setMob(null);
        path = path.parent;
        f.map.get(path.data.x).get(path.data.y).setMob(this);
        return f;
    }
    
    /**
     * creates a tree containing potential paths for the pather
     * @param current 
     */
    void findPath(Node current)
    {
        if(current.data == start)
        {
            if(current.height <= path.height)
            {
                path = current;
            }
            return;
        }
        if(current.height >= path.height)  
            return;
        
        //adding children
        if(current.data.x + 1 < f.xMax)
            current.children.add(new Node(current, f.map.get(current.data.x + 1).get(current.data.y))); 
        if(current.data.x - 1 > 0)
            current.children.add(new Node(current, f.map.get(current.data.x - 1).get(current.data.y)));
        if(current.data.y + 1 < f.yMax)
            current.children.add(new Node(current, f.map.get(current.data.x).get(current.data.y + 1)));
        if(current.data.y - 1 > 0)
            current.children.add(new Node(current, f.map.get(current.data.x).get(current.data.y - 1)));
        
        current.ifParentRemoveChild(current.parent);
        current.removeWallChildren();
        
        //recursive call
        if(current.children.size() > 0)
        {
            for(int i = 0; i < current.children.size(); i++)
            {
                findPath(current.children.get(i));
            }
        }
    }
}



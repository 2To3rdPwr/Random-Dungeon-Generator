package rdg;
import java.util.ArrayList;

/**
 * Nodes for the Pather's searching tree
 * @author Jake
 */
public class Node 
{
    public Node parent;
    public Tile data;
    public ArrayList<Node> children = new ArrayList<Node>();
    int height;
    
    //child nodes
    public Node(Node parent, Tile data)
    {
        this.data = data;
        this.parent = parent;
        height = parent.height + 1;
    }
    
    //root node
    public Node(Tile data)
    {
        this.data = data;
        parent = null;
        height = 0;
    }
    //adds new child node
    
    void newChild(Tile data)
    {
        children.add(new Node(this, data));
    }

    
    /**
     * checks a node's ancestry and deletes children if they appear in it.
     * First iteration's ancestor should be that node's parent
     * @param current
     * @param ancestor 
     */
    void ifParentRemoveChild(Node ancestor)
    {
        if(ancestor == null)
            return;
        if(children.size() > 0)
        {
            for(int i = 0; i < children.size(); i++)
            {
                if(children.get(i).data == ancestor.data)
                {
                    children.remove(i);
                    i--;
                }
            }
        }
        ifParentRemoveChild(ancestor.parent);        
    }
    /**
     * removes children if they are a wall
     */
    void removeWallChildren()//this should be extended for more entities as we go along
    {
        if(children.size() > 0)
        {
        for(int i = 0; i < children.size(); i++)
            {
                if(children.get(i).data.getType() != ' ' && children.get(i).data.getType() != '[' && children.get(i).data.getType() != '$' && children.get(i).data.getType() != '%')
                {
                    children.remove(i);
                    i--;
                }
            }
        }
    }
}

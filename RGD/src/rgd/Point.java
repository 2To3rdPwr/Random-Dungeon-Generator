/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rgd;

/**
 * Points.
 * Points are objects stored within each Floor's map. 
 * Points contain an X and Y value, as well as a Type value.
 * @author Jake
 */
public class Point 
{
    /**
     * Type values.
     * "#" - Wall
     * " " - Floor
     * "." - background/empty
     * "D" - default
     * Other types such as "+" are for decoration
     */
    private String type;
    private int x, y;
    /**
     * Constructor.
     * Creates a Point with the given X, Y, and Type values.
     * @param x
     * @param y
     * @param type 
     */
    Point(int x, int y, String type)
    {
        this.type = type;
        this.x = x;
        this.y = y;
    }
    /**
     * Constructor.
     * Creates a Point with the given X and Y values, as well as a default "D" type.
     * Useful for generating points when Type does not matter.
     * @param x
     * @param y 
     */
    Point(int x, int y)
    {
        this.type = "D";
        this.x = x;
        this.y = y;
    }
    
    /**
     * Everything else is mostly self-explanatory
     */
    
    String getType()
    {
        return type;
    }
    void setType(String type)
    {
        this.type = type;
    }
    int getX()
    {
        return x;
    }
    int getY()
    {
        return y;
    }
    void setX(int xx)
    {
        x = xx;
    }
    void setY(int yy)
    {
        y = yy;
    }
}

package com.android.piece;

import java.io.Serializable;

/**
 * This class is for using Point which include row and column
 * 
 * @author Jaehyun
 * @author Drew
 */
public class Point implements Serializable {
    private static final long serialVersionUID = 1233777L;
    /**
     * This field is for row
     */
    private int x;
    /**
     * This field is for column
     */
    private int y;

    /**
     * This is constructor that include x and y.
     * 
     * @param x get row
     * @param y get column
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * This method is getting x.
     * 
     * @return return field x.
     */
    public int getX() {
        return x;
    }

    /**
     * This method is setting x 
     * 
     * @param x get x through parameter
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * This method is for getting y 
     * 
     * @return return field y.
     */
    public int getY() {
        return y;
    }

    /**
     * This method is for setting y 
     * 
     * @param y get y through parameter.
     */
    public void setY(int y) {
        this.y = y;
    }
}

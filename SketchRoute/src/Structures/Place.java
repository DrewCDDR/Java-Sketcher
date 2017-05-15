/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structures;

import Visual.VisualWindow;

/**
 *
 * @author cddr
 */
public class Place {
    
    public final int ID;
    private boolean isRoad, isAirport, isNothing, sorted;
    private boolean[] possiblePaths;
    private boolean[] connections;
    private int x, y;
    private int costs[];
    private final String data;
    private final int ariportCost;
    private java.awt.image.BufferedImage img;
    
    public Place(String data, int airportCost, int l, int id) {
        setIsNothing(true);
        
        connections = new boolean[l];
        
        for (int i = 0; i < l; i++) {
            connections[i] = false;
        }
        
        this.sorted = false;
        this.ariportCost = airportCost;
        this.data = data;
        this.x = -100;
        this.y = -100;
        this.ID = id;
    }
    
    public void setCoords (int _x,int _y){        
        setX(_x);
        setY(_y);        
    }
    
    public static boolean[] findPossiblePaths(String[] v){
        boolean[] b = new boolean[v.length];
        for (int i = 0; i < b.length; i++) {
            if (v[i].equals("x")) {
                b[i] = false;
            }else{
                b[i] = true;
            }
        }
        return b;
    }
    
    public static int[] findCosts(String[] v){
        int[] c = new int[v.length];
        for (int i = 0; i < c.length; i++) {
            if (v[i].equals("x")) {
                c[i] = -1;
            }else{
                c[i] = Integer.parseInt(v[i]);
            }
        }
        return c;
    }
    
    public int getConnectionCost(){
        int c = 0;
        for (int i = 0; i < possiblePaths.length; i++) {
            if (possiblePaths[i]) {
                c += costs[i];
            }
        }
        return c;
    }
    
    public int getPossiblePathsCount(){
        int c = 0;
        for (int i = 0; i < possiblePaths.length; i++) {
            if (possiblePaths[i]) {
                c++;
            }
        }
        return c;
    }
    
    public void setConnectionWith(int i){
        getConnections()[i] = true;
    }
    /**
     * 
     * @return True if the position where the node is going to be is valid, False if not. 
     */
    public boolean isValid(){
        return getX() != 0 && getY() != 0;
    }

    /**
     * @return the isCity
     */
    public boolean isRoad() {
        return isRoad;
    }

    /**
     * @param isCity the isCity to set
     */
    public void setIsRoad(boolean isCity) {
        this.isRoad = isCity;
        if (isCity) {
            this.isNothing = false;
            this.isAirport = false;
            this.img = Assets.ImageLibrary.PLACE_STATE_ROAD;
        }
    }

    /**
     * @return if a city is an Airport
     */
    public boolean isAirport() {
        return isAirport;
    }

    /**
     * @param isAirport If the city has or not an airport
     */
    public void setIsAirport(boolean isAirport) {
        this.isAirport = isAirport;
        
        if(isAirport){
            this.isNothing = false;
            this.isRoad = false;
            for (int i = 0; i < getConnections().length; i++) {
                if (getConnections()[i]) {
                    getConnections()[i] = false;
                    Visual.VisualWindow.PLACES[i].getConnections()[ID] = false;
                    VisualWindow.totalCost -= costs[i];
                    System.out.println("\tDesconexión, entre " +Visual.VisualWindow.PLACES[i].getData() +" y " + this.data);
                }
            }
            this.img = Assets.ImageLibrary.PLACE_STATE_AIRPORT;
        }
    }

    /**
     * @return the isNothing
     */
    public boolean isNothing() {
        return isNothing;
    }

    /**
     * @param isNothing the isNothing to set
     */
    public void setIsNothing(boolean isNothing) {
        this.isNothing = isNothing;
        if (isNothing) {
            this.img = Assets.ImageLibrary.PLACE_STATE_NOTHING;
            this.isAirport = false;
            this.isRoad = false;
        }
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * <p>
     * @return The name of the place or node
     */
    public String getData() {
        return data;
    }

    /**
     * @return the ariportCost
     */
    public int getAriportCost() {
        return ariportCost;
    }

    /**
     * @return the actual image of the node or place
     */
    public java.awt.image.BufferedImage getImg() {
        return img;
    }

    /**
     * @param img the image to set
     */
    public void setImg(java.awt.image.BufferedImage img) {
        this.img = img;
    }

    /**
     * <p> 
     * @return the possible paths that a city may have.
     */
    public boolean[] getPossiblePaths() {
        return possiblePaths;
    }

    /**
     * @param possiblePaths the list of possible paths that a city may have.
     */
    public void setPossiblePaths(boolean[] possiblePaths) {
        this.possiblePaths = possiblePaths;
    }

    /**
     * <p> 
     * @return the cost array of the possible conections of the city.
     */
    public int[] getCosts() {
        return costs;
    }

    /**
     * <p> 
     * @param costs the costs of the possible conections of the city
     */
    public void setCosts(int[] costs) {
        this.costs = costs;
    }

    /**
     * @return the sorted
     */
    public boolean isSorted() {
        return sorted;
    }

    /**
     * @param sorted the sorted to set
     */
    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    /**
     * @return the connections
     */
    public boolean[] getConnections() {
        return connections;
    }

    /**
     * @param connections the connections to set
     */
    public void setConnections(boolean[] connections) {
        this.connections = connections;
    }
    
    
}

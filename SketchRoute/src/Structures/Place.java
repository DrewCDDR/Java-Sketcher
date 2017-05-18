/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structures;

import Visual.VisualWindow;

/**
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
    
    /**
     * This method calls the constructor of the Place class in order to create
     * a new Place variable or node.
     * @param data The name of the place
     * @param airportCost The cost to build an airport.
     * @param l The number of cities or places that are in the landscape. 
     * @param id The specified ID of the city or place.
     */
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
    
    /**
     * This method sets the (x,y) coordinates for drawing the node in the canvas.
     * @param _x The X coordinate that this object will have in the canvas.
     * @param _y The Y coordinate that this object will have in the canvas.
     */
    public void setCoords (int _x,int _y){        
        setX(_x);
        setY(_y);        
    }
    
    /**
     * This method finds the possible paths that a city or place can build.
     * @param v A String array that represent the value of all the connections of the
     * actual city or place.
     * @return An array that specifies with witch place or city this place or
     * city is connected.
     */
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
    
    /**
     * This method finds the cost of all the paths that this city or place may
     * have with the other cities or places.
     * @param v A String array that represent the value of all the connections of the
     * actual city or place.
     * @return An int array with the cost information in order to connect this
     * city or place with another one.
     */
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
    
    /**
     * This method counts the number of possible paths that a city or place can
     * have.
     * @return The maximun number of paths that a city or place can have.
     */
    public int getPossiblePathsCount(){
        int c = 0;
        for (int i = 0; i < possiblePaths.length; i++) {
            if (possiblePaths[i]) {
                c++;
            }
        }
        return c;
    }
    
    /**
     * This method connects a city or place with an index "i" with this city or 
     * palce.
     * @param i The index of the city or place that is going to be connected
     * with this city or place. 
     */
    public void setConnectionWith(int i){
        getConnections()[i] = true;
    }
    
    /**
     * @return True if the position where the node is going to be is valid,
     * False if not. 
     */
    public boolean isValid(){
        return getX() != 0 && getY() != 0;
    }

    /**
     * @return Whether a city or place has an road or connection built in it.
     */
    public boolean isRoad() {
        return isRoad;
    }

    /**
     * @param isCity Says if a city has a road or a connection in it.
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
     * @return If the city o place has an airport built in it.
     */
    public boolean isAirport() {
        return isAirport;
    }

    /**
     * This method sets the city or place "isAirport" value to true or false. 
     * If the value is true, this method also changes the node actual image an 
     * sets all node connections to false.
     * @param isAirport If the city has or has not an airport.
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
//            VisualWindow.totalCost += this.getAriportCost();
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
     * @return The x coordiante value of this node.
     */
    public int getX() {
        return x;
    }

    /**
     * @param x The x coordinate value that the node will have.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return The y coordinate value of this node.
     */
    public int getY() {
        return y;
    }

    /**
     * @param y The y coordinate value that the node will have.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * 
     * @return The name of the city or place.
     */
    public String getData() {
        return data;
    }

    /**
     * @return The ariport construction cost of the city or place.
     */
    public int getAriportCost() {
        return ariportCost;
    }

    /**
     * @return The actual image of the node.
     */
    public java.awt.image.BufferedImage getImg() {
        return img;
    }

    /**
     * @param img The image that the node will have.
     */
    public void setImg(java.awt.image.BufferedImage img) {
        this.img = img;
    }

    /**
     * @return A boolean array that represents the possible paths that a city or
     * place may have.
     */
    public boolean[] getPossiblePaths() {
        return possiblePaths;
    }

    /**
     * @param possiblePaths The list of possible paths that a city or place may
     * have.
     */
    public void setPossiblePaths(boolean[] possiblePaths) {
        this.possiblePaths = possiblePaths;
    }

    /**
     * @return An int array with the cost of the possible conections that the 
     * city or place may have.
     */
    public int[] getCosts() {
        return costs;
    }

    /**
     * @param costs The int arrary of the costs that represents the possible 
     * conections of the city or place with the other cities or places.
     */
    public void setCosts(int[] costs) {
        this.costs = costs;
    }

    /**
     * @return If this city or place has been previously sorted.
     */
    public boolean isSorted() {
        return sorted;
    }

    /**
     * @param sorted The value that represents if this node is sorted.
     */
    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    /**
     * @return The actual connections that this city or place have.
     */
    public boolean[] getConnections() {
        return connections;
    }

    /**
     * @param connections A boolean array that reprensents the actual 
     * connections of this city or place.
     */
    public void setConnections(boolean[] connections) {
        this.connections = connections;
    }
}

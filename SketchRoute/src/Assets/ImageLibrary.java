/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assets;

/**
 *
 * @author cddr
 */
public class ImageLibrary {
    public static java.awt.image.BufferedImage PLACE_STATE_NOTHING;
    public static java.awt.image.BufferedImage PLACE_STATE_ROAD;
    public static java.awt.image.BufferedImage PLACE_STATE_AIRPORT;
    
    public static void loadIamges(){
        try{
            PLACE_STATE_NOTHING = javax.imageio.ImageIO.read((new java.io.File("assets/place_state_nothing.png")));
            PLACE_STATE_AIRPORT = javax.imageio.ImageIO.read((new java.io.File("assets/place_state_airport.png")));
            PLACE_STATE_ROAD = javax.imageio.ImageIO.read((new java.io.File("assets/place_state_road.png")));
        }catch(java.io.IOException e){
            
        }
    }
}

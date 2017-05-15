/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sketcher;

/**
 *
 * @author cddr
 */
public class Sketcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Assets.ImageLibrary.loadIamges();
        new Visual.VisualWindow();
        new Visual.InfoWindow();
    }
    
}

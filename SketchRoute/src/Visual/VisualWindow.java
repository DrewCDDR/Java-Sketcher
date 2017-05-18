/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

import java.awt.HeadlessException;

/**
 * @author cddr
 */
public class VisualWindow extends javax.swing.JFrame{
     
    Display canvas;
    public static boolean imDone = false;
    public static int airportIndex, totalCost;
    public static int[] airports, cities, posibilities;
    public static String[] actions;
    public static Structures.Place[] PLACES;
    
    public VisualWindow() throws HeadlessException {
        setLayout(null);
        setSize(610, 524);
        setResizable(false);
        setTitle("Sketcher");
        getContentPane().setBackground(java.awt.Color.black);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        java.awt.Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/4 - 305, dim.height/2 - 262);
        totalCost = 0;
//        setLocationRelativeTo(null);

        setGI();
        
        setVisible(true);
        
        new Thread (canvas).start();
        
    }
    
    /**
     * This method sets the Graphic Interface or de displayable interface.
     */
    public void setGI(){
        canvas = new Display();
        canvas.setSize(608, 522);
        canvas.setLocation(1, 1);
        
//        canvas.setPlaces(PLACES);
        
        add(canvas);
    }
    
    /**
     * This method sets up all the information that may be needed for setting up
     * the landscape of the user.
     */
    public static void step_0_SetUP(){
        airports = new int[PLACES.length];
        cities = new int[PLACES.length];
        // FILLING.
        for (int i = 0; i < PLACES.length; i++) {
            airports[i] = PLACES[i].getAriportCost();
            cities[i] = i;
        }
        // SORTING.
        Util.Radix.radixsort(airports, airports.length);
        for (int i = 0; i < airports.length; i++) {
            boolean sw = true;
            int j = 0;
            while(j < airports.length && sw){
                if (PLACES[j].getAriportCost() == airports[i]) {
                    if (!PLACES[j].isSorted()) {
                        cities[i] = j;
                        PLACES[j].setSorted(true);
                        sw = false;
                    }
                }
                j++;
            }
        }
    }
    
    /**
     * This method sets up all the information that the step number two may need.
     */
    public static void step_1_setAirportsPossibilites(){
        int possibleActions = 0;
        //GETTIN' ALL AIRPORTS POSSIBLE CONNECTIONS NUMBER
        for (int i = 0; i < PLACES.length; i++) {
            if (PLACES[i].isAirport()) {
                for (int j = 0; j < PLACES[i].getPossiblePaths().length; j++) {
                    if (PLACES[i].getPossiblePaths()[j]) {
                        possibleActions++;
                    }
                }
            }
        }
        if(possibleActions > 0){
            boolean[] seen = new boolean[possibleActions];
            posibilities = new int[possibleActions]; 
            int[] pair = new int[2] ;
            actions = new String[possibleActions]; 
            String[] coords = new String[2] ;
            int k = 0;
            //FILLING POSIBILITIES.
            for (int i = 0; i < PLACES.length; i++) {
                if (PLACES[i].isAirport()) {
                    for (int j = 0; j < PLACES[i].getPossiblePaths().length; j++) {
                        if (PLACES[i].getPossiblePaths()[j]) {
                            pair[0] = PLACES[i].getCosts()[j];
                            coords[0] = "R;" +i +"," +j;
                            pair[1] = PLACES[j].getAriportCost();
                            coords[1] = "A;" +j +",0";
                            if (pair[0] <= pair[1]) {
                                posibilities[k] = pair[0];
                                actions[k] = coords[0];
                            }else{
                                posibilities[k] = pair[1];
                                actions[k] = coords[1];                            
                            }
                            seen[k] = false;
                            k++;
                        }
                    }
                }
            }
            //SORT POSIBILITIES WITH ACTIONS.
            boolean delta = false;
            int[] v = posibilities;
            Util.Radix.radixsort(posibilities, posibilities.length);
            for (int i = 0; i < v.length; i++) {
                if (v[i] != posibilities[i]) {
                    delta = false;
                }
            }
            if(delta){
                for (int i = 0; i < posibilities.length; i++) {
                    int x = Integer.parseInt(actions[i].split(";")[1].split(",")[0]);
                    int y = Integer.parseInt(actions[i].split(";")[1].split(",")[1]);
                    int j = 0;
                    boolean sw = true;
                    while(j < posibilities.length && sw){
                        if(actions[i].split(";")[0].equals("R")){
                            if (posibilities[j] == PLACES[x].getCosts()[y] ) {
                                if (!seen[i]) {
                                    String temp = actions[j];
                                    seen[i] = true;
                                    sw = false;
                                    actions[j] = actions[i];
                                    actions[i] = temp;
                                }
                            }
                        }else if(actions[i].split(";")[0].equals("A")){
                            if (posibilities[j] == PLACES[x].getAriportCost()) {
                                if (!seen[i]) {
                                    String temp = actions[j];
                                    seen[i] = true;
                                    sw = false;
                                    actions[j] = actions[i];
                                    actions[i] = temp;
                                }
                            }
                        }
                        j++;
                    }
                }
            }
        }
    }
    
    /**
     * This method see the best action that can be performed by all the
     * cities or places in the current situation by setting an airport or 
     * setting a connection.
     * @return True if a choice of building an airport or an airport with a 
     * connection, False if not.
     */
    public static boolean step_2_doSomething(){
        imDone = true;
        boolean choiceMade = false;
        int check = 0;
        while(check < actions.length && !choiceMade){
            int x = Integer.parseInt(actions[check].split(";")[1].split(",")[0]);
            int y = Integer.parseInt(actions[check].split(";")[1].split(",")[1]);
            String token = actions[check].split(";")[0];
            if(token.equals("R")){
                if (PLACES[y].isNothing()) {
                    PLACES[x].setConnectionWith(y);
                    PLACES[y].setConnectionWith(x);
                    PLACES[y].setIsRoad(true);
                    System.out.println("\tConexión, entre " +PLACES[x].getData() +" y " +PLACES[y].getData());
                    totalCost += PLACES[x].getCosts()[y];
                    choiceMade = true;
                    for (int i = 0; i < PLACES[x].getPossiblePaths().length; i++) {
                        if (PLACES[x].getPossiblePaths()[i] != PLACES[x].getConnections()[i]) {
                            imDone = false;
                        }
                    }
                }
            }else if(token.equals("A")){
                if (PLACES[x].isNothing()) {
                    PLACES[x].setIsAirport(true);
                    System.out.println("\tConstrucción, aereopuerto en " +PLACES[x].getData());
                    totalCost += PLACES[x].getAriportCost();
                    choiceMade = true;
                }
            }
            check++;
        }
        int a = 0;
        if (choiceMade) {
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * This method sets all the necesary information that the step four may need.
     */
    public static void step_3_checkLonelyPlacesPossibilites(){
        boolean sw = true;
        int possibleActions = 0, index = 0, i = 0;
        while(i < PLACES.length && sw){
            if (PLACES[i].isNothing()) {
                index = i;
                sw = false;
            }
            i++;
        }
        for (int j = 0; j < PLACES.length; j++) {
            if (PLACES[index].getPossiblePaths()[j]) {
                possibleActions++;
            }
        }
        if(possibleActions > 0){
            boolean[] seen = new boolean[possibleActions];
            posibilities = new int[possibleActions];
            int[] pair = new int[2];
            actions = new String[possibleActions];
            String[] coords = new String[2];
            int k = 0;
            for (int j = 0; j < PLACES.length; j++) {
                if (PLACES[index].getPossiblePaths()[j]) {
                    pair[1] = PLACES[index].getAriportCost();
                    coords[1] = "A;" +index +",0";
                    pair[0] = PLACES[j].getAriportCost() + PLACES[j].getCosts()[index];
                    coords[0] = "R;" +index +"," +j; 
                    if (pair[0] <= pair[1]) {
                        posibilities[k] = pair[0];
                        actions[k] = coords[0];
                    }else{
                        posibilities[k] = pair[1];
                        actions[k] = coords[1];
                    }
                    seen[k] = false;
                    k++;
                }
            }

            boolean delta = false;
            int[] v = posibilities;
            for (int j = 0; j < posibilities.length; j++) {
                
            }
            Util.Radix.radixsort(posibilities, posibilities.length);
            for (int j = 0; j < v.length; j++) {
                if (v[j] != posibilities[j]) {
                    delta = false;
                }
            }
            if(delta){
                for (int l = 0; l < posibilities.length; l++) {
                    String s1 = actions[l].split(";")[1].split(",")[0];
                    String s2 = actions[l].split(";")[1].split(",")[1];
                    int x = Integer.parseInt(s1);
                    int y = Integer.parseInt(s2);
                    int j = 0;
                    boolean sel = true;
                    while(j < posibilities.length && sel){
                        String s3 = actions[l].split(";")[0];
                        if(s3.equals("R")){
                            if (posibilities[j] == PLACES[x].getCosts()[y] ) {
                                if (!seen[l]) {
                                    String temp = actions[j];
                                    seen[l] = true;
                                    sel = false;
                                    actions[j] = actions[l];
                                    actions[l] = temp;
                                }
                            }
                        }else if(s3.equals("A")){
                            if (posibilities[j] == PLACES[x].getAriportCost()) {
                                if (!seen[l]) {
                                    String temp = actions[j];
                                    seen[l] = true;
                                    sel = false;
                                    actions[j] = actions[l];
                                    actions[l] = temp;
                                }
                            }
                        }
                        j++;
                    }
                    l++;
                }
            }
        }
    }
            
    /**
     * This method will see the best action that can be performed by all the
     * cities or places in the current situation by setting an airport or an
     * airport with a connection.
     * @return True if a choice of building an airport or an airport with a 
     * connection, False if not.
     */
    public static boolean step_4_lonelyBecomesUnlonely(){
        boolean choiceMade = false;
        int check = 0;
        while(check < actions.length && !choiceMade){
            int x = Integer.parseInt(actions[check].split(";")[1].split(",")[0]);
            int y = Integer.parseInt(actions[check].split(";")[1].split(",")[1]);
            String token = actions[check].split(";")[0];
            if(token.equals("R")){
                if (PLACES[y].isNothing() || PLACES[y].isRoad()) {
                    if(!PLACES[y].isAirport()){
                        PLACES[y].setIsAirport(true);
                        if(!PLACES[x].isAirport()){
                            PLACES[x].setConnectionWith(y);
                            PLACES[y].setConnectionWith(x);
                            PLACES[x].setIsRoad(true);
                            totalCost += PLACES[y].getAriportCost();
                            System.out.println("\tConstruccion, aereopuerto en " +PLACES[y].getData());
                        }else{
                            PLACES[x].setConnectionWith(y);
                            PLACES[y].setConnectionWith(x);
                            PLACES[y].setIsRoad(true);
                        }
                        totalCost += PLACES[x].getCosts()[y];
                        System.out.println("\tConexión, entre " +PLACES[x].getData() +" y " +PLACES[y].getData());
                    }
                    choiceMade = true;
                }
            }else if(token.equals("A")){
                if (PLACES[x].isNothing()) {
                    PLACES[x].setIsAirport(true);
                    System.out.println("\tConstruccion, aereopuerto en " +PLACES[x].getData());
                    totalCost += PLACES[x].getAriportCost();
                    choiceMade = true;
                }
            }
            check++;
        }
        if (choiceMade) {
            return true;
        }else{
            return false;
        }
    }
    /**
     * This method checks a lonely place that should have an airport.
     */
   public static void step_5_IllBeForeverAlone(){
       boolean sw = true;
       int i = 0;
       while(i < PLACES.length && sw){
           if (PLACES[i].isNothing()) {
               PLACES[i].setIsAirport(true);
               System.out.println("\tConstrucción, aereopuerto en " +PLACES[i].getData());
               totalCost += PLACES[i].getAriportCost();
               sw = false;
           }
           i++;
       }
   }
   
   /**
    * This method is a revision in order to see if all it's fine, if not this 
    * method would turn everything in order to be fine.
    */
   public static void step_6_lastCheck(){
       for (int i = 0; i < PLACES.length; i++) {
           if (PLACES[i].isNothing()) {
               boolean exilied = true;
               for (int j = 0; j < PLACES[i].getPossiblePaths().length; j++) {
                   if (PLACES[i].getPossiblePaths()[j]) {
                       exilied = false;
                   }
               }
               if (exilied) {
                   PLACES[i].setIsAirport(true);
                   System.out.println("\tConstrucción, aereopuerto en " +PLACES[i].getData());
                   totalCost += PLACES[i].getAriportCost();
               }else{
                   step_3_checkLonelyPlacesPossibilites();
                   if(!step_4_lonelyBecomesUnlonely()){
                       step_5_IllBeForeverAlone();
                   }
               }
           }
       }
   }
   /**
    * This method find the best airport that can be built.
    * @return The index of the city or place that should have an airport built.
    */
   public static int checkAllAirports_ReturnBestOption(){
        boolean sw = true;
        boolean[] passed;
        int i = 0;
        int[] sameCostIndexes, possibles;
        while(i < PLACES.length && sw){
             if(i +1 < PLACES.length){    
                 if (PLACES[cities[i]].getAriportCost() != PLACES[cities[i +1]].getAriportCost()) {
                     sw = false;
                 }
             }else{
                 sw = false;
             }
            i++;
        }
        if(i > 0){
            if(sw){
                i = i-1;
            }
            sameCostIndexes = new int[i];
            possibles = new int[i];
            passed = new boolean[i];
        }else{
            return -1;
        }
        for (int j = 0; j < sameCostIndexes.length; j++) {
            sameCostIndexes[j] = cities[j];
            possibles[j] = PLACES[cities[j]].getPossiblePathsCount();
            passed[j] = false;
        }
        Util.Radix.radixsort(possibles, possibles.length);
        for (int j = 0; j < sameCostIndexes.length; j++) {
            for (int k = 0; k < sameCostIndexes.length; k++) {
                if (PLACES[sameCostIndexes[k]].getPossiblePathsCount() == possibles[j]) {
                    if (!passed[j]) {
                        int temp = sameCostIndexes[j];
                        sameCostIndexes[j] = sameCostIndexes[k];
                        sameCostIndexes[k] = temp;
                    }
                }
            }
        }
       return sameCostIndexes[sameCostIndexes.length -1];
   }
    
   /**
    * This method solves the situation that the user proposed.
    */
    public static void buildLandscape(){
        boolean Done = false;
        boolean one = false;
        int i = 0;
        totalCost =0;
        airportIndex = 0;
        step_0_SetUP();

        while (!Done) {
            if (!one) {
                for (int j = 0; j < PLACES.length; j++) {
                    boolean isLonely = true;
                    for (int k = 0; k < PLACES.length; k++) {
                        if (PLACES[j].getPossiblePaths()[k]) {
                            isLonely = false;
                        }
                    }
                    if (isLonely) {
                        PLACES[j].setIsAirport(true);
                        System.out.println("\tConstrucción, aereopuerto en " +PLACES[j].getData());
                        totalCost += PLACES[j].getAriportCost();
                        airportIndex++;
                        i++;
                    }
                }
                if(airportIndex < PLACES.length){
                    int p = airportIndex;
                    if (PLACES[cities[airportIndex]].isNothing()) {
                        if (checkAllAirports_ReturnBestOption() >= 0) {
                            PLACES[checkAllAirports_ReturnBestOption()].setIsAirport(true);
                            p = checkAllAirports_ReturnBestOption();
                        }else{
                            PLACES[cities[airportIndex]].setIsAirport(true);
                            p = cities[airportIndex];
                        }
                        System.out.println("\tConstrucción, aereopuerto en " +PLACES[p].getData());
                        totalCost += PLACES[p].getAriportCost();
                        one = true;
                        airportIndex++;
                        i++;
                    }
                }
            }
            if(airportIndex < PLACES.length){
                if (PLACES[i].isNothing() || i < PLACES.length) {
                    step_1_setAirportsPossibilites();
                    if(actions != null){
                        if(step_2_doSomething()){
                            i++;
                        }else{
                            if(imDone){
                                step_3_checkLonelyPlacesPossibilites();
                                if(!(getEmptyPlacesCount() > 1)){
                                    if (step_4_lonelyBecomesUnlonely()) {
                                        i++;
                                    }else{
                                        step_5_IllBeForeverAlone();
                                    }
                                }else{
                                    if (PLACES[cities[airportIndex]].isNothing()) {
                                        PLACES[cities[airportIndex]].setIsAirport(true);
                                        System.out.println("\tConstrucción, aereopuerto en " +PLACES[cities[airportIndex]].getData());
                                        totalCost += PLACES[cities[airportIndex]].getAriportCost();
                                        airportIndex++;
                                        i++;
                                    }else{
    //                                    i++;
                                    }
                                }
                            }else{
                                i++;
                            }
                        }
                    }else{
                        if(PLACES[cities[airportIndex]].isNothing()){
                            PLACES[cities[airportIndex]].setIsAirport(true);
                            System.out.println("\tConstrucción, aereopuerto en " +PLACES[cities[airportIndex]].getData());
                            totalCost += PLACES[cities[airportIndex]].getAriportCost();
                            airportIndex++;
                            i++;
                        }
                    }
                }else{
                    i++;
                }
            }
//            System.out.println("i: " +i);
            if (i == PLACES.length) {
                Done = true;
                step_6_lastCheck();
            }
        }
    }
    /**
     * This method counts the number of cities or places that are "disconnected".
     * @return The number of cities or places that aren't either a road or has
     * an airport built in it.
     */
    public static int getEmptyPlacesCount(){
        int c = 0;
        for (int i = 0; i < PLACES.length; i++) {
            if (PLACES[i].isNothing()) {
                c++;
            }
        }
        return c;
    }
}

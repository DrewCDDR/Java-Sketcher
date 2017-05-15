/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visual;

/**
 *
 * @author cddr
 */
public class Display extends java.awt.Canvas implements Runnable{
    
    private int c;
    private boolean IsGraphReady; 
//    private Structures.Place[] p;
    
    @Override
    public void run() {
        
        createBufferStrategy(3);
        float[] f = new float[3];
        java.awt.Color.RGBtoHSB(219, 248, 225, f);
        setBackground(java.awt.Color.getHSBColor(f[0], f[1], f[2]));
        c = 0;
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e){
                if (VisualWindow.PLACES != null) {
                    VisualWindow.PLACES[c%VisualWindow.PLACES.length].setCoords(e.getX() -32, e.getY() -32);
//                    repaint();
                    c++;
                }
            }           
        });
        
        while(true){
            java.awt.Graphics g = getBufferStrategy().getDrawGraphics();
            if (VisualWindow.PLACES != null) {
                g.clearRect(0, 0, 608, 522);
                g.setColor(java.awt.Color.red);
                
                for (int i = 0; i < VisualWindow.PLACES.length; i++) {
                    for (int j = 0; j < VisualWindow.PLACES.length; j++) {
                        if (VisualWindow.PLACES[i].getConnections()[j]) {
                            int x_1 = VisualWindow.PLACES[i].getX() +32;
                            int y_1 = VisualWindow.PLACES[i].getY() +32;
                            int x_2 = VisualWindow.PLACES[j].getX() +32;
                            int y_2 = VisualWindow.PLACES[j].getY() +32;
                            g.drawLine(x_1, y_1, x_2, y_2);
                        }
                    }
                }
                
                for (int i = 0; i < VisualWindow.PLACES.length; i++) {
                    java.awt.image.BufferedImage img = VisualWindow.PLACES[i].getImg();
                    g.drawImage(img, VisualWindow.PLACES[i].getX(), VisualWindow.PLACES[i].getY(), 64, 64, null);
                    g.setColor(java.awt.Color.gray.darker());
                    g.fillRect(VisualWindow.PLACES[i].getX(), VisualWindow.PLACES[i].getY() +48, 64, 16);
                    g.setColor(java.awt.Color.white.brighter());
                    g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
                    g.drawString(VisualWindow.PLACES[i].getData(), VisualWindow.PLACES[i].getX() +1, VisualWindow.PLACES[i].getY() +60);
                }
                
                
                getBufferStrategy().show();
            }
        }
    }
    
    @Override
    public void paint(java.awt.Graphics g){
        
//        super.paint(g);
        /*nodes.stream().filter((n) -> (n.isValid())).forEachOrdered((n) -> {
            g.setColor(Color.white);
            g.fillOval(n.getX()-radius/2, n.getY()-radius/2, radius, radius);
            g.setColor(Color.black);
            g.drawString(n.getData(), n.getX(), n.getY());
        });*/
        /*if (IsGraphReady) {
            g.setColor(Color.red);
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph.length; j++) {
                    if (graph[i][j] == 1) {
                        Node ni = nodes.get(i), nf = nodes.get(j);
                        g.drawLine(ni.getX(), ni.getY(), nf.getX(), nf.getY());
                    }
                }
            }
        }*/
    }

    /**
     * @return the IsGraphReady
     */
    public boolean isIsGraphReady() {
        return IsGraphReady;
    }

    /**
     * @param IsGraphReady the IsGraphReady to set
     */
    public void setIsGraphReady(boolean IsGraphReady) {
        this.IsGraphReady = IsGraphReady;
    }
}

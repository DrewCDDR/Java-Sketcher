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
public class InfoWindow extends javax.swing.JFrame{
    
//    private boolean IsGraphReady = false;
//    int[] airports;
//    int[][] landscape, prices, routes;
    javax.swing.JLabel nameLabel, airportLabel;
    javax.swing.JButton add, remv, sketch;
    javax.swing.JTextField name, airport;
    javax.swing.JTextArea info;
    javax.swing.JTable table;
    private Structures.Place[] places;
    
    public InfoWindow() throws java.awt.HeadlessException {
        setLayout(null);
        setSize(610, 524);
        setResizable(false);
        setTitle("Sketcher");
        getContentPane().setBackground(java.awt.Color.black);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setDataUI();
        java.awt.Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width*3/4 - 305, dim.height/2 - 262);
//        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void setDataUI(){
        nameLabel = new javax.swing.JLabel("Nombre: ");
        nameLabel.setBackground(java.awt.Color.gray);
        nameLabel.setForeground(java.awt.Color.white);
        nameLabel.setSize(55, 20);
        nameLabel.setLocation(10, 420);
        add(nameLabel);
        
        airportLabel = new javax.swing.JLabel("Aereopuerto:");
        airportLabel.setBackground(java.awt.Color.gray);
        airportLabel.setForeground(java.awt.Color.white);
        airportLabel.setSize(80, 20);
        airportLabel.setLocation(10, 460);
        add(airportLabel);
        
        name = new javax.swing.JTextField("");
//        name.setFocusable(false);
        name.setSize(80, 20);
        name.setLocation(70, 420);
        add(name);
        
        airport = new javax.swing.JTextField("");
//        airport.setFocusable(false);
        airport.setSize(55, 20);
        airport.setLocation(95, 460);
        add(airport);
        
        add = new javax.swing.JButton("Agregar");
        add.setFocusable(false);
        add.setSize(90, 60);
        add.setLocation(160, 420);
        add.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });
        add(add);
        
        remv = new javax.swing.JButton("Remover");
        remv.setFocusable(false);
        remv.setSize(90, 60);
        remv.setLocation(260, 420);
        remv.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });
        add(remv);
        
        sketch = new javax.swing.JButton("Sketch");
        sketch.setFocusable(false);
        sketch.setSize(90, 60);
        sketch.setLocation(505, 420);
        sketch.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SketchActionPerformed(evt);
            }
        });
        add(sketch);
        
        info = new javax.swing.JTextArea();
        info.setLocation(380, 420);
        info.setSize(90, 60);
        info.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        info.setText("Ingrese \"x\", para desconectar 2 ciudades.");
        info.setEditable(false);
        info.setBackground(java.awt.Color.black);
        info.setForeground(java.awt.Color.gray.brighter());
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        info.setVisible(true);
        this.add(info);
        
        table = new javax.swing.JTable(){
            
            
        };
        
        table.setModel(new CustomModel(new String[] {"Aereopuerto", "Nombres"}, 0));
        table.getTableHeader().setForeground(java.awt.Color.gray.darker());
        table.setDragEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(35);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.setSize(600,400);
        table.setLocation(0, 0);
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        
        javax.swing.JScrollPane movement = new javax.swing.JScrollPane
            (table, javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        movement.setSize(600, 400);
        movement.setLocation(1, 1);
        add(movement);
    }
    
    public void AddActionPerformed(java.awt.event.ActionEvent evt){
        String validation = name.getText();
        String confirmation = airport.getText();
        if (!validation.equals("") && !confirmation.equals("")) {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel)table.getModel();
            Object[] columns = new Object[table.getRowCount()];
            model.addColumn(validation, columns);
            Object[] rows = new Object[table.getColumnCount()];
            
            for (int i = 2; i < rows.length; i++) {
                rows[i] = "0";
            }
            
            rows[0] = airport.getText();
            rows[1] = validation;
            model.addRow(rows);
            javax.swing.table.DefaultTableCellRenderer center = new javax.swing.table.DefaultTableCellRenderer();
            center.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            for (int i = 0; i < rows.length; i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(center);
            }
            name.setText("");
//            airport.setText("");
            createLandscape();
        }else{
            javax.swing.JOptionPane.showMessageDialog(null,"Campos vacios.", "Error #1", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    public void RemoveActionPerformed(java.awt.event.ActionEvent evt){
        int rowIndex = table.getSelectedRow();
        if (rowIndex >= 0) {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
            model.removeRow(rowIndex);
            ((CustomModel) model).removeColumn(rowIndex +2);
        }
        createLandscape();
    }
    public void SketchActionPerformed(java.awt.event.ActionEvent evt){
        System.out.println("Sketch started:");
        createLandscape();
        VisualWindow.buildLandscape();
        System.out.println("  Costo total: " +VisualWindow.totalCost);
        System.out.println("Sketch Done.");
        javax.swing.JOptionPane.showMessageDialog(null,"Costo total: " +VisualWindow.totalCost, "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
 
    
    public void createLandscape(){
        javax.swing.table.DefaultTableModel m = (javax.swing.table.DefaultTableModel) table.getModel();
        places = new Structures.Place[m.getRowCount()];
        
        try{
//             Filling the table empty slots.
            for (int i = 0; i < m.getRowCount(); i++) {
                for (int j = 2; j < m.getColumnCount(); j++) {
                    if (i == (j -2)) {
                        table.setValueAt("x", i, i+2);
                    }else if(j > (i +2)){
                        table.setValueAt(table.getValueAt(j -2, i +2).toString(), i, j);
                    }
                }
            }
            for (int i = 0; i < table.getRowCount(); i++) {
                String[] connections = new String[table.getRowCount()];
                for (int j = 0; j < connections.length; j++) {
                    connections[j] = "";
                }
                for (int j = 2; j < table.getColumnCount(); j++) {
                    connections[j-2] = table.getValueAt(i, j).toString();
                }
                places[i] = new Structures.Place(table.getValueAt(i, 1).toString(), Integer.parseInt(table.getValueAt(i, 0).toString()), places.length, i);
                places[i].setPossiblePaths(Structures.Place.findPossiblePaths(connections));
                places[i].setCosts(Structures.Place.findCosts(connections));
                
            }
            if (VisualWindow.PLACES != null) {
                for (int i = 0; i < Integer.min(places.length, VisualWindow.PLACES.length); i++) {
                    places[i].setCoords(VisualWindow.PLACES[i].getX(), VisualWindow.PLACES[i].getY());
                }
            }
            VisualWindow.PLACES = places;
        }catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"Digite números validos.", "Error #2", javax.swing.JOptionPane.ERROR_MESSAGE);
        }finally{
            for (int i = 0; i < m.getRowCount(); i++) {
                for (int j = 2; j < m.getColumnCount(); j++) {
                    if (i == (j -2)) 
                        table.setValueAt("", i, i+2);
                    else if(j > (i +2))
                        table.setValueAt("", i, j);
                }
            }
        }
    }
    
    private class CustomModel extends javax.swing.table.DefaultTableModel{
        
        public CustomModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return ((column != 1) && !(column >=  row+2));
        }
        
        public java.util.Vector getColumnIdentifiers(){
            return columnIdentifiers;
        }
        
        public void removeColumn(int columnIndex) {
            if (columnIndex != getColumnCount()-1) {
                for (int i = 0; i < this.getRowCount(); i++) {
                    java.util.Vector v = (java.util.Vector)dataVector.elementAt(i);
                    v.setElementAt(v.elementAt(columnIndex+1), columnIndex);
                }
            }
            columnIdentifiers.remove(columnIndex);
            fireTableStructureChanged();
        }
    }
}

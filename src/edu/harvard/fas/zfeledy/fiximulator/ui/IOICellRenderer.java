/*
 * File     : IOICellRenderer.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This renderer is used on the JTable that displays IOI 
 *            messages and colors the messages based on their types.
 * 
 */

package edu.harvard.fas.zfeledy.fiximulator.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class IOICellRenderer  extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        int myRow = table.convertRowIndexToModel(row);
        Component component = super.getTableCellRendererComponent(table, value,
                                          isSelected, hasFocus, myRow, column);
        String type = (String) ((IOITableModel)table.getModel())
                .getValueAt(myRow, 1);
        if (type.equals("NEW")) {
            component.setForeground(Color.BLACK);
        }
        if (type.equals("CANCEL")) {
            component.setForeground(Color.RED);
        }
        if (type.equals("REPLACE")) {
            component.setForeground(Color.BLUE);
        }
        return component;
    }        
}

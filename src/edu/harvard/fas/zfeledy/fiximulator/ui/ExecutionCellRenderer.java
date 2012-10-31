/*
 * File     : ExecutionCellRenderer.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This renderer is used on the JTable that displays 
 *            Executions and colors the executions that have received
 *            a DontKnowTrade.
 * 
 */

package edu.harvard.fas.zfeledy.fiximulator.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ExecutionCellRenderer  extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        int myRow = table.convertRowIndexToModel(row);
        Component component = super.getTableCellRendererComponent(table, value,
                                          isSelected, hasFocus, myRow, column);
        Boolean DKd = (Boolean)((ExecutionTableModel)table.getModel())
                .getValueAt(myRow, 12);
                
        if ( DKd ) {
            component.setForeground(Color.RED);
        }
        if ( !DKd ) {
            component.setForeground(Color.BLACK);
        }
        return component;
    }        
}

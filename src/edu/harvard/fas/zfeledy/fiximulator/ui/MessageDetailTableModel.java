/*
 * File     : MessageDetailTableModel.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is the TableModel for the Message Details 
 *            Table.
 * 
 */

package edu.harvard.fas.zfeledy.fiximulator.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import edu.harvard.fas.zfeledy.fiximulator.core.FIXimulator;
import edu.harvard.fas.zfeledy.fiximulator.core.LogMessage;
import edu.harvard.fas.zfeledy.fiximulator.core.LogMessageSet;
import edu.harvard.fas.zfeledy.fiximulator.util.LogField;

public class MessageDetailTableModel extends AbstractTableModel 
        implements ListSelectionListener {
    private static LogMessageSet messages = FIXimulator.getMessageSet();
    private JTable messageTable = null; 
    private ArrayList<LogField> fields = new ArrayList<LogField>();
    private static String[] columns = 
        {"Field", "Tag", "Value", "Value Name", "Required", "Section"};
        
    public MessageDetailTableModel(JTable messageTable){
        this.messageTable = messageTable;
        messageTable.getSelectionModel().addListSelectionListener(this);
    }
    
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class getColumnClass(int column) {
        if (column == 1) return Integer.class;
        return String.class;
    }
        
    public int getRowCount() {
        return fields.size();
    }

    public Object getValueAt( int row, int column ) {
        LogField logField = fields.get( row );
        if (column == 0) return logField.getFieldName();
        if (column == 1) return logField.getTag();
        if (column == 2) return logField.getValue();
        if (column == 3) return logField.getFieldValueName();
        if (column == 4) return (logField.isRequired() ? "Yes" : "No");
        if (column == 5) {
            if (logField.isHeaderField()) return "Header";
            if (logField.isTrailerField()) return "Trailer";
            return "Body";
        }
        return null;
    }

    public void updateMessageDetailsTable(LogMessage message) {
        LogMessage logMessage = message;
        List<LogField> logFields = logMessage.getLogFields();
        fields.clear();

        for (LogField logField : logFields) {
            fields.add(logField);
        }
        
        fireTableDataChanged();
    }
    
    public void valueChanged(ListSelectionEvent selection) {
        if (!selection.getValueIsAdjusting()) {
            int row = messageTable.getSelectedRow();
            // if the first row is selected when it gets purged
            if ( row != -1 ) {
                row = messageTable.convertRowIndexToModel(row);
                LogMessage msg = messages.getMessage( row );
                updateMessageDetailsTable(msg);
            }
        }
    }
}

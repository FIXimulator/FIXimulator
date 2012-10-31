/*
 * File     : QueryTableModel.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is the TableModel for the SQL queries for 
 *            reporting.
 * 
 */

package edu.harvard.fas.zfeledy.fiximulator.ui;

import java.sql.*;
import edu.harvard.fas.zfeledy.fiximulator.core.FIXimulator;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

class QueryTableModel extends AbstractTableModel {
    Vector results = new Vector();
    private static String[] columns = {"Results"};
    Connection connection;
    Statement statement;
    String url;
    String driver;
    String user;
    String pass;
    
    public QueryTableModel() {
        try {
            url = FIXimulator.getApplication().getSettings()
                    .getString("JdbcURL");
            driver = FIXimulator.getApplication().getSettings()
                    .getString( "JdbcDriver");
            user = FIXimulator.getApplication().getSettings()
                    .getString("JdbcUser");
            pass = FIXimulator.getApplication().getSettings()
                    .getString("JdbcPassword");
        } catch (Exception e) {}
    }

    public int getRowCount() {
        return results.size();
    }

    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public Object getValueAt(int row, int column) {
        return ((String[])results.elementAt(row))[column];
    }
    
    public void setQuery(String query) {
        results = new Vector();

        try {
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(url, user, pass);
        }
        catch(Exception e) {
            System.out.println("Could not initialize the database.");
            e.printStackTrace();
        }
        
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData meta = (ResultSetMetaData) rs.getMetaData();
            int fields = meta.getColumnCount();
            columns = new String[fields];
            for ( int i=0; i < fields; i++) {
                columns[i] = meta.getColumnName(i+1);
            }
            while (rs.next()) {
                String[] record = new String[fields];
                for (int i=0; i < fields; i++) {
                    record[i] = rs.getString(i + 1);
                }
                results.addElement(record);
            }
            statement.close();
            rs.close();
            fireTableChanged(null);
        } catch(Exception e) {
            results = new Vector();
            e.printStackTrace();
        }
        
        if (connection != null) {
            try {
                connection.close ();
            } catch (Exception e) {}
        }
    }
}

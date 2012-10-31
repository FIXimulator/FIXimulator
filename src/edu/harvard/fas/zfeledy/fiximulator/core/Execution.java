/*
 * File     : Execution.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is a basic Execution object that is used to 
 *            create and store execution details.
 * 
 */

package edu.harvard.fas.zfeledy.fiximulator.core;

public class Execution implements Cloneable {
    private static int nextID = 1;
    private Order order;
    private boolean DKd = false;
    private String ID = null;
    private String refID = null;
    private char execType;
    private char execTranType;
    private double lastShares = 0.0;
    private double lastPx = 0.0;
    private double leavesQty = 0.0;
    private double cumQty = 0.0;
    private double avgPx = 0.0;

    @Override
    public Execution clone() {
        try {
            Execution execution = (Execution)super.clone();
            execution.setRefID(getID());
            execution.setID(generateID());
            execution.setDKd(false);
            return execution;
        } catch(CloneNotSupportedException e) {}
        return null;
    }
        
    public Execution( Order order ) {
        ID = generateID();
        this.order = order;
    }
    
    public String generateID() {
        return "E" + Long.valueOf(
                System.currentTimeMillis()+(nextID++)).toString();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isDKd() {
        return DKd;
    }

    public void setDKd(boolean DKd) {
        this.DKd = DKd;
    }

    public double getAvgPx() {
        return avgPx;
    }

    public void setAvgPx(double avgPx) {
        this.avgPx = avgPx;
    }

    public double getCumQty() {
        return cumQty;
    }

    public void setCumQty(double cumQty) {
        this.cumQty = cumQty;
    }

    public String getExecTranType() {
        if (execTranType == '0') return "New";
        if (execTranType == '1') return "Cancel";
        if (execTranType == '2') return "Correct";
        if (execTranType == '3') return "Status";
        return "<UNKOWN>";
    }

    public char getFIXExecTranType() {
        return execTranType;
    }
    
    public void setExecTranType(char execTranType) {
        this.execTranType = execTranType;
    }

    public String getExecType() {
        if (execType == '0') return "New";
        if (execType == '1') return "Partial fill";
        if (execType == '2') return "Fill";
        if (execType == '3') return "Done for day";
        if (execType == '4') return "Canceled";
        if (execType == '5') return "Replace";
        if (execType == '6') return "Pending Cancel";
        if (execType == '7') return "Stopped";
        if (execType == '8') return "Rejected";
        if (execType == '9') return "Suspended";
        if (execType == 'A') return "Pending New";
        if (execType == 'B') return "Calculated";
        if (execType == 'C') return "Expired";
        if (execType == 'D') return "Restated";
        if (execType == 'E') return "Pending Replace";
        return "<UNKNOWN>";
    }
    
    public char getFIXExecType() {
        return execType;
    }

    public void setExecType(char execType) {
        this.execType = execType;
    }

    public double getLastPx() {
        return lastPx;
    }

    public void setLastPx(double lastPx) {
        this.lastPx = lastPx;
    }

    public double getLastShares() {
        return lastShares;
    }

    public void setLastShares(double lastShares) {
        this.lastShares = lastShares;
    }

    public double getLeavesQty() {
        return leavesQty;
    }

    public void setLeavesQty(double leavesQty) {
        this.leavesQty = leavesQty;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }
}

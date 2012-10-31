/*
 * File     : Order.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is a basic Order object that is used to 
 *            create and store order details.
 * 
 */

package edu.harvard.fas.zfeledy.fiximulator.core;

import quickfix.FieldNotFound;
import quickfix.field.ClOrdID;
import quickfix.field.OrdType;
import quickfix.field.OrderQty;
import quickfix.field.OrigClOrdID;
import quickfix.field.Price;
import quickfix.field.SecurityID;
import quickfix.field.IDSource;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.TimeInForce;

public class Order implements Cloneable {
    private static int nextID = 1;
    private boolean receivedOrder = false;
    private boolean receivedCancel = false;
    private boolean receivedReplace = false;
    private boolean rejectedCancelReplace = false;
    private char side;
    private char type;
    private char tif = '0';     // Day order if omitted
    private char status;
    private String ID = null;
    private String clientID = null;
    private String origClientID = null;
    private String symbol = null;
    private String securityID = null;
    private String idSource = null;
    private double quantity = 0.0;
    private double open = 0.0;
    private double executed = 0.0;
    private double limit = 0.0;
    private double avgPx = 0.0;
    
    @Override
    public Order clone() {
        try {
            Order order = (Order)super.clone();
            order.setOrigClientID(getID());
            order.setID(generateID());
            return order;
        } catch ( CloneNotSupportedException e ) {}
        return null;
    }
    
    public Order () {
        ID = generateID();
    }
    
    public Order( quickfix.fix42.NewOrderSingle message ) {
        ID = generateID();
        
        // ClOrdID
        try {
            ClOrdID clOrdID = new ClOrdID();
            message.get(clOrdID);
            this.setClientID(clOrdID.getValue().toString());
        } catch (FieldNotFound ex) {}
        
        // Side
        try {
            Side msgSide = new Side();
            message.get(msgSide);
            setSide(msgSide.getValue());
        } catch (FieldNotFound ex) {}
        
        // Symbol
        try {
            Symbol msgSymbol = new Symbol();
            message.get(msgSymbol);
            setSymbol(msgSymbol.getValue());
        } catch (FieldNotFound ex) {}
        
        // Type
        try {
            OrdType msgType = new OrdType();
            message.get(msgType);
            setType(msgType.getValue());
        } catch (FieldNotFound ex) {}
        
        // OrderQty
        try {
            OrderQty msgQty = new OrderQty();
            message.get(msgQty);
            setQuantity(msgQty.getValue());
            setOpen(msgQty.getValue());
        } catch (FieldNotFound ex) {}
        
        // TimeInForce
        try {
            TimeInForce msgTIF = new TimeInForce();
            message.get(msgTIF);
            this.setTif(msgTIF.getValue());
        } catch (FieldNotFound ex) {}
        
        // Price
        try {
            Price price = new Price();
            message.get(price);
            this.setLimit(price.getValue());
        } catch (FieldNotFound ex) {}        

        // SecurityID
        try {
            SecurityID secID = new SecurityID();
            message.get(secID);
            this.setSecurityID(secID.getValue());
        } catch (FieldNotFound ex) {} 
        
        // IDSource
        try {
            IDSource idSrc = new IDSource();
            message.get(idSrc);
            this.setIdSource(idSrc.getValue());
        } catch (FieldNotFound ex) {}    
        
        System.out.println("SecurityID: " + this.getSecurityID());
        System.out.println("IDSource: " + this.getIdSource());
    }
    
    public Order( quickfix.fix42.OrderCancelRequest message ) {
        ID = generateID();
        
        // ClOrdID
        try {
            ClOrdID clOrdID = new ClOrdID();
            message.get(clOrdID);
            this.setClientID(clOrdID.getValue().toString());
        } catch (FieldNotFound ex) {}

        // OrigClOrdID
        try {
            OrigClOrdID origClOrdID = new OrigClOrdID();
            message.get(origClOrdID);
            this.setOrigClientID(origClOrdID.getValue().toString());
        } catch (FieldNotFound ex) {}
        
        Order oldOrder = FIXimulator.getApplication()
                .getOrders().getOrder(origClientID);
        if ( oldOrder != null ) {        
            open = oldOrder.getOpen();
            executed = oldOrder.getExecuted();
            limit = oldOrder.getLimit();
            avgPx = oldOrder.getAvgPx();
            status = oldOrder.getFIXStatus();
        }
        
        // Side
        try {
            Side msgSide = new Side();
            message.get(msgSide);
            setSide(msgSide.getValue());
        } catch (FieldNotFound ex) {}
        
        // Symbol
        try {
            Symbol msgSymbol = new Symbol();
            message.get(msgSymbol);
            setSymbol(msgSymbol.getValue());
        } catch (FieldNotFound ex) {}
        
        // OrderQty
        try {
            OrderQty msgQty = new OrderQty();
            message.get(msgQty);
            setQuantity(msgQty.getValue());
            setOpen(msgQty.getValue());
        } catch (FieldNotFound ex) {}
                
        // SecurityID
        try {
            SecurityID secID = new SecurityID();
            message.get(secID);
            this.setSecurityID(secID.getValue());
        } catch (FieldNotFound ex) {} 
        
        // IDSource
        try {
            IDSource idSrc = new IDSource();
            message.get(idSrc);
            this.setIdSource(idSrc.getValue());
        } catch (FieldNotFound ex) {} 
    }
    
    public Order( quickfix.fix42.OrderCancelReplaceRequest message ) {
        ID = generateID();
        
        // ClOrdID
        try {
            ClOrdID clOrdID = new ClOrdID();
            message.get(clOrdID);
            this.setClientID(clOrdID.getValue().toString());
        } catch (FieldNotFound ex) {}

        // OrigClOrdID
        try {
            OrigClOrdID origClOrdID = new OrigClOrdID();
            message.get(origClOrdID);
            this.setOrigClientID(origClOrdID.getValue().toString());
        } catch (FieldNotFound ex) {}
        
        Order oldOrder = FIXimulator.getApplication()
                .getOrders().getOrder(origClientID);
        if ( oldOrder != null ) {        
            open = oldOrder.getOpen();
            executed = oldOrder.getExecuted();
            avgPx = oldOrder.getAvgPx();
            status = oldOrder.getFIXStatus();
        }
        
        // Side
        try {
            Side msgSide = new Side();
            message.get(msgSide);
            setSide(msgSide.getValue());
        } catch (FieldNotFound ex) {}
        
        // Symbol
        try {
            Symbol msgSymbol = new Symbol();
            message.get(msgSymbol);
            setSymbol(msgSymbol.getValue());
        } catch (FieldNotFound ex) {}
        
        // Type
        try {
            OrdType msgType = new OrdType();
            message.get(msgType);
            setType(msgType.getValue());
        } catch (FieldNotFound ex) {}
        
        // OrderQty
        try {
            OrderQty msgQty = new OrderQty();
            message.get(msgQty);
            setQuantity(msgQty.getValue());
            setOpen(msgQty.getValue());
        } catch (FieldNotFound ex) {}
        
        // TimeInForce
        try {
            TimeInForce msgTIF = new TimeInForce();
            message.get(msgTIF);
            this.setTif(msgTIF.getValue());
        } catch (FieldNotFound ex) {}
        
        // Price
        try {
            Price price = new Price();
            message.get(price);
            this.setLimit(price.getValue());
        } catch (FieldNotFound ex) {} 
        
        // SecurityID
        try {
            SecurityID secID = new SecurityID();
            message.get(secID);
            this.setSecurityID(secID.getValue());
        } catch (FieldNotFound ex) {} 
        
        // IDSource
        try {
            IDSource idSrc = new IDSource();
            message.get(idSrc);
            this.setIdSource(idSrc.getValue());
        } catch (FieldNotFound ex) {}         
    }
    
    public String generateID() {
        return "O" + Long.valueOf(
                System.currentTimeMillis()+(nextID++)).toString();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getOrigClientID() {
        return origClientID;
    }

    public void setOrigClientID(String origClientID) {
        this.origClientID = origClientID;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getAvgPx() {
        return avgPx;
    }

    public void setAvgPx(double avgPx) {
        this.avgPx = avgPx;
    }

    public double getExecuted() {
        return executed;
    }

    public void setExecuted(double executed) {
        this.executed = executed;
    }

    public static int getNextID() {
        return nextID;
    }

    public static void setNextID(int nextID) {
        Order.nextID = nextID;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getSide() {
        if (side == '1') return "Buy";
        if (side == '2') return "Sell";
        if (side == '3') return "Buy minus";
        if (side == '4') return "Sell plus";
        if (side == '5') return "Sell short";
        if (side == '6') return "Sell short exempt";
        if (side == '7') return "Undisclosed";
        if (side == '8') return "Cross";
        if (side == '9') return "Cross short";
        return "<UNKNOWN>";
    }
    
    public char getFIXSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public String getStatus() {
        if (receivedOrder) return "Received";
        if (receivedCancel) return "Cancel Received";
        if (receivedReplace) return "Replace Received";
        if (rejectedCancelReplace) return "Cancel/Replace Rejected";
        if (status == '0') return "New";
        if (status == '1') return "Partially filled";
        if (status == '2') return "Filled";
        if (status == '3') return "Done for day";
        if (status == '4') return "Canceled";
        if (status == '5') return "Replaced";
        if (status == '6') return "Pending Cancel";
        if (status == '7') return "Stopped";
        if (status == '8') return "Rejected";
        if (status == '9') return "Suspended";
        if (status == 'A') return "Pending New";
        if (status == 'B') return "Calculated";
        if (status == 'C') return "Expired";
        if (status == 'D') return "Accepted for bidding";
        if (status == 'E') return "Pending Replace";
        return "<UNKNOWN>";
     }
    
    public char getFIXStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTif() {
        if (tif == '0') return "Day";
        if (tif == '1') return "GTC";
        if (tif == '2') return "OPG";
        if (tif == '3') return "IOC";
        if (tif == '4') return "FOK";
        if (tif == '5') return "GTX";
        if (tif == '6') return "GTD";
        return "<UNKNOWN>";
    }
    
    public char getFIXTif() {
        return tif;
    }
    
    public void setTif(char tif) {
        this.tif = tif;
    }

    public String getIdSource() {
        return idSource;
    }

    public void setIdSource(String idSource) {
        this.idSource = idSource;
    }

    public String getSecurityID() {
        return securityID;
    }

    public void setSecurityID(String securityID) {
        this.securityID = securityID;
    }

    public String getType() {
        if (type == '1') return "Market";
        if (type == '2') return "Limit";
        if (type == '3') return "Stop";
        if (type == '4') return "Stop limit";
        if (type == '5') return "Market on close";
        if (type == '6') return "With or without";
        if (type == '7') return "Limit or better";
        if (type == '8') return "Limit with or without";
        if (type == '9') return "On basis";
        if (type == 'A') return "On close";
        if (type == 'B') return "Limit on close";
        if (type == 'C') return "Forex - Market";
        if (type == 'D') return "Previously quoted";
        if (type == 'E') return "Previously indicated";
        if (type == 'F') return "Forex - Limit";
        if (type == 'G') return "Forex - Swap";
        if (type == 'H') return "Forex - Previously Quoted";
        if (type == 'I') return "Funari";
        if (type == 'P') return "Pegged";
        return "<UNKNOWN>";
    }
    
    public char getFIXType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public boolean isReceivedCancel() {
        return receivedCancel;
    }

    public void setReceivedCancel(boolean receivedCancel) {
        this.receivedCancel = receivedCancel;
    }

    public boolean isReceivedOrder() {
        return receivedOrder;
    }

    public void setReceivedOrder(boolean receivedOrder) {
        this.receivedOrder = receivedOrder;
    }

    public boolean isReceivedReplace() {
        return receivedReplace;
    }

    public void setReceivedReplace(boolean receivedReplace) {
        this.receivedReplace = receivedReplace;
    }

    public boolean isRejectedCancelReplace() {
        return rejectedCancelReplace;
    }

    public void setRejectedCancelReplace(boolean rejectedCancelReplace) {
        this.rejectedCancelReplace = rejectedCancelReplace;
    }
}

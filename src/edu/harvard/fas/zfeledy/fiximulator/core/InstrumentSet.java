/*
 * File     : InstrumentSet.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is a Set of Instrument objects with a utility 
 *            methods to to access the individual instruments.
 * 
 */

package edu.harvard.fas.zfeledy.fiximulator.core;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import edu.harvard.fas.zfeledy.fiximulator.ui.InstrumentTableModel;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class InstrumentSet extends DefaultHandler {
    private ArrayList<Instrument> instruments = new ArrayList<Instrument>();
    private ArrayList<Instrument> oldInstruments = new ArrayList<Instrument>();
    private InstrumentTableModel instrumentModel = null;
    
    public InstrumentSet( File file ) {
        try {
            InputStream input = 
                    new BufferedInputStream(new FileInputStream(file));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( input, this);		
        } catch ( Exception e ) {
            System.out.println( "Error reading/parsing instrument file." );
            e.printStackTrace();
        }
    }
	
    public void reloadInstrumentSet( File file ) {
        try {
            oldInstruments.clear();
            oldInstruments.addAll(instruments);
            instruments.clear();
            InputStream input = 
                    new BufferedInputStream(new FileInputStream(file));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( input, this);
            instrumentModel.update();
        } catch ( Exception e ) {
            System.out.println( "Error reading/parsing instrument file." );
            e.printStackTrace();
            instruments.clear();
            instruments.addAll(oldInstruments);
        }
    }
    
    @Override
    public void startElement( String namespace, String localName, 
                    String qualifiedName, Attributes attributes ){
        if ( qualifiedName.equals( "instrument" )) {
            String ticker = attributes.getValue( "ticker" );
            String cusip = attributes.getValue( "cusip" );
            String sedol = attributes.getValue( "sedol" );
            String name = attributes.getValue( "name" );
            String ric = attributes.getValue( "ric" );
            String price = attributes.getValue( "price" );
            Instrument instrument = 
                    new Instrument( ticker, sedol, name, ric, cusip, price );
            instruments.add( instrument );
        }
    }
	
    public int getCount() {
        return instruments.size();
    }
	
    public Instrument getInstrument( int i ) {
        return instruments.get( i );
    }
    
    public Instrument getInstrument( String identifier ) {
        Iterator<Instrument> iterator = instruments.iterator();
        while ( iterator.hasNext() ){
            Instrument instrument = iterator.next();
            if ( instrument.getTicker().equals( identifier ) ||
                 instrument.getSedol().equals( identifier ) ||
                 instrument.getCusip().equals( identifier ) ||
                 instrument.getName().equals( identifier ))
                return instrument;
        }
        return null;
    }
	
    public Instrument randomInstrument() {
        Instrument instrument = null;
        Random generator = new Random();
        int size = instruments.size();
        int index = generator.nextInt( size );
        instrument = instruments.get( index );
        return instrument;
    }
	
    public void outputToXML() {
        try {
            BufferedWriter writer = 
                   new BufferedWriter(new FileWriter("config/instruments.xml"));
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<instruments>\n");
            Iterator<Instrument> iterator = instruments.iterator();
            while (iterator.hasNext()) {
                Instrument instrument = (Instrument)iterator.next();
                String output = "   <instrument";
                output += " name=\"" + instrument.getName() + "\"";
                output += " ticker=\"" + instrument.getTicker() + "\"";
                output += " cusip=\"" + instrument.getCusip() + "\"";
                output += " sedol=\"" + instrument.getSedol() + "\"";
                output += " ric=\"" + instrument.getRIC() + "\"";
                output += " price=\"" + instrument.getPrice() + "\"";
                output += "/>\n";
                writer.write( output );
            }
            writer.write("</instruments>\n");
            writer.close();
        } catch ( IOException e ) {e.printStackTrace();}
    }
        
    public void addCallback(InstrumentTableModel instrumentModel){
        this.instrumentModel = instrumentModel;
    }
}


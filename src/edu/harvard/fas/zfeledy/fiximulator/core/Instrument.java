/*
 * File     : Instrument.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is a basic Instrument object that is used to 
 *            create and store instrument details.
 * 
 */

package edu.harvard.fas.zfeledy.fiximulator.core;

public class Instrument {
	
	private String ticker;
	private String cusip;
	private String sedol;
	private String name;
	private String ric;
	private String price;
	
	public Instrument( String ticker, String sedol, String name,
			String ric, String cusip, String price ) {
		this.ticker = ticker;
		this.cusip = cusip;
		this.sedol = sedol;
		this.name = name;
		this.ric = ric;
		this.price = price;
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public String getCusip() {
		return cusip;
	}
	
	public String getSedol() {
		return sedol;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRIC() {
		return ric;
	}
	
	public String getPrice() {
		return price;
	}
}
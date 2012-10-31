/*
 * File     : LogMessageSet.java
 *
 * Author   : Zoltan Feledy
 * 
 * Contents : This class is a Set of LogMessage objects with utility 
 *            methods to access the individual messages.
 */

package edu.harvard.fas.zfeledy.fiximulator.core;

import java.util.ArrayList;
import edu.harvard.fas.zfeledy.fiximulator.ui.MessageTableModel;
import quickfix.DataDictionary;
import quickfix.Message;
import quickfix.SessionID;


public class LogMessageSet {
	private static final long serialVersionUID = 1L;
	private ArrayList<LogMessage> messages = null;
	private MessageTableModel model;
	private int messageIndex = 0;
	
	public LogMessageSet() {
		messages = new ArrayList<LogMessage>();
	}
	
	public void add(Message message, boolean incoming, 
                DataDictionary dictionary, SessionID sessionID) {
		messageIndex++;
		LogMessage msg = 
                        new LogMessage(messageIndex, incoming, sessionID,
				message.toString(), dictionary);
                messages.add(msg);
                int limit = 50;
                try {
                    limit = (int)FIXimulator.getApplication().getSettings()
                            .getLong("FIXimulatorCachedObjects");
                } catch ( Exception e ) {}
                while ( messages.size() > limit ) {
                    messages.remove(0);
                }
		//call back to the model to update
		model.update();		
	}
	
	public LogMessage getMessage( int i ) {
		return messages.get(i);
	}
	
	public int getCount(){
		return messages.size();
	}

	public void addCallback(MessageTableModel model) {
		this.model = model;	
	}
}

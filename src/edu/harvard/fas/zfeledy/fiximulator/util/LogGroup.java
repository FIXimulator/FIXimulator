/*
 * File     : LogGroup.java
 *
 * Author   : Brian M. Coyner
 * 
 * Contents : This class is an enhanced Log Group used by the Log4FIX
 *            project for handling repeating groups.  
 *            Adapted for the needs of FIXimulator by Zoltan Feledy.
 * 
 */

package edu.harvard.fas.zfeledy.fiximulator.util;
/*
 * The Log4FIX Software License
 * Copyright (c) 2006 - 2007 opentradingsolutions.org  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. Neither the name of the product (Log4FIX), nor opentradingsolutions.org,
 *    nor the names of its contributors may be used to endorse or promote
 *    products derived from this software without specific prior written 
 *    permission.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL OPENTRADINGSOLUTIONS.ORG OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

import java.util.ArrayList;
import java.util.List;

import quickfix.DataDictionary;
import quickfix.Field;
import quickfix.field.MsgType;

/**
 * @author Brian M. Coyner
 */
public class LogGroup extends LogField {

    private List<LogField> fields;

    public LogGroup(MsgType messageType, Field field,
            DataDictionary dictionary) {
        super(messageType, field, dictionary);
        fields = new ArrayList<LogField>();
    }

    public void addField(LogField logField) {
        fields.add(logField);
    }

    public List<LogField> getFields() {
        return fields;
    }
}
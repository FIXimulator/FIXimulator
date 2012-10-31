/*
 * File     : LogField.java
 *
 * Author   : Brian M. Coyner
 * 
 * Contents : This class is an enhanced Log Field used by the Log4FIX
 *            project.  Adapted for the needs of FIXimulator 
 *            by Zoltan Feledy.
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
import java.util.Iterator;
import java.util.List;

import quickfix.DataDictionary;
import quickfix.Field;
import quickfix.FieldType;
import quickfix.field.MsgType;

/**
 * Represents a single QuickFIX field. This object provides extra information 
 * about the field in the context of its message. The QuickFIX 
 * <code>DataDictionary</code> is used to provide information about whether or 
 * not the field is a header or trailer field, if it is a required field, etc.
 * 
 * @author Brian M. Coyner
 */
public class LogField {

    private Field field;
    private FieldType fieldType;
    private String fieldName;
    private String fieldValueName;
    private boolean required;
    private boolean header;
    private boolean trailer;
    private List<LogGroup> groups;

    private DataDictionary dictionary;

    public static LogField createLogField(MsgType messageType, Field field,
            DataDictionary dictionary) {
        return new LogField(messageType, field, dictionary);
    }

    /**
     * @param messageType what message the field is part of.
     * @param field the actual field we are wrapping.
     * @param dictionary dictionary used to look up field information.
     */
    protected LogField(
            MsgType messageType, Field field, DataDictionary dictionary) {
        this.dictionary = dictionary;
        this.field = field;

        final String messageTypeString = messageType.getValue();
        final int fieldTag = field.getTag();

        fieldType = dictionary.getFieldTypeEnum(fieldTag);
        fieldName = dictionary.getFieldName(fieldTag);
        fieldValueName = dictionary.getValueName(fieldTag,
                field.getObject().toString());
        required = getDataDictionary().isRequiredField(messageTypeString,
                fieldTag);
        header = getDataDictionary().isHeaderField(fieldTag);
        if (!header) {
            trailer = getDataDictionary().isTrailerField(fieldTag);
        }
    }

    public Iterator<LogField> group() {
        return null;
    }

    public Field getField() {
        return field;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public int getTag() {
        return field.getTag();
    }

    public Object getValue() {
        return field.getObject();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValueName() {
        return fieldValueName;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isHeaderField() {
        return header;
    }

    public boolean isTrailerField() {
        return trailer;
    }

    public boolean isRepeatingGroup() {
        return groups != null;
    }

    /**
     * @return true if this this field is not a header field or a trailer field.
     */
    public boolean isBodyField() {
        return !isHeaderField() || !isTrailerField();
    }

    public DataDictionary getDataDictionary() {
        return dictionary;
    }

    public void addGroup(LogGroup group) {
        if (groups == null) {
            groups = new ArrayList<LogGroup>();
        }

        groups.add(group);
    }

    public List<LogGroup> getGroups() {
        return groups;
    }
}


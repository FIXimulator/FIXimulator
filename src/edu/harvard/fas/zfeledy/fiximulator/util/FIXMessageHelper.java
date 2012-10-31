/*
 * File     : FIXMessageHelper.java
 *
 * Author   : Brian M. Coyner
 * 
 * Contents : This class is a helper class used by Log4FIX for 
 *            handling message details.  It was adapted for the needs 
 *            of FIXimulator by Zoltan Feledy
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
import java.util.Date;

import quickfix.FieldConvertError;
import quickfix.field.converter.UtcTimestampConverter;

/**
 * @author Brian M. Coyner
 */
public class FIXMessageHelper {

    public static String getTargetCompId(String rawMessage, char delimeter) {
        int beginIndex = rawMessage.indexOf("56=") + 3;
        int endIndex = rawMessage.indexOf(delimeter, beginIndex);
        return rawMessage.substring(beginIndex, endIndex);
    }

    public static String getSenderCompId(String rawMessage, char delimeter) {
        int beginIndex = rawMessage.indexOf("49=") + 3;
        int endIndex = rawMessage.indexOf(delimeter, beginIndex);
        return rawMessage.substring(beginIndex, endIndex);
    }

    public static String getMessageType(String rawMessage, char delimeter) {
        int beginIndex = rawMessage.indexOf("35=");
        if (beginIndex == -1) {
            return null;
        }

        beginIndex += 3;

        int endIndex = rawMessage.indexOf(delimeter, beginIndex);
        return rawMessage.substring(beginIndex, endIndex);
    }

    public static Date getSendingTime(String rawMessage, char delimeter)
            throws FieldConvertError {
        int beginIndex = rawMessage.indexOf("52=");
        if (beginIndex == -1) {
            return null;
        }

        beginIndex += 3;

        int endIndex = rawMessage.indexOf(delimeter, beginIndex);
        return UtcTimestampConverter.convert(
                rawMessage.substring(beginIndex, endIndex));
    }
}


package com.att.nod.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.att.nod.core.exception.NODCommonException;

public class NODXMLUtils {
    private static NODXMLUtils handle = null;
    private NODXMLUtils() {
        super();
    }
    public static synchronized NODXMLUtils getInstance() {
        if (null == handle) {
            handle = new NODXMLUtils();
        }
        return handle;
    }
   
    public Document build(InputStream inputStream, boolean validate)
        throws NODCommonException {
        Document document = null;

        try {
            SAXBuilder builder = new SAXBuilder(validate);
            document = builder.build(inputStream);
        } catch (JDOMException je) {
            throw new NODCommonException("Error parsing XML document" + je.getMessage()); 

        } catch (Exception je) {
            throw new NODCommonException("Error parsing XML document" + je.getMessage()); 

        }

        return document;
    }

    public Document build(String fileName) throws NODCommonException {
        return build(fileName, false);
    }

    public Document build(String fileName, boolean validate)
        throws NODCommonException {
        Document document = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(fileName));
            document = build(fileInputStream, validate);
        } catch (FileNotFoundException fnfe) {
            throw new NODCommonException("Error creating xml document:" + fnfe.getMessage()); 
        } finally {
            if (null != fileInputStream) {
                try {
                    fileInputStream.close();
                } catch (IOException ioexc) {
                    ioexc.printStackTrace();
                    throw new NODCommonException("Error creating xml document:" + ioexc.getMessage()); 

                }
            }
        }
        return document;
    }
    
        
}

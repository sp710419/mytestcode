package com.att.nod.core.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.att.nod.core.exception.NODCommonException;

public class NODBootstrapUtil { 

    String bootStrapFile = "nodDBBootstrap";  
    private static NODBootstrapUtil instance = null;
    ResourceBundle bundle = null;

    private NODBootstrapUtil() {
        bundle = ResourceBundle.getBundle(bootStrapFile);
    }
    public static synchronized NODBootstrapUtil getInstance() {

        if (null == instance) {
            instance = new NODBootstrapUtil();
        }
        return instance;
    }

    public String getValue(String key) throws NODCommonException {
       String path = null;
       try{           
           path=bundle.getString(key);            
       }catch(MissingResourceException e){
           throw new NODCommonException(e.toString());            
       }       
        
       return path;
    }

} 

package com.netlynxtech.advancedmonitor.classes;

public class Consts {
	 
    public static final String TCP_RELAY_ON_TODEVICE = "^R|00|ZZ~";
    public static final String TCP_RELAY_ON_TOMOBILE = "^r|00|ZZ~";
    
    public static final String TCP_RELAY_OFF_TODEVICE = "^R|11|ZZ~";
    public static final String TCP_RELAY_OFF_TOMOBILE = "^r|11|ZZ~";
    
    public static final String TCP_RELAY_GET_STATUS = "^R||ZZ~";
    
    public static final String UDP_BROADCAST_TODEVICE = "HELLO NT";
 
}

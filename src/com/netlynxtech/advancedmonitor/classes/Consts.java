package com.netlynxtech.advancedmonitor.classes;

public class Consts {

	public static final String DEVICE_SOFT_ACCESS_IP = "192.168.4.1";
	public static final String DEVICE_SOFT_ACCESS_PORT = "9012";
	
	public static final String TCP_RELAY_ON_TODEVICE = "^R|00|ZZ~";
	public static final String TCP_RELAY_ON_TOMOBILE = "^r|00|ZZ~";

	public static final String TCP_RELAY_OFF_TODEVICE = "^R|11|ZZ~";
	public static final String TCP_RELAY_OFF_TOMOBILE = "^r|11|ZZ~";

	public static final String TCP_RELAY_GET_STATUS = "^R||ZZ~";

	public static final String UDP_BROADCAST_TODEVICE = "HELLO NT";

	
	public static final String B_CONFIGURATION_TOPHONE = "^B~";
	
	public static final String X_CONFIGURE_ONE_PHONE_TODEVICE = "^X|1|%1$s|%2$s~";
	public static final String X_CONFIGURE_TWO_WIFISERVER_TODEVICE = "^X|2|%1$s|%2$s|%3$s|%4$s|%5$s|%6$s|%7$s~";
	public static final String X_CONFIGURE_USERID_NULL = "^X|2~";
	
}

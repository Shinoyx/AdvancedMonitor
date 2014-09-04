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
	public static final String X_CONFIGURE_USERID_NULL = "^x|2|NR~";
	
	
	
	
	
	final static public int WEBREQUEST_TIMEOUT = 20000;
	final static public String PROJECT_NUMBER = "534726101262";

	final static public String NOISELYNX_API_URL = "http://192.168.10.8/wsIOT/TH.asmx";
	final static public String NOISELYNX_API_REGISTERDEVICE_SOAP_ACTION = "http://NetLynxTech.com/RegisterDevice";
	final static public String NOISELYNX_API_REGISTERDEVICE_METHOD_NAME = "RegisterDevice";
	
	
	final static public String NOISELYNX_API_GETDEVICES_SOAP_ACTION = "http://NetLynxTech.com/GetDevices";
	final static public String NOISELYNX_API_GETDEVICES_METHOD_NAME = "GetDevices";
	
	final static public String NOISELYNX_API_GETDEVICE_SOAP_ACTION = "http://NetLynxTech.com/GetDevice";
	final static public String NOISELYNX_API_GETDEVICE_METHOD_NAME = "GetDevice";
	
	final static public String NOISELYNX_API_GETLOCATION_SOAP_ACTION = "http://NetLynxTech.com/GetLocation";
	final static public String NOISELYNX_API_GETLOCATION_METHOD_NAME = "GetLocation";
	
	final static public String NOISELYNX_API_SETOUTPUT_SOAP_ACTION = "http://NetLynxTech.com/SetOutput";
	final static public String NOISELYNX_API_SETOUTPUT_METHOD_NAME = "SetOutput";

	final static public String NOISELYNX_API_UPDATEDESCRIPTIONS_SOAP_ACTION = "http://NetLynxTech.com/UpdateDescriptions";
	final static public String NOISELYNX_API_UPDATEDESCRIPTIONS_METHOD_NAME = "UpdateDescriptions";
	
	final static public String RESULTCODE = "ResultCode";
	final static public String RESULTDESCRIPTION = "ResultDescription";
	final static public String ERRORCODE = "ErrorCode";
	
	final static public String GETDEVICES_VERSION = "Version";
	final static public String GETDEVICES_DEVICEID = "DeviceID";
	final static public String GETDEVICES_TEMPERATURE = "Temperature";
	final static public String GETDEVICES_HUMIDITY = "Humidity";
	final static public String GETDEVICES_VOLTAGE = "Voltage";
	final static public String GETDEVICES_INPUT1 = "Input1";
	final static public String GETDEVICES_INPUT2 = "Input2";
	final static public String GETDEVICES_OUTPUT1 = "Output1";
	final static public String GETDEVICES_OUTPUT2 = "Output2";
	final static public String GETDEVICES_ENABLETEMPERATURE = "EnableTemperature";
	final static public String GETDEVICES_ENABLEHUMIDITY = "EnableHumidity";
	final static public String GETDEVICES_ENABLEINPUT1 = "EnableInput1";
	final static public String GETDEVICES_ENABLEINPUT2 = "EnableInput2";
	final static public String GETDEVICES_ENABLEOUTPUT1 = "EnableOutput1";
	final static public String GETDEVICES_ENABLEOUTPUT2 = "EnableOutput2";
	final static public String GETDEVICES_TIMESTAMP = "TimeStamp";
	final static public String GETDEVICES_DESCRIPTION = "Description";
	final static public String GETDEVICES_DESCRIPTIONINPUT1 = "DescriptionInput1";
	final static public String GETDEVICES_DESCRIPTIONINPUT2 = "DescriptionInput2";
	final static public String GETDEVICES_DESCRIPTIONOUTPUT1 = "DescriptionOutput1";
	final static public String GETDEVICES_DESCRIPTIONOUTPUT2 = "DescriptionOutput2";
	final static public String GETDEVICES_TEMPERATUREHI = "TemperatureHi";
	final static public String GETDEVICES_TEMPERATURELO = "TemperatureLo";
	final static public String GETDEVICES_HUMIDITYHI = "HumidityHi";
	final static public String GETDEVICES_HUMIDITYLO = "HumidityLo";
	final static public String GETDEVICES_REVERSELOGICINPUT1 = "ReverseLogicInput1";
	final static public String GETDEVICES_REVERSELOGICINPUT2 = "ReverseLogicInput2";
	final static public String GETDEVICES_REVERSELOGICOUTPUT1 = "ReverseLogicOutput1";
	final static public String GETDEVICES_REVERSELOGICOUTPUT2 = "ReverseLogicOutput2";
	final static public String GETDEVICES_HUMIDITYSTATE = "HumidityState";
	final static public String GETDEVICES_TEMPERATURESTATE = "TemperatureState";
	final static public String GETDEVICES_LATITUDE = "Latitude";
	final static public String GETDEVICES_LONGITUDE = "Longitude";
	
	
	final static public String NOISELYNX_API_CHECKPIN_SOAP_ACTION = "http://NetlynxTech.com/CheckPIN";
	final static public String NOISELYNX_API_CHECKPIN_METHOD_NAME = "CheckPIN";

	final static public String NOISELYNX_API_GETHISTORY_SOAP_ACTION = "http://NetlynxTech.com/GetHistory";
	final static public String NOISELYNX_API_GETHISTORY_METHOD_NAME = "GetHistory";
	final static public String NOISELYNX_API_GETMESSAGES_SOAP_ACTION = "http://NetlynxTech.com/GetNewMessages";
	final static public String NOISELYNX_API_GETMESSAGES_METHOD_NAME = "GetNewMessages";
	final static public String NOISELYNX_API_GETTHRESHOLD_SOAP_ACTION = "http://NetlynxTech.com/GetThreshold";
	final static public String NOISELYNX_API_GETTHRESHOLD_METHOD_NAME = "GetThreshold";
	final static public String NOISELYNX_API_UPDATELATLONG_SOAP_ACTION = "http://NetlynxTech.com/UpdateLatLong";
	final static public String NOISELYNX_API_UPDATELATLONG_METHOD_NAME = "UpdateLatLong";
	final static public String NAMESPACE = "http://NetlynxTech.com/";

	final static public String PREFERENCES_PHONE_NO = "phone";
	final static public String PREFERENCES_UDID = "udid";
	final static public String PREFERENCES_GCMID = "gcmid";
	final static public String PREFERENCES_PASSWORD = "password";
	final static public String MONITORING_DEVICE_ID = "DeviceID";
	final static public String MONITORING_DATE_TIME = "DataDateTime";
	final static public String MONITORING_LOCATION = "Location";
	final static public String MONITORING_LEQ_FIVE_MINUTES = "Leq5";
	final static public String MONITORING_LEQ_ONE_HOUR = "Leq1";
	final static public String MONITORING_LEQ_TWELVE_HOUR = "Leq12";
	final static public String MONITORING_LOCATION_LAT = "Latitude";
	final static public String MONITORING_LOCATION_LONG = "Longitude";
	final static public String MONITORING_ALERT = "Alert";
	final static public String MONITORING_ALERT_YES = "true";
	final static public String MONITORING_ALERT_NO = "false";

	final static public String HISTORY_DATETIMESTAMP = "DataTimestamp";

	final static public String MESSAGES_MESSAGE_ID = "MessageID";
	final static public String MESSAGES_MESSAGE_TIMESTAMP = "MessageTimestamp";
	final static public String MESSAGES_MESSAGE_SUBJECT = "Subject";
	final static public String MESSAGES_MESSAGE_BODY = "Message";
	final static public String MESSAGES_MESSAGE_PRIORITY = "Priority";

	final static public String THRESHOLD_TIMESPAN = "TimeSpan";
	final static public String THRESHOLD_THRESHOLD = "Threhold"; // GRAMMAR ERROR CAREFUL
	final static public String DATABASE_COLUMN_UNIX = "unixTimestamp";
	
}

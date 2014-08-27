package com.netlynxtech.advancedmonitor.classes;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Log;

public class WebRequestAPI {
	Context context;
	ArrayList<String> locationList = new ArrayList<String>();

	public WebRequestAPI(Context context) {
		this.context = context;
	}

	public ArrayList<String> getLocationList() {
		return locationList;
	}

	public String RegisterDevice(String udid, String deviceId) {
		SoapObject rpc = new SoapObject(Consts.NAMESPACE, Consts.NOISELYNX_API_REGISTERDEVICE_METHOD_NAME); // create new soap object
		rpc.addProperty("UDID", udid); // set parameter
		rpc.addProperty("deviceID", deviceId); // set parameter
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE ht = new HttpTransportSE(Consts.NOISELYNX_API_URL);
		ht.debug = true;
		try {
			ht.call(Consts.NOISELYNX_API_REGISTERDEVICE_SOAP_ACTION, envelope);
			System.err.println(ht.responseDump);
			SoapObject result = (SoapObject) envelope.getResponse();
			if (result.getProperty(0).toString().equals("1")) {
				return "success";
			} else {
				return result.getProperty(1).toString();
			}
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			return "Timed out. Please try again.";
		} catch (HttpResponseException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String checkPin(String mobileNumber, String pin, String gcm_id, String udid) {
		SoapObject rpc = new SoapObject(Consts.NAMESPACE, Consts.NOISELYNX_API_CHECKPIN_METHOD_NAME);
		rpc.addProperty("mobile_no", mobileNumber);
		rpc.addProperty("pin", pin);
		rpc.addProperty("GCM_ID", gcm_id);
		rpc.addProperty("UDID", udid);
		Log.e("TEST", mobileNumber + "|" + pin + "|" + gcm_id + "|" + udid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE ht = new HttpTransportSE(Consts.NOISELYNX_API_URL);
		ht.debug = true;
		try {
			// Log.e("WebRequest", "TRY!");
			ht.call(Consts.NOISELYNX_API_CHECKPIN_SOAP_ACTION, envelope);
			// System.err.println(ht.responseDump);
			SoapObject result = (SoapObject) envelope.getResponse();
			// Log.e("COUNT", result.getPropertyCount() + "");
			// Log.e("COUNT", result.getProperty(0).toString());
			if (result.getProperty(0).toString().equals("1")) {
				return "success|" + result.getProperty(1).toString();
			} else {
				return result.getProperty(1).toString();
			}
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			return "Timed out. Please try again.";
		} catch (HttpResponseException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public ArrayList<HashMap<String, String>> GetDevices(String udid) {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		SoapObject rpc = new SoapObject(Consts.NAMESPACE, Consts.NOISELYNX_API_GETDEVICES_METHOD_NAME);
		rpc.addProperty("UDID", udid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE ht = new HttpTransportSE(Consts.NOISELYNX_API_URL);
		ht.debug = true;
		try {
			ht.call(Consts.NOISELYNX_API_GETDEVICES_SOAP_ACTION, envelope);
			System.err.println(ht.responseDump);
			SoapObject result = (SoapObject) envelope.getResponse();
			HashMap<String, String> map;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				SoapObject object = (SoapObject) result.getProperty(i);
				map = new HashMap<String, String>();
				map.put(Consts.GETDEVICES_VERSION, object.getProperty(Consts.GETDEVICES_VERSION).toString());
				map.put(Consts.GETDEVICES_DEVICEID, object.getProperty(Consts.GETDEVICES_DEVICEID).toString());
				map.put(Consts.GETDEVICES_TEMPERATURE, object.getProperty(Consts.GETDEVICES_TEMPERATURE).toString());
				map.put(Consts.GETDEVICES_HUMIDITY, object.getProperty(Consts.GETDEVICES_HUMIDITY).toString());
				map.put(Consts.GETDEVICES_VOLTAGE, object.getProperty(Consts.GETDEVICES_VOLTAGE).toString());
				map.put(Consts.GETDEVICES_INPUT1, object.getProperty(Consts.GETDEVICES_INPUT1).toString());
				map.put(Consts.GETDEVICES_INPUT2, object.getProperty(Consts.GETDEVICES_INPUT2).toString());
				map.put(Consts.GETDEVICES_OUTPUT1, object.getProperty(Consts.GETDEVICES_OUTPUT1).toString());
				map.put(Consts.GETDEVICES_OUTPUT2, object.getProperty(Consts.GETDEVICES_OUTPUT2).toString());
				map.put(Consts.GETDEVICES_ENABLETEMPERATURE, object.getProperty(Consts.GETDEVICES_ENABLETEMPERATURE).toString());
				map.put(Consts.GETDEVICES_ENABLEHUMIDITY, object.getProperty(Consts.GETDEVICES_ENABLEHUMIDITY).toString());
				map.put(Consts.GETDEVICES_ENABLEINPUT1, object.getProperty(Consts.GETDEVICES_ENABLEINPUT1).toString());
				map.put(Consts.GETDEVICES_ENABLEINPUT2, object.getProperty(Consts.GETDEVICES_ENABLEINPUT2).toString());
				map.put(Consts.GETDEVICES_ENABLEOUTPUT1, object.getProperty(Consts.GETDEVICES_ENABLEOUTPUT1).toString());
				map.put(Consts.GETDEVICES_ENABLEOUTPUT2, object.getProperty(Consts.GETDEVICES_ENABLEOUTPUT2).toString());
				map.put(Consts.GETDEVICES_DESCRIPTION, object.getProperty(Consts.GETDEVICES_DESCRIPTION).toString());
				map.put(Consts.GETDEVICES_DESCRIPTIONINPUT1, object.getProperty(Consts.GETDEVICES_DESCRIPTIONINPUT1).toString());
				map.put(Consts.GETDEVICES_DESCRIPTIONINPUT2, object.getProperty(Consts.GETDEVICES_DESCRIPTIONINPUT2).toString());
				map.put(Consts.GETDEVICES_DESCRIPTIONOUTPUT1, object.getProperty(Consts.GETDEVICES_DESCRIPTIONOUTPUT1).toString());
				map.put(Consts.GETDEVICES_DESCRIPTIONOUTPUT2, object.getProperty(Consts.GETDEVICES_DESCRIPTIONOUTPUT2).toString());
				map.put(Consts.GETDEVICES_TEMPERATUREHI, object.getProperty(Consts.GETDEVICES_TEMPERATUREHI).toString());
				map.put(Consts.GETDEVICES_TEMPERATURELO, object.getProperty(Consts.GETDEVICES_TEMPERATURELO).toString());
				map.put(Consts.GETDEVICES_HUMIDITYHI, object.getProperty(Consts.GETDEVICES_HUMIDITYHI).toString());
				map.put(Consts.GETDEVICES_HUMIDITYLO, object.getProperty(Consts.GETDEVICES_HUMIDITYLO).toString());
				map.put(Consts.GETDEVICES_REVERSELOGICINPUT1, object.getProperty(Consts.GETDEVICES_REVERSELOGICINPUT1).toString());
				map.put(Consts.GETDEVICES_REVERSELOGICINPUT2, object.getProperty(Consts.GETDEVICES_REVERSELOGICINPUT2).toString());
				map.put(Consts.GETDEVICES_REVERSELOGICOUTPUT1, object.getProperty(Consts.GETDEVICES_REVERSELOGICOUTPUT1).toString());
				map.put(Consts.GETDEVICES_REVERSELOGICOUTPUT2, object.getProperty(Consts.GETDEVICES_REVERSELOGICOUTPUT2).toString());
				map.put(Consts.GETDEVICES_HUMIDITYSTATE, object.getProperty(Consts.GETDEVICES_HUMIDITYSTATE).toString());
				map.put(Consts.GETDEVICES_TEMPERATURESTATE, object.getProperty(Consts.GETDEVICES_TEMPERATURESTATE).toString());
				map.put(Consts.GETDEVICES_LATITUDE, object.getProperty(Consts.GETDEVICES_LATITUDE).toString());
				map.put(Consts.GETDEVICES_LONGITUDE, object.getProperty(Consts.GETDEVICES_LONGITUDE).toString());
				list.add(map);
			}

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			// Toast.makeText(context, "Timed out. Please try again", Toast.LENGTH_SHORT).show();

		} catch (HttpResponseException e) {
			e.printStackTrace();
			// return e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			// return e.getMessage();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			// return e.getMessage();
		}
		return list;
	}

}

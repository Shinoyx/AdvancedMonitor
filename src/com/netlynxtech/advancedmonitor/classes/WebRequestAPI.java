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
		SoapObject rpc = new SoapObject(Consts.NAMESPACE, Consts.NOISELYNX_API_REGISTERDEVICE_METHOD_NAME); //create new soap object
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
			//Log.e("WebRequest", "TRY!");
			ht.call(Consts.NOISELYNX_API_CHECKPIN_SOAP_ACTION, envelope);
			//System.err.println(ht.responseDump);
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

	public ArrayList<HashMap<String, String>> getDevices(String udid) {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		SoapObject rpc = new SoapObject(Consts.NAMESPACE, Consts.NOISELYNX_API_GETDEVICES_METHOD_NAME);
		rpc.addProperty("UDID", udid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE ht = new HttpTransportSE(Consts.NOISELYNX_API_URL);
		ht.debug = true;
		try {
			//Log.e("WebRequest", "TRY!");
			ht.call(Consts.NOISELYNX_API_GETDEVICES_SOAP_ACTION, envelope);
			//System.err.println(ht.responseDump);
			SoapObject result = (SoapObject) envelope.getResponse();
			HashMap<String, String> map;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				SoapObject object = (SoapObject) result.getProperty(i);
				map = new HashMap<String, String>();
				// Log.e("Location", object.getProperty(Consts.MONITORING_LOCATION) + "");
				locationList.add(object.getProperty(Consts.MONITORING_LOCATION).toString());
				map.put(Consts.MONITORING_DEVICE_ID, object.getProperty(Consts.MONITORING_DEVICE_ID).toString());
				map.put(Consts.MONITORING_DATE_TIME, object.getProperty(Consts.MONITORING_DATE_TIME).toString());
				map.put(Consts.MONITORING_LOCATION, object.getProperty(Consts.MONITORING_LOCATION).toString());
				map.put(Consts.MONITORING_LEQ_FIVE_MINUTES, object.getProperty(Consts.MONITORING_LEQ_FIVE_MINUTES).toString());
				map.put(Consts.MONITORING_LEQ_ONE_HOUR, object.getProperty(Consts.MONITORING_LEQ_ONE_HOUR).toString());
				map.put(Consts.MONITORING_LEQ_TWELVE_HOUR, object.getProperty(Consts.MONITORING_LEQ_TWELVE_HOUR).toString());
				map.put(Consts.MONITORING_LOCATION_LAT, object.getProperty(Consts.MONITORING_LOCATION_LAT).toString());
				map.put(Consts.MONITORING_LOCATION_LONG, object.getProperty(Consts.MONITORING_LOCATION_LONG).toString());
				map.put(Consts.MONITORING_ALERT, object.getProperty(Consts.MONITORING_ALERT).toString());
				Log.e("ALERT", object.getProperty(Consts.MONITORING_ALERT).toString());
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

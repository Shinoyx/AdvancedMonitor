package com.netlynxtech.advancedmonitor.classes;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

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

	public ArrayList<Device> GetDevices(String udid) {
		ArrayList<Device> list = new ArrayList<Device>();
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
			Device map;
			for (int i = 0; i < result.getPropertyCount(); i++) {
				SoapObject object = (SoapObject) result.getProperty(i);
				map = new Device();
				map.setVersion(object.getProperty(Consts.GETDEVICES_VERSION).toString());
				map.setDeviceID(object.getProperty(Consts.GETDEVICES_DEVICEID).toString());
				map.setTemperature(object.getProperty(Consts.GETDEVICES_TEMPERATURE).toString());
				map.setHumidity(object.getProperty(Consts.GETDEVICES_HUMIDITY).toString());
				map.setVoltage(object.getProperty(Consts.GETDEVICES_VOLTAGE).toString());
				map.setInput1(object.getProperty(Consts.GETDEVICES_INPUT1).toString());
				map.setInput2(object.getProperty(Consts.GETDEVICES_INPUT2).toString());
				map.setOutput1(object.getProperty(Consts.GETDEVICES_OUTPUT1).toString());
				map.setOutput2(object.getProperty(Consts.GETDEVICES_OUTPUT2).toString());
				map.setEnableTemperature(object.getProperty(Consts.GETDEVICES_ENABLETEMPERATURE).toString());
				map.setEnableHumidity(object.getProperty(Consts.GETDEVICES_ENABLEHUMIDITY).toString());
				map.setEnableInput1(object.getProperty(Consts.GETDEVICES_ENABLEINPUT1).toString());
				map.setEnableInput2(object.getProperty(Consts.GETDEVICES_ENABLEINPUT2).toString());
				map.setEnableOutput1(object.getProperty(Consts.GETDEVICES_ENABLEOUTPUT1).toString());
				map.setEnableOutput2(object.getProperty(Consts.GETDEVICES_ENABLEOUTPUT2).toString());
				Log.e("TIME", object.getProperty(Consts.GETDEVICES_TIMESTAMP).toString());
				map.setTimestamp(object.getProperty(Consts.GETDEVICES_TIMESTAMP).toString());

				map.setDescription(object.getProperty(Consts.GETDEVICES_DESCRIPTION).toString());
				map.setDescriptionInput1(object.getProperty(Consts.GETDEVICES_DESCRIPTIONINPUT1).toString());
				map.setDescriptionInput2(object.getProperty(Consts.GETDEVICES_DESCRIPTIONINPUT2).toString());
				map.setDescriptionOutput1(object.getProperty(Consts.GETDEVICES_DESCRIPTIONOUTPUT1).toString());
				map.setDescriptionOutput2(object.getProperty(Consts.GETDEVICES_DESCRIPTIONOUTPUT2).toString());
				map.setTemperatureHi(object.getProperty(Consts.GETDEVICES_TEMPERATUREHI).toString());
				map.setTemperatureLo(object.getProperty(Consts.GETDEVICES_TEMPERATURELO).toString());
				map.setHumidityHi(object.getProperty(Consts.GETDEVICES_HUMIDITYHI).toString());
				map.setHumidityLo(object.getProperty(Consts.GETDEVICES_HUMIDITYLO).toString());
				map.setReverseLogicInput1(object.getProperty(Consts.GETDEVICES_REVERSELOGICINPUT1).toString());
				map.setReverseLogicInput2(object.getProperty(Consts.GETDEVICES_REVERSELOGICINPUT2).toString());
				map.setReverseLogicOutput1(object.getProperty(Consts.GETDEVICES_REVERSELOGICOUTPUT1).toString());
				map.setReverseLogicOutput2(object.getProperty(Consts.GETDEVICES_REVERSELOGICOUTPUT2).toString());
				map.setHumidityState(object.getProperty(Consts.GETDEVICES_HUMIDITYSTATE).toString());
				map.setTemperatureState(object.getProperty(Consts.GETDEVICES_TEMPERATURESTATE).toString());
				map.setLatitude(object.getProperty(Consts.GETDEVICES_LATITUDE).toString());
				map.setLongitude(object.getProperty(Consts.GETDEVICES_LONGITUDE).toString());
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

	public Device GetDevice(String deviceId) {
		Device map = new Device();
		SoapObject rpc = new SoapObject(Consts.NAMESPACE, Consts.NOISELYNX_API_GETDEVICE_METHOD_NAME);
		rpc.addProperty("UDID", new Utils(context).getDeviceUniqueId());
		rpc.addProperty("deviceID", deviceId);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE ht = new HttpTransportSE(Consts.NOISELYNX_API_URL);
		ht.debug = true;
		try {
			ht.call(Consts.NOISELYNX_API_GETDEVICE_SOAP_ACTION, envelope);
			System.err.println(ht.responseDump);
			SoapObject result = (SoapObject) envelope.getResponse();
			map.setVersion(result.getProperty(Consts.GETDEVICES_VERSION).toString());
			map.setDeviceID(result.getProperty(Consts.GETDEVICES_DEVICEID).toString());
			map.setTemperature(result.getProperty(Consts.GETDEVICES_TEMPERATURE).toString());
			map.setHumidity(result.getProperty(Consts.GETDEVICES_HUMIDITY).toString());
			map.setVoltage(result.getProperty(Consts.GETDEVICES_VOLTAGE).toString());
			map.setInput1(result.getProperty(Consts.GETDEVICES_INPUT1).toString());
			map.setInput2(result.getProperty(Consts.GETDEVICES_INPUT2).toString());
			map.setOutput1(result.getProperty(Consts.GETDEVICES_OUTPUT1).toString());
			map.setOutput2(result.getProperty(Consts.GETDEVICES_OUTPUT2).toString());
			map.setEnableTemperature(result.getProperty(Consts.GETDEVICES_ENABLETEMPERATURE).toString());
			map.setEnableHumidity(result.getProperty(Consts.GETDEVICES_ENABLEHUMIDITY).toString());
			map.setEnableInput1(result.getProperty(Consts.GETDEVICES_ENABLEINPUT1).toString());
			map.setEnableInput2(result.getProperty(Consts.GETDEVICES_ENABLEINPUT2).toString());
			map.setEnableOutput1(result.getProperty(Consts.GETDEVICES_ENABLEOUTPUT1).toString());
			map.setEnableOutput2(result.getProperty(Consts.GETDEVICES_ENABLEOUTPUT2).toString());
			Log.e("TIME", result.getProperty(Consts.GETDEVICES_TIMESTAMP).toString());
			map.setTimestamp(result.getProperty(Consts.GETDEVICES_TIMESTAMP).toString());
			map.setDescription(result.getProperty(Consts.GETDEVICES_DESCRIPTION).toString());
			map.setDescriptionInput1(result.getProperty(Consts.GETDEVICES_DESCRIPTIONINPUT1).toString());
			map.setDescriptionInput2(result.getProperty(Consts.GETDEVICES_DESCRIPTIONINPUT2).toString());
			map.setDescriptionOutput1(result.getProperty(Consts.GETDEVICES_DESCRIPTIONOUTPUT1).toString());
			map.setDescriptionOutput2(result.getProperty(Consts.GETDEVICES_DESCRIPTIONOUTPUT2).toString());
			map.setTemperatureHi(result.getProperty(Consts.GETDEVICES_TEMPERATUREHI).toString());
			map.setTemperatureLo(result.getProperty(Consts.GETDEVICES_TEMPERATURELO).toString());
			map.setHumidityHi(result.getProperty(Consts.GETDEVICES_HUMIDITYHI).toString());
			map.setHumidityLo(result.getProperty(Consts.GETDEVICES_HUMIDITYLO).toString());
			map.setReverseLogicInput1(result.getProperty(Consts.GETDEVICES_REVERSELOGICINPUT1).toString());
			map.setReverseLogicInput2(result.getProperty(Consts.GETDEVICES_REVERSELOGICINPUT2).toString());
			map.setReverseLogicOutput1(result.getProperty(Consts.GETDEVICES_REVERSELOGICOUTPUT1).toString());
			map.setReverseLogicOutput2(result.getProperty(Consts.GETDEVICES_REVERSELOGICOUTPUT2).toString());
			map.setHumidityState(result.getProperty(Consts.GETDEVICES_HUMIDITYSTATE).toString());
			map.setTemperatureState(result.getProperty(Consts.GETDEVICES_TEMPERATURESTATE).toString());
			map.setLatitude(result.getProperty(Consts.GETDEVICES_LATITUDE).toString());
			map.setLongitude(result.getProperty(Consts.GETDEVICES_LONGITUDE).toString());

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
		return map;
	}

	public ArrayList<String> GetLocation(String deviceId) {
		ArrayList<String> map = new ArrayList<String>();
		SoapObject rpc = new SoapObject(Consts.NAMESPACE, Consts.NOISELYNX_API_GETLOCATION_METHOD_NAME);
		rpc.addProperty("UDID", new Utils(context).getDeviceUniqueId());
		rpc.addProperty("deviceID", deviceId);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE ht = new HttpTransportSE(Consts.NOISELYNX_API_URL);
		ht.debug = true;
		try {
			ht.call(Consts.NOISELYNX_API_GETLOCATION_SOAP_ACTION, envelope);
			System.err.println(ht.responseDump);
			SoapObject result = (SoapObject) envelope.getResponse();
			map.add(result.getProperty(Consts.RESULTCODE).toString());
			map.add(result.getProperty(Consts.RESULTDESCRIPTION).toString());
			map.add(result.getProperty(Consts.ERRORCODE).toString());

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
		return map;
	}
	
	public String SetOutput(String deviceId, String outputNum, String onOff) {
		Log.e("DATA", deviceId + "|" + outputNum + "|" + onOff);
		SoapObject rpc = new SoapObject(Consts.NAMESPACE, Consts.NOISELYNX_API_SETOUTPUT_METHOD_NAME);
		rpc.addProperty("UDID", new Utils(context).getDeviceUniqueId());
		rpc.addProperty("deviceID", deviceId);
		rpc.addProperty("outputNum", outputNum);
		rpc.addProperty("OnOff", onOff);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE ht = new HttpTransportSE(Consts.NOISELYNX_API_URL);
		ht.debug = true;
		try {
			// Log.e("WebRequest", "TRY!");
			ht.call(Consts.NOISELYNX_API_SETOUTPUT_SOAP_ACTION, envelope);
			System.err.println(ht.responseDump);
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
}

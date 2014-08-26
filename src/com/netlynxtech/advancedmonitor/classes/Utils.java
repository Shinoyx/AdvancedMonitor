package com.netlynxtech.advancedmonitor.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Utils {

	Context context;

	public Utils(Context con) {
		this.context = con;
	}

	public String getDeviceUniqueId() {
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String deviceId = deviceUuid.toString();
		Log.e("Unique ID", deviceId);
		return deviceId;
	}

	public String pregmatchIP(String text) {
		// String data = new UDPClass("255.255.255.255", "1025", Consts.UDP_BROADCAST_TODEVICE).run(); Pattern pattern4 =
		Pattern p = Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]" + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
				+ "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}" + "|[1-9][0-9]|[0-9]))");
		Matcher matcher = p.matcher(text);
		if (matcher.find()) {
			return matcher.group(1).trim();
		}
		return "";
	}

	public void turnOnWifi() {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// boolean wifiEnabled = wifiManager.isWifiEnabled()
		wifiManager.setWifiEnabled(true);
	}

	public ArrayList<HashMap<String, String>> getWifiDevices() {
		final ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.startScan();
		context.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context c, Intent intent) {
				final List<ScanResult> results = wifiManager.getScanResults();
				for (ScanResult n : results) {
					HashMap<String, String> item = new HashMap<String, String>();
					item.put("name", n.SSID);
					item.put("capabilities", n.capabilities);
					Log.e(n.SSID, n.capabilities);
					data.add(item);
				}
			}
		}, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		return data;
	}
}

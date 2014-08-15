package com.netlynxtech.advancedmonitor.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

public class Utils {

	Context context;

	public Utils(Context con) {
		this.context = con;
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

package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.netlynxtech.advancedmonitor.classes.Consts;
import com.netlynxtech.advancedmonitor.classes.TCPClass;
import com.netlynxtech.advancedmonitor.classes.UDPClass;

public class ChooseDeviceActivity extends Activity {
	Spinner sDeviceList, sWifi;
	WifiManager wifiManager;
	ArrayList<HashMap<String, String>> data;
	SimpleAdapter deviceAdapter;
	EditText etWifiPassword;
	Button bConnect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		data = new ArrayList<HashMap<String, String>>();
		wifiManager = (WifiManager) ChooseDeviceActivity.this.getSystemService(Context.WIFI_SERVICE);
		registerReceiver(wifiBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifiManager.startScan();
		sDeviceList = (Spinner) findViewById(R.id.sDeviceList);
		sWifi = (Spinner) findViewById(R.id.sWifi);
		etWifiPassword = (EditText) findViewById(R.id.etWifiPassword);
		bConnect = (Button) findViewById(R.id.bConnect);
		deviceAdapter = new SimpleAdapter(ChooseDeviceActivity.this, data, R.layout.spinner_device_list_item, new String[] { "name" }, new int[] { R.id.tvName });
		sDeviceList.setAdapter(deviceAdapter);
		sWifi.setAdapter(deviceAdapter);
		bConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						String wifiToConnectTo = sWifi.getSelectedItem().toString();
						String wifiToConnectToPassword = etWifiPassword.getText().toString().trim();

						if (wifiToConnectToPassword.length() > 0) {
							TCPClass tcp = new TCPClass(ChooseDeviceActivity.this, Consts.DEVICE_SOFT_ACCESS_IP, Consts.DEVICE_SOFT_ACCESS_PORT, new TCPClass.OnMessageReceived() {

								@Override
								public void messageReceived(String message) {
									Log.e("messageReceived", message);
									TCPClass.CloseConnection();
								}
							});
							TCPClass.sendDataWithString("^X|1|81396537|ZZ~");
						}

						return null;
					}
				}.execute();
			}
		});
	}

	private BroadcastReceiver wifiBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			Log.e("RECEIVED", "RECEIVED");
			final List<ScanResult> results = wifiManager.getScanResults();
			for (ScanResult n : results) {
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("name", n.SSID);
				item.put("mac", n.BSSID);
				item.put("capabilities", n.capabilities);
				Log.e(n.SSID, n.capabilities);
				data.add(item);
				deviceAdapter.notifyDataSetChanged();
			}
			unregisterReceiver(this);
		}
	};

	@Override
	protected void onPause() {
		try {
			unregisterReceiver(wifiBroadcastReceiver);
		} catch (Exception e) {
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(wifiBroadcastReceiver);
		} catch (Exception e) {
		}
		super.onDestroy();
	}

}

package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.netlynxtech.advancedmonitor.classes.Consts;
import com.netlynxtech.advancedmonitor.classes.TCPClass;
import com.netlynxtech.advancedmonitor.classes.Utils;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

public class ChooseDeviceActivity extends Activity {
	Spinner sDeviceList, sWifi;
	WifiManager wifiManager;
	ArrayList<String> data;
	ArrayAdapter<String> adapter;
	EditText etWifiPassword;
	Button bConnect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		data = new ArrayList<String>();
		wifiManager = (WifiManager) ChooseDeviceActivity.this.getSystemService(Context.WIFI_SERVICE);
		registerReceiver(wifiBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifiManager.startScan();
		sDeviceList = (Spinner) findViewById(R.id.sDeviceList);
		sWifi = (Spinner) findViewById(R.id.sWifi);
		etWifiPassword = (EditText) findViewById(R.id.etWifiPassword);
		bConnect = (Button) findViewById(R.id.bConnect);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
		sDeviceList.setEnabled(false);
		sDeviceList.setClickable(false);
		sDeviceList.setAdapter(adapter);
		sWifi.setAdapter(adapter);
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mWifi.isConnected()) {
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			Log.d("SSID", wifiInfo.getSSID());
			sDeviceList.setSelection(adapter.getPosition(wifiInfo.getSSID()));
		}
		bConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						String wifiToConnectTo = sWifi.getSelectedItem().toString();
						String wifiToConnectToPassword = etWifiPassword.getText().toString().trim();

						if (wifiToConnectToPassword.length() > 0) {
							new startConnecting().execute();
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
				data.add(n.SSID);
				adapter.notifyDataSetChanged();
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

	private class startConnecting extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog;
		boolean canEndTask = false;

		@Override
		protected void onPreExecute() {
			ChooseDeviceActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					dialog = new ProgressDialog(ChooseDeviceActivity.this);
					dialog.setMessage("Starting connection.. Please wait..");
					dialog.show();
				}
			});
		}

		@Override
		protected Void doInBackground(Void... params) {
			TCPClass tcp = new TCPClass(ChooseDeviceActivity.this, Consts.DEVICE_SOFT_ACCESS_IP, Consts.DEVICE_SOFT_ACCESS_PORT, new TCPClass.OnMessageReceived() {
				String deviceId = "";
				@Override
				public void messageReceived(String message) {
					Log.e("messageReceived", message);
					if (message.startsWith("^B|")) {
						// ^B|[Version]|[Device ID]|[User ID]|[CheckSum]~
						String[] split = message.split("\\|");
						deviceId = split[2].toString().trim();
					} else if (message.startsWith("^x|1")) {

					} else if (message.startsWith("^x|2")) {
						if (!message.equals(Consts.X_CONFIGURE_USERID_NULL)) {
							Pattern p = Pattern.compile("\\^x\\|2\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)\\|(.*?)~");
							Matcher m = p.matcher(message);
							if (m.find()){
								canEndTask = true;
								TCPClass.CloseConnection();
								Log.e("TASK", "Ending task");
							   // String res = m.toMatchResult().group(0);
							}
							
						} else {

						}

					}
				}
			});
			//new WebRequestAPI(ChooseDeviceActivity.this).RegisterDevice(new Utils(ChooseDeviceActivity.this).getDeviceUniqueId(), "51235000031111");
			//new WebRequestAPI(ChooseDeviceActivity.this).GetDevices(new Utils(ChooseDeviceActivity.this).getDeviceUniqueId());
			
			while (!canEndTask) {
				TCPClass.sendDataWithString("^B~");
				SystemClock.sleep(3000);
				TCPClass.sendDataWithString("^X|1|81396537|ZZ~");
				SystemClock.sleep(3000);
				TCPClass.sendDataWithString(String.format(Consts.X_CONFIGURE_TWO_WIFISERVER_TODEVICE, "YEN", "24AC3A4A5C", "192.168.10.8", "5090", "192.168.10.8", "5090", "ZZ"));
				SystemClock.sleep(1000);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ChooseDeviceActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					dialog.cancel();
				}
			});
		}

	}
}

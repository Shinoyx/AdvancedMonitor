package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.netlynxtech.advancedmonitor.adapters.DevicesAdapter;
import com.netlynxtech.advancedmonitor.classes.Device;
import com.netlynxtech.advancedmonitor.classes.Utils;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

public class DeviceListActivity extends ActionBarActivity{

	ArrayList<Device> devices;
	DevicesAdapter adapter;
	ListView lvDevices;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_list);
		lvDevices = (ListView) findViewById(R.id.lvDevices);
		new getDevice().execute();
	}

	private class getDevice extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			devices = new WebRequestAPI(DeviceListActivity.this).GetDevices(new Utils(DeviceListActivity.this).getDeviceUniqueId());
			adapter = new DevicesAdapter(DeviceListActivity.this, devices);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			DeviceListActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					lvDevices.setAdapter(adapter);
				}
			});
		}
		
		
		
	}
}

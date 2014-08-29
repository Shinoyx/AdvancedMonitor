package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

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
		lvDevices.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView tvDeviceId = (TextView) view.findViewById(R.id.tvDeviceId);
				startActivity(new Intent(DeviceListActivity.this, IndividualDeviceActivity.class).putExtra("deviceId", tvDeviceId.getText().toString().trim()));
			}
		});
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

package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.netlynxtech.advancedmonitor.classes.Device;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

public class IndividualDeviceActivity extends ActionBarActivity {
	String deviceId = "";
	TextView tvTemperature, tvTemperatureHi, tvTemperatureLo, tvHumidity, tvHumidityHi, tvHumidityLo, tvVoltage, tvLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		deviceId = i.getStringExtra("deviceId");
		if (deviceId.length() < 1) {
			finish();
		}
		setContentView(R.layout.activity_individual_device);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		tvTemperature = (TextView) findViewById(R.id.tvTemperature);
		tvTemperatureHi = (TextView) findViewById(R.id.tvTemperatureHi);
		tvTemperatureLo = (TextView) findViewById(R.id.tvTemperatureLo);
		tvHumidity = (TextView) findViewById(R.id.tvHumidity);
		tvHumidityHi = (TextView) findViewById(R.id.tvHumidityHi);
		tvHumidityLo = (TextView) findViewById(R.id.tvHumidityLo);
		tvVoltage = (TextView) findViewById(R.id.tvVoltage);
		tvLocation = (TextView) findViewById(R.id.tvLocation);
		new AsyncTask<Void, Void, Void>() {
			Device device = new Device();
			ArrayList<String> deviceLocation;

			@Override
			protected Void doInBackground(Void... params) {
				device = new WebRequestAPI(IndividualDeviceActivity.this).GetDevice(deviceId);
				deviceLocation = new WebRequestAPI(IndividualDeviceActivity.this).GetLocation(deviceId);

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {

				super.onPostExecute(result);
				IndividualDeviceActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						tvTemperature.setText(device.getTemperature());
						tvTemperatureHi.setText(device.getTemperatureHi());
						tvTemperatureLo.setText(device.getTemperatureLo());
						tvHumidity.setText(device.getHumidity());
						tvHumidityHi.setText(device.getHumidityHi());
						tvHumidityLo.setText(device.getHumidityLo());
						tvVoltage.setText(device.getVoltage());
						tvLocation.setText(deviceLocation.get(1));
					}
				});
			}

		}.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_individual_device, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_individual_users:
			startActivity(new Intent(IndividualDeviceActivity.this, UsersActivity.class));
			break;
		case android.R.id.home:
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

}

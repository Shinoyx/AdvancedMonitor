package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manuelpeinado.refreshactionitem.ProgressIndicatorType;
import com.manuelpeinado.refreshactionitem.RefreshActionItem;
import com.manuelpeinado.refreshactionitem.RefreshActionItem.RefreshActionListener;
import com.netlynxtech.advancedmonitor.classes.Device;
import com.netlynxtech.advancedmonitor.classes.Utils;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

public class IndividualDeviceActivity extends ActionBarActivity {
	String deviceId = "";
	TextView tvTemperature, tvTemperatureHi, tvTemperatureLo, tvHumidity, tvHumidityHi, tvHumidityLo, tvVoltage, tvLocation, tvTimestamp, tvInput1Description, tvInput2Description,
			tvOutput1Description, tvOutput2Description;
	ImageView ivInput1, ivInput2, ivOutput1, ivOutput2;
	RefreshActionItem mRefreshActionItem;

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
		tvTimestamp = (TextView) findViewById(R.id.tvTimestamp);

		tvInput1Description = (TextView) findViewById(R.id.tvInput1Description);
		tvInput2Description = (TextView) findViewById(R.id.tvInput2Description);
		tvOutput1Description = (TextView) findViewById(R.id.tvOutput1Description);
		tvOutput2Description = (TextView) findViewById(R.id.tvOutput2Description);

		ivInput1 = (ImageView) findViewById(R.id.ivInput1);
		ivInput2 = (ImageView) findViewById(R.id.ivInput2);
		ivOutput1 = (ImageView) findViewById(R.id.ivOutput1);
		ivOutput2 = (ImageView) findViewById(R.id.ivOutput2);
	}

	private class loadData extends AsyncTask<Void, Void, Void> {
		Device device = new Device();
		ArrayList<String> deviceLocation;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mRefreshActionItem.showProgress(true);
		}

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
					tvTemperature.setText(device.getTemperature() + (char) 0x00B0 + "c");
					tvTemperatureHi.setText(device.getTemperatureHi() + (char) 0x00B0 + "c");
					tvTemperatureLo.setText(device.getTemperatureLo() + (char) 0x00B0 + "c");
					tvHumidity.setText(device.getHumidity() + "%");
					tvHumidityHi.setText(device.getHumidityHi() + "%");
					tvHumidityLo.setText(device.getHumidityLo() + "%");
					tvVoltage.setText(device.getVoltage());
					tvLocation.setText(deviceLocation.get(1));
					tvInput1Description.setText(device.getDescriptionInput1());
					tvInput2Description.setText(device.getDescriptionInput2());
					tvOutput1Description.setText(device.getDescriptionOutput1());
					tvOutput2Description.setText(device.getDescriptionOutput2());
					tvTimestamp.setText(new Utils(IndividualDeviceActivity.this).parseDatetime(device.getTimestamp()));
					if (device.getEnableInput1().equals("1")) {
						if (device.getInput1().equals("1")) {
							ivInput1.setImageDrawable(getResources().getDrawable(R.drawable.ic_greendot));
						} else {
							ivInput1.setImageDrawable(getResources().getDrawable(R.drawable.ic_reddot));
						}
					} else {
						ivInput1.setImageDrawable(getResources().getDrawable(R.drawable.ic_emptydot));
					}

					if (device.getEnableInput2().equals("1")) {
						if (device.getInput2().equals("1")) {
							ivInput2.setImageDrawable(getResources().getDrawable(R.drawable.ic_greendot));
						} else {
							ivInput2.setImageDrawable(getResources().getDrawable(R.drawable.ic_reddot));
						}
					} else {
						ivInput2.setImageDrawable(getResources().getDrawable(R.drawable.ic_emptydot));
					}

					if (device.getEnableOutput1().equals("1")) {
						if (device.getOutput1().equals("1")) {
							ivOutput1.setImageDrawable(getResources().getDrawable(R.drawable.ic_greendot));
						} else {
							ivOutput1.setImageDrawable(getResources().getDrawable(R.drawable.ic_reddot));
						}
					} else {
						ivOutput1.setImageDrawable(getResources().getDrawable(R.drawable.ic_emptydot));
					}

					if (device.getEnableOutput2().equals("1")) {
						if (device.getOutput2().equals("1")) {
							ivOutput2.setImageDrawable(getResources().getDrawable(R.drawable.ic_greendot));
						} else {
							ivOutput2.setImageDrawable(getResources().getDrawable(R.drawable.ic_reddot));
						}
					} else {
						ivOutput2.setImageDrawable(getResources().getDrawable(R.drawable.ic_emptydot));
					}

					ivOutput1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							changeOutput("1", device.getOutput1());
						}
					});
					ivOutput2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							changeOutput("2", device.getOutput1());
						}
					});
					mRefreshActionItem.showProgress(false);
				}
			});
		}

	}

	private void changeOutput(final String outputNum, String currentStatus) {
		String status = "1";
		if (currentStatus.equals("1")) {
			status = "0";
		}
		final String finalStatus = status;
		new AsyncTask<String, Void, Void>() {

			String data;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mRefreshActionItem.showProgress(true);
			}

			@Override
			protected Void doInBackground(String... params) {
				data = new WebRequestAPI(IndividualDeviceActivity.this).SetOutput(deviceId, outputNum, finalStatus);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				IndividualDeviceActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						mRefreshActionItem.showProgress(false);
						if (data.startsWith("success|")) {
							if (outputNum.equals("1")) {
								if (finalStatus.equals("1")) {
									ivOutput1.setImageResource(R.drawable.ic_greendot);
								} else if (finalStatus.equals("0")) {
									ivOutput1.setImageResource(R.drawable.ic_reddot);
								}
							} else if (outputNum.equals("2")) {
								if (finalStatus.equals("1")) {
									ivOutput2.setImageResource(R.drawable.ic_greendot);
								} else if (finalStatus.equals("0")) {
									ivOutput2.setImageResource(R.drawable.ic_reddot);
								}
							}
						} else {
							Toast.makeText(IndividualDeviceActivity.this, data, Toast.LENGTH_SHORT).show();
						}
					}
				});
			}

		}.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_individual_device, menu);
		MenuItem item = menu.findItem(R.id.menu_individual_refresh);
		mRefreshActionItem = (RefreshActionItem) MenuItemCompat.getActionView(item);
		mRefreshActionItem.setMenuItem(item);
		mRefreshActionItem.setProgressIndicatorType(ProgressIndicatorType.INDETERMINATE);
		mRefreshActionItem.setRefreshActionListener(new RefreshActionListener() {

			@Override
			public void onRefreshButtonClick(RefreshActionItem sender) {
				new loadData().execute();
			}
		});
		new loadData().execute();
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

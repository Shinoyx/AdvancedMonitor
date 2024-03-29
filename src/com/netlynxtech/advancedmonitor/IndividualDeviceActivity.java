package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;
import java.util.HashMap;

import mehdi.sakout.dynamicbox.DynamicBox;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.manuelpeinado.refreshactionitem.ProgressIndicatorType;
import com.manuelpeinado.refreshactionitem.RefreshActionItem;
import com.manuelpeinado.refreshactionitem.RefreshActionItem.RefreshActionListener;
import com.netlynxtech.advancedmonitor.classes.Consts;
import com.netlynxtech.advancedmonitor.classes.Device;
import com.netlynxtech.advancedmonitor.classes.Utils;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

import de.ankri.views.Switch;

public class IndividualDeviceActivity extends ActionBarActivity {
	String deviceId = "", deviceDescription = "";
	RefreshActionItem mRefreshActionItem;
	Device device = new Device();
	AsyncTask<Void, Void, Void> task = new loadData();
	DynamicBox box;
	TextView tvDeviceId, tvDeviceDescription, tvDeviceTemperature, tvDeviceHumidity, tvDeviceVoltage, tvDeviceTimestamp, tvInputOneDescription, tvInputTwoDescription, tvOutputOneDescription,
			tvOutputTwoDescription;
	ImageView ivInputOne, ivInputTwo;
	Switch sOutputOne, sOutputTwo;
	boolean isProcessing = false, loadedBefore = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		deviceId = i.getStringExtra("deviceId");
		deviceDescription = i.getStringExtra("deviceDescription");
		if (deviceId.length() < 1) {
			finish();
		}
		device = (Device) i.getSerializableExtra("device");
		Log.e("Individual", deviceId);
		getSupportActionBar().setTitle(deviceDescription);
		setContentView(R.layout.activity_individual_device);

		RelativeLayout rlIndividualDevice = (RelativeLayout) findViewById(R.id.rlIndividualDevice);
		box = new DynamicBox(IndividualDeviceActivity.this, rlIndividualDevice);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		tvDeviceId = (TextView) findViewById(R.id.tvDeviceId);
		tvDeviceDescription = (TextView) findViewById(R.id.tvDeviceDescription);
		tvDeviceTemperature = (TextView) findViewById(R.id.tvDeviceTemperature);
		tvDeviceHumidity = (TextView) findViewById(R.id.tvDeviceHumidity);
		tvDeviceVoltage = (TextView) findViewById(R.id.tvDeviceVoltage);
		tvInputOneDescription = (TextView) findViewById(R.id.tvInputOneDescription);
		tvInputTwoDescription = (TextView) findViewById(R.id.tvInputTwoDescription);
		tvOutputOneDescription = (TextView) findViewById(R.id.tvOutputOneDescription);
		tvOutputTwoDescription = (TextView) findViewById(R.id.tvOutputTwoDescription);
		tvDeviceTimestamp = (TextView) findViewById(R.id.tvDeviceTimestamp);

		ivInputOne = (ImageView) findViewById(R.id.ivInputOne);
		ivInputTwo = (ImageView) findViewById(R.id.ivInputTwo);
		sOutputOne = (Switch) findViewById(R.id.sOutputOne);
		sOutputTwo = (Switch) findViewById(R.id.sOutputTwo);
		setData();
		new loadGraphData().execute();
		processData();
	}

	private void setData() {
		isProcessing = true;
		tvDeviceTimestamp.setText(Html.fromHtml("<b><i>" + Utils.parseTime(device.getTimestamp()) + "</b></i>"));
		tvDeviceTimestamp.setTextColor(Color.parseColor("#A4A4A4"));
		tvDeviceId.setText(device.getDeviceID());
		tvDeviceDescription.setText(device.getDescription());
		tvInputOneDescription.setText(device.getDescriptionInput1());
		tvInputTwoDescription.setText(device.getDescriptionInput2());
		tvDeviceTemperature.setText(Html.fromHtml("Temperature<br>" + "<b><i><font color='#00FF00'>" + device.getTemperature() + " " + (char) 0x00B0 + "C" + "</b></i></font>"));
		tvDeviceTemperature.setTextColor(Color.WHITE);
		float temperatureCurrent = Float.parseFloat(device.getTemperature());
		float temperatureHi = Float.parseFloat(device.getTemperatureHi());
		float temperatureLo = Float.parseFloat(device.getTemperatureLo());
		if (temperatureCurrent > temperatureHi) {
			tvDeviceTemperature.setText(Html.fromHtml("Temperature<br>" + "<b><i><font color='#FF0000'>" + device.getTemperature() + " " + (char) 0x00B0 + "C" + "</b></i></font>"));
		}
		if (temperatureCurrent < temperatureLo) {
			tvDeviceTemperature.setText(Html.fromHtml("Temperature<br>" + "<b><i><font color='#FFFF00'>" + device.getTemperature() + " " + (char) 0x00B0 + "C" + "</b></i></font>"));
		}

		tvDeviceHumidity.setText(Html.fromHtml("Humidity<br>" + "<b><i><font color='#00FF00'>" + device.getHumidity() + " %" + "</b></i></font>"));
		tvDeviceHumidity.setTextColor(Color.WHITE);
		float humidityCurrent = Float.parseFloat(device.getHumidity());
		float humidityHi = Float.parseFloat(device.getHumidityHi());
		float humidityLo = Float.parseFloat(device.getHumidityLo());
		if (humidityCurrent > humidityHi) {
			tvDeviceHumidity.setText(Html.fromHtml("Humidity<br>" + "<b><i><font color='#FF0000'>" + device.getHumidity() + " %" + "</b></i></font>"));
		}
		if (humidityCurrent < humidityLo) {
			tvDeviceHumidity.setText(Html.fromHtml("Humidity<br>" + "<b><i><font color='#FFFF00'>" + device.getHumidity() + " %" + "</b></i></font>"));
		}

		tvDeviceVoltage.setText(Html.fromHtml("Voltage<br>" + "<b><i><font color='#00FF00'>" + device.getVoltage() + " V" + "</b></i></font>"));
		tvDeviceVoltage.setTextColor(Color.WHITE);

		if (device.getEnableInput1().equals("1")) {
			if (device.getInput1().equals("1")) {
				ivInputOne.setImageDrawable(IndividualDeviceActivity.this.getResources().getDrawable(R.drawable.ic_greendot));
			} else {
				ivInputOne.setImageDrawable(IndividualDeviceActivity.this.getResources().getDrawable(R.drawable.ic_reddot));
			}
		} else {
			tvInputOneDescription.setVisibility(View.GONE);
			ivInputOne.setVisibility(View.GONE);
		}

		if (device.getEnableInput2().equals("1")) {
			if (device.getInput2().equals("1")) {
				ivInputTwo.setImageDrawable(IndividualDeviceActivity.this.getResources().getDrawable(R.drawable.ic_greendot));
			} else {
				ivInputTwo.setImageDrawable(IndividualDeviceActivity.this.getResources().getDrawable(R.drawable.ic_reddot));
			}
		} else {
			tvInputTwoDescription.setVisibility(View.GONE);
			ivInputTwo.setVisibility(View.GONE);
		}

		if (device.getEnableOutput1().equals("1")) {
			sOutputOne.setEnabled(true);
			tvOutputOneDescription.setText(device.getDescriptionOutput1().trim());
			Log.e("OUTPUT1", "INSIDE 1");
			if (device.getOutput1().equals("1")) {
				sOutputOne.setChecked(true);
			} else {
				sOutputOne.setChecked(false);
			}
		} else {
			sOutputOne.setVisibility(View.GONE);
			tvOutputOneDescription.setVisibility(View.GONE);
		}
		sOutputOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncTask<String, Void, Void>() {
					String finalStatus, data;

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						if (device.getOutput1().equals("1")) {
							finalStatus = "0";
						} else {
							finalStatus = "1";
						}
					}

					@Override
					protected Void doInBackground(String... params) {
						data = new WebRequestAPI(IndividualDeviceActivity.this).SetOutput(device.getDeviceID(), "1", finalStatus);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						if (data.startsWith("success|")) {
							if (finalStatus.equals("1")) {
								sOutputOne.setChecked(true);
								device.setOutput1("1");
							} else if (finalStatus.equals("0")) {
								sOutputOne.setChecked(false);
								device.setOutput1("0");
							}
						} else {
							if (finalStatus.equals("1")) {
								sOutputOne.setChecked(false);
							} else if (finalStatus.equals("0")) {
								sOutputOne.setChecked(true);
							}
							Toast.makeText(IndividualDeviceActivity.this, data, Toast.LENGTH_SHORT).show();
						}
					}
				}.execute();
			}
		});

		if (device.getEnableOutput2().equals("1")) {
			sOutputTwo.setEnabled(true);
			tvOutputTwoDescription.setText(device.getDescriptionOutput2().trim());
			Log.e("OUTPUT2", "INSIDE 2");
			if (device.getOutput2().equals("1")) {
				sOutputTwo.setChecked(true);
			} else {
				sOutputTwo.setChecked(false);
			}
		} else {
			sOutputTwo.setVisibility(View.GONE);
			tvOutputTwoDescription.setVisibility(View.GONE);
		}
		sOutputTwo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncTask<String, Void, Void>() {
					String finalStatus, data;

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						if (device.getOutput2().equals("1")) {
							finalStatus = "0";
						} else {
							finalStatus = "1";
						}
					}

					@Override
					protected Void doInBackground(String... params) {
						data = new WebRequestAPI(IndividualDeviceActivity.this).SetOutput(device.getDeviceID(), "2", finalStatus);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						if (data.startsWith("success|")) {
							if (finalStatus.equals("1")) {
								sOutputTwo.setChecked(true);
								device.setOutput2("1");
							} else if (finalStatus.equals("0")) {
								sOutputTwo.setChecked(false);
								device.setOutput2("0");
							}
						} else {
							if (finalStatus.equals("1")) {
								sOutputTwo.setChecked(false);
							} else if (finalStatus.equals("0")) {
								sOutputTwo.setChecked(true);
							}
							Toast.makeText(IndividualDeviceActivity.this, data, Toast.LENGTH_SHORT).show();
						}
					}
				}.execute();
			}
		});
		isProcessing = false;
		loadedBefore = true;
	}

	private void processData() {
		if (!isProcessing) {
			Log.e("PROCESSDATA", "PROCESSDATA");

			(new Thread(new Runnable() {

				@Override
				public void run() {
					if (!Thread.interrupted())
						try {
							Thread.sleep(15000);
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									if (task != null) {
										task = null;
										task = new loadData();
										task.execute();
									}
								}
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			})).start();
		}
	}

	private class loadGraphData extends AsyncTask<Void, Void, Void> {
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		ArrayList<Double> temperature = new ArrayList<Double>();
		ArrayList<Double> humidity = new ArrayList<Double>();
		ArrayList<String> timing = new ArrayList<String>();
		int num = 12;

		@Override
		protected Void doInBackground(Void... params) {
			// data = new WebRequestAPI(IndividualDeviceActivity.this).GetChartData(deviceId, Utils.getCustomDateTime(), Utils.getCurrentDateTime(), 12);
			data = new WebRequestAPI(IndividualDeviceActivity.this).GetChartData(deviceId, Utils.getCurrentDateTime(), Utils.getCustomDateTime(), 12);
			if (data.size() > 0) {
				for (HashMap<String, String> d : data) {
					temperature.add(Double.parseDouble(d.get(Consts.GETDEVICES_TEMPERATURE)));
					// temperature.add(Double.parseDouble("1.1"));
					humidity.add(Double.parseDouble(d.get(Consts.GETDEVICES_HUMIDITY)));
					timing.add(d.get(Consts.GETDEVICES_DATATIMESTAMP));
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			IndividualDeviceActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (data.size() > 0) {
						int num = 12;
						GraphViewData[] tempData = new GraphViewData[num];
						GraphViewData[] humidData = new GraphViewData[num];
						String[] timeData = new String[num];
						for (int i = 0; i < num; i++) {
							tempData[i] = new GraphViewData(i, temperature.get(i));
							humidData[i] = new GraphViewData(i, humidity.get(i));
							timeData[i] = timing.get(i);
							Log.e("HERE?!", timing.get(i));
						}

						GraphViewSeries tempGraph = new GraphViewSeries("Temperature", new GraphViewSeriesStyle(Color.rgb(200, 50, 00), 3), tempData);
						GraphViewSeries humidGraph = new GraphViewSeries("Humidity", new GraphViewSeriesStyle(Color.rgb(90, 250, 00), 3), humidData);

						GraphView graphView = new LineGraphView(IndividualDeviceActivity.this, "History");

						graphView.addSeries(tempGraph);
						graphView.addSeries(humidGraph);
						// optional - set view port, start=2, size=10
						graphView.setViewPort(2, 10);
						graphView.setScalable(true);
						// optional - legend
						graphView.setShowLegend(true);
						graphView.setLegendAlign(LegendAlign.BOTTOM);
						graphView.setLegendWidth(210);
						graphView.setHorizontalLabels(timeData);
						RelativeLayout llIndividualDevice = (RelativeLayout) findViewById(R.id.rlIndividualDevice);

						RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

						p.addRule(RelativeLayout.BELOW, R.id.rl_device_details);

						graphView.setLayoutParams(p);
						llIndividualDevice.addView(graphView);
						graphView.setLayoutParams(p);
					} else {
						Toast.makeText(IndividualDeviceActivity.this, "Unable to load graph", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}

	}

	private class loadData extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isProcessing = true;
			mRefreshActionItem.showProgress(true);
			if (!loadedBefore) {
				box.showLoadingLayout();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			device = new WebRequestAPI(IndividualDeviceActivity.this).GetDevice(deviceId);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			IndividualDeviceActivity.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (!loadedBefore) {
						box.hideAll();
						loadedBefore = true;
					}
					mRefreshActionItem.showProgress(false);
					isProcessing = false;
					if (device != null && device.getDescription() != null && device.getDescription().length() > 0) {
						setData();
					}
					processData();
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
						box.hideAll();
						/*
						 * if (data.startsWith("success|")) { if (outputNum.equals("1")) { if (finalStatus.equals("1")) { ivOutput1.setImageResource(R.drawable.ic_greendot); device.setOutput1("1"); }
						 * else if (finalStatus.equals("0")) { ivOutput1.setImageResource(R.drawable.ic_reddot); device.setOutput1("0"); } } else if (outputNum.equals("2")) { if
						 * (finalStatus.equals("1")) { device.setOutput2("1"); ivOutput2.setImageResource(R.drawable.ic_greendot); } else if (finalStatus.equals("0")) { device.setOutput2("0");
						 * ivOutput2.setImageResource(R.drawable.ic_reddot); } } } else { Toast.makeText(IndividualDeviceActivity.this, data, Toast.LENGTH_SHORT).show(); }
						 */
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
				if (task != null) {
					task = null;
					task = new loadData();
					task.execute();
				}
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_individual_users:
			startActivity(new Intent(IndividualDeviceActivity.this, UsersActivity.class).putExtra("deviceId", device.getDeviceID()));
			break;
		case android.R.id.home:
			finish();
			break;
		case R.id.menu_edit_device:
			Intent i = new Intent(IndividualDeviceActivity.this, IndividualDeviceEditDetailsActivity.class);
			i.putExtra("deviceId", deviceId);
			i.putExtra("deviceDescription", device.getDescription());
			if (device.getEnableInput1().equals("1")) {
				i.putExtra("input1", device.getDescriptionInput1());
			}
			if (device.getEnableInput2().equals("1")) {
				i.putExtra("input2", device.getDescriptionInput2());
			}
			if (device.getEnableOutput1().equals("1")) {
				i.putExtra("output1", device.getDescriptionOutput1());
			}
			if (device.getEnableOutput2().equals("1")) {
				i.putExtra("output2", device.getDescriptionOutput2());
			}
			startActivity(i);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e("RESUME", "RESUME");
		try {
			if (task != null) {
				task.execute();
			} else {
				task = new loadData();
				task.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (task != null) {
			task.cancel(true);
			task = null;
		}
	}
}

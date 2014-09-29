package com.netlynxtech.advancedmonitor.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.netlynxtech.advancedmonitor.R;
import com.netlynxtech.advancedmonitor.classes.Device;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

public class DevicesAdapter extends BaseAdapter {
	Context context;
	ArrayList<Device> data;
	private static LayoutInflater inflater = null;

	public DevicesAdapter(Context context, ArrayList<Device> data) {
		this.context = context;
		this.data = data;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView tvDeviceId;
		TextView tvDeviceDescription;
		TextView tvDeviceTemperature;
		TextView tvDeviceHumidity;
		TextView tvDeviceVoltage;
		TextView tvDeviceTimestamp;
		TextView tvInputOneDescription;
		TextView tvInputTwoDescription;
		ImageView ivInputOne;
		ImageView ivInputTwo;
		ToggleButton tOutputOne;
		ToggleButton tOutputTwo;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.device_list_item, parent, false);
			holder = new ViewHolder();
			holder.tvDeviceId = (TextView) convertView.findViewById(R.id.tvDeviceId);
			holder.tvDeviceDescription = (TextView) convertView.findViewById(R.id.tvDeviceDescription);
			holder.tvDeviceTemperature = (TextView) convertView.findViewById(R.id.tvDeviceTemperature);
			holder.tvDeviceHumidity = (TextView) convertView.findViewById(R.id.tvDeviceHumidity);
			holder.tvDeviceVoltage = (TextView) convertView.findViewById(R.id.tvDeviceVoltage);
			holder.tvInputOneDescription = (TextView) convertView.findViewById(R.id.tvInputOneDescription);
			holder.tvInputTwoDescription = (TextView) convertView.findViewById(R.id.tvInputTwoDescription);
			holder.tvDeviceTimestamp = (TextView) convertView.findViewById(R.id.tvDeviceTimestamp);

			holder.ivInputOne = (ImageView) convertView.findViewById(R.id.ivInputOne);
			holder.ivInputTwo = (ImageView) convertView.findViewById(R.id.ivInputTwo);
			holder.tOutputOne = (ToggleButton) convertView.findViewById(R.id.tOutputOne);
			holder.tOutputTwo = (ToggleButton) convertView.findViewById(R.id.tOutputTwo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Device item = data.get(position);
		holder.tvDeviceTimestamp.setText(item.getTimestamp());
		holder.tvDeviceId.setText(item.getDeviceID());
		holder.tvDeviceDescription.setText(item.getDescription());
		holder.tvInputOneDescription.setText(item.getDescriptionInput1());
		holder.tvInputTwoDescription.setText(item.getDescriptionInput2());
		holder.tvDeviceTemperature.setText(item.getTemperature() + (char) 0x00B0 + "c");
		holder.tvDeviceHumidity.setText(item.getHumidity() + "%");
		holder.tvDeviceVoltage.setText(item.getVoltage() + "V");
		if (item.getEnableInput1().equals("1")) {
			if (item.getInput1().equals("1")) {
				holder.ivInputOne.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_greendot));
			} else {
				holder.ivInputOne.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_reddot));
			}
		} else {
			holder.ivInputOne.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_emptydot));
		}

		if (item.getEnableInput2().equals("1")) {
			if (item.getInput2().equals("1")) {
				holder.ivInputTwo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_greendot));
			} else {
				holder.ivInputTwo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_reddot));
			}
		} else {
			holder.ivInputTwo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_emptydot));
		}

		if (item.getEnableOutput1().equals("1")) {
			holder.tOutputOne.setEnabled(true);
			holder.tOutputOne.setText(item.getDescriptionOutput1());
			Log.e("OUTPUT1", "INSIDE 1");
			if (item.getOutput1().equals("1")) {
				holder.tOutputOne.setChecked(true);
			} else {
				holder.tOutputOne.setChecked(false);
			}
		} else {
			holder.tOutputOne.setText("");
			holder.tOutputOne.setEnabled(false);
		}
		holder.tOutputOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncTask<String, Void, Void>() {
					String finalStatus, data;

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
						if (item.getOutput1().equals("1")) {
							finalStatus = "0";
						} else {
							finalStatus = "1";
						}
					}

					@Override
					protected Void doInBackground(String... params) {
						data = new WebRequestAPI(context).SetOutput(item.getDeviceID(), "1", finalStatus);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						if (data.startsWith("success|")) {
							if (finalStatus.equals("1")) {
								holder.tOutputOne.setChecked(true);
								item.setOutput1("1");
							} else if (finalStatus.equals("0")) {
								holder.tOutputOne.setChecked(false);
								item.setOutput1("0");
							}
						} else {
							Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
						}
					}
				}.execute();
			}
		});

		if (item.getEnableOutput2().equals("1")) {
			holder.tOutputTwo.setEnabled(true);
			holder.tOutputTwo.setText(item.getDescriptionOutput2());
			Log.e("OUTPUT2", "INSIDE 2");
			if (item.getOutput2().equals("1")) {
				holder.tOutputTwo.setChecked(true);
			} else {
				holder.tOutputTwo.setChecked(false);
			}
		} else {
			holder.tOutputTwo.setText("");
			holder.tOutputTwo.setEnabled(false);
		}
		return convertView;
	}
}

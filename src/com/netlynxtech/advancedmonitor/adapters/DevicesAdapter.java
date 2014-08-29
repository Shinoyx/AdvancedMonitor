package com.netlynxtech.advancedmonitor.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netlynxtech.advancedmonitor.R;
import com.netlynxtech.advancedmonitor.classes.Device;

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
		TextView tvDescription;
		TextView tvTemperature;
		TextView tvHumidity;
		TextView tvVoltage;
		ImageView ivInput1;
		ImageView ivInput2;
		ImageView ivOutput1;
		ImageView ivOutput2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.device_list_item, parent, false);
			holder = new ViewHolder();
			holder.tvDeviceId = (TextView) convertView.findViewById(R.id.tvDeviceId);
			holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
			holder.tvTemperature = (TextView) convertView.findViewById(R.id.tvTemperature);
			holder.tvHumidity = (TextView) convertView.findViewById(R.id.tvHumidity);
			holder.tvVoltage = (TextView) convertView.findViewById(R.id.tvVoltage);
			
			holder.ivInput1 = (ImageView) convertView.findViewById(R.id.ivInput1);
			holder.ivInput2 = (ImageView) convertView.findViewById(R.id.ivInput2);
			holder.ivOutput1 = (ImageView) convertView.findViewById(R.id.ivOutput1);
			holder.ivOutput2 = (ImageView) convertView.findViewById(R.id.ivOutput2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Device item = new Device();
		item = data.get(position);
		holder.tvDeviceId.setText(item.getDeviceID());
		holder.tvDescription.setText(item.getDescription());
		holder.tvTemperature.setText(item.getTemperature() + (char) 0x00B0 + "c");
		holder.tvHumidity.setText(item.getHumidity() + "%");
		holder.tvVoltage.setText(item.getVoltage() + "V");
		if (item.getEnableInput1().equals("0")) {
			holder.ivInput1.setBackgroundResource(R.drawable.ic_reddot);
		} else {
			holder.ivInput1.setBackgroundResource(R.drawable.ic_greendot);
		}
		
		if (item.getEnableInput2().equals("0")) {
			holder.ivInput2.setBackgroundResource(R.drawable.ic_reddot);
		} else {
			holder.ivInput2.setBackgroundResource(R.drawable.ic_greendot);
		}
		
		if (item.getEnableOutput1().equals("0")) {
			holder.ivOutput1.setBackgroundResource(R.drawable.ic_reddot);
		} else {
			holder.ivOutput1.setBackgroundResource(R.drawable.ic_greendot);
		}
		
		if (item.getEnableOutput2().equals("0")) {
			holder.ivOutput2.setBackgroundResource(R.drawable.ic_reddot);
		} else {
			holder.ivOutput2.setBackgroundResource(R.drawable.ic_greendot);
		}
		return convertView;
	}
}

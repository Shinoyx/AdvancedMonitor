package com.netlynxtech.advancedmonitor.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.netlynxtech.advancedmonitor.R;
import com.netlynxtech.advancedmonitor.classes.Consts;
import com.netlynxtech.advancedmonitor.classes.Device;
import com.netlynxtech.advancedmonitor.classes.Utils;

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
		TextView tvDescription;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.device_list_item, parent, false);
			holder = new ViewHolder();
			holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Device item = new Device();
		item = data.get(position);
		holder.tvDescription.setText(item.getDescription());
		
		return convertView;
	}
}

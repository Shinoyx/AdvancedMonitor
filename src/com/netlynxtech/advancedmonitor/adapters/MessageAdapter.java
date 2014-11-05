package com.netlynxtech.advancedmonitor.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.netlynxtech.advancedmonitor.R;
import com.netlynxtech.advancedmonitor.classes.DeviceMembers;
import com.netlynxtech.advancedmonitor.classes.Message;
import com.netlynxtech.advancedmonitor.classes.Utils;
import com.netlynxtech.advancedmonitor.classes.WebRequestAPI;

public class MessageAdapter extends BaseAdapter {
	Context context;
	ArrayList<Message> data;
	private static LayoutInflater inflater = null;
	
	public MessageAdapter(Context context, ArrayList<Message> data) {
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
		TextView tvTitle;
		TextView tvTimestamp;
		TextView tvMessage;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_message_item, parent, false);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
			holder.tvMessage = (TextView) convertView.findViewById(R.id.tvMessage);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Message d = data.get(position);
		// String udid = d.getUdid();
		holder.tvTitle.setText(d.getTitle());
		holder.tvTimestamp.setText(Utils.parseTime(d.getTimestamp()));
		holder.tvMessage.setText(d.getMessage());
		return convertView;

	}

}

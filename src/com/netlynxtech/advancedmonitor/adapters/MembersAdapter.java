package com.netlynxtech.advancedmonitor.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netlynxtech.advancedmonitor.R;
import com.netlynxtech.advancedmonitor.classes.DeviceMembers;
import com.netlynxtech.advancedmonitor.classes.Utils;

public class MembersAdapter extends BaseAdapter {
	Context context;
	ArrayList<DeviceMembers> data;
	private static LayoutInflater inflater = null;

	public MembersAdapter(Context context, ArrayList<DeviceMembers> data) {
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
		TextView tvName;
		TextView tvTimestamp;
		TextView tvRequestStatus;
		TextView tvRole;
		ImageView ivDeleteUser;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_users_item, parent, false);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
			holder.tvRequestStatus = (TextView) convertView.findViewById(R.id.tvRequestStatus);
			holder.tvRole = (TextView) convertView.findViewById(R.id.tvRole);
			holder.ivDeleteUser = (ImageView) convertView.findViewById(R.id.ivDeleteUser);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final DeviceMembers d = data.get(position);
		holder.tvName.setText(d.getName());
		if (d.getRequestStatus().equals("0")) {
			holder.tvRequestStatus.setText("Pending");
		} else if (d.getRequestStatus().equals("1")) {
			holder.tvRequestStatus.setText("Accepted");
		} else {
			holder.tvRequestStatus.setText("Unknown");
		}

		holder.tvTimestamp.setText(Utils.parseTime(d.getUpdateTimestamp()));
		if (d.getRole().equals("9")) {
			holder.tvRole.setText("Administrator");
		} else if (d.getRole().equals("2")) {
			holder.tvRole.setText("Controller");
		} else if (d.getRole().equals("1")) {
			holder.tvRole.setText("Viewer");
		} else {
			holder.tvRole.setText("No Access");
		}
		holder.ivDeleteUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		return convertView;
	}

}

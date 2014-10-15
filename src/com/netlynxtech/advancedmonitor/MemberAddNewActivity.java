package com.netlynxtech.advancedmonitor;

import java.util.ArrayList;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.netlynxtech.advancedmonitor.classes.Device;

public class MemberAddNewActivity extends ActionBarActivity {
	ArrayList<Device> devices;
	LinearLayout ll_devices_checkboxes;
	Spinner sRoles;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_add_new);
		sRoles = (Spinner) findViewById(R.id.sRoles);
		
		Bundle information = getIntent().getExtras();
		devices = (ArrayList<Device>) getIntent().getSerializableExtra("devices");
		ll_devices_checkboxes = (LinearLayout) findViewById(R.id.ll_devices_checkboxes);
		for (Device d : devices) {
			CheckBox cb = new CheckBox(this);
			cb.setText(d.getDescription());
			ll_devices_checkboxes.addView(cb);
		}
		Button b = new Button(this);
		b.setText("Send Request");
		ll_devices_checkboxes.addView(b);
		
		Resources res = getResources();
		final TypedArray selectedValues = res
		        .obtainTypedArray(R.array.roles_array_value);

		sRoles.setOnItemSelectedListener(new OnItemSelectedListener() {

		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view,
		            int position, long id) {
		        //Get the selected value
		        int selectedValue = selectedValues.getInt(position, -1);
		        Log.d("demo", "selectedValues = " + selectedValue);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> arg0) {
		        // TODO Auto-generated method stub

		    }
		});
	}

}

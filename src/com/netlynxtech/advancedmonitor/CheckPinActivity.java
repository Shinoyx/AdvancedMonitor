package com.netlynxtech.advancedmonitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.netlynxtech.advancedmonitor.classes.Consts;
import com.netlynxtech.advancedmonitor.classes.ProgressGenerator;
import com.netlynxtech.advancedmonitor.classes.ProgressGenerator.OnCompleteListener;
import com.netlynxtech.advancedmonitor.classes.Utils;

public class CheckPinActivity extends ActionBarActivity {
	GoogleCloudMessaging gcm;
	String GCM_register_ID = "";

	EditText etPinNo;
	ActionProcessButton bCheckPin;
	TextView tvError, tvGCMID, tvStatusDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getSupportActionBar().setTitle(CheckPinActivity.this.getResources().getString(R.string.check_pin_name));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_pin_activity);
		tvError = (TextView) findViewById(R.id.tvError);
		etPinNo = (EditText) findViewById(R.id.etPinNo);
		tvGCMID = (TextView) findViewById(R.id.tvGCMID);
		tvStatusDesc = (TextView) findViewById(R.id.tvStatusDesc);
		bCheckPin = (ActionProcessButton) findViewById(R.id.bCheckPin);
		bCheckPin.setMode(ActionProcessButton.Mode.ENDLESS);
		etPinNo.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().length() > 0) {
					bCheckPin.setEnabled(true);
				} else {
					bCheckPin.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		final ProgressGenerator progressGenerator = new ProgressGenerator(new OnCompleteListener() {

			@Override
			public void onComplete() {
				if (!bCheckPin.getText().toString().equals("Success")) {
					tvError.setText(bCheckPin.getText().toString());
					etPinNo.setEnabled(true);
					bCheckPin.setEnabled(true);
					bCheckPin.setProgress(0);
					tvError.setVisibility(View.VISIBLE);
				} else {
					new Utils(CheckPinActivity.this).setGCMID(GCM_register_ID);
					//startActivity(new Intent(CheckPinActivity.this, PasswordActivity.class).putExtra("message", tvStatusDesc.getText().toString()));
					//finish();
				}
			}
		});

		bCheckPin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Log.e("Device ID", new Utils(CheckPinActivity.this).getDeviceId());
				if (GCM_register_ID.length() < 1) {
					//Log.e("Button", "No GCM ID. Cannot continue");
					tvError.setVisibility(View.VISIBLE);
					tvError.setText(CheckPinActivity.this.getString(R.string.gcm_id_retrieved_failed));
				} else {
					if (etPinNo.getText().toString().length() > 0) {
						etPinNo.setEnabled(false);
						bCheckPin.setEnabled(false);
						progressGenerator.checkPin(bCheckPin, etPinNo.getText().toString(), GCM_register_ID, tvStatusDesc, CheckPinActivity.this);
					} else {
						tvError.setVisibility(View.VISIBLE);
						tvError.setText("PIN do not match");
					}
				}
			}
		});
		getGCMID();
	}

	public void retryGetGCMID() {
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			public void run() {
				getGCMID();
			}
		}, 5000);
	}

	public void getGCMID() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				CheckPinActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						tvError.setVisibility(View.VISIBLE);
						if (GCM_register_ID.length() > 0) {
							tvGCMID.setVisibility(View.GONE);
							tvError.setText(CheckPinActivity.this.getString(R.string.gcm_id_retrieved_successful));
						} else {
							tvGCMID.setText(CheckPinActivity.this.getString(R.string.gcm_id_retrieved_retrying));
							tvError.setText(CheckPinActivity.this.getString(R.string.gcm_id_retrieved_failed));
							retryGetGCMID();
						}
					}
				});
			}

			@Override
			protected Void doInBackground(Void... params) {
				try {
					new Utils(CheckPinActivity.this).getDeviceId();
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					}
					GCM_register_ID = gcm.register(Consts.PROJECT_NUMBER);
					Log.e("RegisteredID", GCM_register_ID);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}.execute(null, null, null);
	}

}

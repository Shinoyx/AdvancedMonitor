package com.netlynxtech.advancedmonitor;

import android.os.AsyncTask;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.netlynxtech.advancedmonitor.classes.UDPClass;

public class MainActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new tryConnect().execute();

	}

	private class tryConnect extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TCPClass tcp = new TCPClass(MainActivity.this, "255.255.255.255", "1025");
			// TCPClass.sendDataWithString(Consts.UDP_BROADCAST_TODEVICE);

			new UDPClass("255.255.255.255", "1025", "HELLO NT").start();
			return null;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
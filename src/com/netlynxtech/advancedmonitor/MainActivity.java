package com.netlynxtech.advancedmonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.netlynxtech.advancedmonitor.classes.TCPClass;
import com.netlynxtech.advancedmonitor.classes.TCPClient;

public class MainActivity extends SherlockActivity {

	private TCPClient mTcpClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new tryConnect().execute();
/*
		new connectTask().execute();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {

				if (mTcpClient != null) {
					Log.e("Send", "Sending command");
					mTcpClient.sendMessage("^R|00|ZZ~");
				}
			}
		}, 5000);*/

	}

	private class tryConnect extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			TCPClass tcp  = new TCPClass(MainActivity.this, "192.168.4.1", "9012");
			TCPClass.sendDataWithString("^R|00|ZZ~");
			return null;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		// disconnect
		mTcpClient.stopClient();
		mTcpClient = null;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class connectTask extends AsyncTask<String, String, TCPClient> {

		@Override
		protected void onPostExecute(TCPClient result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("ONPOSTEXECUTE", "ONPOSTEXECUTE");
		}

		@Override
		protected TCPClient doInBackground(String... message) {
			// we create a TCPClient object and
			mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
				@Override
				// here the messageReceived method is implemented
				public void messageReceived(String message) {
					// this method calls the onProgressUpdate
					Log.e("SERVER", message);
					publishProgress(message);
				}
			});
			mTcpClient.run();

			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			Log.e("RESULT", values[0]);
		}
	}
}
package com.netlynxtech.advancedmonitor.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;

public class TCPClient {

	public static final String SERVER_IP = "192.168.10.95"; // your computer IP address
	public static final int SERVER_PORT = 9012;
	// message to send to the server
	private String mServerMessage;
	// sends message received notifications
	private OnMessageReceived mMessageListener;
	// while this is true, the server will continue running
	private boolean mRun = false;
	// used to send messages
	private PrintWriter mBufferOut;
	// used to read messages from the server
	private BufferedReader mBufferIn;

	/**
	 * Constructor of the class. OnMessagedReceived listens for the messages received from server
	 */
	public TCPClient(OnMessageReceived listener) {
		mMessageListener = listener;
	}

	/**
	 * Sends the message entered by client to the server
	 * 
	 * @param message
	 *            text entered by client
	 */
	public void sendMessage(String message) {
		if (mBufferOut != null && !mBufferOut.checkError()) {
			// mBufferOut.print(message);
			mBufferOut.write(message);
			mBufferOut.flush();
			Log.e("Message", "Flushed");
		}
	}

	/**
	 * Close the connection and release the members
	 */
	public void stopClient() {

		// send mesage that we are closing the connection
		sendMessage(Consts.CLOSED_CONNECTION + "Kazy");

		mRun = false;

		if (mBufferOut != null) {
			mBufferOut.flush();
			mBufferOut.close();
		}

		mMessageListener = null;
		mBufferIn = null;
		mBufferOut = null;
		mServerMessage = null;
	}

	public void run() {

		mRun = true;

		try {
			// here you must put your computer's IP address.
			InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

			Log.e("TCP Client", "C: Connecting..." + serverAddr.toString());

			// create a socket to make the connection with the server
			Socket socket = new Socket(SERVER_IP, SERVER_PORT);

			try {

				// sends the message to the server
				mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				// mBufferOut = new PrintWriter(socket.getOutputStream());

				// receives the message which the server sends back
				mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// send login name
				Log.e("TCP Client", "Connected");
				sendMessage("^R|00|ZZ~");

				// in this while the client listens for the messages sent by the server
				while (mRun) {
					try {
						Log.e("Server Message", mBufferIn.readLine());
						mServerMessage = mBufferIn.readLine();
						Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + mServerMessage + "'");
						if (mServerMessage != null && mMessageListener != null) {
							// call the method messageReceived from MyActivity class
							mMessageListener.messageReceived(mServerMessage);
							mRun = false;
							break;
						}
					} catch (Exception e) {

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("TCP", "S: Error", e);
				mRun = false;
			} finally {
				Log.e("CLOSING", "SOCKET CLOSING");
				mRun = false;
				// the socket must be closed. It is not possible to reconnect to this socket
				// after it is closed, which means a new socket instance has to be created.
				socket.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("TCP", "C: Error", e);
			mRun = false;
		}

	}

	// Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
	// class at on asynckTask doInBackground
	public interface OnMessageReceived {
		public void messageReceived(String message);
	}
}

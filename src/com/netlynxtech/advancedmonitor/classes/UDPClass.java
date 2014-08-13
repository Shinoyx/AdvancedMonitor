package com.netlynxtech.advancedmonitor.classes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.util.Log;

public class UDPClass extends Thread {
	private static final String TAG = "Discovery";

	private static final int DISCOVERY_PORT = 1025;
	private static final int TIMEOUT_MS = 999000;

	private WifiManager mWifi;

	interface DiscoveryReceiver {
		void addAnnouncedServers(InetAddress[] host, int port[]);
	}

	public UDPClass(WifiManager wifi) {
		mWifi = wifi;
	}

	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT);
			socket.setBroadcast(true);
			socket.setSoTimeout(TIMEOUT_MS);

			sendDiscoveryRequest(socket);
			listenForResponses(socket);
		} catch (IOException e) {
			Log.e(TAG, "Could not send discovery request", e);
		}
	}

	/**
	 * Send a broadcast UDP packet containing a request for boxee services to announce themselves.
	 * 
	 * @throws IOException
	 */
	private void sendDiscoveryRequest(DatagramSocket socket) throws IOException {
		String data = "HELLO NT";
		Log.d(TAG, "Sending data " + data);

		DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), getBroadcastAddress(), DISCOVERY_PORT);
		socket.send(packet);
	}

	/**
	 * Calculate the broadcast IP we need to send the packet along. If we send it to 255.255.255.255, it never gets sent. I guess this has something to do with the mobile network not wanting to do
	 * broadcast.
	 */
	private InetAddress getBroadcastAddress() throws IOException {
		DhcpInfo dhcp = mWifi.getDhcpInfo();
		if (dhcp == null) {
			Log.d(TAG, "Could not get dhcp info");
			return null;
		}

		int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		Log.e(TAG, InetAddress.getByName("255.255.255.255").toString());
		return InetAddress.getByName("255.255.255.255");
		// return InetAddress.getByAddress(quads);
	}

	/**
	 * Listen on socket for responses, timing out after TIMEOUT_MS
	 * 
	 * @param socket
	 *            socket on which the announcement request was sent
	 * @throws IOException
	 */
	private void listenForResponses(DatagramSocket socket) throws IOException {
		byte[] buf = new byte[1024];
		try {
			while (true) {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				String s = new String(packet.getData(), 0, packet.getLength());
				Log.d(TAG, "Received response " + s);
			}
		} catch (SocketTimeoutException e) {
			Log.d(TAG, "Receive timed out");
		}
	}
}
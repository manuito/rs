package com.elecomte.rs;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import org.junit.Test;

public class TestStack {

	public static final Vector<RemoteDevice> devicesDiscovered = new Vector<>();

	@Test
	public void test() throws InterruptedException, BluetoothStateException {

		final Object inquiryCompletedEvent = new Object();

		devicesDiscovered.clear();

		DiscoveryListener listener = new DiscoveryListener() {

			@Override
			public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
				System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
				devicesDiscovered.addElement(btDevice);
				try {
					System.out.println("     name " + btDevice.getFriendlyName(false));
				} catch (IOException cantGetDeviceName) {
					// Empty
				}
			}

			@Override
			public void inquiryCompleted(int discType) {
				System.out.println("Device Inquiry completed!");
				synchronized (inquiryCompletedEvent) {
					inquiryCompletedEvent.notifyAll();
				}
			}

			@Override
			public void serviceSearchCompleted(int transID, int respCode) {
				// Empty
			}

			@Override
			public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
				// Empty
			}
		};

		synchronized (inquiryCompletedEvent) {
			
			boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent()
					.startInquiry(DiscoveryAgent.GIAC, listener);
			
			if (started) {
				System.out.println("wait for device inquiry to complete...");
				inquiryCompletedEvent.wait();
				System.out.println(devicesDiscovered.size() + " device(s) found");
			}
		}

		fail("Not yet implemented");
	}

}

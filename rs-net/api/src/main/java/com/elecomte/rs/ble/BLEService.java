package com.elecomte.rs.ble;

import java.util.List;

public interface BLEService {

	boolean isReady();
	
	/**
	 * List available devices on specified BLE interface
	 */
	List<DeviceIdentifier> listDevices();

	DeviceSpec getDeviceSpec(DeviceIdentifier id);

	void setDeviceCharacteristic(DeviceIdentifier id, CharacteristicIdentifier identifier, String value);

	String getDeviceCharacteristic(DeviceIdentifier id, CharacteristicIdentifier identifier);
}

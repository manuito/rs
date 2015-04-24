package com.elecomte.rs.ble;

import java.util.List;
import java.util.function.Consumer;

public interface BLEService {

	boolean isReady();

	/**
	 * List available devices on specified BLE interface
	 */
	List<DeviceIdentifier> listDevices();

	DeviceSpec getDeviceSpec(DeviceIdentifier id);

	void setDeviceCharacteristic(DeviceIdentifier id, CharacteristicIdentifier identifier, String value);

	void addDeviceCharacteristicListener(DeviceIdentifier id, CharacteristicIdentifier charId,
			Consumer<String> listener);

	String getDeviceCharacteristic(DeviceIdentifier id, CharacteristicIdentifier identifier);
}
